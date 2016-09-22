package com.example.model.base.secure;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class BaseRequest extends BaseMessageObject{
	public String sys_name ;  // required
	public String sys_version ;  // required
	public String app_name ;  // required
	public String app_version ;  // required
	public String api_version  = "1.1.6";  // required
	public String session_id ;  // optional

}