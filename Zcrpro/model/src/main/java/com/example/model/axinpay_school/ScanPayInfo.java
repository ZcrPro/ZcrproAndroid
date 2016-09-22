package com.example.model.axinpay_school;

import com.example.model.base.secure.BaseMessageObject;
import com.example.model.customer.PayChannel;

import java.util.ArrayList;


/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class ScanPayInfo extends BaseMessageObject {
	public String pay_amt ;  // required
	public ArrayList<ScanPay_OrderItem> orderitems ;  // required
	public String transaction_no ;  // required
	public String tips4payed ;  // required
	public String trade_summary ;  // required
	public PayChannel channel_code ;  // required

}