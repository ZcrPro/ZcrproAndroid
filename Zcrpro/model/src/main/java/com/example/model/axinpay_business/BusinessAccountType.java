package com.example.model.axinpay_business;

public enum BusinessAccountType {
	School(),
	UBSchoolFee(),
	;

	static{
		for(BusinessAccountType e: values()){
			if(!e.manual && e.ordinal() > 0){
				e.code = values()[e.ordinal() - 1].code + 1;
			}
		}
	}

	private int code = 0;
	private boolean manual = false;
	private BusinessAccountType(int code){
		this.code = code;
		this.manual = true;
	}

	private BusinessAccountType(){
	}

	public int getCode(){
		return code;
	}
}
