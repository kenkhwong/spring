package com.example.ken;

import com.example.ken.jpa.BaseEntity;

@SuppressWarnings("serial")
class NoRecordException extends RuntimeException {
	
	public NoRecordException(Class<? extends BaseEntity> entityClass, Object keyValue) {
		super("No such record: entity = " + entityClass.getSimpleName() + ", key = " + keyValue);
	}
}
