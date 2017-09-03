package cn.chronoswap.chronoswap.UI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ZeroGo on 2017/9/3.
 */

public class FragmentsAdapter extends FragmentPagerAdapter {
    FragmentsAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new TaskFragment();
            case 1: return new AcceptedFragment();
            case 2: return new IssuedFragment();
            case 3: return new ProfileFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
