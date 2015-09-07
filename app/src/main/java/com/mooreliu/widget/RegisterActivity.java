package com.mooreliu.widget;

/**
 * Created by mooreliu on 2015/9/7.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.mooreliu.R;
import com.mooreliu.util.CommonUtil;
import com.mooreliu.util.Constants;
import com.mooreliu.util.LogUtil;

public class RegisterActivity extends BaseActivity implements OnClickListener{
    private static final String TAG = "RegisterActivity";
    private EditText mEditTextTelephoneNumber;
    private EditText mEditTextRegisterNumber;
    private EditText mEditTextPassword;
    private EditText mEditTextComfirmPassword;
    private Button mButtonRegister;
    private Button mButtonGetRegisterNumber;
    private CountDownTimer timer;

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
    }

    @Override
    public void initView() {

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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_do_register:
                LogUtil.e(TAG ,"注册");
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
                 mButtonGetRegisterNumber.setBackgroundColor(getResources().getColor(R.color.snow));
                 timer = new CountDownTimer(Constants.TIMEOUT*1000 , 1000) {
                    @Override
                    public void onTick(long remainTime) {
//                        LogUtil.e(TAG, "current thread  in onTick" + Thread.currentThread() + "remainTime"+remainTime / 1000L);
                        int remain = (int)(remainTime/1000L);
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
//                new getRegisterNumberCountDownTask().execute(Constants.TIMEOUT);
                break;
            default:
                CommonUtil.toastMessage("error in register Acitivyt");
        }
    }

    class getRegisterNumberCountDownTask extends AsyncTask<Integer , Integer ,Void> {
        private CountDownTimer timer = null;
        public getRegisterNumberCountDownTask() {
            super();
            LogUtil.e(TAG, "getRegisterNumberCountDownTask");

        }
        @Override
        public Void doInBackground(Integer ... countDown) {
            Looper.prepare();
            LogUtil.e(TAG, "current thread in doInBackground" + Thread.currentThread());
            LogUtil.e(TAG, "countDown init" + countDown[0]);
            LogUtil.e(TAG, "countDown init countDown[0].intValue()" + countDown[0].intValue());
            timer = new CountDownTimer(countDown[0].intValue()*1000 , 1000) {
                @Override
                public void onTick(long remainTime) {
                    publishProgress((int) (remainTime / 1000L));
                    LogUtil.e(TAG,
                            "current thread  in onTick" + Thread.currentThread() + "remainTime"+remainTime / 1000L);
                }

                @Override
                public void onFinish() {
                    LogUtil.e(TAG, "onFinish");
                    Void v = null;
                    onPostExecute(v);
                    if(timer != null) {
                        timer.cancel();
                    }
                }
            };
            timer.start();
            Looper.loop();
            LogUtil.e(TAG,"doinBackGround end");
            return null;
        }
        @Override
        public void onPostExecute(Void v) {
            LogUtil.e(TAG, "onPostExecute");
            return;
//            mButtonGetRegisterNumber.setText(getResources().getString(R.string.get_register_number));

        }
        @Override
        public void onProgressUpdate(Integer ... count) {
            LogUtil.e(TAG, " count[0].intValue()" + count[0].intValue());
            if (count[0].intValue() != 1) {
                mButtonGetRegisterNumber.setClickable(false);
                LogUtil.e(TAG, "onProgressUpdate" + count[0].intValue());
                mButtonGetRegisterNumber.setText("(" + count[0].intValue() + ")s后重新发送");

            } else {
                if(timer != null) {
                    LogUtil.e(TAG,"timer.cancel()");
                    timer.cancel();
                }
                LogUtil.e(TAG,"mButtonGetRegisterNumber.setClickable(true);");
                mButtonGetRegisterNumber.setClickable(true);
                mButtonGetRegisterNumber.setText(getResources().getString(R.string.get_register_number));
            }
        }

    }
}
