package cn.chronoswap.chronoswap.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import static java.lang.Integer.parseInt;

public class TaskCreateActivity extends AppCompatActivity {

    EditText etTitle, etPlace, etReward, etDetails;
    EditText etMonth, etDay, etHour, etMinute;
    TextView tvError;
    Button btnPublish;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
        etTitle = (EditText) findViewById(R.id.create_et_title);
        etPlace = (EditText) findViewById(R.id.create_et_place);
        etReward = (EditText) findViewById(R.id.create_et_reward);
        etDetails = (EditText) findViewById(R.id.create_et_detail);
        etMonth = (EditText) findViewById(R.id.create_et_deadline_month);
        etDay = (EditText) findViewById(R.id.create_et_deadline_day);
        etHour = (EditText) findViewById(R.id.create_et_deadline_hour);
        etMinute = (EditText) findViewById(R.id.create_et_deadline_minute);
        tvError = (TextView) findViewById(R.id.create_tv_error);
        btnPublish = (Button) findViewById(R.id.create_btn_publish);
        btnPublish.setOnClickListener(listener);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.obj.toString().charAt(0) == '1') {
                    Toast.makeText(TaskCreateActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (msg.obj.toString().charAt(0) == '0') {
                    Toast.makeText(TaskCreateActivity.this, "会话过期，请重新登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TaskCreateActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (msg.obj.toString().charAt(0) == '2') {
                    Toast.makeText(TaskCreateActivity.this, "TP点不足，无法发布", Toast.LENGTH_SHORT).show();
                } else tvError.setText(msg.obj.toString());
            }
        };
    }

    //发布按钮监听
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkInput()) {
                publish();
            }
        }
    };

    //取消按钮响应
    public void onCancelButtonClicked(View v) {
        finish();
    }

    //发布任务操作
    protected void publish() {
        String userid = UserInfoManager.getID(TaskCreateActivity.this);
        String title = etTitle.getText().toString();
        String deadline = etMonth.getText().toString() + "-" + etDay.getText().toString() + " " + etHour.getText().toString() + ":" + etMinute.getText().toString();
        String place = etPlace.getText().toString();
        String reward = etReward.getText().toString();
        String details = etDetails.getText().toString();
        String session = UserInfoManager.getSession(TaskCreateActivity.this);
        String path = "http://www.chronoswap.cn/mission_set.php";
        //创建okHttpClient对象
        OkHttpClient ohc = new OkHttpClient();
        //表单数据
        RequestBody fb = new FormBody.Builder()
                .add("userid", userid)
                .add("title", title)
                .add("deadline", deadline)
                .add("place", place)
                .add("reward", reward)
                .add("note", details)
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
                String test = response.body().string();
                Message msg = new Message();
                msg.obj = test;
                mHandler.sendMessage(msg);
            }
        });
    }

    //检测输入是否合法
    private boolean checkInput() {
        if (TextUtils.isEmpty(etTitle.getText())) {
            Toast.makeText(TaskCreateActivity.this, "请填写标题", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(etPlace.getText())) {
            Toast.makeText(TaskCreateActivity.this, "请填写地点", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(etMonth.getText()) | TextUtils.isEmpty(etDay.getText()) | TextUtils.isEmpty(etHour.getText()) | TextUtils.isEmpty(etMinute.getText())) {
            Toast.makeText(TaskCreateActivity.this, "请填写时间", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(etReward.getText())) {
            Toast.makeText(TaskCreateActivity.this, "请填写奖励", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            int month = parseInt(etMonth.getText().toString());
            int day = parseInt(etDay.getText().toString());
            int hour = Integer.parseInt(etHour.getText().toString());
            int minute = Integer.parseInt(etMinute.getText().toString());
            if (month < 1 || month > 12 || day < 1 || day > 31 || hour < 1 || hour > 24 || minute < 1 || minute > 59) {
                Toast.makeText(TaskCreateActivity.this, "请输入合法的时间", Toast.LENGTH_SHORT).show();
                return false;
            } else return true;
        }
    }
}
