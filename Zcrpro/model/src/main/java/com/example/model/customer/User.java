package com.example.model.customer;

import android.content.Context;

import com.axinpay.sharedprefercence.AbsSharedPreferencesData;
import com.axinpay.sharedprefercence.Persist;
import com.example.model.base.secure.AppConstants;
import com.example.model.base.secure.Session;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by John on 2015/7/20.
 */
public class User extends AbsSharedPreferencesData {
	public static final String TAG = "User";

	public static final boolean ENCRYPT_CONTENT = true;
	public static final String ENCRYPT_KEY = AppConstants.ENCRYPT_KEY;

	public static final String SAVE_LIBRARY_NAME = "USER";

	@Persist
	private String loginUserName;

	private Session session;

	public static User sInstance;
	public static User getInstance(){
		if(sInstance == null){
			throw new IllegalStateException("Must call initialize() before use");
		}

		return sInstance;
	}

	public static void initialize(Context context){
		if(sInstance == null){
			sInstance = new User(context.getApplicationContext());

		}
	}

	@Override
	public void load() {
		super.load();
		session.load();
	}

	@Override
	public void save() {
		super.save();
		session.save();
	}

	@Override
	public void clear() {
		super.clear();
		session.clear();
	}

	protected User(Context context) {
		super(context);
		sInstance = this;

		Session.initialize(context);
		sInstance.session = Session.getInstance();
	}

	@Override
	public String getName() {
		return SAVE_LIBRARY_NAME;
	}

	@Override
	public <T> T read(String key, Type type) {
		T value = super.read(key, type);

		// FIXME decrypt value

		return value;
	}

	@Override
	public void write(Map<String, Object> values) {
		// FIXME encrypt value
		super.write(values);
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public Session getSession(){
		return session;
	}
}
