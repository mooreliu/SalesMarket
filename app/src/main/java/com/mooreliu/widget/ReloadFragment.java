package com.mooreliu.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.mooreliu.R;
import com.mooreliu.util.CommonUtil;

/**
 * Created by mooreliu on 2015/9/11.
 */
public class ReloadFragment extends Fragment implements OnClickListener{
    private static final String TAG = "ReloadFragment";
    private View rootView;
    Button mReloadButton;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_reload, container, false);
        findView();
        initView();
        setOnClick();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle onSavedInstanceState) {
        super.onActivityCreated(onSavedInstanceState);
    }

    private void findView() {
        mReloadButton = (Button) rootView.findViewById(R.id.reload);
    }

    private void initView() {

    }

    private void setOnClick() {
        if(mReloadButton == null)
            mReloadButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.reload:
                CommonUtil.toastMessage(getResources().getString(R.string.reload));
                break;
            default:
                break;
        }
    }

}
