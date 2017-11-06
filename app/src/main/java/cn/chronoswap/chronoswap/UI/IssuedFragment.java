package cn.chronoswap.chronoswap.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.chronoswap.chronoswap.R;

public class IssuedFragment extends Fragment {

    Toolbar tbCreate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
/*        tbCreate = (Toolbar) getActivity().findViewById(R.id.issued_tb_create);
        ((AppCompatActivity) getActivity()).setSupportActionBar(tbCreate);
        tbCreate.setNavigationIcon(R.drawable.ic_task_create);
        setHasOptionsMenu(true);
        tbCreate.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_create:
                        Intent intent = new Intent(getActivity(), TaskCreateActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });*/
        return inflater.inflate(R.layout.fragment_issued_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


/*    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.e(TAG, "onCreateOptionsMenu()");
        menu.clear();
        inflater.inflate(R.menu.menu_fragment_issued, menu);
    }*/
}
