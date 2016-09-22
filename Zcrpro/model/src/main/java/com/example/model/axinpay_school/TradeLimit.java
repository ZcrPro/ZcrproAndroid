package com.example.model.axinpay_school;


import com.example.model.base.secure.BaseMessageObject;

import java.util.ArrayList;

/**
 * Created by zhengzheng on 16/7/23.
 */
public class TradeLimit extends BaseMessageObject {
    public String trade_limit_amt;
    public ArrayList<BankInfo> bank_ifnos;
    public String out_of_limit_notice_normal;
    public String out_of_limit_notice_splitable;
    public String other_bank_notice;
}

