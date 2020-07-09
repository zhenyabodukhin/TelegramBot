package com.telegram.bot.repository;

import com.telegram.bot.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select u from City u where u.name = :name")
    City findByName(@Param("name") String name);
}
