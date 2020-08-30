package com.example.ken.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn
public abstract class DerivedIntegerKey extends BaseEntity {

	@Column(nullable=false)
	private int nextInt;
	
	protected DerivedIntegerKey() {}
	
	protected DerivedIntegerKey(int nextInt) {
		this.nextInt = nextInt;
	}

	public int getNextInt() {
		return nextInt;
	}

	public void setNextInt(int nextInt) {
		this.nextInt = nextInt;
	}
	
}
