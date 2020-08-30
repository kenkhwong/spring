package com.example.ken.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract interface EntityRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

}
