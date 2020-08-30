package com.example.ken;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import com.example.ken.jpa.Account;
import com.example.ken.jpa.AccountRepository;
import com.example.ken.support.AccountNumberHandler;

@Configuration
class LoadDatabase {
	
	@Bean
	CommandLineRunner initAccounts(AccountRepository repository, AccountNumberHandler keyHandler) {
	  return args -> {
		  /*
		  Account[] accounts = { new Account(keyHandler.nextInt(),"Jai Kumar", 41000),
				  new Account(keyHandler.nextInt(),"Rishi", 35000) };
		  
		  for (Account a : accounts) {
			  repository.save(a);
			  System.out.println("Preloaded " + a);
		  }
		  */
	  };
	}
}
