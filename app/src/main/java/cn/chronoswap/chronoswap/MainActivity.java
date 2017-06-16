package cn.chronoswap.chronoswap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private Fragment[] fragments;
    public TaskFragment taskFragment;
    private AcceptedFragment acceptedFragment;
    private IssuedFragment issuedFragment;
    private ProfileFragment profileFragment;
    private ImageView[] bottomButton;
    private TextView[] bottomText;

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

        //图标
        bottomButton = new ImageView[4];
        bottomButton[0] = ButterKnife.findById(this, R.id.main_ib_task);
        bottomButton[1] = ButterKnife.findById(this, R.id.main_ib_accepted);
        bottomButton[2] = ButterKnife.findById(this, R.id.main_ib_issued);
        bottomButton[3] = ButterKnife.findById(this, R.id.main_ib_profile);
        bottomButton[0].setSelected(true);

        //文字
        bottomText = new TextView[4];
        bottomText[0] = ButterKnife.findById(this, R.id.main_tv_task);
        bottomText[1] = ButterKnife.findById(this, R.id.main_tv_accepted);
        bottomText[2] = ButterKnife.findById(this, R.id.main_tv_issued);
        bottomText[3] = ButterKnife.findById(this, R.id.main_tv_profile);
        bottomText[0].setTextColor(getResources().getColor(R.color.selected));

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
    }

    //点击下边栏按钮的跳转
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.main_re_task_list:
                index = 0;
                break;

            case R.id.main_re_accepted_list:
                index = 1;
                break;

            case R.id.main_re_issued_list:
                index = 2;
                break;

            case R.id.main_re_profile:
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
        bottomButton[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        bottomButton[index].setSelected(true);
        bottomText[currentTabIndex].setTextColor(getResources().getColor(R.color.white));
        bottomText[index].setTextColor(getResources().getColor(R.color.selected));
        currentTabIndex = index;
    }

    //个人页面的登录按钮跳转
    public void onLoginButtonClicked(View v){
        Intent intent=new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
