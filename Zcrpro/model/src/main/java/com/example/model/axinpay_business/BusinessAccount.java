package com.example.model.axinpay_business;

import com.example.model.base.secure.BaseMessageObject;

import java.util.List;



/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class BusinessAccount extends BaseMessageObject {
    public String no ;
    public String title;
    public String name;
    public BusinessAccountType type;
    public BusinessAccountStatus status;
    public List<AccountVerifyItem> verify_required_items;
    public String open_notice;
    public List<AccountInfoItem> account_info;
    public boolean is_important_account;
}
