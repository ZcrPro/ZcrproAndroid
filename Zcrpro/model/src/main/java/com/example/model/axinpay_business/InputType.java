package com.example.model.axinpay_business;

public enum InputType {
	number(),
	decimal(),
	text(),
	mobile(),
	password(),

	;

	static{
		for(InputType e: values()){
			if(!e.manual && e.ordinal() > 0){
				e.code = values()[e.ordinal() - 1].code + 1;
			}
		}
	}

	private int code = 0;
	private boolean manual = false;
	private InputType(int code){
		this.code = code;
		this.manual = true;
	}

	private InputType(){
	}

	public int getCode(){
		return code;
	}
}
