package com.example.model.axinpay_business;

public enum BusinessType {
	SchoolFee(),
	ECard(),
	Web(),
	AnXinJie(),
	MobileRecharge(),
	FlowRecharge(),
	TdtcFee(),
	SchoolRoll(),
	UBSchoolFee(),
	;

	static{
		for(BusinessType e: values()){
			if(!e.manual && e.ordinal() > 0){
				e.code = values()[e.ordinal() - 1].code + 1;
			}
		}
	}

	private int code = 0;
	private boolean manual = false;
	private BusinessType(int code){
		this.code = code;
		this.manual = true;
	}

	private BusinessType(){
	}

	public int getCode(){
		return code;
	}
}
