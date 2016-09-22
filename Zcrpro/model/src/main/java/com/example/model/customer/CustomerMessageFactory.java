package com.example.model.customer;

import android.content.Context;
import android.util.Log;

import com.example.model.base.secure.BaseMessageObject;
import com.example.model.base.secure.BaseRequest;
import com.example.model.base.secure.MessageFactory;

/**
 * Created by John on 2014/7/28.
 */
public class CustomerMessageFactory extends MessageFactory {
	public static final String TAG = "CustomerMessageFactory";

	public <T extends BaseMessageObject> T create(Context context, Class<T> clazz){
		try {
			BaseMessageObject msg = super.create(context, clazz);
			if(msg instanceof BaseRequest){
				initBaseRequest(context, (BaseRequest) msg);
			}
			if(msg instanceof CustomerInfo){
				initCustomerInfo(context, (CustomerInfo)msg);
			}

			return (T)msg;
		} catch (Exception e) {
			Log.w(TAG, "Create item throws exception. ", e);
			e.printStackTrace();
			return null;
		}
	}

	private void initCustomerInfo(Context context, CustomerInfo info){
		CustomerInfo myInfo = AXFUser.getInstance().getCustomerInfo();
		if(myInfo != null) {
			CustomerBaseInfo baseInfo = new CustomerBaseInfo();
			baseInfo.nickname = myInfo.base_info.nickname;
			baseInfo.mobile = myInfo.base_info.mobile;
			baseInfo.sexual = myInfo.base_info.sexual;
			baseInfo.head_url = myInfo.base_info.head_url;

//			info.base_info = baseInfo;
//			info.school_info.name = myInfo.school_info.name;
//			info.school_info = myInfo.school_info == null? new SchoolInfo(): myInfo.school_info;
		}
	}

}
