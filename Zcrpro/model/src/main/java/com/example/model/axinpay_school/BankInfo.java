package com.example.model.axinpay_school;


import com.example.model.base.secure.BaseMessageObject;

/**
 * Created by zhengzheng on 16/7/23.
 * 限额提示
 */
public class BankInfo extends BaseMessageObject {
    public String name;// 银行名
    public String logo_url; // 银行图标地址
    public String lpt_amt;// 单笔限额
    public String lpd_amt; // 单日限额

    //扩展
    public boolean local=false;
    public int local_logo;
}
