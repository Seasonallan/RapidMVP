package com.season.example.ui.activity;

import android.content.ClipData;
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
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.season.example.ui.dialog.PicChooseDialog;
import com.season.example.util.ScreenTool;
import com.season.example.util.SystemIntentUtils;
import com.season.lib.util.ToastUtil;
import com.season.mvp.ui.BaseTLEActivity;
import com.season.rapiddevelopment.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVFile;
import cn.leancloud.AVObject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class GoodsAddActivity extends BaseTLEActivity {

    private String url;

    private String imagePath;
    private File file;
    private Uri imageUri;
    private ImageView imageView;
    private LinearLayout contentView;
    private boolean top = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_add);
        //设置顶部底部菜单透明
        //ScreenTool.setTransparent(this, true, R.color.blue_bg);

        getTitleBar().setTopTile("添加商品");
        getTitleBar().enableLeftButton();


        contentView = findViewById(R.id.main_ll);
        imageView = findViewById(R.id.iv_picture);
        findViewById(R.id.main_btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top = true;
                new PicChooseDialog(GoodsAddActivity.this) {
                    @Override
                    protected void onConfirm() {
                        super.onConfirm();
                        file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                        imagePath = file.toString();
                        imageUri = SystemIntentUtils.openCamera(GoodsAddActivity.this, file);
                    }

                    @Override
                    protected void onCancel() {
                        super.onCancel();
                        SystemIntentUtils.openPhoto(GoodsAddActivity.this);
                    }
                }.show();
            }
        });
        findViewById(R.id.main_btn_add2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top = false;
                new PicChooseDialog(GoodsAddActivity.this) {
                    @Override
                    protected void onConfirm() {
                        super.onConfirm();
                        file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                        imagePath = file.toString();
                        imageUri = SystemIntentUtils.openCamera(GoodsAddActivity.this, file);
                    }

                    @Override
                    protected void onCancel() {
                        super.onCancel();
                        SystemIntentUtils.openPhotos(GoodsAddActivity.this);
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
                if (imageView.getTag() == null) {
                    ToastUtil.showToast("请选择轮播图片");
                    return;
                }

                getLoadingView().showLoadingView();
                topImageFile = imageView.getTag().toString();
                fileLists = new ArrayList<>();
                for (int i = 0; i < contentView.getChildCount(); i++) {
                    ImageView imageView = (ImageView) contentView.getChildAt(i);
                    fileLists.add(imageView.getTag().toString());
                }
                upload();

            }
        });
    }

    private void onSubmit() {
        System.out.println("onSubmit");
        // 构建对象
        AVObject todo = new AVObject("QingGoods");
        todo.put("title", ((EditText) findViewById(R.id.et_name)).getText().toString());
        todo.put("price", ((EditText) findViewById(R.id.et_price)).getText().toString());
        todo.put("image", topImageUrl);
        todo.put("content", contentImageUrls);
        todo.saveInBackground().subscribe(new Observer<AVObject>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(AVObject todo) {
                ToastUtil.showToast("发布成功");
                getLoadingView().dismissLoadingView();
                finish();
            }

            public void onError(Throwable throwable) {
                ToastUtil.showToast("发布失败");
                getLoadingView().dismissLoadingView();
            }

            public void onComplete() {
                getLoadingView().dismissLoadingView();
            }
        });
    }


    private String topImageUrl;
    List<String> contentImageUrls;

    private void upload() {
        try {
            AVFile file = AVFile.withAbsoluteLocalPath("title" + topImageFile.substring(topImageFile.lastIndexOf(".")), topImageFile);
            file.saveInBackground().subscribe(new Observer<AVFile>() {
                public void onSubscribe(Disposable disposable) {
                }

                public void onNext(AVFile file) {
                    System.out.println("文件保存完成。objectId：" + file.getObjectId());
                    System.out.println("文件保存完成。url：" + file.getUrl());
                    topImageUrl = file.getUrl();
                    contentImageUrls = new ArrayList<>();
                    uploadFile();
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
    }

    String topImageFile;
    List<String> fileLists;

    private void uploadFile() {
        if (fileLists.size() <= 0) {
            onSubmit();
            return;
        }
        String itemFile = fileLists.remove(0);
        try {
            AVFile file = AVFile.withAbsoluteLocalPath("content" + itemFile.substring(itemFile.lastIndexOf(".")), itemFile);
            file.saveInBackground().subscribe(new Observer<AVFile>() {
                public void onSubscribe(Disposable disposable) {
                }

                public void onNext(AVFile file) {
                    System.out.println("文件保存完成。objectId：" + file.getObjectId());
                    System.out.println("文件保存完成。url：" + file.getUrl());
                    contentImageUrls.add(file.getUrl());
                    uploadFile();
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

    }



    private void getPhoto(Intent data){
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
                    if (top) {
                        getPhoto(data);
                    } else {
                        try {
                            ClipData clipdata = data.getClipData();
                            if (clipdata == null){
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                                String imageFile = saveImage(bitmap);
                                showImageView(imageFile, bitmap);
                            }else{
                                for (int i = 0; i < clipdata.getItemCount(); i++) {
                                    try {
                                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), clipdata.getItemAt(i).getUri());
                                        String imageFile = saveImage(bitmap);
                                        showImageView(imageFile, bitmap);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

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
        if (top) {
            imageView.setImageBitmap(photo);
            imageView.setTag(path);
            ((Button) findViewById(R.id.main_btn_add)).setText("替换");
        } else {
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(photo);
            imageView.setTag(path);
            contentView.addView(imageView);
        }
    }

}
