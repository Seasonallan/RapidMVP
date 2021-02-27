package com.season.example.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.season.example.model.ModelFactory;
import com.season.example.util.ScreenTool;
import com.season.lib.util.ToastUtil;
import com.season.mvp.ui.BaseTLEActivity;
import com.season.rapiddevelopment.R;

import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends BaseTLEActivity {


    protected boolean isTopTileEnable() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        editText.setText(ModelFactory.local().sharedPreferences().common().getValueImmediately("user").toString());
        editText.setSelection(editText.getText().length());
    }

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ModelFactory.local().sharedPreferences().common().getValueImmediately("user").toString().length() > 0) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);
        //设置顶部底部菜单透明
        ScreenTool.setTransparent(this, true, R.color.blue_bg);


        editText = findViewById(R.id.et_name);
        findViewById(R.id.main_btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(R.id.et_name, R.id.et_pwd)) {
                    ToastUtil.showToast("输入不完整");
                    return;
                }
                getLoadingView().showLoadingView();
                AVQuery<AVObject> query = new AVQuery<>("QingUsers");
                query.whereEqualTo("user", ((EditText) findViewById(R.id.et_name)).getText().toString());
                query.whereEqualTo("pwd", ((EditText) findViewById(R.id.et_pwd)).getText().toString());
                query.findInBackground().subscribe(new Observer<List<AVObject>>() {
                    public void onSubscribe(Disposable disposable) {
                    }

                    public void onNext(List<AVObject> students) {
                        getLoadingView().dismissLoadingView();
                        if (students.size() > 0) {
                            ToastUtil.showToast("登录成功");
                            AVObject user = students.get(0);
                            ModelFactory.local().sharedPreferences().common().setValueImmediately("id", user.getObjectId());
                            ModelFactory.local().sharedPreferences().common().setValueImmediately("level", user.getInt("level"));
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            ToastUtil.showToast("登录失败");
                        }
                    }

                    public void onError(Throwable throwable) {
                    }

                    public void onComplete() {
                    }
                });
            }
        });

        findViewById(R.id.password_tv_forget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }


}
