package com.nbkj.bootjpa.repository;

import com.nbkj.bootjpa.entity.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AddressRepository
 * 
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}