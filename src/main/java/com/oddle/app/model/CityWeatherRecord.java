package com.oddle.app.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "city_weather_record")
public class CityWeatherRecord implements java.io.Serializable{

    private Integer cityWeatherRecordId;
    private City city;
    private Integer timestamp;
    private double temperature;
    private double pressure;
    private double humidity;
    private double temperatureMin;
    private double temperatureMax;
    private double windSpeed;
    private String weatherCondition;

    public CityWeatherRecord() {
    }

    public CityWeatherRecord(City city, Integer timestamp, double temperature, double pressure, double humidity,
                             double temperatureMin, double temperatureMax, double windSpeed,String weatherCondition ) {
        this.city = city;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.windSpeed = windSpeed;
        this.weatherCondition = weatherCondition;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "city_weather_record_id", unique = true, nullable = false)
    public Integer getCityWeatherRecordId() {
        return this.cityWeatherRecordId;
    }

    public void setCityWeatherRecordId(Integer cityWeatherRecordId) {
        this.cityWeatherRecordId = cityWeatherRecordId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id", nullable = false)
    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Column(name = "timestamp")
    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    @Column(name = "temperature")
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Column(name = "pressure")
    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    @Column(name = "humidity")
    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    @Column(name = "temperature_min")
    public double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    @Column(name = "temperature_max")
    public double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    @Column(name = "wind_speed")
    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Column(name = "weather_condition")
    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }
}
