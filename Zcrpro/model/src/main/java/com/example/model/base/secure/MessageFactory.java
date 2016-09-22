package com.example.model.base.secure;

import android.content.Context;
import android.os.Build;

import com.axinpay.sharedprefercence.Util;
import com.google.gson.Gson;

import java.math.BigInteger;

/**
 * Created by John on 2014/7/28.
 */
public class MessageFactory {
	private static final String TAG = "MessageFactory";

	public String toStringMessage(BaseMessageObject obj){
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		return json;
	}

	public static class DummyBusinessConfigResponse extends CommResponse{
		public BigInteger cfg ;

	}

	public <T extends BaseMessageObject> T fromStringMessage(String message, Class<T> clazz){
		Gson gson = new Gson();
//		if(clazz == SchoolService.BusinessConfigResponse.class){
//			// 因为服务器返回的long是无符号的, 有可能会返回大于Long.MAX_VALUE
//			// 所以需要对返回的值作处理
//			try{
//				return gson.fromJson(message, clazz);
//			}
//			catch (Exception e){
//				//返回的cfg大于Long.MAX_VALUE, 需要转为有符号的值
//
//				DummyBusinessConfigResponse dummyResp = gson.fromJson(message, DummyBusinessConfigResponse.class);
//				SchoolService.BusinessConfigResponse resp = new SchoolService.BusinessConfigResponse();
//
//				resp.base = dummyResp.base;
//				resp.cfg = dummyResp.cfg.longValue();
//
//				return (T) resp;
//			}
//		}
//		else{
			return gson.fromJson(message, clazz);//3614
//		}
	}

	public <T extends BaseMessageObject> T create(Context context, Class<T> clazz){
		try {
			BaseMessageObject msg = clazz.newInstance();
			if(msg instanceof BaseRequest){
				initBaseRequest(context, (BaseRequest)msg);
			}
			if(msg instanceof BaseResponse){
				initBaseResponse(context, (BaseResponse)msg);
			}
			return (T)msg;
		} catch (Exception e) {
//			Log.e(TAG, String.format("Create msg: %s failed", clazz), e);
			return null;
		}
	}

	public <T extends BaseMessageObject> T clone(BaseMessageObject obj){
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		T newObj = (T)gson.fromJson(json, ((Object)obj).getClass());

		return newObj;
	}

	protected void initBaseRequest(Context context, BaseRequest baseRequest){
		baseRequest.app_name = "com.zhihuianxin.xyaxf.debug";
		baseRequest.app_version = Util.getPackageInfo(context).versionName;
		baseRequest.sys_name = AppConstants.SYSTEM_NAME;
		baseRequest.sys_version = Build.VERSION.RELEASE;
		baseRequest.session_id = Session.getInstance().getId();
	}

	protected void initBaseResponse(Context context, BaseResponse baseResponse){
		baseResponse.result_code = 0;
		baseResponse.result_desc = "成功";
	}

	public BaseRequest createBaseRequest(Context context){
		BaseRequest req = create(context, BaseRequest.class);
		return req;
	}
}
