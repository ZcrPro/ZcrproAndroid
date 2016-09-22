package com.example.model.axinpay_business;

public enum BusinessContainer {
	Undefined(),
	AnXinFu(),
	Life(),

	;

	static{
		for(BusinessContainer e: values()){
			if(!e.manual && e.ordinal() > 0){
				e.code = values()[e.ordinal() - 1].code + 1;
			}
		}
	}

	private int code = 0;
	private boolean manual = false;
	private BusinessContainer(int code){
		this.code = code;
		this.manual = true;
	}

	private BusinessContainer(){
	}

	public int getCode(){
		return code;
	}
}
