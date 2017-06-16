package cn.chronoswap.chronoswap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;

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

        navigationView= ButterKnife.findById(this, R.id.navigation_view);
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

    //个人页面的登录按钮跳转
    public void onLoginButtonClicked(View v){
        Intent intent=new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
