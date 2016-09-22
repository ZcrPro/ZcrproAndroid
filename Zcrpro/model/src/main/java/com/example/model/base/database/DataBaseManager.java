package com.example.model.base.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.model.daily.Daily;
import com.example.model.splash.Splash;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import rx.schedulers.Schedulers;

/**
 * Created by zcrpro on 16/9/19.
 */
public class DataBaseManager {
    private static SQLiteOpenHelper mSqLiteOpenHelper;
    private static DataBaseManager mDataBaseManager;
    private static final String DB_NAME = "axf_test";
    private static SqlBrite mSqlBrite;
    private static BriteDatabase mBriteDatabase;

    public static BriteDatabase getBriteDatabase(Context context) {
        if (mBriteDatabase == null) {
            synchronized (DataBaseManager.class) {
                if (mDataBaseManager == null) {
                    mDataBaseManager = new DataBaseManager();
                    mDataBaseManager.mSqLiteOpenHelper = new SQLiteOpenHelper(context, DB_NAME, null, 1) {
                        @Override
                        public void onCreate(SQLiteDatabase db) {
                            init(db);
                        }

                        @Override
                        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

                        }
                    };
                    mSqlBrite = SqlBrite.create();
                    mBriteDatabase = mSqlBrite.wrapDatabaseHelper(mSqLiteOpenHelper, Schedulers.io());
                    mBriteDatabase.setLoggingEnabled(true);
                }
            }
        }
        return mBriteDatabase;
    }

    private static void init(SQLiteDatabase db) {
        db.execSQL(Splash.CREATE_TABLE);
        db.execSQL(Daily.CREATE_TABLE);
    }
}