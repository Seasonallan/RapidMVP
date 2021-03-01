package com.season.example.util;

import android.content.Context;

import androidx.core.content.FileProvider;

public class ImageFileProvider extends FileProvider {
    public static String getFileProviderName(Context context) {
        return context.getPackageName() + ".provider";
    }
}