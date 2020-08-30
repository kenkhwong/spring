package com.example.ken.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn
public class Account extends BaseEntity {
	
	@Column(unique=true, nullable=false)
	private int accountNumber;
	@Column(nullable=false)
	private String owner;
	private double balance;
	
	@OneToOne(orphanRemoval=true)
	private Address address;
	
	protected Account() {}
	
	public Account(int accountNumber, String owner, double balance)
	{
		this.accountNumber = accountNumber;
		this.owner = owner;
		this.balance = balance;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public Address getAddress() {
		return address;
	}
	
	@Override
	public String toString() {
	    return "Account: " + accountNumber + " " + owner + " " + balance;
	}
}
