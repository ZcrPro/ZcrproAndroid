package com.example.model.axinpay_business;

public enum BusinessAccountStatus {
	NotOpened(),
	OK(),
	Error(),

	;

	static{
		for(BusinessAccountStatus e: values()){
			if(!e.manual && e.ordinal() > 0){
				e.code = values()[e.ordinal() - 1].code + 1;
			}
		}
	}

	private int code = 0;
	private boolean manual = false;
	private BusinessAccountStatus(int code){
		this.code = code;
		this.manual = true;
	}

	private BusinessAccountStatus(){
	}

	public int getCode(){
		return code;
	}
}
