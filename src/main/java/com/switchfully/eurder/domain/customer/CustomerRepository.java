package com.switchfully.eurder.domain.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
@Transactional
public interface CustomerRepository extends CrudRepository<Customer, UUID>{}
