package com.oddle.app.service;

import com.oddle.app.dao.CityDao;
import com.oddle.app.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("cityService")
@Transactional
public class CityServiceImpl implements CityService{

    @Autowired
    CityDao cityDao;

    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public void save(City city) {
        cityDao.save(city);
    }

    public void update(City city) {
        cityDao.update(city);
    }

    public void delete(City city) {
        cityDao.delete(city);
    }

    public City findByCityName(String cityName) {
        return cityDao.findByCityName(cityName);
    }

    public boolean isUniqueCity(String cityName) {
        City city = findByCityName(cityName);
        return ( city == null || !cityName.equalsIgnoreCase(city.getCityName()));
    }
}
