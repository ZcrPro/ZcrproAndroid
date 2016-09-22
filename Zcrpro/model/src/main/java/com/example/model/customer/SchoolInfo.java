package com.example.model.customer;


import com.example.model.base.secure.BaseMessageObject;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class SchoolInfo extends BaseMessageObject {
	public String code ;  // required
	public String name ;  // required
	public String logo_url ;  // optional
	public Boolean is_partner  = false;  // optional
	public String quanpin ;  // optional
	public CityInfo city_info ;  // required
	public String city_code ;  // required

}