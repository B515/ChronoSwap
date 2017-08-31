package cn.chronoswap.chronoswap.UI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;

import cn.chronoswap.chronoswap.R;
import cn.chronoswap.chronoswap.db.UserInfoManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InfoActivity extends AppCompatActivity {
    private TextView tvID, tvNickname, tvGender, tvBirthday;
    private Handler mHandler;
    String[] single_list = {"男", "女"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        tvID = (TextView) findViewById(R.id.info_tv_ID);
        tvNickname = (TextView) findViewById(R.id.info_tv_nickname);
        tvGender = (TextView) findViewById(R.id.info_tv_gender);
        tvBirthday = (TextView) findViewById(R.id.info_tv_birth);
        getInfo();
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    String[] userInfo = msg.obj.toString().split("&");
                    tvID.setText(userInfo[0]);
                    tvNickname.setText(userInfo[1]);
                    tvGender.setText(userInfo[2]);
                    tvBirthday.setText(userInfo[3]);
                }
            }
        };
    }

    //从网络获取用户个人信息
    private void getInfo(){
        String id = UserInfoManager.getID(InfoActivity.this);
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
                if (text==null){
                    msg.what = 0;
                }
                else {
                    msg.what = 1;
                    msg.obj = text;
                }
                mHandler.sendMessage(msg);
            }
        });
    }

    //性别选择对话框
    private void showGenderChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("性别")
                .setIcon(R.mipmap.ic_launcher)
                .setSingleChoiceItems(single_list, 0, new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str = single_list[which];
                        tvGender.setText(str);
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //昵称输入对话框
    private void showNameInputDialog() {
        final EditText etNickname = new EditText(InfoActivity.this);
        new AlertDialog.Builder(this)
                .setTitle("请输入昵称")
                .setView(etNickname)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str = etNickname.getText().toString();
                        tvNickname.setText(str);
                    }
                }).setNegativeButton("取消", null)
                .show();
    }

    //日期选择对话框
    protected Dialog DateChoiceDialog() {
        Calendar calendar = Calendar.getInstance();
        Dialog dateDialog = new DatePickerDialog(InfoActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String str = year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日";
                        tvBirthday.setText(str);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        return dateDialog;
    }

    //点击性别响应
    public void onGenderButtonClicked(View v) {
        showGenderChoiceDialog();
    }

    //点击生日响应
    public void onDateButtonClicked(View v) {
        DateChoiceDialog().show();
    }

    //点击昵称响应
    public void onNameButtonClicked(View v) {
        showNameInputDialog();
    }
}
