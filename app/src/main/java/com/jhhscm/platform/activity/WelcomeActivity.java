package com.jhhscm.platform.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.jhhscm.platform.R;
import com.umeng.analytics.MobclickAgent;

import java.util.List;


public class WelcomeActivity extends AppCompatActivity {
   // private AlertDialog myDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
     //   myDialog = new AlertDialog(this).builder();
        if (XXPermissions.isHasPermission(this, Permission.Group.STORAGE, Permission.Group.CAMERA, Permission.Group.LOCATION, Permission.Group.PHONE, Permission.Group.SMS)) {
            // Toast.makeText(PermissionActivity.this, "已经获取到权限，不需要再次申请了", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            // Toast.makeText(PermissionActivity.this, "还没有获取到权限或者部分权限未授予", Toast.LENGTH_SHORT).show();
            huoqu();
        }
    }

    private void huoqu() {
        XXPermissions.with(WelcomeActivity.this)
                .permission(Permission.Group.STORAGE, Permission.Group.CAMERA, Permission.Group.LOCATION, Permission.Group.PHONE, Permission.Group.SMS)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            // Toast.makeText(PermissionActivity.this, "获取权限成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setClass(WelcomeActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(WelcomeActivity.this, "获取权限成功，部分权限未正常授予", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent =new Intent();
                        intent.setClass(WelcomeActivity.this,MainActivity.class);

                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            Toast.makeText(WelcomeActivity.this, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_SHORT).show();
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(WelcomeActivity.this);
                        } else {
                            Toast.makeText(WelcomeActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }
}
