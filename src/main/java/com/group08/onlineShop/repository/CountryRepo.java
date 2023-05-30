package com.group08.onlineShop.repository;

import com.group08.onlineShop.model.Country;
import com.group08.onlineShop.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepo extends JpaRepository<Country, Long> {
    List<State> findCountrysByCountryId(Long countryID);
}
