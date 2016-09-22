package com.example.model.axinpay_school;

public enum AccessType {
	In(),
	Out(),

	;

	static{
		for(AccessType e: values()){
			if(!e.manual && e.ordinal() > 0){
				e.code = values()[e.ordinal() - 1].code + 1;
			}
		}
	}

	private int code = 0;
	private boolean manual = false;
	private AccessType(int code){
		this.code = code;
		this.manual = true;
	}

	private AccessType(){
	}

	public int getCode(){
		return code;
	}
}
