package cn.chronoswap.chronoswap.UI;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.chronoswap.chronoswap.R;


public class TaskFragment extends ListFragment {

    private String TAG = TaskFragment.class.getName();
    private ListView list ;
    private SimpleAdapter adapter;

    /**
     * @描述 在onCreateView中加载布局
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container,false);
        list = (ListView) view.findViewById(android.R.id.list);
        Log.i(TAG, "--------onCreateView");
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //b = savedInstanceState;
        Log.i(TAG, "--------onCreate");
        String[] list = {"Class 1","Class 2","class 3","Class 4","Class 5"};
        adapter = new SimpleAdapter(getActivity(), getData(list), R.layout.fragment_task, new String[]{"payment", "time", "title"}, new int[]{R.id.task_tv_payment, R.id.task_tv_task, R.id.task_tv_time});
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        System.out.println(l.getChildAt(position));
        HashMap<String, Object> view= (HashMap<String, Object>) l.getItemAtPosition(position);
        System.out.println(view.get("title").toString()+"+++++++++title");

        Toast.makeText(getActivity(), TAG+l.getItemIdAtPosition(position), Toast.LENGTH_LONG).show();
        System.out.println(v);

        System.out.println(position);
    }





    private List<? extends Map<String, ?>> getData(String[] strs) {
        List<Map<String ,Object>> list = new ArrayList<Map<String,Object>>();

/*        for (int i = 0; i < strs.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", strs[i]);
            list.add(map);
        }*/
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("payment", "G1");
        map.put("time", "google 1");
        map.put("title", "tt");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("payment", "G2");
        map.put("time", "google 2");
        map.put("title", "tt");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("payment", "G3");
        map.put("time", "google 3");
        map.put("title", "tt");
        list.add(map);

        return list;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "--------onActivityCreated");

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "----------onAttach");
    }



















































/*
//failed


    //private TextView thepayment,thetime,thetitle;
    private ListView list;
    private SimpleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        list = (ListView) view.findViewById(android.R.id.list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1);
        list.setAdapter(arrayAdapter);

*/
/*      thepayment = (TextView) view.findViewById(R.id.task_tv_payment);
        thetime = (TextView) view.findViewById(R.id.task_tv_time);
        thetitle = (TextView) view.findViewById(R.id.task_tv_task);*//*

        //return inflater.inflate(R.layout.fragment_task_list, null);                //不知道这行和下面那行哪个是对的
        return view;                                                                 //不知道这行和上面那行哪个是对的--好像这个是对的
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    public class MyListView3 extends ListActivity {


        // private List<String> data = new ArrayList<String>();
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            String[] listItem = { "a", "b", "c", "d", "e" };

        */
/*thepayment = (TextView)findViewById(R.id.task_tv_payment);
        thetime = (TextView) findViewById(R.id.task_tv_time);
        thetitle = (TextView)findViewById(R.id.task_tv_task);
        setContentView(R.layout.fragment_task);*//*



            adapter = new SimpleAdapter(getActivity(), getData(), R.layout.fragment_task,
                    new String[]{"payment", "time", "title"},
                    new int[]{R.id.task_tv_payment, R.id.task_tv_task, R.id.task_tv_time});
            setListAdapter(adapter);
        }

*/
/*        public void onCreate(Bundle savedInstanceState) {             //old version
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_task);


            thepayment = (TextView)findViewById(R.id.task_tv_payment);
            thetime = (TextView) findViewById(R.id.task_tv_time);
            thetitle = (TextView)findViewById(R.id.task_tv_task);


            setContentView(R.layout.fragment_task);
            SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.fragment_task,
                    new String[]{"payment", "time", "title"},
                    new int[]{R.id.task_tv_payment, R.id.task_tv_task, R.id.task_tv_time});
            setListAdapter(adapter);
        }*//*


        private List<Map<String, Object>> getData() {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();


*/
/*//*
/每一块是一个list的内容
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("payment", "G1");
            map.put("time", "google 1");
            map.put("title", "tt");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("payment", "G2");
            map.put("time", "google 2");
            map.put("title", "tt");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("payment", "G3");
            map.put("time", "google 3");
            map.put("title", "tt");
            list.add(map);*//*


            for (int i = 0; i < 5; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("payment", "G3");
                map.put("time", "google 3");
                map.put("title", "tt");
                list.add(map);

            }



            return list;
        }
    }

*/

}
