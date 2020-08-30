package com.example.ken.jpa;

@SuppressWarnings("serial")
public class NoRecordException extends Exception {
	
	public NoRecordException(String keyName, Object keyValue) {
		super(keyName + " = " + keyValue);
	}
}
