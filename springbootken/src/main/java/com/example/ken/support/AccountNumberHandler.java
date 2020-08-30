package com.example.ken.support;

import org.springframework.stereotype.Service;

import com.example.ken.jpa.AccountNumberKey;
import com.example.ken.jpa.AccountNumberKeyRepository;

@Service
public class AccountNumberHandler extends IncrementalIntegerKeyHandler<AccountNumberKey> {
	
	public AccountNumberHandler(AccountNumberKeyRepository keyRepo) {
		super(keyRepo, new AccountNumberKey(1), 1);
	}
}
