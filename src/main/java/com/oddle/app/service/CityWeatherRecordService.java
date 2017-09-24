package com.oddle.app.service;

import com.oddle.app.model.CityWeatherRecord;

import java.util.List;

public interface CityWeatherRecordService {

    CityWeatherRecord findById(int id);

    List<CityWeatherRecord> findAllByCityName(String cityName);

    void save(CityWeatherRecord cityWeatherRecord);

    void deleteById(Integer id);

    List<CityWeatherRecord> findAllCityWeatherRecord();
}
