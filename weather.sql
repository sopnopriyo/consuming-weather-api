
CREATE TABLE `city` (
  `city_id` int(11) NOT NULL AUTO_INCREMENT,
  `city_name` varchar(255) NOT NULL,
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
CREATE TABLE `city_weather_record` (
  `city_weather_record_id` int(11) NOT NULL AUTO_INCREMENT,
  `city_id` int(11) DEFAULT NULL,
  `timestamp` int(11) DEFAULT NULL,
  `temperature` double DEFAULT NULL,
  `pressure` double DEFAULT NULL,
  `humidity` double DEFAULT NULL,
  `temperature_min` double DEFAULT NULL,
  `temperature_max` double DEFAULT NULL,
  `wind_speed` double DEFAULT NULL,
  `weather_condition` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`city_weather_record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

