package com.mooreliu.widget;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.mooreliu.R;
import com.mooreliu.util.CommonUtil;

/**
 * Created by liuyi on 15/9/6.
 */
public class LoginActivity extends  BaseActivity implements OnClickListener {
    private static final String TAG = "LoginActivity";
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private Button mButtonRegister;
    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.layout_login);
        mEditTextPassword.getBackground().setColorFilter(
                getResources().getColor(R.color.black), PorterDuff.Mode.DST_ATOP);
        mEditTextPassword.getBackground().setColorFilter(
                getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle onSavedInstanceState) {
        super.onSaveInstanceState(onSavedInstanceState);
    }

    @Override
    public void findView() {
        mEditTextUserName = (EditText) findViewById(R.id.login_username);
        mEditTextPassword = (EditText) findViewById(R.id.login_password);
        mButtonLogin = (Button) findViewById(R.id.btn_login);
        mButtonRegister = (Button) findViewById(R.id.btn_register);

    }
    @Override
    public void initView() {
        setToolBarTitle(getString(R.string.login));

    }

    @Override
    public void setOnClick() {
        mButtonRegister.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.btn_login:
                CommonUtil.toastMessage("µÇÂ½");
                break;

            case R.id.btn_register:
                Intent intent = new Intent(this , RegisterActivity.class);
                startActivity(intent);
                break;

            default:
                CommonUtil.toastMessage("Error in LoginAcitity");
        }
    }
}
