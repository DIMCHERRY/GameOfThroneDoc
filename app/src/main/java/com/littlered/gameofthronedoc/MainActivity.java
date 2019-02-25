package com.littlered.gameofthronedoc;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.KeyEvent;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.littlered.gameofthronedoc.cultures.PickCulturesFragment;
import com.littlered.gameofthronedoc.view.fragment.PickNameFragment;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private Fragment fg1, fg2, fg3;
    private FragmentManager fgManager;
    private Fragment mLastFragment;
    //存放上一次点击“返回键”的时刻
    private Long mExitTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏掉系统原先的导航栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
/*        //toolbar取代actionbar
        setSupportActionBar(toolbar);*/
        fgManager = getSupportFragmentManager();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_name);
        initMainFragment();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点了返回键
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //初始化页面
    private void initMainFragment() {
        FragmentTransaction fgTransaction = fgManager.beginTransaction();
        if (fg1 == null) {
            fg1 = new PickNameFragment();
            fgTransaction.add(R.id.fl_content, fg1);
        } else {
            fgTransaction.show(fg1);
        }
        fgTransaction.commit();
    }

/*    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
/*        getMenuInflater().inflate(R.menu.toolbar_main, menu);*/
/*        MenuItem searchItem = menu.findItem(R.id.action_menu_search);
        //通过MenuItem得到SearchView
        SearchView mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setIconifiedByDefault(false);*/
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
/*        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_name) {
            if (fg1 == null) {
                fg1 = new PickNameFragment();
            }
            showFragment(fg1);
        } else if (id == R.id.nav_gallery) {
            if (fg2 == null) {
                fg2 = new PickCulturesFragment();
            }
            showFragment(fg2);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //切换fragment
    private void showFragment(Fragment fragment) {
        //为当前fragment，则返回
        if (fragment == mLastFragment) {
            return;
        }
        FragmentTransaction transaction = fgManager.beginTransaction();
        if (!fragment.isAdded()) {
            if (mLastFragment == null) {
                transaction.add(R.id.fl_content, fragment);
            } else {
                transaction.hide(mLastFragment).add(R.id.fl_content, fragment);
            }
        }
        //显示fragment
        if (mLastFragment == null) {
            transaction.show(fragment).commit();
        } else {
            transaction.hide(mLastFragment).show(fragment).commit();
        }
        mLastFragment = fragment;
    }
}
