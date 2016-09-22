package com.example.model.base.secure;

import android.content.Context;

import com.axinpay.sharedprefercence.AbsSharedPreferencesData;
import com.axinpay.sharedprefercence.Persist;

/**
 * Created by John on 2015/6/2.
 */
public class Session extends AbsSharedPreferencesData {
	public enum State{
		OK, OutOfDate, Invalid, Updating
	}

	@Persist
	private String id;
	@Persist
	private State state = State.Invalid;

	public static Session sInstance;
	public static Session getInstance(){
		if(sInstance == null){
			throw new IllegalStateException("Must call initialize() before use");
		}

		return sInstance;
	}

	public static void initialize(Context context){
		if(sInstance == null){
			sInstance = new Session(context.getApplicationContext());

			sInstance.load();
		}
	}

	private Session(Context context) {
		super(context);
	}

	@Override
	public String getName() {
		return "session";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
