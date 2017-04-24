package cn.chronoswap.chronoswap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Fragment[] fragments;
    public TaskFragment taskFragment;
    private AcceptedFragment acceptedFragment;
    private IssuedFragment issuedFragment;
    private ProfileFragment profileFragment;
    private ImageView[] imagebuttons;
    private TextView[] textviews;

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
        imagebuttons = new ImageView[4];
        imagebuttons[0] = (ImageView) findViewById(R.id.main_ib_task);
        imagebuttons[1] = (ImageView) findViewById(R.id.main_ib_accepted);
        imagebuttons[2] = (ImageView) findViewById(R.id.main_ib_issued);
        imagebuttons[3] = (ImageView) findViewById(R.id.main_ib_profile);
        imagebuttons[0].setSelected(true);

        //文字
        textviews = new TextView[4];
        textviews[0] = (TextView) findViewById(R.id.main_tv_task);
        textviews[1] = (TextView) findViewById(R.id.main_tv_accepted);
        textviews[2] = (TextView) findViewById(R.id.main_tv_issued);
        textviews[3] = (TextView) findViewById(R.id.main_tv_profile);
        textviews[0].setTextColor(0xFF45C01A);

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
        imagebuttons[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        imagebuttons[index].setSelected(true);
        textviews[currentTabIndex].setTextColor(0xFF999999);
        textviews[index].setTextColor(0xFF45C01A);
        currentTabIndex = index;
    }
}
