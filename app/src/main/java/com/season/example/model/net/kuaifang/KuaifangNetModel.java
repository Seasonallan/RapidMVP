package com.season.example.model.net.kuaifang;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.season.example.entry.ClientKey;
import com.season.example.model.DNS;
import com.season.rapiddevelopment.model.BaseNetModel;
import com.season.rapiddevelopment.tools.Console;
import com.season.rapiddevelopment.tools.Crypto;
import com.season.rapiddevelopment.tools.PkgManagerUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Disc: 快放视频的网络数据解析器
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:29
 */
public abstract class KuaifangNetModel extends BaseNetModel{


    private Gson gson = new Gson();

    @Override
    protected String getBaseUrl() {
        return DNS.BASE_URL;
    }

    @Override
    protected Interceptor getInterceptor() {
        return new ParamsInterceptor();
    }

    @Override
    protected Converter.Factory getConverterFactory() {
        return new ConverterAesJsonFactory();
    }

    /**
     * 返回数据解密Convert
     */
    class ConverterAesJsonFactory extends Converter.Factory {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                                Annotation[] annotations,
                                                                Retrofit retrofit) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new DecodeResponseBodyConverter(adapter);
        }

    }


    static class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final TypeAdapter<T> adapter;

        DecodeResponseBodyConverter(TypeAdapter<T> adapter) {
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody responseBody) throws IOException {
            String response = responseBody.string();
            Console.logNetMessage("服务器数据：" + response);
            String result = Crypto.desEncrypt(response);//解密
            Console.logNetMessage("解密的服务器数据：" + result);
            if (result == null){
                return null;
            }
            return adapter.fromJson(result);
        }
    }

    /**
     * 参数加密过滤器
     */
    class ParamsInterceptor implements Interceptor {

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
                    String sig = Crypto.MD5(ClientKey.getClientKey().key + sigBuffer.toString()).substring(5, 21).toLowerCase();

                    builder.add("sig", sig);

                    Console.logNetMessage(builder);
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
                String sig = Crypto.MD5(ClientKey.getClientKey().key + sigBuffer.toString()).substring(5, 21).toLowerCase();
                url.addQueryParameter("sig", sig);

                Console.logNetMessage(url);
                Request newRequest = orgRequest.newBuilder()
                        .url(url.build())
                        .build();
                return chain.proceed(newRequest);
            }
            return chain.proceed(orgRequest);

        }

    }
}
