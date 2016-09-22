package com.example.model.axinpay_school;

import com.example.model.base.secure.BaseMessageObject;
import com.example.model.customer.PayChannel;

import java.util.ArrayList;


/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class ECardInfo extends BaseMessageObject {
	public String student_no ;  // optional
	public String student_name ;  // optional
	public String card_no ;  // optional
	public String card_balance ;  // optional
	public LossCardType losscard_type  = LossCardType.unsupport;  // required
	public String losscard_desc ;  // optional
	public QueryType consumption_query_type  = QueryType.none;  // required
	public String consumption_query_url ;  // optional
	public ECardStatus status  = ECardStatus.OK;  // required
	public ArrayList<PayChannel> channel_codes ;  // required
	public ArrayList<Integer> recharge_amts ;  // optional

}