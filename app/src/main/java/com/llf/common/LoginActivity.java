package com.llf.common;

import android.content.Intent;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.llf.basemodel.base.BaseActivity;
import com.llf.common.entity.User;


import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;



public class LoginActivity extends BaseActivity {


    @BindView(R.id.b_iv)
    ImageView bIv;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_yzm)
    EditText etYzm;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.wjmm)
    TextView wjmm;
    @BindView(R.id.register)
    TextView register;
    private String password, phone;
    private Unbinder mUnbinder;
    Intent intent;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
    /*  Bmob.initialize(LoginActivity.this, "60ddbb889ba5307f9ab29d155b116148");
        //获取登录成功后的本地用户信息
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            //得到当前登陆的用户，如果得到跳转到首页
            intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            LoginActivity.this.finish();
    }*/
    }


    @OnClick({R.id.b_iv, R.id.login, R.id.wjmm, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.b_iv:
                finish();
                break;
            case R.id.login:
              /* if (TextUtils.isEmpty(etPhone.getText().toString()) || TextUtils.isEmpty(
                        etYzm.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "用户名密码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                User user = new User();
                user.setUsername(etPhone.getText().toString());
                user.setPassword(etYzm.getText().toString());
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            LoginActivity.this.finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "登录失败" + e, Toast.LENGTH_LONG).show();
                        }
                    }
                });*/
                break;
            case R.id.wjmm:

            case R.id.register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
