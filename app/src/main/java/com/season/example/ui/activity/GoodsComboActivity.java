package com.season.example.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.season.example.ui.dialog.PicChooseDialog;
import com.season.example.util.SystemIntentUtils;
import com.season.lib.util.ToastUtil;
import com.season.mvp.model.ImageModel;
import com.season.mvp.ui.BaseTLEActivity;
import com.season.rapiddevelopment.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.leancloud.AVFile;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class GoodsComboActivity extends BaseTLEActivity {

    private String imagePath;
    private File file;
    private ImageView imageView;

    JSONObject item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_combo);
        //设置顶部底部菜单透明

        String json = getIntent().getStringExtra("item");
        if (!TextUtils.isEmpty(json)) {
            try {
                item = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        getTitleBar().setTopTile(item == null ? "添加套餐" : "编辑套餐");
        getTitleBar().enableLeftButton();


        imageView = findViewById(R.id.iv_picture);

        if (item != null) {
            try {
                ((EditText) findViewById(R.id.et_name)).setText(item.getString("title"));
                ((EditText) findViewById(R.id.et_price)).setText(item.getString("price"));

                topImageUrl = item.getString("image");
                ImageModel.bindImage2View(imageView, topImageUrl);
                imageView.setTag(R.id.tag_url, topImageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        findViewById(R.id.main_btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PicChooseDialog(GoodsComboActivity.this) {
                    @Override
                    protected void onConfirm() {
                        super.onConfirm();
                        file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                        imagePath = file.toString();
                        SystemIntentUtils.openCamera(GoodsComboActivity.this, file);
                    }

                    @Override
                    protected void onCancel() {
                        super.onCancel();
                        SystemIntentUtils.openPhoto(GoodsComboActivity.this);
                    }
                }.show();
            }
        });


        findViewById(R.id.main_btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(R.id.et_name, R.id.et_price)) {
                    ToastUtil.showToast("输入不完整");
                    return;
                }

                getLoadingView().showLoadingView();
                upload();

            }
        });
    }

    private void onSubmit() {
        System.out.println("onSubmit");
        item = new JSONObject();
        try {
            item.put("image", topImageUrl);
            item.put("title", ((EditText) findViewById(R.id.et_name)).getText().toString());
            item.put("price", ((EditText) findViewById(R.id.et_price)).getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("combo", item.toString());
        resultIntent.putExtras(bundle);
        setResult(RESULT_OK, resultIntent);
        finish();

    }


    private String topImageUrl;

    private void upload() {
        if (imageView.getTag(R.id.tag_file) != null) {
            topImageFile = imageView.getTag(R.id.tag_file).toString();
            try {
                AVFile file = AVFile.withAbsoluteLocalPath("title" + topImageFile.substring(topImageFile.lastIndexOf(".")), topImageFile);
                file.saveInBackground().subscribe(new Observer<AVFile>() {
                    public void onSubscribe(Disposable disposable) {
                    }

                    public void onNext(AVFile file) {
                        System.out.println("文件保存完成。objectId：" + file.getObjectId());
                        System.out.println("文件保存完成。url：" + file.getUrl());
                        topImageUrl = file.getUrl();
                        onSubmit();
                    }

                    public void onError(Throwable throwable) {
                        getLoadingView().dismissLoadingView();
                        ToastUtil.showToast("图片上传失败");
                    }

                    public void onComplete() {
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            onSubmit();
        }
    }

    String topImageFile;


    private void getPhoto(Intent data) {
        Uri imageUri = data.getData();
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(imageUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        FileInputStream fileInputStream = null;
        try {
            imagePath = cursor.getString(column_index);
            fileInputStream = new FileInputStream(imagePath);
            Bitmap photo = BitmapFactory.decodeStream(fileInputStream);
            showImageView(imagePath, photo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SystemIntentUtils.SystemRequestCodePhoto:
                    //相册
                    getPhoto(data);
                    break;
                case SystemIntentUtils.SystemRequestCodeCamera:
                    //拍照
                    try {
                        FileInputStream fileInputStream = new FileInputStream(imagePath);
                        Bitmap photo = BitmapFactory.decodeStream(fileInputStream);
                        showImageView(imagePath, photo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    /**
     * 保存图片到相册
     */
    public String saveImage(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(getCacheDir(), fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file.toString();
    }

    private void showImageView(String path, Bitmap photo) {
        imageView.setImageBitmap(photo);
        imageView.setTag(R.id.tag_file, path);
        ((Button) findViewById(R.id.main_btn_add)).setText("替换");
    }

}
