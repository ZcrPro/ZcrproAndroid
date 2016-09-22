package com.example.model.axinpay_school;

public enum QRCodeMethod {
	scan_bind_account(),
	scan_pay(),
	unknown(),

	;

	static{
		for(QRCodeMethod e: values()){
			if(!e.manual && e.ordinal() > 0){
				e.code = values()[e.ordinal() - 1].code + 1;
			}
		}
	}

	private int code = 0;
	private boolean manual = false;
	private QRCodeMethod(int code){
		this.code = code;
		this.manual = true;
	}

	private QRCodeMethod(){
	}

	public int getCode(){
		return code;
	}
}
