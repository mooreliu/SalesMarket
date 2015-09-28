package com.mooreliu.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mooreliu.R;

/**
 * User: lizhangqu(513163535@qq.com)
 * Date: 2015-06-26
 * Time: 15:34
 * FIXME
 */

public class ExitDialog extends Dialog {

    private static final String TAG = "ExitDialog";
    private Context mContext;
    private Button mConfirm;
    private Button mCancel;

    public ExitDialog(Context context) {
        super(context, R.style.ExitDialog);
        mContext = context;
    }

    public ExitDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_exit);
        //设置为我们的布局
        this.setCanceledOnTouchOutside(false);
        //设置为点击对话框之外的区域对话框不消失
        mConfirm = (Button) findViewById(R.id.dialog_confirm);
        mCancel = (Button) findViewById(R.id.dialog_cancel);
        //设置事件
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExitDialog.this.dismiss();
            }
        });

    }

}
