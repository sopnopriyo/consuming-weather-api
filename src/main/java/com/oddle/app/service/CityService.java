package com.oddle.app.service;

import com.oddle.app.model.City;

public interface CityService {

    void save(City city);
    void update(City city);
    void delete(City city);
    City findByCityName(String cityName);
    boolean isUniqueCity(String cityName);
}

