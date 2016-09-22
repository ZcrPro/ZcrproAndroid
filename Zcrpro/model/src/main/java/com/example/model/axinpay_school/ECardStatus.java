package com.example.model.axinpay_school;

public enum ECardStatus {
	OK(),
	HasReportLoss(),
	Closed(),
	NeedPassword(),
	Disabled(),

	;

	static{
		for(ECardStatus e: values()){
			if(!e.manual && e.ordinal() > 0){
				e.code = values()[e.ordinal() - 1].code + 1;
			}
		}
	}

	private int code = 0;
	private boolean manual = false;
	private ECardStatus(int code){
		this.code = code;
		this.manual = true;
	}

	private ECardStatus(){
	}

	public int getCode(){
		return code;
	}
}
