package com.example.model.axinpay_business;

public enum BusinessStatus {
	OK(),
	Disabled(),
	Error(),

	;

	static{
		for(BusinessStatus e: values()){
			if(!e.manual && e.ordinal() > 0){
				e.code = values()[e.ordinal() - 1].code + 1;
			}
		}
	}

	private int code = 0;
	private boolean manual = false;
	private BusinessStatus(int code){
		this.code = code;
		this.manual = true;
	}

	private BusinessStatus(){
	}

	public int getCode(){
		return code;
	}
}
