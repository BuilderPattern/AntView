package com.example.administrator.anyview;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Instrumentation;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.anyview.fragments.CenterFragment;
import com.example.administrator.anyview.fragments.HomeFragment;
import com.example.administrator.anyview.fragments.LookForFragment;
import com.example.administrator.anyview.fragments.ResidentFragment;
import com.example.administrator.anyview.my_views.NoSlideViewPager;
import com.example.anyview.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.KITKAT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    NoSlideViewPager mNoSlideViewPager;

    LinearLayout mLinearLayout;

    TextView mHomeTab1;
    TextView mHomeTab2;
    TextView mHomeTab3;
    TextView mHomeTab4;

    MyFragmentAdapter mAdapter;

    int mCurrPage = 0;

    public static WeakReference<MainActivity> mWeakReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDarkImmersion(this);
        setContentView(R.layout.activity_main);
        mWeakReference = new WeakReference<>(MainActivity.this);

        intent = new Intent(MainActivity.this, KeepAliveService.class);
        initViews();
        initEvents();
    }

    public void setDarkImmersion(AppCompatActivity appCompatActivity) {
        if (Build.VERSION.SDK_INT >= KITKAT) {//Android 4.4以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Android 5.0以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android 6.0以上，状态栏颜色：底色为浅色，标签深色（状态栏颜色设置只在6.0之后生效,老版本手机的标签还是白色）
                    appCompatActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    appCompatActivity.getWindow().setStatusBarColor(appCompatActivity.getColor(R.color.color_00000000));
                } else {
                    appCompatActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                    appCompatActivity.getWindow().setStatusBarColor(appCompatActivity.getResources().getColor(R.color.color_33000000));
                }
            } else {
                Window window = appCompatActivity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                window.setAttributes(attributes);
            }
        }

    }


    private void initViews() {

        mNoSlideViewPager = (NoSlideViewPager) findViewById(R.id.main_viewPager);
        mLinearLayout = (LinearLayout) findViewById(R.id.main_tabs);

        mHomeTab1 = (TextView) findViewById(R.id.home_tab_1);
        mHomeTab2 = (TextView) findViewById(R.id.home_tab_2);
        mHomeTab3 = (TextView) findViewById(R.id.home_tab_3);
        mHomeTab4 = (TextView) findViewById(R.id.home_tab_4);

        ArrayList<Fragment> mFragments = new ArrayList<>();

        HomeFragment homeFragment = new HomeFragment();
        ResidentFragment residentFragment = new ResidentFragment();
        LookForFragment lookForFragment = new LookForFragment();
        CenterFragment centerFragment = new CenterFragment();

        mFragments.add(homeFragment);
        mFragments.add(residentFragment);
        mFragments.add(lookForFragment);
        mFragments.add(centerFragment);

        mAdapter = new MyFragmentAdapter(getSupportFragmentManager(), mFragments);
        mNoSlideViewPager.setOffscreenPageLimit(4);

        mNoSlideViewPager.setAdapter(mAdapter);

        mNoSlideViewPager.setCurrentItem(0);

        mHomeTab1.setSelected(true);
        mHomeTab1.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_0786e7));

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, KeepAliveBroadCastReceiver.class);
        intent.setAction("alarm_action");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        manager.cancel(pendingIntent);
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60000, pendingIntent);
    }

    class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            data.readFloat();
            return super.onTransact(code, data, reply, flags);
        }
    }

    private void initEvents() {
        mHomeTab1.setOnClickListener(this);
        mHomeTab2.setOnClickListener(this);
        mHomeTab3.setOnClickListener(this);
        mHomeTab4.setOnClickListener(this);
    }

    Intent intent = null;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_tab_1:
                switchPage(0);
//                startService(intent);
//                stopService(intent);
                break;
            case R.id.home_tab_2:
                switchPage(1);
//                stopService(intent);
                break;
            case R.id.home_tab_3:
                switchPage(2);
//                isBind = bindService(intent, mConnect, BIND_AUTO_CREATE);
                break;
            case R.id.home_tab_4:
                switchPage(3);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(new Intent(this, KeepAliveService.class));
                } else {
                    startService(new Intent(this, KeepAliveService.class));
                }
                bindService(new Intent(this, KeepAliveService.class), mConnection, BIND_AUTO_CREATE);
                break;
            default:
                break;
        }
    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void switchPage(int position) {
        if (position != mCurrPage) {
            TextView mTv1 = (TextView) mLinearLayout.getChildAt(mCurrPage);
            mTv1.setSelected(false);
            mTv1.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_666666));

            mCurrPage = position;
            mNoSlideViewPager.setCurrentItem(mCurrPage);
            mAdapter.notifyDataSetChanged();
            TextView mTv2 = (TextView) mLinearLayout.getChildAt(mCurrPage);
            mTv2.setSelected(true);
            mTv2.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_0786e7));
            switch (position) {
                case 0:
                    mHomeTab1.setSelected(true);
                    break;
                case 1:
                    mHomeTab1.setSelected(true);
                    break;
                case 2:
                    mHomeTab1.setSelected(true);
                    break;
                case 3:
                    mHomeTab1.setSelected(true);
                    break;
                default:
                    break;
            }
        }
    }
}

