package com.llf.common;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.llf.basemodel.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;



public class RegisterActivity extends BaseActivity {

    @BindView(R.id.b_iv)
    ImageView bIv;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mm)
    EditText etMm;
    Button yzm;
    @BindView(R.id.login)
    Button login;
    private Unbinder mUnbinder;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
       // Bmob.initialize(RegisterActivity.this, "60ddbb889ba5307f9ab29d155b116148");

    }

    @OnClick({R.id.b_iv, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.b_iv:
                finish();
                break;
            case R.id.login:
              /*  if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etMm.
                        getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "用户名密码不能为空",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                User user = new User();
                user.setUsername(etName.getText().toString());
                user.setPassword(etMm.getText().toString());
                user.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                            RegisterActivity.this.finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG
                            ).show();
                        }
                    }
                });*/
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
