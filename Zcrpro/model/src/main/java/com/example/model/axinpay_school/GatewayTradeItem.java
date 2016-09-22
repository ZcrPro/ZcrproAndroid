package com.example.model.axinpay_school;


import com.example.model.base.secure.BaseMessageObject;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class GatewayTradeItem extends BaseMessageObject {
	public String order_no ;  // required
	public String payment_amt ;  // required
	public String student_name ;  // optional
	public String student_no ;  // optional
	public String trade_summary ;  // required
	public String school_code ;  // required
	public String sign ;  // required

}