package com.example.model.axinpay_school;

import com.example.model.base.secure.BaseMessageObject;
import com.example.model.customer.PayChannel;

import java.util.ArrayList;



/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class SchoolFeeItem extends BaseMessageObject {
	public String id ;  // required
	public String name ;  // required
	public String amount ;  // required
	public String more_info ;  // optional
	public String start_date ;  // required
	public String end_date ;  // required
	public ArrayList<SchoolFeeItemExt> exts ;  // optional
	public ArrayList<PayChannel> channel_codes ;  // required
	public Boolean invoice_enabled  = false;  // required
	public SchoolFeeGroupType group_type  = SchoolFeeGroupType.normal;  // required
	public Boolean split_able  = false;  // required
	public String split_min_amount ;  // optional
	public String pay_amount ;  // optional
	public Boolean is_priority  = false;  // optional
	public Boolean is_locked  = false;  // optional

}