package com.example.model.axinpay_school;


import com.example.model.base.secure.BaseMessageObject;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class SchoolFeeItemExt extends BaseMessageObject {
	public String id ;  // required
	public String name ;  // required
	public String amount ;  // required
	public String more_info ;  // optional
	public Boolean split_able  = false;  // required
	public String split_min_amount ;  // optional
	public String pay_amount ;  // optional
	public Boolean is_priority = false;
}