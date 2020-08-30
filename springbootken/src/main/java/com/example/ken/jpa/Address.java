package com.example.ken.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn
public class Address extends BaseEntity {

	@Column(nullable=false)
	private String street;
	@Column(nullable=false)
	private String town;
	@Column(nullable=false)
	private String postCode;
	
	protected Address() {}
	
	public Address(String street, String town, String postCode) {
		this.street = street;
		this.town = town;
		this.postCode = postCode;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public void setTown(String town) {
		this.town = town;
	}
	
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	public String getStreet() {
		return street;
	}
	
	public String getTown() {
		return town;
	}
	
	public String getPostCode() {
		return postCode;
	}
	
	@Override
	public String toString() {
	    return street + ", " + town + " " + postCode;
	}
}
