package com.example.model.axinpay_business;


import com.example.model.base.secure.BaseMessageObject;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class AccountVerifyItem extends BaseMessageObject {
	public String name ;  // required
	public Boolean trim  = false;  // optional
	public String title ;  // required
	public String hint ;  // optional
	public InputType type ;  // required
	public String reg_exp ;  // required
	public Integer max_length ;  // optional

}
