package com.example.model.axinpay_school;


import com.example.model.base.secure.BaseMessageObject;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class QRCodeInfo extends BaseMessageObject {
	public QRCodeMethod method  = QRCodeMethod.unknown;  // required
	public String school_code ;  // required
	public String account_no ;  // required
	public String name ;  // required

}