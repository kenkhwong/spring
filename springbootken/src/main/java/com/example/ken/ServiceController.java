package com.example.ken;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ken.jpa.Account;
import com.example.ken.jpa.AccountRepository;
import com.example.ken.jpa.Address;
import com.example.ken.jpa.AddressRepository;

import com.example.ken.support.AccountNumberHandler;

@Transactional(readOnly=true)
@RestController
class ServiceController {

	@Autowired
	private AccountRepository accounts;
	@Autowired
	private AddressRepository addresses;
	@Autowired
	private AccountNumberHandler accountNumbers;
	
	@GetMapping("/accounts")
	List<Account> getAll() {	
		return accounts.findAll();
	}
	
	@GetMapping("/accounts/{accountNumber}")
	Account get(@PathVariable int accountNumber) {
		return accounts.findByAccountNumber(accountNumber)
	      .orElseThrow(() -> new NoRecordException(Account.class, accountNumber));
	}
	
	@Transactional
	@PostMapping("/accounts")
	Account newAccount(@RequestParam String owner, @RequestParam(defaultValue="0") int balance) {
		return accounts.save(new Account(accountNumbers.nextInt(), owner, balance));
	}
	
	@Transactional
	@PutMapping("/accounts/{accountNumber}")
	Account updateBalance(@RequestParam int balance, @PathVariable int accountNumber) {
	    return accounts.findByAccountNumber(accountNumber)
	    	.map(account -> {
	    		account.setBalance(balance);
	    		return accounts.save(account);
	    	})
	    	.orElseThrow(() -> new NoRecordException(Account.class, accountNumber));
	}
	
	@Transactional
	@PutMapping("/accounts/{accountNumber}/address")
	Account setAddress(@RequestBody Address address, @PathVariable int accountNumber) {
	    return accounts.findByAccountNumber(accountNumber)
	      .map(account -> {
	    	  Address oldAddress = account.getAddress();
	    	  if (oldAddress == null) {
	    		  account.setAddress(addresses.save(address));
	    	  } else {
	    		  oldAddress.setStreet(address.getStreet());
	    		  oldAddress.setTown(address.getTown());
	    		  oldAddress.setPostCode(address.getPostCode());
	    	  }
	    	  return accounts.save(account);
	      })
	      .orElseThrow(() -> new NoRecordException(Account.class, accountNumber));
	}
	
	@Transactional
	@DeleteMapping("/accounts/{accountNumber}")
	void deleteAccount(@PathVariable int accountNumber) {
		accounts.delete(accounts.findByAccountNumber(accountNumber)
			.orElseThrow(() -> new NoRecordException(Account.class, accountNumber)));
	}
}
