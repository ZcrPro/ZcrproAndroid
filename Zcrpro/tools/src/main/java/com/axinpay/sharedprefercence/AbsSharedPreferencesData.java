package com.axinpay.sharedprefercence;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by zcrpro on 16/9/22.
 */

abstract public class AbsSharedPreferencesData extends AbsPersistData {
    public static final String TAG = "AbsSharedPreferencesData";
    private WeakReference<Context> context;
    private SharedPreferences sp;
    private Gson gson;
    public AbsSharedPreferencesData(Context context) {
        this.context = new WeakReference<Context>(context.getApplicationContext());

        sp = context.getSharedPreferences(getName(), Context.MODE_PRIVATE);
        gson = new Gson();

        sp.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                try {
                    sp = sharedPreferences;

                    Field f = AbsSharedPreferencesData.this.getPersistField(key);
                    Object value = read(key, f.getGenericType());

                    if(!Util.equals(value, f.get(AbsSharedPreferencesData.this))){
                        f.set(AbsSharedPreferencesData.this, value);
                    }
                }
                catch (Exception e) {
                    Log.e(TAG, String.format("%s onSharedPreferenceChanged, update value: %s throws exception, remove this value", AbsSharedPreferencesData.this.getClass().getName(), key), e);
                    sharedPreferences.edit().remove(key).commit();
                }
            }
        });
    }

    @Override
    public void write(Map<String, Object> values) {
        SharedPreferences.Editor editor = sp.edit();
        for(String key: values.keySet()){
            Object value = values.get(key);
            String strValue = gson.toJson(value);

            editor.putString(key, strValue);
        }

        editor.commit();
    }

    @Override
    public boolean has(String key) {
        return sp.contains(key);
    }

    @Override
    public <T> T read(String key, Type type) {
        String strValue = sp.getString(key, null);
        T value = gson.fromJson(strValue, type);

        return value;
    }

    @Override
    public void clear() {
        super.clear();
        save();
    }

    @Override
    public void save() {
        super.save();
    }

    abstract public String getName();

    public Context getContext(){
        return context.get();
    }

    @Override
    protected AbsPersistData getDefaultInstance() {
        try {
            Constructor c = getClass().getDeclaredConstructor(Context.class);
            c.setAccessible(true);
            return (AbsPersistData) c.newInstance(getContext());
        } catch (Exception e) {
            Log.w(TAG, String.format("Create default instance of %s failed", getClass().getName()), e);
            return null;
        }
    }
}
