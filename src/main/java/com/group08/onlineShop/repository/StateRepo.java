package com.group08.onlineShop.repository;


import com.group08.onlineShop.model.Country;
import com.group08.onlineShop.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StateRepo extends JpaRepository<State, Long> {
    List<Country> findStateByStateId(Long stateID);
}
