package cn.chronoswap.chronoswap.db;

import android.content.Context;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by wangh on 2017-08-31.
 */

public class UserInfoManager {
    public static void saveID(Context context, String ID) {
        File file = new File(context.getFilesDir(), "userID.txt");
        try {
            //向文件写入ID信息
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(ID.getBytes());
        } catch (Exception e) {
        }
    }

    public static String getID(Context context) {
        File file = new File(context.getFilesDir(), "userID.txt");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String ID = EncodingUtils.getString(buffer, "UTF-8");
            return ID;
        } catch (Exception e) {
            return null;
        }
    }
}
