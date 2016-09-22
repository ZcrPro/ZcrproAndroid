package com.example.model.axinpay_school;

public enum SchoolFeeGroupType {
	normal(),
	pack(),
	single(),

	;

	static{
		for(SchoolFeeGroupType e: values()){
			if(!e.manual && e.ordinal() > 0){
				e.code = values()[e.ordinal() - 1].code + 1;
			}
		}
	}

	private int code = 0;
	private boolean manual = false;
	private SchoolFeeGroupType(int code){
		this.code = code;
		this.manual = true;
	}

	private SchoolFeeGroupType(){
	}

	public int getCode(){
		return code;
	}
}
