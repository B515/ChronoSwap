package cn.chronoswap.chronoswap.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.chronoswap.chronoswap.R;
import cn.chronoswap.chronoswap.db.UserInfoManager;

public class ProfileFragment extends Fragment {

    TextView tvNickname, tvID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvNickname = (TextView) getActivity().findViewById(R.id.profile_tv_nickname);
        tvID = (TextView) getActivity().findViewById(R.id.profile_tv_id);
        tvNickname.setText(UserInfoManager.getNickname(getActivity()));
        tvID.setText("ID:" + UserInfoManager.getID(getActivity()));
    }
}
