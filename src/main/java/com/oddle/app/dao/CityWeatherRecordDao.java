package com.oddle.app.dao;

import com.oddle.app.model.CityWeatherRecord;

import java.util.List;

public interface CityWeatherRecordDao {

    CityWeatherRecord findById(int id);

    List<CityWeatherRecord> findAllByCityName(String cityName);

    void save(CityWeatherRecord cityWeatherRecord);

    void deleteById(Integer id);

    List<CityWeatherRecord> findAllCityWeatherRecord();
}
