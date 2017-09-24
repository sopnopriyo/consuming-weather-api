package com.oddle.app.dao;

import com.oddle.app.model.City;
import com.oddle.app.model.CityWeatherRecord;
import com.oddle.app.service.CityService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("cityWeatherRecordDao")
public class CityWeatherRecordDaoImpl extends AbstractDao<Integer,CityWeatherRecord> implements CityWeatherRecordDao{

    @Autowired
    CityService cityService;
    public CityWeatherRecord findById(int id) {
        return null;
    }

    public List<CityWeatherRecord> findAllByCityName(String cityName) {
        Criteria criteria = createEntityCriteria().addOrder(Order.desc("timestamp"));
        criteria.add(Restrictions.eq("city",cityService.findByCityName(cityName)));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<CityWeatherRecord> cityWeatherRecords = (List<CityWeatherRecord>) criteria.list();
        return cityWeatherRecords;
    }

    public void save(CityWeatherRecord cityWeatherRecord) {
        if(findByTimestampAndCityId(cityWeatherRecord.getTimestamp(),cityWeatherRecord.getCity())==null){
            persist(cityWeatherRecord);
        }
    }

    private CityWeatherRecord findByTimestampAndCityId(Integer timestamp, City city){
        Criteria criteria = getSession().createCriteria(CityWeatherRecord.class);
        criteria.add(Restrictions.eq("timestamp",timestamp));
        criteria.add(Restrictions.eq("city",city));
        return (CityWeatherRecord) criteria.uniqueResult();
    }

    public void deleteById(Integer id) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("cityWeatherRecordId", id));
        CityWeatherRecord cityWeatherRecord = (CityWeatherRecord)crit.uniqueResult();
        delete(cityWeatherRecord);
    }

    public List<CityWeatherRecord> findAllCityWeatherRecord() {
        Criteria criteria = createEntityCriteria();
        criteria.addOrder(Order.asc("city"));
        criteria.addOrder(Order.desc("timestamp"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<CityWeatherRecord> cityWeatherRecords = (List<CityWeatherRecord>) criteria.list();
        return cityWeatherRecords;
    }
}
