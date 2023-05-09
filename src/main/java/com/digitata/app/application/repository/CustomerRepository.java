package com.digitata.app.application.repository;

import com.digitata.app.application.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by user on 7/8/2021.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
