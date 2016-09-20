package com.example.model.daily.dao;

import android.database.Cursor;

import com.example.model.daily.Daily;
import com.squareup.sqlbrite.BriteDatabase;

import rx.Observable;

/**
 * Created by zcrpro on 16/9/20.
 */
public class DailyDaoImpl implements DailyDao {

    private BriteDatabase mBriteDatabase;

    public DailyDaoImpl(BriteDatabase mBriteDatabase) {
        this.mBriteDatabase = mBriteDatabase;
    }

    @Override
    public Observable<Daily> getDaily() {
        return null;
    }

    @Override
    public void insertDaily(Daily daily) {
        BriteDatabase.Transaction trans = mBriteDatabase.newTransaction();
        try {
            Cursor cursor = mBriteDatabase.query(Daily.SELECT_BY_NAME,daily.name());
            if (!cursor.moveToNext()) {
                mBriteDatabase.insert(Daily.TABLE_NAME,Daily.FACTORY.marshal()
                        .background(daily.background())
                        .color(daily.color())
                        .name(daily.name())
                        .description(daily.description())
                        .image(daily.image())
                        .image_source(daily.image_source())
                        .asContentValues());
            } else {
                mBriteDatabase.update(Daily.TABLE_NAME,Daily.FACTORY.marshal()
                        .background(daily.background())
                        .color(daily.color())
                        .name(daily.name())
                        .description(daily.description())
                        .image(daily.image())
                        .image_source(daily.image_source())
                        .asContentValues(),"name=?",daily.name());
            }
            trans.markSuccessful();
        } finally {
            trans.end();
        }
    }
}
