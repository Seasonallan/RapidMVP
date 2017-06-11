package com.season.rapiddevelopment.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.season.example.entry.ClientKey;
import com.season.rapiddevelopment.BaseApplication;
import com.season.rapiddevelopment.DNS;
import com.season.rapiddevelopment.tools.Console;
import com.season.rapiddevelopment.tools.PkgManagerUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 21:15
 */
public class BaseNetModel {

    private Context mContext;
    protected Retrofit mRetrofit;

    public BaseNetModel() {
        mContext = BaseApplication.sContext;
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ParamsInterceptor())
                .retryOnConnectionFailure(true)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(ConverterAesJsonFactory.create())//解析方法
                        //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(DNS.BASE_URL)//主机地址
                .build();
    }

    /**
     * 返回数据解密Convert
     */
    public static class ConverterAesJsonFactory extends Converter.Factory {
        private final Gson gson;

        public static ConverterAesJsonFactory create() {
            return create(new Gson());
        }

        public static ConverterAesJsonFactory create(Gson gson) {
            return new ConverterAesJsonFactory(gson);
        }


        private ConverterAesJsonFactory(Gson gson) {
            if (gson == null) throw new NullPointerException("gson == null");
            this.gson = gson;
        }

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                                Annotation[] annotations,
                                                                Retrofit retrofit) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new DecodeResponseBodyConverter(adapter);
        }

    }


    public static class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final TypeAdapter<T> adapter;

        DecodeResponseBodyConverter(TypeAdapter<T> adapter) {
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody responseBody) throws IOException {
            String response = responseBody.string();
            Console.log("服务器数据：" + response);
            String result = desEncrypt(response);//解密
            Console.log("解密的服务器数据：" + result);
            return adapter.fromJson(result);
        }
    }


    public static String desEncrypt(String data) {
        try {
            String key = "e25f00187e4389ad";
            String iv = "568ae5cfa410e9d3";

            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);

            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 参数加密过滤器
     */
    public class ParamsInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request requestBeforeHeader = chain.request();
            Request orgRequest = requestBeforeHeader.newBuilder().addHeader("User-Agent", "TTBaoXiao/" + PkgManagerUtil.getApkVersionName(mContext) + " Android/" + PkgManagerUtil.getSystemMessage()).build();
            if (orgRequest.method() == "POST") {
                RequestBody requestBody = orgRequest.body();
                if (requestBody instanceof FormBody) {
                    FormBody body = (FormBody) requestBody;
                    FormBody.Builder builder = new FormBody.Builder();

                    HashMap<String, String> params = new HashMap<>();
                    builder.add("os_type", 2 + "");
                    params.put("os_type", 2 + "");

                    builder.add("device_type", 3 + "");
                    params.put("device_type", 3 + "");

                    builder.add("client_id", ClientKey.getClientKey().client_id);
                    params.put("client_id", ClientKey.getClientKey().client_id);

                    builder.add("tms", System.currentTimeMillis() + "");
                    params.put("tms", System.currentTimeMillis() + "");
                    //添加原请求体
                    for (int i = 0; i < body.size(); i++) {
                        builder.addEncoded(body.encodedName(i), body.encodedValue(i));
                        params.put(body.encodedName(i), body.encodedValue(i));
                    }

                    String[] keys = new String[body.size()];
                    int position = 0;
                    Set<Map.Entry<String, String>> set = params.entrySet();
                    for (Map.Entry<String, String> entry : set) {
                        keys[position] = entry.getKey();
                        position++;
                    }
                    Arrays.sort(keys);
                    StringBuffer sigBuffer = new StringBuffer();
                    for (int i = 0; i < keys.length; i++) {
                        sigBuffer.append(keys[i]);
                        sigBuffer.append("=");
                        sigBuffer.append(params.get(keys[i]));
                    }
                    String sig = MD5(ClientKey.getClientKey().key + sigBuffer.toString()).substring(5, 21).toLowerCase();

                    builder.add("sig", sig);

                    Console.log(builder);
                    RequestBody newBody = builder.build();


                    Request newRequest = orgRequest.newBuilder()
                            .url(orgRequest.url())
                            .method(orgRequest.method(), newBody)
                            .build();
                    return chain.proceed(newRequest);
                }
            } else {
                HttpUrl.Builder url = orgRequest.url().newBuilder();
                url.addQueryParameter("os_type", 2 + "");
                url.addQueryParameter("device_type", 3 + "");
                url.addQueryParameter("client_id", ClientKey.getClientKey().client_id);
                url.addQueryParameter("tms", System.currentTimeMillis() + "");

                HttpUrl httpUrl = url.build();
                Set<String> keySet = httpUrl.queryParameterNames();

                int position = 0;
                String[] keys = new String[keySet.size()];
                for (String str : keySet) {
                    keys[position] = str;
                    position++;
                }
                Arrays.sort(keys);
                StringBuffer sigBuffer = new StringBuffer();
                for (int i = 0; i < keys.length; i++) {
                    sigBuffer.append(keys[i]);
                    sigBuffer.append("=");
                    sigBuffer.append(httpUrl.queryParameter((keys[i])));
                }
                String sig = MD5(ClientKey.getClientKey().key + sigBuffer.toString()).substring(5, 21).toLowerCase();
                url.addQueryParameter("sig", sig);

                Console.log(url);
                Request newRequest = orgRequest.newBuilder()
                        .url(url.build())
                        .build();
                return chain.proceed(newRequest);
            }
            return chain.proceed(orgRequest);

        }

    }


    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
