package cn.chronoswap.chronoswap.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import cn.chronoswap.chronoswap.R;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    //初始化Fragment
    private void initView() {
        viewPager = ButterKnife.findById(this, R.id.viewPager);
        viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem == null) {
                    navigationView.getMenu().getItem(0).setChecked(false);
                } else
                    menuItem.setChecked(false);
                menuItem = navigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigationView = ButterKnife.findById(this, R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_task:
                        viewPager.setCurrentItem(0);
                        break;

                    case R.id.main_accepted:
                        viewPager.setCurrentItem(1);
                        break;

                    case R.id.main_issued:
                        viewPager.setCurrentItem(2);
                        break;

                    case R.id.main_profile:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
        BottomNavigationViewHelper.disableShiftMode(navigationView);
    }

    //个人页面的个人资料跳转
    public void onInfoButtonClicked(View v) {
        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
        startActivity(intent);
    }

    //个人页面的账户余额跳转
    public void onBalanceButtonClicked(View v) {
        Intent intent = new Intent(MainActivity.this, BalanceActivity.class);
        startActivity(intent);
    }

    //个人页面的任务历史按钮跳转
    public void onHistoryButtonClicked(View v) {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }

    //个人页面的设置按钮跳转
    public void onSettingButtonClicked(View v) {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    //个人页面的注销按钮跳转
    public void onLogoutButtonClicked(View v) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //发布任务按钮跳转
    public void onCreateButtonClicked(View v) {
        Intent intent = new Intent(MainActivity.this, TaskCreateActivity.class);
        startActivity(intent);
    }
}
