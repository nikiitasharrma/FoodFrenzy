package com.nikita.frenzy.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikita.frenzy.food.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
