package com.mooreliu.widget;

/**
 * user:mooreliu
 * createTime:2015-08-29
 * MainActivity主界面
 */

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mooreliu.R;
import com.mooreliu.adapter.TabFragmentAdapter;
import com.mooreliu.event.EventType;
import com.mooreliu.event.Notify;
import com.mooreliu.event.NotifyInfo;
import com.mooreliu.service.BroadcastReceiverNetCheck;
import com.mooreliu.util.CommonUtil;
import com.mooreliu.util.LogUtil;
import com.mooreliu.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseObserverActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    public static String POSITION = "POSITION";

    private BroadcastReceiverNetCheck br;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar mToolbar;
    private View mLeftMenu1;
    private View mLeftMenu2;
    private View mLeftMenu3;
    private View mLeftMenu4;
    private ImageView login_avatar;
    private TextView mTextViewUserName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDrawer();
        initBroadcastRecevier();
        checkUserState();
    }

    private void checkUserState() {
        // 检查用户登陆状态
        if(UserUtil.isLogined()) {
            Notify.getInstance().NotifyActivity(new NotifyInfo(EventType.EVENT_LOGIN));
        }else {
            Notify.getInstance().NotifyActivity(new NotifyInfo(EventType.EVENT_LOGINOUT));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }

    private void initBroadcastRecevier() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        br = new BroadcastReceiverNetCheck();
        registerReceiver(br, intentFilter);

    }

    @Override
    public void findView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLeftMenu1 =findViewById(R.id.item_menu_1);
        mLeftMenu2 =findViewById(R.id.item_menu_2);
        mLeftMenu3 =findViewById(R.id.item_menu_3);
        mLeftMenu4 =findViewById(R.id.item_menu_4);
        login_avatar = (ImageView) findViewById(R.id.user_image_iv);
        mTextViewUserName = (TextView) findViewById(R.id.username_tv);

    }

    @Override
    public void initView() {
        setToolBarTitle("首页");
        setTabLayout();
        setViewPager();
    }

    private void setDrawer(){
        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    public void setTabLayout() {
        mViewPager = (ViewPager)findViewById(R.id.viewPager);
        mTabLayout = (TabLayout)findViewById(R.id.tabLayout);
//        List<String> tabList = new ArrayList<String>();
//        tabList.add("首页");
//        tabList.add("购物车");
//        tabList.add("我的taobao");
//        tabLayout.setTabTextColors(android.R.color.white, android.R.color.holo_red_dark);//设置TabLayout两种状态
//        mTabLayout.addTab(mTabLayout.newTab().setText(tabList.get(0)));//添加tab选项卡
//        mTabLayout.addTab(mTabLayout.newTab().setText(tabList.get(1)));
//        mTabLayout.addTab(mTabLayout.newTab().setText(tabList.get(2)));
        for(int i = 0;i<3;i++)
            mTabLayout.addTab(mTabLayout.newTab());
        List<Fragment> fragmentList = new ArrayList<>();
        Fragment mMainPageFragment = new MainPageFragment();
        MyPageFragment mMyPageFragment = new MyPageFragment();
        ShoppingListPageFragment mShoppingListPageFragment = new ShoppingListPageFragment();

        fragmentList.add(mMainPageFragment);
        fragmentList.add(mShoppingListPageFragment);
        fragmentList.add(mMyPageFragment);

        TabFragmentAdapter fragmentAdapter =
                new TabFragmentAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(fragmentAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);//给Tabs设置适配器
    }

    public void setViewPager() {

    }

    @Override
    public void setOnClick() {
        mLeftMenu1.setOnClickListener(this);
        mLeftMenu2.setOnClickListener(this);
        mLeftMenu3.setOnClickListener(this);
        mLeftMenu4.setOnClickListener(this);
        login_avatar.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this,"设置",Toast.LENGTH_SHORT).show();
            return true;
        } else  if (id == R.id.action_share) {
            Toast.makeText(this,"分享",Toast.LENGTH_SHORT).show();
            return true;
        } else if(id == R.id.action_trash) {
            Toast.makeText(this,"删除",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onChange(NotifyInfo notifyInfo) {
        String eventType = notifyInfo.getEventType();
        if(eventType.equals(EventType.EVENT_LOGIN)) {
            login_avatar.setImageResource(R.drawable.avatar_logined);
            login_avatar.setClickable(false);
            mTextViewUserName.setText("已登陆");
        } else if(eventType.equals(EventType.EVENT_LOGINOUT)) {
            login_avatar.setImageResource(R.drawable.avatar_logout);
            mTextViewUserName.setText(getResources().getString(R.string.unlogined));
            login_avatar.setClickable(true);
        }

    }

    @Override
    protected String[] getObserverEventType() {
        return new String[]{
                EventType.EVENT_LOGIN,
                EventType.EVENT_LOGINOUT,
                EventType.EVENT_ADD_RECORD
        };
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, mTabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mViewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }

    @Override
    public void onBackPressed() {
        LogUtil.e(TAG, "onBackPressed");
        ExitDialog dialog=new ExitDialog(MainActivity.this);
        dialog.show();
        //super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.item_menu_1:
                mLeftMenu1.setBackgroundResource(R.color.gray);
                mLeftMenu2.setBackgroundResource(R.color.white);
                mLeftMenu3.setBackgroundResource(R.color.white);
                mLeftMenu4.setBackgroundResource(R.color.white);
                break;
            case R.id.item_menu_2:
                mLeftMenu1.setBackgroundResource(R.color.white);
                mLeftMenu2.setBackgroundResource(R.color.gray);
                mLeftMenu3.setBackgroundResource(R.color.white);
                mLeftMenu4.setBackgroundResource(R.color.white);

                break;
            case R.id.item_menu_3:
                mLeftMenu1.setBackgroundResource(R.color.white);
                mLeftMenu2.setBackgroundResource(R.color.white);
                mLeftMenu3.setBackgroundResource(R.color.gray);
                mLeftMenu4.setBackgroundResource(R.color.white);

                break;
            case R.id.item_menu_4:
                mLeftMenu1.setBackgroundResource(R.color.white);
                mLeftMenu2.setBackgroundResource(R.color.white);
                mLeftMenu3.setBackgroundResource(R.color.white);
                mLeftMenu4.setBackgroundResource(R.color.gray);
                UserUtil.loginout();
                if(UserUtil.isLoginoutSuccess()) {
                    Notify.getInstance().NotifyActivity(new NotifyInfo(EventType.EVENT_LOGINOUT));//通知登录成功
                    CommonUtil.toastMessage("注销成功");
                }
                break;
            case R.id.user_image_iv:
                Intent intent = new Intent(this , LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
