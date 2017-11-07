package cn.chronoswap.chronoswap.UI;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.chronoswap.chronoswap.R;
import cn.chronoswap.chronoswap.db.UserInfoManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class TaskFragment1 extends ListFragment {

    private String TAG = TaskFragment1.class.getName();
    private ListView list;
    private SimpleAdapter adapter;
    private Handler mHandler;
    private int num;
    private String[][] strand;

    /**
     * @描述 在onCreateView中加载布局
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //创建viwe和list对象
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        list = (ListView) view.findViewById(android.R.id.list);
        Log.i(TAG, "--------onCreateView");
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //b = savedInstanceState;
        Log.i(TAG, "--------onCreate");
        //loadlist();         //调用函数
        String[][] list = strand;
        //设置adapter
        adapter = new SimpleAdapter(getActivity(), getData(list), R.layout.fragment_task, new String[]{"payment", "time", "title"}, new int[]{R.id.task_tv_payment, R.id.task_tv_task, R.id.task_tv_time});
        setListAdapter(adapter);

    }

    //loadlist
    protected void loadlist() {

        //TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        //String imei = TelephonyMgr.getDeviceId();
        String imei = "355905073732092";     //需要修改355905073732092
        String path = "http://www.chronoswap.cn/mission_info.php";
        //创建okHttpClient对象
        OkHttpClient ohc = new OkHttpClient();
        //表单数据
        RequestBody fb = new FormBody.Builder()
                .add("userid", UserInfoManager.getID(getActivity()))
                .add("session", imei)
                .build();
        //创建一个Request
        Request req = new Request.Builder()
                .url(path)
                .post(fb)
                .build();
        Call call = ohc.newCall(req);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                Message msg = new Message();
                msg.obj = text;
                mHandler.sendMessage(msg);
            }
        });

    }


    public void stropration(Message msg) {              //分拣字符串
        String[] strenter;
        String msgobj;
        msgobj = msg.obj.toString();
        strenter = msgobj.split("\n");          //换行符分割不知道可不可行
        num= strenter.length;
        for (int i = 0; i < strenter.length; i++) {
            strand[i] = strenter[i].split("&");     //按&划分
        }


    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {        //点击事件。待定
        super.onListItemClick(l, v, position, id);

        System.out.println(l.getChildAt(position));
        HashMap<String, Object> view = (HashMap<String, Object>) l.getItemAtPosition(position);
        System.out.println(view.get("title").toString() + "+++++++++title");

        Toast.makeText(getActivity(), TAG + l.getItemIdAtPosition(position), Toast.LENGTH_LONG).show();
        System.out.println(v);

        System.out.println(position);
    }


    private List<? extends Map<String, ?>> getData(String[][] strand) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

       for (int i = 0; i < num; i++) {
           Map<String, Object> map = new HashMap<String, Object>();
           map.put("title", strand[i][2]);
           map.put("payment", strand[i][5]);
           map.put("time", strand[i][3]);

           list.add(map);
        }

/*        for (int i = 0; i < strs.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", strs[i]);
            list.add(map);
        }*/

/*        Map<String, Object> map = new HashMap<String, Object>();
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
        list.add(map);*/

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


}
