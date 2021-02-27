package com.season.example.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.season.example.model.ModelFactory;
import com.season.example.util.ScreenTool;
import com.season.lib.util.ToastUtil;
import com.season.mvp.ui.BaseTLEActivity;
import com.season.rapiddevelopment.R;

import cn.leancloud.AVObject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RegisterActivity extends BaseTLEActivity {


    protected boolean isTopTileEnable() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //设置顶部底部菜单透明
        ScreenTool.setTransparent(this, true, R.color.blue_bg);

        findViewById(R.id.main_btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(R.id.et_code, R.id.et_name, R.id.et_pwd)) {
                    ToastUtil.showToast("输入不完整");
                    return;
                }
                String pwd = ((EditText) findViewById(R.id.et_pwd)).getText().toString();
                if (pwd.length() < 6){
                    ToastUtil.showToast("密码太短了");
                    return;
                }
                String code = ((EditText) findViewById(R.id.et_code)).getText().toString();
                if ("qing".equals(code)) {
                    getLoadingView().showLoadingView();
                    // 构建对象
                    AVObject todo = new AVObject("QingUsers");
                    todo.put("user", ((EditText) findViewById(R.id.et_name)).getText().toString());
                    todo.put("pwd", pwd);
                    todo.saveInBackground().subscribe(new Observer<AVObject>() {
                        public void onSubscribe(Disposable disposable) {
                        }

                        public void onNext(AVObject todo) {
                            getLoadingView().dismissLoadingView();
                            ToastUtil.showToast("注册成功");
                            ModelFactory.local().sharedPreferences().common().setValueImmediately("user", ((EditText) findViewById(R.id.et_name)).getText().toString());
                            finish();
                        }

                        public void onError(Throwable throwable) {
                            ToastUtil.showToast("注册失败");
                        }

                        public void onComplete() {
                        }
                    });
                } else {
                    ToastUtil.showToast("授权码无效");
                }

            }
        });
    }


}
