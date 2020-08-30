package com.example.ken.jpa;

import java.util.Optional;

public interface AccountRepository extends EntityRepository<Account> {

	public Optional<Account> findByAccountNumber(int accountNum);
}
