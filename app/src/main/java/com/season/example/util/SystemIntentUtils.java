package com.season.example.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.io.File;

public class SystemIntentUtils {
    public static final  int SystemRequestCodePhoto=0x1111;
    public static  final int SystemRequestCodeCamera=0x1112;
    public static  final int SystemRequestCodeCrop=0x1113;

    /**
     * 开启相册
     */
    public static void openPhoto(@NonNull Activity context) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intent,SystemRequestCodePhoto);
    }

    /**
     * 开启相册
     */
    public static void openPhotos(@NonNull Activity context) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(Intent.createChooser(intent, "Select Picture"), SystemRequestCodePhoto);
    }

    /**
     * 开启相机
     */
    public static Uri openCamera(@NonNull Activity context, File file) {
        Uri uri = null;
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(
                    context,
                    ImageFileProvider.getFileProviderName(context),
                    file);
            } else {
                uri = Uri.fromFile(file);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            context.startActivityForResult(intent, SystemRequestCodeCamera);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  uri;
    }
    /**
     * 裁剪
     */
    public static void startPhotoCrop(Uri imageUri, File outCrop, int width, int height, Activity context) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        //设置源地址uri
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        Uri uriCrop = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //系統>7.0开启临时权限
            uriCrop=  FileProvider.getUriForFile(context, ImageFileProvider.getFileProviderName(context), outCrop);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setClipData(ClipData.newRawUri(MediaStore.EXTRA_OUTPUT, uriCrop));
        }else{
            uriCrop= Uri.fromFile(outCrop);
        }
        intent.putExtra("scale", true);
        //设置裁剪完保存的URI
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriCrop);
        //设置图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);//data不需要返回,避免图片太大异常。小米的系统使用这个会使返回数据为null，所以设置为false用uri来获取数据
        //头像识别 会启动系统的拍照时人脸识别
        intent.putExtra("noFaceDetection", true);
        context.startActivityForResult(intent, SystemRequestCodeCrop);
    }
}
