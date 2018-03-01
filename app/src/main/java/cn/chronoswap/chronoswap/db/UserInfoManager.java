package cn.chronoswap.chronoswap.db;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by wangh on 2017-08-31.
 */

public class UserInfoManager {
    //合并数组
    private static String toOne(String[] str) {
        return str[0] + "&" + str[1] + "&" + str[2] + "&" + str[3] + "&" + str[4] + "&" + str[5] + "&" + str[6] + "&" + str[7] + "&" + str[8] + "&" + str[9];
    }

    //获取信息
    private static String[] getUserInfo(Context context) {
        File file = new File(context.getFilesDir(), "userInfo.txt");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String[] userInfo = new String(buffer, "UTF-8").split("&");
            return userInfo;
        } catch (Exception e) {
            return null;
        }
    }

    //向文件写入用户信息
    public static void setUserInfo(Context context, String info) {
        File file = new File(context.getFilesDir(), "userInfo.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(info.getBytes());
        } catch (Exception e) {
        }
    }

    //获取ID信息
    public static String getID(Context context) {
        return UserInfoManager.getUserInfo(context)[0];
    }

    //写入性别信息
    public static void setGender(Context context, String gender) {
        String[] info = UserInfoManager.getUserInfo(context);
        UserInfoManager.getUserInfo(context)[1] = gender;
        File file = new File(context.getFilesDir(), "userInfo.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(UserInfoManager.toOne(info).getBytes());
        } catch (Exception e) {
        }
    }

    //获取性别信息
    public static String getGender(Context context) {
        return UserInfoManager.getUserInfo(context)[1];
    }

    //写入生日信息
    public static void setBirthday(Context context, String birthday) {
        String[] info = UserInfoManager.getUserInfo(context);
        UserInfoManager.getUserInfo(context)[2] = birthday;
        File file = new File(context.getFilesDir(), "userInfo.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(UserInfoManager.toOne(info).getBytes());
        } catch (Exception e) {
        }
    }

    //获取生日信息
    public static String getBirthday(Context context) {
        return UserInfoManager.getUserInfo(context)[2];
    }

    //写入大学信息
    public static void setUniversity(Context context, String university) {
        String[] info = UserInfoManager.getUserInfo(context);
        UserInfoManager.getUserInfo(context)[3] = university;
        File file = new File(context.getFilesDir(), "userInfo.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(UserInfoManager.toOne(info).getBytes());
        } catch (Exception e) {
        }
    }

    //获取大学信息
    public static String getUniversity(Context context) {
        return UserInfoManager.getUserInfo(context)[3];
    }

    //写入学号信息
    public static void setStudentID(Context context, String stuID) {
        String[] info = UserInfoManager.getUserInfo(context);
        UserInfoManager.getUserInfo(context)[4] = stuID;
        File file = new File(context.getFilesDir(), "userInfo.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(UserInfoManager.toOne(info).getBytes());
        } catch (Exception e) {
        }
    }

    //获取学号信息
    public static String getStudentID(Context context) {
        return UserInfoManager.getUserInfo(context)[4];
    }

    //写入手机号码信息
    public static void setPhoneNumber(Context context, String PhoneNum) {
        String[] info = UserInfoManager.getUserInfo(context);
        UserInfoManager.getUserInfo(context)[5] = PhoneNum;
        File file = new File(context.getFilesDir(), "userInfo.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(UserInfoManager.toOne(info).getBytes());
        } catch (Exception e) {
        }
    }

    //获取手机号码信息
    public static String getPhoneNumber(Context context) {
        return UserInfoManager.getUserInfo(context)[5];
    }

    //写入昵称信息
    public static void setNickname(Context context, String nickname) {
        String[] info = UserInfoManager.getUserInfo(context);
        UserInfoManager.getUserInfo(context)[6] = nickname;
        File file = new File(context.getFilesDir(), "userInfo.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(UserInfoManager.toOne(info).getBytes());
        } catch (Exception e) {
        }
    }

    //获取昵称信息
    public static String getNickname(Context context) {
        return UserInfoManager.getUserInfo(context)[6];
    }

    //写入TP信息
    public static void setTP(Context context, String tp) {
        String[] info = UserInfoManager.getUserInfo(context);
        UserInfoManager.getUserInfo(context)[7] = tp;
        File file = new File(context.getFilesDir(), "userInfo.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(UserInfoManager.toOne(info).getBytes());
        } catch (Exception e) {
        }
    }

    //获取TP信息
    public static String getTP(Context context) {
        return UserInfoManager.getUserInfo(context)[7];
    }

    //写入CP信息
    public static void setCP(Context context, String cp) {
        String[] info = UserInfoManager.getUserInfo(context);
        UserInfoManager.getUserInfo(context)[8] = cp;
        File file = new File(context.getFilesDir(), "userInfo.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(UserInfoManager.toOne(info).getBytes());
        } catch (Exception e) {
        }
    }

    //获取CP信息
    public static String getCP(Context context) {
        return UserInfoManager.getUserInfo(context)[8];
    }

    //获取Session
    public static String getSession(Context context) {
        return UserInfoManager.getUserInfo(context)[9];
    }
}
