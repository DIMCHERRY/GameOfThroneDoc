package com.littlered.gameofthronedoc.base;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.littlered.gameofthronedoc.R;
import com.littlered.gameofthronedoc.util.ActivityManagerUtils;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 是否沉浸状态栏
     **/
    private boolean isSetStatusBar = false;
    /**
     * 是否允许全屏
     **/
    private boolean mAllowFullScreen = false;
    /**
     * 是否禁止旋转屏幕
     **/
    private boolean isAllowScreenRoate = false;
    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;
    /**
     * 日志输出标志
     **/
    private final String TAG = this.getClass().getSimpleName();

    private long lastClickTime;



    /**
     * toolbar的图标
     */
    private boolean picToolbar = true;
    /**
     * 是否含有toolbar
     */
    private boolean hasToolbar = true;
    /**
     * toolbar的标题
     */
    private int mToolbarTitle = 0;

    /**
     * View点击
     **/
    protected abstract void widgetClick(View v);

    protected LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    public void setPicToolbar(boolean picToolbar) {
        this.picToolbar = picToolbar;
    }

    public void setmToolbarTitle(int mToolbarTitle) {
        this.mToolbarTitle = mToolbarTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "BaseActivity-->onCreate()");
        Bundle bundle = getIntent().getExtras();

        View mView = bindView();
        if (null == mView) {
            mContextView = LayoutInflater.from(this)
                    .inflate(bindLayout(), null);
        } else {
            mContextView = mView;
        }
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (isSetStatusBar) {
            steepStatusBar();
        }
        setContentView(mContextView);
        if (!isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ButterKnife.bind(this);
        initParms(bundle);
        initView(mContextView);
        initToolbar();
        setListener();
        doBusiness(this);
        //为每一个Activity添加管理
        ActivityManagerUtils.getInstance().addActivity(BaseActivity.this);
    }

    /**
     * 关闭指定页面
     *
     * @param cls 指定界面
     */
    protected void finishThinActivity(Class<?> cls) {
        ActivityManagerUtils.getInstance().finishActivityclass(cls);
    }

    /**
     * 是否处理返回按钮点击事迹
     *
     * @return 是否处理
     */
    protected boolean handleNvgOnClickListener() {
        return false;
    }

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 透明导航栏
//            getWindow().addFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 初始化toolbar
     */
    protected void initToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(picToolbar);
            if (!hasToolbar) {
                getSupportActionBar().hide();
            }
            if (mToolbarTitle != 0) {
                getSupportActionBar().setTitle(mToolbarTitle);
            }
        }
    }

    /**
     * [初始化参数]
     *
     * @param parms
     */
    protected abstract void initParms(Bundle parms);

    /**
     * [绑定视图]
     *
     * @return
     */
    protected abstract View bindView();

    /**
     * [绑定布局]
     *
     * @return
     */
    protected abstract int bindLayout();

    /**
     * [初始化控件]
     *
     * @param view
     */
    protected abstract void initView(final View view);

    /**
     * [绑定控件]
     *
     * @param resId
     * @return
     */
    protected <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    /**
     * [设置监听]
     */
    protected abstract void setListener();

    @Override
    public void onClick(View v) {
        if (!isFastClick(1000)) {
            this.widgetClick(v);
        } else {
            Toast.makeText(getApplicationContext(),R.string.double_click,Toast.LENGTH_SHORT ).show();
        }
    }

    /**
     * 防止快速点击
     */
    private final boolean isFastClick(int millisecond) {
        long curClickTime = System.currentTimeMillis();
        long interval = curClickTime - this.lastClickTime;
        long var10000 = (long) (millisecond - 1);
        if (1L <= interval) {
            if (var10000 >= interval) {
                return true;
            }
        }
        this.lastClickTime = curClickTime;
        return false;
    }

    /**
     * [业务操作]
     *
     * @param mContext
     */
    protected abstract void doBusiness(Context mContext);


    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    protected void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }






    /**
     * [是否允许屏幕旋转]
     *
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }


    /**
     * [是否允许全屏]
     *
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /**
     * [是否设置沉浸状态栏]
     *
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }


}






