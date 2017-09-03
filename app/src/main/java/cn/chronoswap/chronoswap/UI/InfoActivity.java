package cn.chronoswap.chronoswap.UI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView tvID, tvNickname, tvGender, tvBirthday, tvUni, tvSID, tvPhong;
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
        tvUni = (TextView) findViewById(R.id.info_tv_uni);
        tvSID = (TextView) findViewById(R.id.info_tv_stu_id);
        tvPhong = (TextView) findViewById(R.id.info_tv_phong);
        InfoIni();
    }

    //获取用户个人信息
    private void InfoIni() {
        tvID.setText(UserInfoManager.getID(InfoActivity.this));
        tvNickname.setText(UserInfoManager.getNickname(InfoActivity.this));
        tvGender.setText(UserInfoManager.getGender(InfoActivity.this));
        tvBirthday.setText(UserInfoManager.getBirthday(InfoActivity.this));
        tvUni.setText(UserInfoManager.getUniversity(InfoActivity.this));
        tvSID.setText(UserInfoManager.getStudentID(InfoActivity.this));
        tvPhong.setText(UserInfoManager.getPhoneNumber(InfoActivity.this));
    }

    //更改用户个人信息
    private void setInfo(int what, String obj) {
        String id = UserInfoManager.getID(InfoActivity.this);
        String session = UserInfoManager.getSession(InfoActivity.this);
        String path = "";
        String name = "";
        switch (what) {
            case 0:
                path = "http://www.chronoswap.cn/userinfo_setgender.php";
                name = "gender";
                break;
            case 1:
                path = "http://www.chronoswap.cn/userinfo_setnickname.php";
                name = "nickname";
                break;
            case 2:
                path = "http://www.chronoswap.cn/userinfo_setbirthday.php";
                name = "birthday";
                break;
            case 3:
                path = "http://www.chronoswap.cn/userinfo_setuniversity.php";
                name = "university";
                break;
            case 4:
                path = "http://www.chronoswap.cn/userinfo_setStu_id.php";
                name = "Stu_id";
                break;
            case 5:
                path = "http://www.chronoswap.cn/userinfo_setphone_num.php";
                name = "phone_num";
                break;
        }
        //创建okHttpClient对象
        OkHttpClient ohc = new OkHttpClient();
        //表单数据
        RequestBody fb = new FormBody.Builder()
                .add("userid", id)
                .add(name, obj)
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
                msg.obj = text;
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
                        final String str = single_list[which];
                        setInfo(0, str);
                        mHandler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                if (msg.obj.toString().charAt(0) == '1') {
                                    tvGender.setText(str);
                                    UserInfoManager.setGender(InfoActivity.this, str);
                                    Toast.makeText(InfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                } else if (msg.obj.toString().charAt(0) == '0') {
                                    Toast.makeText(InfoActivity.this, "会话过期，请重新登录", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(InfoActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else
                                    Toast.makeText(InfoActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                            }
                        };
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
                        final String str = etNickname.getText().toString();
                        setInfo(1, str);
                        mHandler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                if (msg.obj.toString().charAt(0) == '1') {
                                    tvNickname.setText(str);
                                    UserInfoManager.setNickname(InfoActivity.this, str);
                                    Toast.makeText(InfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                } else if (msg.obj.toString().charAt(0) == '0') {
                                    Toast.makeText(InfoActivity.this, "会话过期，请重新登录", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(InfoActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else
                                    Toast.makeText(InfoActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                            }
                        };
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
                        final String str = year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日";
                        setInfo(2, str);
                        mHandler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                if (msg.obj.toString().charAt(0) == '1') {
                                    tvBirthday.setText(str);
                                    UserInfoManager.setBirthday(InfoActivity.this, str);
                                    Toast.makeText(InfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                } else if (msg.obj.toString().charAt(0) == '0') {
                                    Toast.makeText(InfoActivity.this, "会话过期，请重新登录", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(InfoActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else
                                    Toast.makeText(InfoActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                            }
                        };
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        return dateDialog;
    }

    //大学输入对话框
    private void showUniversityInputDialog() {
        final EditText etUniversity = new EditText(InfoActivity.this);
        new AlertDialog.Builder(this)
                .setTitle("请输入大学")
                .setView(etUniversity)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String str = etUniversity.getText().toString();
                        setInfo(3, str);
                        mHandler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                if (msg.obj.toString().charAt(0) == '1') {
                                    tvUni.setText(str);
                                    UserInfoManager.setUniversity(InfoActivity.this, str);
                                    Toast.makeText(InfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                } else if (msg.obj.toString().charAt(0) == '0') {
                                    Toast.makeText(InfoActivity.this, "会话过期，请重新登录", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(InfoActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else
                                    Toast.makeText(InfoActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                            }
                        };
                    }
                }).setNegativeButton("取消", null)
                .show();
    }

    //学号输入对话框
    private void showStuIDInputDialog() {
        final EditText etStuID = new EditText(InfoActivity.this);
        etStuID.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(this)
                .setTitle("请输入学号")
                .setView(etStuID)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String str = etStuID.getText().toString();
                        setInfo(4, str);
                        mHandler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                if (msg.obj.toString().charAt(0) == '1') {
                                    tvSID.setText(str);
                                    UserInfoManager.setStudentID(InfoActivity.this, str);
                                    Toast.makeText(InfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                } else if (msg.obj.toString().charAt(0) == '0') {
                                    Toast.makeText(InfoActivity.this, "会话过期，请重新登录", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(InfoActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else
                                    Toast.makeText(InfoActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                            }
                        };
                    }
                }).setNegativeButton("取消", null)
                .show();
    }

    //手机号码输入对话框
    private void showPhongNumInputDialog() {
        final EditText etPhone = new EditText(InfoActivity.this);
        etPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        new AlertDialog.Builder(this)
                .setTitle("请输入手机号码")
                .setView(etPhone)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String str = etPhone.getText().toString();
                        setInfo(5, str);
                        mHandler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                if (msg.obj.toString().charAt(0) == '1') {
                                    tvPhong.setText(str);
                                    UserInfoManager.setPhoneNumber(InfoActivity.this, str);
                                    Toast.makeText(InfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                } else if (msg.obj.toString().charAt(0) == '0') {
                                    Toast.makeText(InfoActivity.this, "会话过期，请重新登录", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(InfoActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else
                                    Toast.makeText(InfoActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                            }
                        };
                    }
                }).setNegativeButton("取消", null)
                .show();
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

    //点击大学响应
    public void onUniButtonClicked(View v) {
        showUniversityInputDialog();
    }

    //点击学号响应
    public void onSIDButtonClicked(View v) {
        showStuIDInputDialog();
    }

    //点击手机响应
    public void onPhoneButtonClicked(View v) {
        showPhongNumInputDialog();
    }
}
