package com.season.example.util;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;

public class FastPermissions {
    private final String TAG = FastPermissions.class.getSimpleName();
    private Activity activity;

    private PermissionFragment permissionFragment;


    /**
     * 订阅回调接口
     */
    public interface Subscribe {
        void onResult(int requestCode, boolean allGranted, String[] permissions);
    }

    /**
     * 内部回调使用，用户不应该调用或者实现此接口
     */
    public interface Callback {
        void onRequestPermissionsCallback(final int requestCode, String[] permissions, int[] grantResults);
    }

    public static class PermissionFragment extends Fragment {

        private HashMap<Integer, Callback> callbacks = new HashMap<>();
        private ArrayList<Runnable> runnables = new ArrayList<>();


        public void registerCallback(int requestCode, Callback callback) {
            callbacks.put(requestCode, callback);
        }

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
            while (!runnables.isEmpty())
                runnables.remove(0).run();
        }

        public void postRequestPermissions(final String[] permissions, final int requestCode) {
            if (isAdded()) {
                requestPermissions(permissions, requestCode);
            } else {
                runnables.add(new Runnable() {
                    @Override
                    public void run() {
                        requestPermissions(permissions, requestCode);
                    }
                });
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, final String[] permissions, final int[] grantResults) {
            if (callbacks.containsKey(requestCode)) {
                Callback callback = callbacks.get(requestCode);
                if (callback != null)
                    callback.onRequestPermissionsCallback(requestCode, permissions, grantResults);
            }
        }
    }

    /**
     * 快捷的进行权限申请，通过添加一个临时的Fragment来进行生命周期管理，因此
     * 一个Activity请只初始化一个FastPermissions实例
     *
     * @param appCompatActivity 所在的activity,必须继承自AppCompatActivity
     */

    public FastPermissions(AppCompatActivity appCompatActivity) {
        if (appCompatActivity == null)
            throw new IllegalArgumentException("Activity must not be null!");
        permissionFragment = new PermissionFragment();
        activity = appCompatActivity;
        FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
        fragmentTransaction(fragmentManager);

    }

    public FastPermissions(Fragment fragment) {
        activity = fragment.getActivity();
        if (activity == null)
            throw new IllegalArgumentException("Activity must not be null!");
        permissionFragment = new PermissionFragment();
        FragmentManager fragmentManager = fragment.getChildFragmentManager();
        fragmentTransaction(fragmentManager);
    }

    private void fragmentTransaction(FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(permissionFragment, PermissionFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    /**
     * 设置所需要申请的权限
     *
     * @param permission 需要申请的权限
     * @return 返回一个RequestPermissionsResult实例用于进行后续操作
     */
    public RequestPermissionsResult need(String permission) {
        return need(new String[]{permission});
    }

    /**
     * 设置所需要申请的权限
     *
     * @param permissions 需要申请的权限数组
     * @return 返回一个RequestPermissionsResult实例用于进行后续操作
     */
    public RequestPermissionsResult need(String... permissions) {
        return new RequestPermissionsResult(activity, permissions, permissionFragment);
    }

    public static class RequestPermissionsResult implements Callback {
        private Activity activity;
        private String[] permissions;
        private PermissionFragment permissionFragment;
        private Subscribe subscribe;
        private boolean showDialog = true;

        RequestPermissionsResult(Activity activity, String[] permissions, PermissionFragment permissionFragment) {
            this.activity = activity;
            this.permissions = permissions;
            this.permissionFragment = permissionFragment;
        }

        /**
         * 设置当权限被拒绝的时候是否弹窗提示用户
         *
         * @param showDialog 为true则弹窗反之不弹窗 默认为true
         * @return 返回RequestPermissionsResult
         */
        public RequestPermissionsResult showDialog(boolean showDialog) {
            this.showDialog = showDialog;
            return this;
        }

        private boolean requestPermissions(String[] permissions, int requestCode) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(activity, permission)
                        == PackageManager.PERMISSION_DENIED) {
                    //注册回调
                    permissionFragment.registerCallback(requestCode, this);
                    permissionFragment.postRequestPermissions(permissions, requestCode);
                    return false;
                }
            }
            return true;
        }

        /**
         * 开始请求权限
         *
         * @param requestCode 请求号
         */
        public void request(int requestCode) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!requestPermissions(permissions, requestCode)) {
                    return;
                }
            }
            //通知权限监听回调
            publish(requestCode, true, permissions);
        }

        private void publish(int requestCode, boolean allGranted, String[] permissions) {
            if (subscribe != null)
                subscribe.onResult(requestCode, allGranted, permissions);
        }

        /**
         * 设置订阅回调
         *
         * @param subscribe 权限被允许或者拒绝的时候会回调此实例
         *                  实例必须实现Subscribe方法
         * @return 返回RequestPermissionsResult实例
         */
        public RequestPermissionsResult subscribe(Subscribe subscribe) {
            this.subscribe = subscribe;
            return this;
        }

        /**
         * 提供权限的回调接口 此方法为内部接口 用户不应该调用此方法
         *
         * @param requestCode  请求号
         * @param permissions  权限
         * @param grantResults 授权结果
         */
        @Override
        public void onRequestPermissionsCallback(final int requestCode, final String[] permissions, final int[] grantResults) {
            //保证运行在主线程
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            });
        }

        private void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
            int i = 0;
            for (int grant : grantResults) {
                String permission = permissions[i];
                if (grant == PackageManager.PERMISSION_DENIED) {
                    if (showDialog) {
                        showDialogTips(permission, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                publish(requestCode, false, permissions);
                            }
                        });
                    } else {
                        publish(requestCode, false, permissions);
                    }
                    return;
                }
            }
            publish(requestCode, true, permissions);
        }

        private void showDialogTips(String permission, DialogInterface.OnClickListener onDenied) {
            AlertDialog alertDialog = new AlertDialog.Builder(activity).setTitle("权限被禁用").setMessage(
                    String.format("您拒绝了相关权限，无法正常使用本功能。\n请前往 设置->应用管理->权限管理中启用权限"
                    )).setCancelable(false).
                    setNegativeButton("返回", onDenied).
                    setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent manageAppIntent = new Intent();
                            manageAppIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            //第二个参数为包名
                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                            manageAppIntent.setData(uri);
                            activity.startActivity(manageAppIntent);
                        }
                    }).create();
            alertDialog.show();

        }


    }


}


