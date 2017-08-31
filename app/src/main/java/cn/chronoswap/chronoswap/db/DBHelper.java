package cn.chronoswap.chronoswap.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangh on 2017-08-31.
 */

public class DBHelper extends SQLiteOpenHelper {
    /**
     * @param context 上下文环境（例如，一个 Activity）
     * @param name    数据库名字
     * @param factory 一个可选的游标工厂（通常是 Null）
     * @param version 数据库模型版本的整数
     *                <p>
     *                会调用父类 SQLiteOpenHelper的构造函数
     */
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 在数据库第一次创建的时候会调用这个方法
     * <p>
     * 根据需要对传入的SQLiteDatabase 对象填充表和初始化数据。
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    /**
     * 当数据库需要修改的时候（两个数据库版本不同），Android系统会主动的调用这个方法。
     * 一般我们在这个方法里边删除数据库表，并建立新的数据库表.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //三个参数，一个 SQLiteDatabase 对象，一个旧的版本号和一个新的版本号
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        // 每次成功打开数据库后首先被执行
        super.onOpen(db);
    }
}
