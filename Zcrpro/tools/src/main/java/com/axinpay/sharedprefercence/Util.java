package com.axinpay.sharedprefercence;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by zcrpro on 16/9/22.
 */

public class Util {

    public static boolean equals(Object o1, Object o2){
        if(o1 == o2){
            return true;
        }

        if(o1 != null){
            return o1.equals(o2);
        }

        if(o2 != null){
            return o2.equals(o1);
        }

        return false;
    }

    public static PackageInfo getPackageInfo(Context context){
        return getPackageInfo(context, context.getPackageName());
    }

    public static PackageInfo getPackageInfo(Context context, String packageName){
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            return info;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isEnabled(String value){
        return !isEmpty(value);
    }

    public static <T>boolean isEnabled(List<T> value){
        return !isEmpty(value);
    }

    public static <T>boolean isEnabled(T[] value){
        return !isEmpty(value);
    }

    public static boolean isEmpty(String text) {
        return text == null || text.trim().length() == 0;
    }

    public static <T>boolean isEmpty(List<T> list){
        return list == null || list.size() == 0;
    }

    public static <T>boolean isEmpty(T[] list){
        return list == null || list.length == 0;
    }

    public static String byte2HexStr(byte[] data){
        if(data == null){
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for(byte b: data){
            String bHexStr = Integer.toHexString(0x00ff & b);
            if(bHexStr.length() == 1){
                sb.append('0');
            }

            sb.append(bHexStr);

        }

        return sb.toString();
    }

}
