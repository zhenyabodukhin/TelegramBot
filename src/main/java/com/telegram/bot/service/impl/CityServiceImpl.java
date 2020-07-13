package com.telegram.bot.service.impl;

import com.telegram.bot.domain.City;
import com.telegram.bot.exception.EntityAlreadyExistException;
import com.telegram.bot.exception.EntityNotFoundException;
import com.telegram.bot.repository.CityRepository;
import com.telegram.bot.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    @Transactional
    public City save(City city) {
        String name = city.getName();
        City result = cityRepository.findByName(name);
        if(result == null) {
            return cityRepository.save(city);
        } else {
            throw new EntityAlreadyExistException(City.class, name);
        }
    }

    @Override
    @Transactional
    public City update(City city) {
        String name = city.getName();
        City result = cityRepository.findByName(name);
        if(result != null) {
            return cityRepository.saveAndFlush(city);
        } else {
            throw new EntityNotFoundException(City.class, name);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(cityRepository.findById(id).isPresent()) {
            cityRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(City.class, id);
        }
    }

    @Override
    public City findById(Long id) {
        Optional<City> result = cityRepository.findById(id);
        if(result.isPresent()) {
            return result.get();
        } else {
            throw new EntityNotFoundException(City.class, id);
        }
    }

    @Override
    public City findByName(String name) {
        return cityRepository.findByName(name);
    }
}
