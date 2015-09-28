package com.mooreliu.widget;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.mooreliu.R;
import com.mooreliu.event.EventType;
import com.mooreliu.event.Notify;
import com.mooreliu.event.NotifyInfo;
import com.mooreliu.util.CommonUtil;
import com.mooreliu.util.Constants;
import com.mooreliu.util.LogUtil;
import com.mooreliu.util.TextUtil;

/**
 * Created by liuyi on 15/9/6.
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
    private static final String TAG = "LoginActivity";
    private final static int AVOS_LOGIN_OVER_MSG = 1;
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private Button mButtonRegister;
    private TextView mTextViewLoginErrorMsg;
    private String mUserNameInput;
    private String mPasswordInput;
    private String AVOS_LOGIN_ERROR_MSG;
    private int AVOS_LOGIN_ERROR_CODE;
    private Handler handler;

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_login);
        mEditTextPassword.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.DST_ATOP);
        mEditTextPassword.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        handler = new Handler() {/*处理登陆错误信息*/
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case AVOS_LOGIN_OVER_MSG:
                        LogUtil.e(TAG, "login error code" + AVOS_LOGIN_ERROR_CODE);
                        if (AVOS_LOGIN_ERROR_CODE == Constants.AVOS_ERROR_CODE_USERNAME_AND_PASSWORD_MISMATCH_)
                            mTextViewLoginErrorMsg.setText(getResources().getString(R.string.username_and_password_mismatch));
                        else if (AVOS_LOGIN_ERROR_CODE == Constants.AVOS_ERROR_CODE_PHONENUMBER_ISNOT_VERIFIED)
                            mTextViewLoginErrorMsg.setText(getResources().getString(R.string.phonenumer_is_not_verified));
                        else if (AVOS_LOGIN_ERROR_CODE == Constants.AVOS_ERROR_CODE_COUND_NOT_FIND_USER)
                            mTextViewLoginErrorMsg.setText(getResources().getString(R.string.this_telephone_is_not_registered));
                        else mTextViewLoginErrorMsg.setText(AVOS_LOGIN_ERROR_MSG);
                        mButtonLogin.setClickable(true);
                        mButtonRegister.setClickable(true);
                        break;
                }
            }
        };
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
        mTextViewLoginErrorMsg = (TextView) findViewById(R.id.login_error_msg);
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
        if (CommonUtil.isFastDoubleClick(2 * 1000)) //2 seconds
            return;
        int id = view.getId();
        switch (id) {
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.btn_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                CommonUtil.toastMessage("Error in LoginAcitity");
        }
    }

    private boolean checkLoginValidation() {
        mUserNameInput = mEditTextUserName.getText().toString();
        mPasswordInput = mEditTextPassword.getText().toString();
        if (TextUtil.isEmpty(mUserNameInput)) {
            mTextViewLoginErrorMsg.setText(getResources().getString(R.string.username_could_not_be_empty));
            return false;
        }
        if (TextUtil.isEmpty(mPasswordInput)) {
            mTextViewLoginErrorMsg.setText(getResources().getString(R.string.password_could_not_be_empty));
            return false;
        }
        return true;
    }

    private void doLogin() {
        mButtonLogin.setClickable(false); //此时登陆不能用了
        mButtonRegister.setClickable(false);
        if (!checkLoginValidation()) {
            mButtonLogin.setClickable(true);
            mButtonRegister.setClickable(true);
            return;
        }

        AVUser.loginByMobilePhoneNumberInBackground(mUserNameInput, mPasswordInput, new LogInCallback() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    CommonUtil.toastMessage(getResources().getString(R.string.login_success));
                    Notify.getInstance().NotifyActivity(new NotifyInfo(EventType.EVENT_LOGIN));//通知登录成功
                    returnToAcitivityAhead();
                } else {
                    LogUtil.e(TAG, "login error " + e.getLocalizedMessage());
                    AVOS_LOGIN_ERROR_CODE = e.getCode();
                    AVOS_LOGIN_ERROR_MSG = e.getLocalizedMessage();
                    Message msg = new Message();
                    msg.what = AVOS_LOGIN_OVER_MSG;
                    handler.sendMessage(msg);
                }
            }
        });

    }

    private void returnToAcitivityAhead() {
        this.finish(); // 直接销毁LoginAcitivity。
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LogUtil.e(TAG, "onBackPressed()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
