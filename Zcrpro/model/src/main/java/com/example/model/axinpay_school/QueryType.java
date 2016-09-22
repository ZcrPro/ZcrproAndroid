package com.example.model.axinpay_school;

public enum QueryType {
	none(),
	normal(),
	password(),
	webpage(),

	;

	static{
		for(QueryType e: values()){
			if(!e.manual && e.ordinal() > 0){
				e.code = values()[e.ordinal() - 1].code + 1;
			}
		}
	}

	private int code = 0;
	private boolean manual = false;
	private QueryType(int code){
		this.code = code;
		this.manual = true;
	}

	private QueryType(){
	}

	public int getCode(){
		return code;
	}
}
