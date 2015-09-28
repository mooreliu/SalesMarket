package com.mooreliu.widget; /**
 * user:mooreliu createTime:2015-08-29 MainActivity主界面
 */

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.mooreliu.receiver.BroadcastReceiverNetCheck;
import com.mooreliu.task.UpdateVersionTask;
import com.mooreliu.util.CommonUtil;
import com.mooreliu.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseObserverActivity implements View.OnClickListener {
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
	private TextView mLoginOrLoginout;
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setDrawer();
		checkUserState();
		mContext = this;
	}

	@Override
	public void findView() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mLeftMenu1 = findViewById(R.id.item_menu_1);
		mLeftMenu2 = findViewById(R.id.item_menu_2);
		mLeftMenu3 = findViewById(R.id.item_menu_update);
		mLeftMenu4 = findViewById(R.id.item_menu_login_logout);
		login_avatar = (ImageView) findViewById(R.id.user_image_iv);
		mTextViewUserName = (TextView) findViewById(R.id.username_tv);
		mLoginOrLoginout = (TextView) findViewById(R.id.left_menu4_tv);
	}

	@Override
	public void initView() {
		setToolBarTitle("首页");
		setTabLayout();
		setViewPager();
		if (UserUtil.isLogined()) mLoginOrLoginout.setText(getResources().getString(R.string.logout));
		else mLoginOrLoginout.setText(getResources().getString(R.string.tv_login));
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
	public void onClick(View view) {
		int id = view.getId();
		Handler handler = new Handler();
		switch (id) {
			case R.id.item_menu_1:
				mLeftMenu1.setBackgroundResource(R.color.gray);
				mLeftMenu2.setBackgroundResource(R.color.white);
				mLeftMenu3.setBackgroundResource(R.color.white);
				mLeftMenu4.setBackgroundResource(R.color.white);
				Runnable r = new Runnable() {
					@Override
					public void run() {
						mLeftMenu1.setBackgroundResource(R.color.white);
					}
				};
				handler.postDelayed(r, 140);
				break;
			case R.id.item_menu_2:
				mLeftMenu1.setBackgroundResource(R.color.white);
				mLeftMenu2.setBackgroundResource(R.color.gray);
				mLeftMenu3.setBackgroundResource(R.color.white);
				mLeftMenu4.setBackgroundResource(R.color.white);
				r = new Runnable() {
					@Override
					public void run() {
						mLeftMenu2.setBackgroundResource(R.color.white);
					}
				};
				handler.postDelayed(r, 140);
				break;
			case R.id.item_menu_update:
				// update version
				mLeftMenu1.setBackgroundResource(R.color.white);
				mLeftMenu2.setBackgroundResource(R.color.white);
				mLeftMenu3.setBackgroundResource(R.color.gray);
				mLeftMenu4.setBackgroundResource(R.color.white);
				r = new Runnable() {
					@Override
					public void run() {
						mLeftMenu3.setBackgroundResource(R.color.white);
					}
				};
				handler.postDelayed(r, 140);
				// run update job
				showUpdateDialog();
				break;
			case R.id.item_menu_login_logout:
				//login && logout
				mLeftMenu1.setBackgroundResource(R.color.white);
				mLeftMenu2.setBackgroundResource(R.color.white);
				mLeftMenu3.setBackgroundResource(R.color.white);
				mLeftMenu4.setBackgroundResource(R.color.gray);
				if (UserUtil.isLogined()) {
					UserUtil.loginout();
					if (UserUtil.isLoginoutSuccess()) {
						Notify.getInstance().NotifyActivity(new NotifyInfo(EventType.EVENT_LOGINOUT));//通知登录成功
						CommonUtil.toastMessage("注销成功");
						mLoginOrLoginout.setText(getResources().getString(R.string.tv_login));
						mLeftMenu4.setBackgroundResource(R.color.white);
					}
				} else {
					mLoginOrLoginout.setText(getResources().getString(R.string.tv_logout));
					Intent intent = new Intent(this, LoginActivity.class);
					//startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
					startActivity(intent);
					mLeftMenu4.setBackgroundResource(R.color.white);
				}
				break;
			case R.id.user_image_iv:
				mDrawerLayout.closeDrawers();
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				break;
		}
	}

	private void setDrawer() {
		mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close) {
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
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
		for (int i = 0; i < 3; i++) mTabLayout.addTab(mTabLayout.newTab());
		List<Fragment> fragmentList = new ArrayList<>();
		TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager());/**/
		mViewPager.setAdapter(fragmentAdapter);/*给ViewPager设置适配器*/
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(final int position, final float v, final int i2) {
			}

			@Override
			public void onPageSelected(final int position) { /*LogUtil.e(TAG, "position onPageSelected" + position);*/ }

			@Override
			public void onPageScrollStateChanged(final int position) {
			}
		});
		mViewPager.setOffscreenPageLimit(1);
		mTabLayout.setupWithViewPager(mViewPager);/*将TabLayout和ViewPager关联起来。*/
		mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);/*给Tabs设置适配器*/
	}

	public void setViewPager() {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (CommonUtil.isFastDoubleClick(2 * 1000))
			return false;
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
			return true;
		} else if (id == R.id.action_refresh) {
			Toast.makeText(this, "刷新", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected String[] getObserverEventType() {
		return new String[]{
						EventType.EVENT_LOGIN,
						EventType.EVENT_LOGINOUT
		};
	}

	@Override
	protected void onChange(NotifyInfo notifyInfo) {
		String eventType = notifyInfo.getEventType();
		if (eventType.equals(EventType.EVENT_LOGIN)) {
			login_avatar.setImageResource(R.drawable.avatar_logined);
			login_avatar.setClickable(false);
			mLoginOrLoginout.setText(getResources().getString(R.string.tv_logout));
			mTextViewUserName.setText("已登陆");
		} else if (eventType.equals(EventType.EVENT_LOGINOUT)) {
			login_avatar.setImageResource(R.drawable.avatar_logout);
			mTextViewUserName.setText(getResources().getString(R.string.unlogined));
			mLoginOrLoginout.setText(getResources().getString(R.string.tv_login));
			login_avatar.setClickable(true);
		}
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
		ExitDialog dialog = new ExitDialog(MainActivity.this);
		dialog.show();
	}


	private void checkUserState() { /* 检查用户登陆状态*/
		if (UserUtil.isLogined())
			Notify.getInstance().NotifyActivity(new NotifyInfo(EventType.EVENT_LOGIN));
		else Notify.getInstance().NotifyActivity(new NotifyInfo(EventType.EVENT_LOGINOUT));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (br != null) unregisterReceiver(br);
	}

	private void showUpdateDialog() {
		Builder builder = new Builder(this);
		builder.setTitle(getString(R.string.version_update));
		builder.setMessage(getString(R.string.update_message));
		builder.setPositiveButton(getString(R.string.do_download), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				UpdateVersionTask task = new UpdateVersionTask(mContext);
				task.execute();
			}
		});
		builder.setNegativeButton(getString(R.string.ask_later), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.dismiss();
			}
		});
		builder.create().show();
	}


}
