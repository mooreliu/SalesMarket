package com.mooreliu.widget;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mooreliu.R;

/**
 * Created by liuyi on 15/8/29.
 */
public class MyPageFragment extends Fragment {
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_mypage, container, false);

        return mView;
    }

    @Override public void onActivityCreated(Bundle onSavedInstanceState) {
        super.onActivityCreated(onSavedInstanceState);

        //Uri uri = Uri.parse(" http://ww2.sinaimg.cn/large/610dc034gw1eqnjfdn45qj20h30mk443.jpg");

        Uri uri = Uri.parse("http://img0.bdstatic.com/img/image/cb5016e0d29c198a7b0e7f3dabf7a1351410240928.jpg");
        SimpleDraweeView draweeView = (SimpleDraweeView)getActivity().findViewById(R.id.my_image_view);
        draweeView.setImageURI(uri);

    }
}
