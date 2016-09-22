package com.example.model.customer;

public enum PayChannel {
	LLYT(),
	UnionPay(),
	RFID(),
	AnXinDai(),
	CCBWapPay(),
	BindPay(),
	none(),

	;

	static{
		for(PayChannel e: values()){
			if(!e.manual && e.ordinal() > 0){
				e.code = values()[e.ordinal() - 1].code + 1;
			}
		}
	}

	private int code = 0;
	private boolean manual = false;
	private PayChannel(int code){
		this.code = code;
		this.manual = true;
	}

	private PayChannel(){
	}

	public int getCode(){
		return code;
	}
}
