package com.example.model.customer;


import com.example.model.axinpay_business.Business;
import com.example.model.axinpay_business.BusinessAccount;
import com.example.model.base.secure.BaseMessageObject;

import java.util.ArrayList;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class CustomerInfo extends BaseMessageObject {
	public CustomerBaseInfo base_info ;  // required
	public SchoolInfo school_info ;  // optional
	public ArrayList<Business> businesses ;  // optional
	public ArrayList<BusinessAccount> business_accounts ;  // optional

}