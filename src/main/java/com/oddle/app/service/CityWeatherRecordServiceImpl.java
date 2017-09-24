package com.oddle.app.service;

import com.oddle.app.dao.CityWeatherRecordDao;
import com.oddle.app.model.CityWeatherRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("cityWeatherRecordService")
@Transactional
public class CityWeatherRecordServiceImpl implements CityWeatherRecordService{

    @Autowired
    CityWeatherRecordDao cityWeatherRecordDao;

    public CityWeatherRecord findById(int id) {
        return cityWeatherRecordDao.findById(id);
    }

    public List<CityWeatherRecord> findAllByCityName(String cityName) {
        return cityWeatherRecordDao.findAllByCityName(cityName);
    }

    public void save(CityWeatherRecord cityWeatherRecord) {
        cityWeatherRecordDao.save(cityWeatherRecord);
    }

    public void deleteById(Integer id) {
        cityWeatherRecordDao.deleteById(id);
    }

    public List<CityWeatherRecord> findAllCityWeatherRecord() {
        return cityWeatherRecordDao.findAllCityWeatherRecord();
    }
}
