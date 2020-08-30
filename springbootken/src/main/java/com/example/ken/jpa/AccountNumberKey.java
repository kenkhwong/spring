package com.example.ken.jpa;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn
public class AccountNumberKey extends DerivedIntegerKey {
	
	protected AccountNumberKey() {}
	
	public AccountNumberKey(int nextInt) {
		super(nextInt);
	}
}
