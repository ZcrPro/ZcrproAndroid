package com.example.model.customer;

import android.content.Context;

import com.axinpay.sharedprefercence.Persist;
import com.axinpay.sharedprefercence.Util;
import com.example.model.axinpay_business.BusinessAccount;
import com.example.model.axinpay_business.BusinessAccountStatus;
import com.example.model.axinpay_business.BusinessAccountType;
import com.example.model.axinpay_school.SchoolingFee;
import com.example.model.base.secure.AppConstants;
import com.example.model.base.secure.Session;

import java.util.ArrayList;


/**
 * Created by John on 2015/7/20.
 */
public class AXFUser extends User {
	public static final boolean ENCRYPT_CONTENT = true;
	public static final String ENCRYPT_KEY = AppConstants.ENCRYPT_KEY;
	public static final String CURRENT_VERSION_NAME = "BC"; // bus cfg

	public static AXFUser sInstance;
	public static AXFUser getInstance(){
		if(sInstance == null){
			throw new IllegalStateException("Must call initialize() before use");
		}

		return sInstance;
	}

	public static void initialize(Context context){
		if(sInstance == null){
			new AXFUser(context.getApplicationContext());
		}
	}

	@Persist
	private String version;
	@Persist
	private boolean autoLogin = false;
	@Persist
	private String pushID;
	@Persist
	private CustomerInfo customerInfo = new CustomerInfo();
	@Persist
	private boolean hasSetTag = false;

	@Persist
	private boolean pushPaymentNotice = true;
	@Persist
	private boolean pushPreferential = true;
	@Persist
	private boolean pushSystemNotice = true;
	@Persist
	private boolean pushVibrate = true;
	@Persist
	private SchoolingFee schoolFee;


	protected AXFUser(Context context) {
		super(context);
		sInstance = this;
	}

	public boolean isAutoLogin() {
		return hasLogin() && autoLogin;
	}

	public void setAutoLogin(boolean autoLogin) {
		this.autoLogin = autoLogin;
	}

	public boolean hasBindPushID(String pushID) {
		return pushID != null && Util.equals(pushID, this.pushID);
	}

	public void setPushID(String pushID) {
		this.pushID = pushID;
	}

	public boolean hasSetTag(){
		return hasSetTag;
	}

	public void setHasSetTag(boolean hasSetTag){
		this.hasSetTag = hasSetTag;
	}

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
		if(customerInfo != null && customerInfo.base_info != null && Util.isEnabled(customerInfo.base_info.mobile)){
			this.setLoginUserName(customerInfo.base_info.mobile);
		}

		updateVersion();
	}

	public boolean isPushPaymentNotice() {
		return pushPaymentNotice;
	}

	public void setPushPaymentNotice(boolean pushPaymentNotice) {
		this.pushPaymentNotice = pushPaymentNotice;
	}

	public boolean isPushPreferential() {
		return pushPreferential;
	}

	public void setPushPreferential(boolean pushPreferential) {
		this.pushPreferential = pushPreferential;
	}

	public boolean isPushSystemNotice() {
		return pushSystemNotice;
	}

	public void setPushSystemNotice(boolean pushSystemNotice) {
		this.pushSystemNotice = pushSystemNotice;
	}

	public boolean isPushVibrate() {
		return pushVibrate;
	}

	public void setPushVibrate(boolean pushVibrate) {
		this.pushVibrate = pushVibrate;
	}

	public boolean hasLogin(){
		return customerInfo != null && Util.isEnabled(getLoginUserName()) && Session.getInstance().getState() != Session.State.Invalid;
	}

	public boolean hasSchoolInfo() {
		return customerInfo != null && customerInfo.school_info != null;
	}

//	public boolean hasBindSchoolAccount(){
//		boolean state = false;
//		ArrayList<BusinessAccount> list = null;//(ArrayList<BusinessAccount>) customerInfo.business_accounts
//		if(list == null || list.size() == 0){
//			return false;
//		} else{
//			for(BusinessAccount account : list){
//				if(account.type.equals(AXFuConstants.TEXT_SCHOOL)){
//					state = (account.status == BusinessAccountStatus.OK);
//				}
//			}
//			return customerInfo != null && state;
//		}
//	}

	public void updateBusinessAccount(BusinessAccount newAccount){
		for(BusinessAccount account : customerInfo.business_accounts){
			if(account.type == newAccount.type){
				account = newAccount;
			}
		}
		save();
	}


	/**
	 * 是否是合法的绑定一卡通用户
	 * @return Result
     */
	public boolean hasBindSchoolAccount(){
		boolean state = false;
		ArrayList<BusinessAccount> list = customerInfo.business_accounts;
		if(list == null || list.size() == 0){
			return false;
		} else{
			for(BusinessAccount account : list){
				if(account.type == BusinessAccountType.School){
					state = (account.status == BusinessAccountStatus.OK);
				}
			}
			return customerInfo != null && state;
		}
	}

	/**
	 * 是否是新生缴费的绑定成功者
	 * @return Result
	 */
	public boolean hasUnBindAccount(){
		boolean state = false;
		ArrayList<BusinessAccount> list = customerInfo.business_accounts;
		if(list == null || list.size() == 0){
			return false;
		} else{
			for(BusinessAccount account : list){
				if(account.type == BusinessAccountType.UBSchoolFee){
					state = (account.status == BusinessAccountStatus.OK);
				}
			}
			return customerInfo != null && state;
		}
	}

	public SchoolingFee getSchoolFee(){
		return Config.getConfig(Config.POSITION_SCHOOL_FEE_ENABLED)? schoolFee: null;
	}

	public void setSchoolFee(SchoolingFee schoolFee){
		this.schoolFee = Config.getConfig(Config.POSITION_SCHOOL_FEE_ENABLED)? schoolFee: null;
		save();
	}

	public boolean isVersionUpToDate(){
		return CURRENT_VERSION_NAME.equals(this.version);
	}

	public void updateVersion(){
		this.version = CURRENT_VERSION_NAME;
	}
}
