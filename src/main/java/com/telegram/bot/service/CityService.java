package com.telegram.bot.service;

import com.telegram.bot.domain.City;

import java.util.List;

public interface CityService {
    List<City> findAll();

    City save (City city);

    City update (City city);

    void delete (Long id);

    City findById(Long id);

    City findByName (String name);
}
