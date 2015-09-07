package com.mooreliu.widget;

/**
 * Created by mooreliu on 2015/9/7.
 */

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.mooreliu.R;
import com.mooreliu.util.CommonUtil;

public class RegisterActivity extends BaseActivity implements OnClickListener{
    private static final String TAG = "RegisterActivity";
    private EditText mEditTextTelephoneNumber;
    private EditText mEditTextRegisterNumber;
    private EditText mEditTextPassword;
    private EditText mEditTextComfirmPassword;
    private Button mButtonRegister;
    private Button mButtonGetRegisterNumber;

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_register);
    }
    @Override
    public void findView() {
        mEditTextTelephoneNumber = (EditText) findViewById(R.id.input_telephone_number);
        mEditTextRegisterNumber = (EditText) findViewById(R.id.input_register_number);
        mEditTextPassword = (EditText) findViewById(R.id.input_password);
        mEditTextComfirmPassword = (EditText) findViewById(R.id.input_comfirm_password);

        mButtonGetRegisterNumber
    }

    @Override
    public void initView() {

    }

    @Override
    public void setOnClick() {

    }

    @Override
    public void onSaveInstanceState(Bundle onSavedInstanceState) {
        super.onSaveInstanceState(onSavedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case 1:
                break;
            default:
                CommonUtil.toastMessage("error in register Acitivyt");
        }
    }
}
