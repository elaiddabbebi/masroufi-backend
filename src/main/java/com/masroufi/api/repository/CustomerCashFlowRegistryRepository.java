package com.masroufi.api.repository;

import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.CustomerCashFlowRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerCashFlowRegistryRepository extends JpaRepository<CustomerCashFlowRegistry, Long>, JpaSpecificationExecutor<CustomerCashFlowRegistry> {

    CustomerCashFlowRegistry findByUuid(String uuid);

    List<CustomerCashFlowRegistry> findAllByCustomerOrderByDateDescIdDesc(Account customer);

}
