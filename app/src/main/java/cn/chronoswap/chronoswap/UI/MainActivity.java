package cn.chronoswap.chronoswap.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import cn.chronoswap.chronoswap.R;

public class MainActivity extends AppCompatActivity {
    private Fragment[] fragments;
    public TaskFragment taskFragment;
    private AcceptedFragment acceptedFragment;
    private IssuedFragment issuedFragment;
    private ProfileFragment profileFragment;
    private BottomNavigationView navigationView;

    private int index;
    // 当前fragment的index
    private int currentTabIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    //下拉菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //初始化Fragment
    private void initView() {

        taskFragment = new TaskFragment();
        acceptedFragment = new AcceptedFragment();
        issuedFragment = new IssuedFragment();
        profileFragment = new ProfileFragment();

        fragments = new Fragment[]{taskFragment, acceptedFragment, issuedFragment, profileFragment};

        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, taskFragment)
                .add(R.id.fragment_container, acceptedFragment)
                .add(R.id.fragment_container, issuedFragment)
                .add(R.id.fragment_container, profileFragment)
                .hide(acceptedFragment)
                .hide(issuedFragment)
                .hide(profileFragment)
                .show(taskFragment)
                .commit();

        navigationView = ButterKnife.findById(this, R.id.navigation_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_task:
                        index = 0;
                        break;

                    case R.id.main_accepted:
                        index = 1;
                        break;

                    case R.id.main_issued:
                        index = 2;
                        break;

                    case R.id.main_profile:
                        index = 3;
                        break;
                }

                if (currentTabIndex != index) {
                    FragmentTransaction trx = getSupportFragmentManager()
                            .beginTransaction();
                    trx.hide(fragments[currentTabIndex]);
                    if (!fragments[index].isAdded()) {
                        trx.add(R.id.fragment_container, fragments[index]);
                    }
                    trx.show(fragments[index]).commit();
                }
                currentTabIndex = index;
                return true;
            }
        });
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
