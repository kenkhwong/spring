package com.example.ken.support;

import java.util.List;

import com.example.ken.jpa.DerivedIntegerKey;
import com.example.ken.jpa.DerivedIntegerKeyRepository;

public abstract class IncrementalIntegerKeyHandler<K extends DerivedIntegerKey> {
	
	private final DerivedIntegerKeyRepository<K> keyRepo;
	
	private final K initialKey;
	private final int increment;
	
	protected IncrementalIntegerKeyHandler(DerivedIntegerKeyRepository<K> keyRepo,
			K initialKey, int increment) {
		this.keyRepo = keyRepo;
		this.initialKey = initialKey;
		this.increment = increment;
	}
	
	public int nextInt() {
		List<DerivedIntegerKey> keys = keyRepo.findAll();
		
		DerivedIntegerKey key;
		if (keys.isEmpty()) key = initialKey;
		else key = keys.get(0);
		
		int nextNum = key.getNextInt();
		key.setNextInt(nextNum + increment);
		keyRepo.save(key);
		
		return nextNum;
	}
}
