package com.mooreliu.widget;

/**
 * Created by mooreliu on 2015/9/7.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
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

import java.io.IOException;

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
    private boolean isGetRegisterNumberSuccess =false;
    private AVUser user;
    private int SIGNUP_ERROR_CODE;
    private int VERIFY_SMS_ERROR_CODE;
    private String SIGNUP_ERROR_MSG ;
    private String VERIFY_SMS_ERROR_MSG;
    private String REQUEST_MOBILEPHONE_VERIFY_ERROR_MSG;

    private Handler handler = null;
    private static final int SIGN_UP_OVER_MSG = 1;
    private static final int REQUEST_SMS_OVER_MSG = 3;
    private static final int VERIFY_SMS_OVER_MSG = 5;

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

        mButtonRegister = (Button) findViewById(R.id.btn_do_register);
        mButtonGetRegisterNumber = (Button) findViewById(R.id.btn_do_send_register_number);

        mTextViewRegisterErrorMsg =(TextView) findViewById(R.id.register_error_message);
        mTextViewSMSErrorMsg =(TextView) findViewById(R.id.sms_error_message);

    }

    @Override
    public void initView() {
        setToolBarTitle(getResources().getString(R.string.register));
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                case SIGN_UP_OVER_MSG:
                    if(!TextUtil.isEmpty(SIGNUP_ERROR_MSG))
                    mTextViewSMSErrorMsg.setText(SIGNUP_ERROR_MSG);
                    break;

                case VERIFY_SMS_OVER_MSG:
                    if(!TextUtil.isEmpty(VERIFY_SMS_ERROR_MSG))
                        mTextViewRegisterErrorMsg.setText(VERIFY_SMS_ERROR_MSG);
                    break;
                case REQUEST_SMS_OVER_MSG:
                    if(!TextUtil.isEmpty(REQUEST_MOBILEPHONE_VERIFY_ERROR_MSG))
                        mTextViewSMSErrorMsg.setText(VERIFY_SMS_ERROR_MSG);
                    break;

                }

            }
        };
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
        LogUtil.e(TAG ,"registerUser");
        mRegisterNumberInput = mEditTextRegisterNumber.getText().toString();
        AVUser.verifyMobilePhoneInBackground(mRegisterNumberInput, new AVMobilePhoneVerifyCallback() {
            @Override
            public void done(AVException e) {
                // TODO Auto-generated method stub
                if( e == null) {
                    CommonUtil.toastMessage(getResources().getString(R.string.register_succuess));
                }else {
                    VERIFY_SMS_ERROR_MSG = e.getLocalizedMessage();
                    if(e.getCode() == Constants.AVOS_ERROR_CODE_INVALID_SNS_CODE) {
                        CommonUtil.toastMessage(getResources().getString(R.string.invalid_sms_code));
                    }
                }

            }
        });
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        displayErrorMessage();
    }

    private boolean verify_phonenumber_password_validation() {
        // 验证手机号码和密码的合法性
        mTelephoneNumberInput =  mEditTextTelephoneNumber.getText().toString();
        mPasswordInput = mEditTextPassword.getText().toString();
        mPasswordComfirmInput = mEditTextComfirmPassword.getText().toString();
        if(mPasswordInput.isEmpty()) {
            CommonUtil.toastMessage(getResources().getString(R.string.password_cannot_empty));
            return false;
        }

        if(!mPasswordInput.equals(mPasswordComfirmInput)) {
            LogUtil.e(TAG , "密码 ="+mPasswordInput+"  确认密码 "+mPasswordComfirmInput);
            CommonUtil.toastMessage(getResources().getString(R.string.password_comfirm_error));
            return false;
        }
        if(mTelephoneNumberInput.length() != 11) {
            CommonUtil.toastMessage(getResources().getString(R.string.telephone_number_length_error));
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
        isGetRegisterNumberSuccess = true;
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {

                    } else {
                        isGetRegisterNumberSuccess = false;
                        SIGNUP_ERROR_CODE = e.getCode();
                        SIGNUP_ERROR_MSG = e.getLocalizedMessage();
                        if (e.getCode() == AVException.USER_MOBILE_PHONENUMBER_TAKEN) {
                            LogUtil.e(TAG, "error code  " + AVException.USER_MOBILE_PHONENUMBER_TAKEN);
                            LogUtil.e(TAG, "error message  " + e.getLocalizedMessage());
                            //  CommonUtil.toastMessage(getResources().getString(R.string.user_mobile_phonenumber_taken));
                        }
                        e.printStackTrace();
                    }
            }
        });
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    user.signUp();
//                } catch (AVException e) {
//                    if (e == null) {
//
//                    } else {
//                        isGetRegisterNumberSuccess = false;
//                        SIGNUP_ERROR_CODE = e.getCode();
//                        SIGNUP_ERROR_MSG = e.getLocalizedMessage();
//                        if (e.getCode() == AVException.USER_MOBILE_PHONENUMBER_TAKEN) {
//                            LogUtil.e(TAG, "error code  " + AVException.USER_MOBILE_PHONENUMBER_TAKEN);
//                            LogUtil.e(TAG, "error message  " + e.getLocalizedMessage());
//                            //  CommonUtil.toastMessage(getResources().getString(R.string.user_mobile_phonenumber_taken));
//                        }
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        thread.start();
        return  true;
    }
    private boolean getRegisterNumber() {//点击获取注册码按钮
        LogUtil.e(TAG ,"getRegisterNumber");
       if( !signUp())
           return false;
        AVUser.requestMobilePhoneVerifyInBackground(mTelephoneNumberInput, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                //发送了验证码以后做点什么呢
                LogUtil.e(TAG, "in done");
                if (e == null) {
                    LogUtil.e(TAG, "Register Number send no error" + mTelephoneNumberInput);
                    isGetRegisterNumberSuccess = true;
                } else {
                    REQUEST_MOBILEPHONE_VERIFY_ERROR_MSG = e.getLocalizedMessage();
                    LogUtil.e(TAG, "requestMobilePhoneVerifyInBackground rror code" + AVException.USER_MOBILE_PHONENUMBER_TAKEN);
                    isGetRegisterNumberSuccess = false;
                }

            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        displayErrorMessage();
        return isGetRegisterNumberSuccess;

    }


    private void displayErrorMessage() {
        LogUtil.e(TAG,"SIGNUP_ERROR_MSG"+SIGNUP_ERROR_MSG);
        LogUtil.e(TAG,"REQUEST_MOBILEPHONE_VERIFY_ERROR_MSG"+REQUEST_MOBILEPHONE_VERIFY_ERROR_MSG);
        LogUtil.e(TAG,"VERIFY_SMS_ERROR_MSG"+VERIFY_SMS_ERROR_MSG);
        if(!TextUtil.isEmpty(SIGNUP_ERROR_MSG)) {
            mTextViewSMSErrorMsg.setText(SIGNUP_ERROR_MSG);
        }
        else if(!TextUtil.isEmpty(REQUEST_MOBILEPHONE_VERIFY_ERROR_MSG)) {
            mTextViewSMSErrorMsg.setText(REQUEST_MOBILEPHONE_VERIFY_ERROR_MSG);
        } else {
            mTextViewSMSErrorMsg.setText("");
        }
        if(!TextUtil.isEmpty(VERIFY_SMS_ERROR_MSG)) {
            mTextViewRegisterErrorMsg.setText(VERIFY_SMS_ERROR_MSG);
        } else {
            mTextViewRegisterErrorMsg.setText("");
        }
        SIGNUP_ERROR_MSG = "";
        REQUEST_MOBILEPHONE_VERIFY_ERROR_MSG = "";
        VERIFY_SMS_ERROR_MSG = "";
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_do_register:
                LogUtil.e(TAG ,"注册");
                registerUser();
                CommonUtil.toastMessage(getResources().getString(R.string.register));
                break;
            case R.id.btn_do_send_register_number:
//                try{
//                    getRegisterNumberCountDownTask task = new getRegisterNumberCountDownTask();
//                    if(task == null)
//                        LogUtil.e(TAG,"task == null");
//                    task.execute(Constants.TIMEOUT);
//                } catch(Exception e){
//                    e.printStackTrace();
//                }
                 if(getRegisterNumber()) {
                     LogUtil.e(TAG,"发送SMS成功");
                     mButtonGetRegisterNumber.setBackgroundColor(getResources().getColor(R.color.snow));
                     timer = new CountDownTimer(Constants.TIMEOUT * 1000, 1000) {
                         @Override
                         public void onTick(long remainTime) {
                             //                        LogUtil.e(TAG, "current thread  in onTick" + Thread.currentThread() + "remainTime"+remainTime / 1000L);
                             int remain = (int) (remainTime / 1000L);
                             if (remain != 1) {
                                 mButtonGetRegisterNumber.setClickable(false);
                                 //                            LogUtil.e(TAG, "onProgressUpdate" + remain);
                                 mButtonGetRegisterNumber.setText("(" + remain + ")s后重新发送");

                             } else {
                                 //                            LogUtil.e(TAG, "mButtonGetRegisterNumber.setClickable(true);");
                                 mButtonGetRegisterNumber.setText(getResources().getString(R.string.get_register_number));
                             }

                         }

                         @Override
                         public void onFinish() {
                             //                        LogUtil.e(TAG, "onFinish");
                             mButtonGetRegisterNumber.setClickable(true);
                             mButtonGetRegisterNumber.setBackgroundColor(getResources().getColor(R.color.DodgerBlue));
                         }
                     };
                     timer.start();
                     LogUtil.e(TAG, "发送注册号码");
                     CommonUtil.toastMessage("发送注册号码");
                 }
//                new getRegisterNumberCountDownTask().execute(Constants.TIMEOUT);
                break;
            default:
                CommonUtil.toastMessage("error in register Acitivyt");
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
}
