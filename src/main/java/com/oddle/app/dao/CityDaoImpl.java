package com.oddle.app.dao;

import com.oddle.app.model.City;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository("cityDao")
public class CityDaoImpl extends AbstractDao<Integer,City> implements CityDao{


    public void save(City city) {
        persist(city);
    }

    public void update(City city) {
        getSession().update(city);
    }

    public void delete(City city) {
        getSession().delete(city);
    }

    public City findByCityName(String cityName) {
        Criteria criteria = getSession().createCriteria(City.class);
        criteria.add(Restrictions.eq("cityName",cityName));
        return (City) criteria.uniqueResult();
    }
}
