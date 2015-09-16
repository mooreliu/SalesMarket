package com.mooreliu.widget;

/**
 * Created by mooreliu on 2015/9/7.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.mooreliu.R;
import com.mooreliu.util.CommonUtil;
import com.mooreliu.util.Constants;
import com.mooreliu.util.LogUtil;
import com.mooreliu.util.TextUtil;

public class RegisterActivity extends BaseActivity implements OnClickListener{
    private static final String TAG = "RegisterActivity";
    private EditText mEditTextTelephoneNumber;
    private EditText mEditTextRegisterNumber;
    private EditText mEditTextPassword;
    private EditText mEditTextComfirmPassword;
    private Button mButtonRegister;
    private Button mButtonGetRegisterNumber;
    private TextView mTextViewRegisterErrorMsg;
    private TextView mTextViewSMSErrorMsg;
    private CountDownTimer timer;

//    private String mUserNameInput;
    private String mTelephoneNumberInput;
    private String mPasswordInput;
    private String mPasswordComfirmInput;
    private String mRegisterNumberInput;
    private AVUser user;
    private int SIGNUP_ERROR_CODE;
    private int VERIFY_SMS_ERROR_CODE;
    private String SIGNUP_ERROR_MSG ;
    private String VERIFY_SMS_ERROR_MSG;
    private String REQUEST_MOBILEPHONE_VERIFY_ERROR_MSG;

    private Handler handler = null;
    private static final int SIGN_UP_FAIL_MSG = 1;
    private static final int REQUEST_SMS_FAIL_MSG = 2;
    private static final int VERIFY_SMS_FAIL_MSG = 3;

    private static final int SIGN_UP_SUCCESS_MSG =4;
    private static final int VERIFY_SMS_SUCCESS_MSG =5;
    private static final int REQUEST_SMS_SUCCESS_MSG =6;

    private int SIGN_UP_FAIL_ERROR_CODE;
    private int REQUEST_SMS_FAIL_ERROR_CODE;
    private int VERIFY_SMS_FAIL_ERROR_CODE;

    private boolean IS_SIGN_UP_SUCCESS;
    private boolean IS_VERIFY_SMS_SUCCESS;
    private boolean IS_REQUEST_SMS_SUCCESS;

    private DigitsKeyListener mDigitsKeyListender;
    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_register);
        initHandler();
    }
    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case SIGN_UP_FAIL_MSG:
                        LogUtil.e(TAG ," "+SIGNUP_ERROR_MSG);
                        if(!TextUtil.isEmpty(SIGNUP_ERROR_MSG))
                            mTextViewSMSErrorMsg.setText(SIGNUP_ERROR_MSG);
                        break;

                    case VERIFY_SMS_FAIL_MSG:
                        LogUtil.e(TAG ," "+VERIFY_SMS_ERROR_MSG);
                        if(!TextUtil.isEmpty(VERIFY_SMS_ERROR_MSG))
                            mTextViewRegisterErrorMsg.setText(VERIFY_SMS_ERROR_MSG);
                        break;

                    case REQUEST_SMS_FAIL_MSG:
                        LogUtil.e(TAG ," "+REQUEST_MOBILEPHONE_VERIFY_ERROR_MSG);
                        if(!TextUtil.isEmpty(REQUEST_MOBILEPHONE_VERIFY_ERROR_MSG))
                            mTextViewSMSErrorMsg.setText(REQUEST_MOBILEPHONE_VERIFY_ERROR_MSG);
                        break;

                    case SIGN_UP_SUCCESS_MSG:
                        getRegisterNumber();
                        break;

                    case VERIFY_SMS_SUCCESS_MSG:
                        CommonUtil.toastMessage(getResources().getString(R.string.register_succuess));
                        break;

                    case REQUEST_SMS_SUCCESS_MSG:
                        getRegisterNumberButtonStateCountDown();
                        break;

                }

            }
        };
    }

    @Override
    public void findView() {
        mEditTextTelephoneNumber = (EditText) findViewById(R.id.input_telephone_number);
        mEditTextRegisterNumber = (EditText) findViewById(R.id.input_register_number);
        mEditTextPassword = (EditText) findViewById(R.id.input_password);
        mEditTextComfirmPassword = (EditText) findViewById(R.id.input_comfirm_password);

        mButtonRegister = (Button) findViewById(R.id.btn_do_register);
        mButtonGetRegisterNumber = (Button) findViewById(R.id.btn_do_send_register_number);

        mTextViewRegisterErrorMsg =(TextView) findViewById(R.id.register_error_message);
        mTextViewSMSErrorMsg =(TextView) findViewById(R.id.sms_error_message);

    }

    @Override
    public void initView() {
        setToolBarTitle(getResources().getString(R.string.register));
        mDigitsKeyListender = new DigitsKeyListener() {
            @Override
            public int getInputType() {
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }
            @Override
            protected char[] getAcceptedChars() {
                char[] data = getResources().getString(R.string.login_only_can_input).toCharArray();
                return data;
            }
        };
        mEditTextPassword.setKeyListener(mDigitsKeyListender);
        mEditTextComfirmPassword.setKeyListener(mDigitsKeyListender);
    }

    private void getRegisterNumberButtonStateCountDown() {
        LogUtil.e(TAG,"发送SMS成功");
        mButtonGetRegisterNumber.setBackgroundColor(getResources().getColor(R.color.snow));
        timer = new CountDownTimer(Constants.TIMEOUT * 1000, 1000) {
            @Override
            public void onTick(long remainTime) {
                 int remain = (int) (remainTime / 1000L);
                if (remain != 1) {
                    mButtonGetRegisterNumber.setClickable(false);
                    mButtonGetRegisterNumber.setText("(" + remain + ")s后可重新发送");
                } else {
                     mButtonGetRegisterNumber.setText(getResources().getString(R.string.get_register_number));
                }
            }
            @Override
            public void onFinish() {
                mButtonGetRegisterNumber.setClickable(true);
                mButtonGetRegisterNumber.setBackgroundColor(getResources().getColor(R.color.DodgerBlue));
            }
        };
        timer.start();
        LogUtil.e(TAG, "发送注册号码");
        CommonUtil.toastMessage("发送注册号码");
    }
    @Override
    public void setOnClick() {
        mButtonRegister.setOnClickListener(this);
        mButtonGetRegisterNumber.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle onSavedInstanceState) {
        super.onSaveInstanceState(onSavedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void registerUser() {//点击注册按钮
        if(!verify_phonenumber_password_validation())
            return;
        LogUtil.e(TAG ,"registerUser");
        mRegisterNumberInput = mEditTextRegisterNumber.getText().toString();
        AVUser.verifyMobilePhoneInBackground(mRegisterNumberInput, new AVMobilePhoneVerifyCallback() {
            @Override
            public void done(AVException e) {
                // TODO Auto-generated method stub
                Message msg = new Message();
                if( e == null) {
                    IS_VERIFY_SMS_SUCCESS = true;
                    msg.what = VERIFY_SMS_SUCCESS_MSG;
                }else {
                    IS_VERIFY_SMS_SUCCESS = false;
                    msg.what = VERIFY_SMS_FAIL_MSG;
                    VERIFY_SMS_ERROR_MSG = e.getLocalizedMessage();
                    VERIFY_SMS_FAIL_ERROR_CODE = e.getCode();
                    if(e.getCode() == Constants.AVOS_ERROR_CODE_INVALID_SNS_CODE) {
                        CommonUtil.toastMessage(getResources().getString(R.string.invalid_sms_code));
                        VERIFY_SMS_ERROR_MSG = getResources().getString(R.string.invalid_sms_code);
                    }
                }
                handler.sendMessage(msg);

            }
        });

    }

    private boolean verify_phonenumber_password_validation() {
        // 验证手机号码和密码的合法性
        mTelephoneNumberInput =  mEditTextTelephoneNumber.getText().toString();
        mPasswordInput = mEditTextPassword.getText().toString();
        mPasswordComfirmInput = mEditTextComfirmPassword.getText().toString();
        if(mTelephoneNumberInput.length() != 11) {
            CommonUtil.toastMessage(getResources().getString(R.string.telephone_number_length_error));
            return false;
        }
        if(mPasswordInput.isEmpty()) {
            CommonUtil.toastMessage(getResources().getString(R.string.password_cannot_empty));
            return false;
        }
        if(mPasswordComfirmInput.isEmpty()) {
            CommonUtil.toastMessage(getResources().getString(R.string.comfirm_password_cannot_empty));
            return false;
        }
        if(!mPasswordInput.equals(mPasswordComfirmInput)) {
            LogUtil.e(TAG , "密码 ="+mPasswordInput+"  确认密码 "+mPasswordComfirmInput);
            CommonUtil.toastMessage(getResources().getString(R.string.password_comfirm_error));
            return false;
        }

        return  true;
    }

    private boolean signUp() {
        if(!verify_phonenumber_password_validation())
            return false;
        user = new AVUser();
        user.setUsername(mTelephoneNumberInput);
        user.setPassword(mPasswordInput);
        user.setMobilePhoneNumber(mTelephoneNumberInput);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                Message msg = new Message();
                if (e == null) {
                        IS_SIGN_UP_SUCCESS =true;
                        msg.what = SIGN_UP_SUCCESS_MSG;
                    } else {
                        IS_SIGN_UP_SUCCESS =false;
                        msg.what = SIGN_UP_FAIL_MSG;
                        SIGN_UP_FAIL_ERROR_CODE = e.getCode();
                        SIGNUP_ERROR_MSG = e.getLocalizedMessage();
                        if (e.getCode() == AVException.USER_MOBILE_PHONENUMBER_TAKEN) {
                           SIGNUP_ERROR_MSG = getResources().getString(R.string.user_mobile_phonenumber_taken);
                            // LogUtil.e(TAG, "error code  " + AVException.USER_MOBILE_PHONENUMBER_TAKEN);
                            //  LogUtil.e(TAG, "error message  " + e.getLocalizedMessage());
                            //  CommonUtil.toastMessage(getResources().getString(R.string.user_mobile_phonenumber_taken));
                        }
                        e.printStackTrace();
                    }
                handler.sendMessage(msg);
            }
        });

        return  true;
    }
    private void getRegisterNumber() {//点击获取注册码按钮
        AVUser.requestMobilePhoneVerifyInBackground(mTelephoneNumberInput, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                Message msg = new Message();
                if (e == null) {
                    IS_REQUEST_SMS_SUCCESS = true;
                    msg.what = REQUEST_SMS_SUCCESS_MSG;
                    LogUtil.e(TAG, "Register Number send no error" + mTelephoneNumberInput);

                } else {
                    IS_REQUEST_SMS_SUCCESS = false;
                    msg.what = REQUEST_SMS_FAIL_MSG;
                    REQUEST_MOBILEPHONE_VERIFY_ERROR_MSG = e.getLocalizedMessage();
                    REQUEST_SMS_FAIL_ERROR_CODE = e.getCode();
                    LogUtil.e(TAG, "requestMobilePhoneVerifyInBackground error code  " + REQUEST_SMS_FAIL_ERROR_CODE);
                }
                handler.sendMessage(msg);
            }
        });

    }


    @Override
    public void onClick(View view) {
        if(CommonUtil.isFastDoubleClick(2*1000))
            return;
        int id = view.getId();
        switch (id) {
            case R.id.btn_do_register:
                LogUtil.e(TAG ,"注册");
                mTextViewSMSErrorMsg.setText("");
                mTextViewRegisterErrorMsg.setText("");
                registerUser();
                break;
            case R.id.btn_do_send_register_number:
                mTextViewSMSErrorMsg.setText("");
                mTextViewRegisterErrorMsg.setText("");
                signUp();
                break;
            default:
                CommonUtil.toastMessage("error in register Activity");
        }
    }

    class signUpTask extends AsyncTask<Void  ,Void ,Boolean> {
        @Override
        protected  Boolean doInBackground(Void ... v) {

                return true;
        }

        @Override
        protected  void onPostExecute(Boolean isSuccess) {

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LogUtil.e(TAG, "onBackPressed()");
    }
}

//
//    class getRegisterNumberCountDownTask extends AsyncTask<Integer , Integer ,Void> {
//        private CountDownTimer timer = null;
//        public getRegisterNumberCountDownTask() {
//            super();
//            LogUtil.e(TAG, "getRegisterNumberCountDownTask");
//
//        }
//        @Override
//        public Void doInBackground(Integer ... countDown) {
//            Looper.prepare();
//            LogUtil.e(TAG, "current thread in doInBackground" + Thread.currentThread());
//            LogUtil.e(TAG, "countDown init" + countDown[0]);
//            LogUtil.e(TAG, "countDown init countDown[0].intValue()" + countDown[0].intValue());
//            timer = new CountDownTimer(countDown[0].intValue()*1000 , 1000) {
//                @Override
//                public void onTick(long remainTime) {
//                    publishProgress((int) (remainTime / 1000L));
//                    LogUtil.e(TAG,
//                            "current thread  in onTick" + Thread.currentThread() + "remainTime"+remainTime / 1000L);
//                }
//
//                @Override
//                public void onFinish() {
//                    LogUtil.e(TAG, "onFinish");
//                    Void v = null;
//                    onPostExecute(v);
//                    if(timer != null) {
//                        timer.cancel();
//                    }
//                }
//            };
//            timer.start();
//            Looper.loop();
//            LogUtil.e(TAG,"doinBackGround end");
//            return null;
//        }
//        @Override
//        public void onPostExecute(Void v) {
//            LogUtil.e(TAG, "onPostExecute");
//            return;
////            mButtonGetRegisterNumber.setText(getResources().getString(R.string.get_register_number));
//
//        }
//        @Override
//        public void onProgressUpdate(Integer ... count) {
//            LogUtil.e(TAG, " count[0].intValue()" + count[0].intValue());
//            if (count[0].intValue() != 1) {
//                mButtonGetRegisterNumber.setClickable(false);
//                LogUtil.e(TAG, "onProgressUpdate" + count[0].intValue());
//                mButtonGetRegisterNumber.setText("(" + count[0].intValue() + ")s后重新发送");
//
//            } else {
//                if(timer != null) {
//                    LogUtil.e(TAG,"timer.cancel()");
//                    timer.cancel();
//                }
//                LogUtil.e(TAG,"mButtonGetRegisterNumber.setClickable(true);");
//                mButtonGetRegisterNumber.setClickable(true);
//                mButtonGetRegisterNumber.setText(getResources().getString(R.string.get_register_number));
//            }
//        }
//
//    }
