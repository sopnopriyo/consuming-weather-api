package com.oddle.app.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "city", uniqueConstraints = {
        @UniqueConstraint(columnNames = "city_id"),
        @UniqueConstraint(columnNames = "city_name") })
public class City {
    private Integer cityId;
    private String cityName;
    private Set<CityWeatherRecord> stockDailyRecords = new HashSet<CityWeatherRecord>(0);

    public City() {
    }

    public City(String cityName, Set<CityWeatherRecord> stockDailyRecords) {
        this.cityName = cityName;
        this.stockDailyRecords = stockDailyRecords;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "city_id", unique = true, nullable = false)
    public Integer getCityId() {
        return this.cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Column(name = "city_name", unique = true, nullable = false, length = 20)
    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    public Set<CityWeatherRecord> getStockDailyRecords() {
        return stockDailyRecords;
    }

    public void setStockDailyRecords(Set<CityWeatherRecord> stockDailyRecords) {
        this.stockDailyRecords = stockDailyRecords;
    }
}
