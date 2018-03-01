package cn.chronoswap.chronoswap.UI;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.chronoswap.chronoswap.R;
import cn.chronoswap.chronoswap.db.UserInfoManager;
import cn.chronoswap.chronoswap.db.lstDataSplit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import static android.arch.lifecycle.ReflectiveGenericLifecycleObserver.getInfo;


/**
 * Created by lenovo on 2017/11/6.
 */




public class TaskFragment extends Fragment {
    RecyclerView mRecyclerView;
    String msgmsg;
    String nnn="0";
/*    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
        mRecyclerView.setAdapter(new NormalRecyclerViewAdapter(this));
    }*/

    public String getSplited(String string, int m, int n){
        String[] outerString=string.split("&");
//        String[][] innerString=new String[100][];
//        for(int i=0;i<outerString.length;i++){
//            innerString[i] = outerString[i].split("&");
//        }
//        return innerString[m][n];
        return outerString[2];
//        return string;
    }


    private List<lst> tsklst = new ArrayList<>();
    private Handler mHandler;
    private void getlstdata(){
        tsklst.clear();
        lst LL=new lst("1","2",nnn);
        tsklst.add(LL);
        lst LLL=new lst(msgmsg,msgmsg,msgmsg);
        tsklst.add(LLL);
//        lst L0=new lst(getSplited(msgmsg,0,2),getSplited(msgmsg,0,4),getSplited(msgmsg,0,7));
//        tsklst.add(L0);
//            lst L1=new lst(lstDataSplit.getSplited(msgmsg,1,2),lstDataSplit.getSplited(msgmsg,1,4),lstDataSplit.getSplited(msgmsg,1,7));
//            tsklst.add(L1);
//            lst L2=new lst(lstDataSplit.getSplited(msgmsg,2,2),lstDataSplit.getSplited(msgmsg,2,4),lstDataSplit.getSplited(msgmsg,2,7));
//            tsklst.add(L2);
//            lst L3=new lst(lstDataSplit.getSplited(msgmsg,3,2),lstDataSplit.getSplited(msgmsg,3,4),lstDataSplit.getSplited(msgmsg,3,7));
//            tsklst.add(L3);
//            lst L4=new lst(lstDataSplit.getSplited(msgmsg,4,2),lstDataSplit.getSplited(msgmsg,4,4),lstDataSplit.getSplited(msgmsg,4,7));
//            tsklst.add(L4);

    }
    //oncreateview
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        getLstFromServer();
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                msgmsg=msg.obj.toString();
                if (msg.what == 3) {
                    getlstdata();
//                    Toast.makeText(LoginActivity.this, "数据更新失败，请重新登录", Toast.LENGTH_SHORT).show();
                } else if (msg.what == 4) {
                    getlstdata();
//                    UserInfoManager.setUserInfo(LoginActivity.this, msg.obj.toString());
//                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);

                }
//                else tvError.setText((String) msg.obj);
            }
        };



        View view = inflater.inflate(R.layout.fragment_issued_list, container, false);
        getlstdata();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        mRecyclerView=ButterKnife.findById(view,R.id.issued_rv_list);
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
        mRecyclerView.setAdapter(new NormalRecyclerViewAdapter(getActivity(), tsklst));
//        mRecyclerView.setAdapter(new NormalRecyclerViewAdapter(getActivity()));
        return view;
    }
//创建adapter
    public class NormalRecyclerViewAdapter extends RecyclerView.Adapter<NormalRecyclerViewAdapter.NormalTextViewHolder> {
        private final LayoutInflater mLayoutInflater;
        private final Context mContext;
        private  List<lst> tsklst;

        public NormalRecyclerViewAdapter(Context context, List<lst> tsklst) {
            this.tsklst = tsklst;
            mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
        }
//hoder
        @Override
        public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item_text, parent, false));
        }

        @Override
        public void onBindViewHolder(NormalTextViewHolder holder, int position) {
            holder.mTextView1.setText(tsklst.get(position).getItem_name());
            holder.mTextView2.setText(tsklst.get(position).getItem_place());
            holder.mTextView3.setText(tsklst.get(position).getItem_time());
            holder.mCardView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, TaskdetailActivity.class);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return tsklst == null ? 0 : tsklst.size();
        }

        public class NormalTextViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.text_view1)
            TextView mTextView1;
            @BindView(R.id.text_view2)
            TextView mTextView2;
            @BindView(R.id.text_view3)
            TextView mTextView3;
            @BindView(R.id.cv_item)
            CardView mCardView;

            NormalTextViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("NormalTextViewHolder", "onClick--> position = " + getPosition());
                    }
                });
            }
        }
    }
    //从网络获取用户信息

    private void getLstFromServer() {
        final String id = UserInfoManager.getID(getActivity());
        final String session = UserInfoManager.getSession(getActivity());
        String path = "http://www.chronoswap.cn/mission_info.php";
        //创建okHttpClient对象
        OkHttpClient ohc = new OkHttpClient();
        //表单数据
        RequestBody fb = new FormBody.Builder()
                .add("userid", id)
                .add("session", session)
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
                if (text == null) {
                    msg.what = 3;
                    nnn="3";
                } else {
                    msg.what = 4;
                    nnn="4";
                    msg.obj = text;
                }
                mHandler.sendMessage(msg);
            }
        });
    }

}

