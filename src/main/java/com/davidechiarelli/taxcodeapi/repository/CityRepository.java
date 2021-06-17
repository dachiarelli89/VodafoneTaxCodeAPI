package com.davidechiarelli.taxcodeapi.repository;

import com.davidechiarelli.taxcodeapi.model.City;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository {
    List<City> getAllCities();
    Optional<City> getCityByBelfioreCode(String code);
    Optional<City> getCityByName(String city);
}
