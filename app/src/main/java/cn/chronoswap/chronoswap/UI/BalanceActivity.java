package cn.chronoswap.chronoswap.UI;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import java.io.IOException;

import cn.chronoswap.chronoswap.R;
import cn.chronoswap.chronoswap.db.UserInfoManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BalanceActivity extends AppCompatActivity {
    private TextView tvTP, tvCP;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        tvTP = (TextView) findViewById(R.id.balance_tv_tp);
        tvCP = (TextView) findViewById(R.id.balance_tv_cp);
        getInfo();
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    String[] userInfo = msg.obj.toString().split("&");
                    tvTP.setText(userInfo[7] + "TP");
                    tvCP.setText("信用积分" + userInfo[8]);
                }
            }
        };
    }

    //从网络获取用户个人信息
    private void getInfo() {
        String id = UserInfoManager.getID(BalanceActivity.this);
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String imei = TelephonyMgr.getDeviceId();
        String path = "http://www.chronoswap.cn/userinfo_get.php";
        //创建okHttpClient对象
        OkHttpClient ohc = new OkHttpClient();
        //表单数据
        RequestBody fb = new FormBody.Builder()
                .add("userid", id)
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
                if (text == null) {
                    msg.what = 0;
                } else {
                    msg.what = 1;
                    msg.obj = text;
                }
                mHandler.sendMessage(msg);
            }
        });
    }
}
