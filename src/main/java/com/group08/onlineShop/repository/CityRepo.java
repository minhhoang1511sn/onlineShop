package com.group08.onlineShop.repository;

import com.group08.onlineShop.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepo extends JpaRepository<City, Long> {
}
