package com.example.model.base.secure;

import java.io.Serializable;

/**
 * Created by John on 2014/7/28.
 */
public class BaseMessageObject implements Serializable, Cloneable {
	public String toString(){
		MessageFactory converter = new MessageFactory();
		return converter.toStringMessage(this);
	}

	public BaseMessageObject clone(){
		try {
			return (BaseMessageObject) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
