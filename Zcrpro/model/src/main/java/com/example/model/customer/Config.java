package com.example.model.customer;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by John on 2015/7/20.
 */
public class Config {
	// 学费功能
	public static final int POSITION_SCHOOL_FEE_ENABLED = 0;
	// 学籍信息
	public static final int POSITION_SCHOOL_ROLL_ENABLED = 1;
	// 禁用一卡通
	public static final int POSITION_ECARD_DISABLED = 2;
	// 安心借
	public static final int POSITION_AXD_ENABLED = 3;
	// 禁用话费充值
	public static final int POSITION_RECHARGE_MOBILE_DISABLED = 4;

	public static final String DATA_NAME = "CONFIG";
	public static final String KEY_NAME = "CONFIG";

	private static long config;
	private static Context context;
	public static void initialize(Context context){
		Config.context = context.getApplicationContext();

		SharedPreferences sp = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
		config = sp.getLong(KEY_NAME, 0l);
	}

	/**
	 * Set school business config
	 * @param config config long
	 * @return true if config changed, other else
	 */
	public static boolean setConfig(long config){
		if(context == null){
			throw new IllegalStateException("Must call initialize() before use");
		}

		if(Config.config == config){
			return false;
		}

		Config.config = config;

		SharedPreferences.Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
		editor.putLong(KEY_NAME, config);
		editor.commit();

		return true;
	}

	public static boolean getConfig(int index){
		if(index == POSITION_SCHOOL_FEE_ENABLED ){
			return true;
		}
		if(index == POSITION_SCHOOL_ROLL_ENABLED){
			return true;
		}
		if(index == POSITION_ECARD_DISABLED && BuildConfig.DEBUG){
			return true;
		}
		if(index == POSITION_AXD_ENABLED && BuildConfig.DEBUG ){
			return true;
		}
		if(index == POSITION_RECHARGE_MOBILE_DISABLED && BuildConfig.DEBUG){
			return true;
		}

		return AXFUser.getInstance().hasBindSchoolAccount() && ((config >> index) & 0x01l) > 0;
	}
}
