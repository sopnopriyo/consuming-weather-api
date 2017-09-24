package com.oddle.app.dao;


import com.oddle.app.model.City;

public interface CityDao {
    void save(City city);
    void update(City city);
    void delete(City city);
    City findByCityName(String cityName);
}
