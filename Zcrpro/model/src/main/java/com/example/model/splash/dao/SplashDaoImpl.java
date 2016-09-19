package com.example.model.splash.dao;

import android.database.Cursor;

import com.example.model.splash.Splash;
import com.squareup.sqlbrite.BriteDatabase;

import rx.Observable;

/**
 * Created by zcrpro on 16/9/19.
 */
public class SplashDaoImpl implements SplashDao {

    private BriteDatabase mBriteDatabase;

    public SplashDaoImpl(BriteDatabase mBriteDatabase) {
        this.mBriteDatabase = mBriteDatabase;
    }

    @Override
    public Observable<Splash> getSplash() {
        //查询操作举例
        return null;
    }

    @Override
    public void insertSplash(Splash splash) {
        BriteDatabase.Transaction trans = mBriteDatabase.newTransaction();
        //轮询出id是否存在
        try {
            Cursor cursor = mBriteDatabase.query(Splash.SELECT_BY_ID, String.valueOf(splash._id()));
            if (!cursor.moveToNext()) {
                mBriteDatabase.insert(Splash.TABLE_NAME,Splash.FACTORY.marshal()._id(splash._id()).img(splash.img()).text(splash.text()).asContentValues());
            } else {
                mBriteDatabase.update(Splash.TABLE_NAME,Splash.FACTORY.marshal()._id(splash._id()).img(splash.img()).text(splash.text()).asContentValues(),"_id=?", String.valueOf(splash._id()));
            }
            trans.markSuccessful();
        } finally {
            trans.end();
        }

//        try { //列表数据
//            for (Splash item : list) {
//                Cursor cursor = mBriteDatabase.query(Article.SELECT_BY_ID, item._id());
//                if (cursor.moveToNext() == false) {
//                    mBriteDatabase.insert(Article.TABLE_NAME,
//                            new Article.Marshal(item).asContentValues());
//                } else {
//                    mBriteDatabase.update(Article.TABLE_NAME,
//                            new Article.Marshal(item).asContentValues()
//                            , "_id=?", item._id());
//                }
//            }
//            trans.markSuccessful();
//        } finally {
//            trans.end();
//        }
    }
}
