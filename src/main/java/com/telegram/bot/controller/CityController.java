package com.telegram.bot.controller;

import com.telegram.bot.domain.City;
import com.telegram.bot.request.CityCrudRequest;
import com.telegram.bot.service.impl.CityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/city")
@RequiredArgsConstructor
public class CityController {

    private final CityServiceImpl cityService;

    @GetMapping("/all")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<City>> getCities() {
        return new ResponseEntity<>(cityService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/find")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<City> getCityDescription(@RequestBody @Valid CityCrudRequest request) {
        return new ResponseEntity<>(cityService.findByName(request.getName()), HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<City> createCity(@RequestBody @Valid CityCrudRequest request) {
        City city = new City();
        city.setName(request.getName());
        city.setDescription(request.getDescription());

        return new ResponseEntity<>(cityService.save(city), HttpStatus.OK);
    }

    @PostMapping("/update")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<City> updateCity(@RequestBody @Valid CityCrudRequest request) {
        City city = cityService.findByName(request.getName());
        city.setDescription(request.getDescription());

        return new ResponseEntity<>(cityService.update(city), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> deleteCity(@RequestBody @Valid CityCrudRequest request) {
        City city = cityService.findByName(request.getName());
        cityService.delete(city.getId());

        return new ResponseEntity<>("Удалено", HttpStatus.OK);
    }
}
