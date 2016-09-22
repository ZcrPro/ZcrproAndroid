package com.example.model.axinpay_school;

public enum LossCardType {
	web(),
	password(),
	no_password(),
	unsupport(),

	;

	static{
		for(LossCardType e: values()){
			if(!e.manual && e.ordinal() > 0){
				e.code = values()[e.ordinal() - 1].code + 1;
			}
		}
	}

	private int code = 0;
	private boolean manual = false;
	private LossCardType(int code){
		this.code = code;
		this.manual = true;
	}

	private LossCardType(){
	}

	public int getCode(){
		return code;
	}
}
