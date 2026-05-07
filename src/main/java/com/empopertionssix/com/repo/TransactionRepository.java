package com.empopertionssix.com.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empopertionssix.com.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long> 
{
	

}
