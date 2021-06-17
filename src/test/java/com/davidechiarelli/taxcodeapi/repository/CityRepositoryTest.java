package com.davidechiarelli.taxcodeapi.repository;

import com.davidechiarelli.taxcodeapi.model.City;
import com.davidechiarelli.taxcodeapi.repository.impl.CityReposytoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CityRepositoryTest {
    CityRepository repository;

    @BeforeEach
    void setUp() {
        repository = new CityReposytoryImpl();
    }

    @Test
    void testSuccess_allCities() {
        List<City> cityList = repository.getAllCities();

        assertThat(cityList).isNotNull();
        assertThat(cityList.size()).isEqualTo(7904);
    }

    @Test
    void testSuccess_byBelfioreCode() {
        Optional<City> city = repository.getCityByBelfioreCode("F784");

        assertThat(city)
                .isNotNull()
                .isPresent();
        assertThat(city.get().getDenominazione()).isEqualTo("Mottola");
    }

    @Test
    void testError_NotFound_byBelfioreCode() {
        Optional<City> city = repository.getCityByBelfioreCode("FZZZ");

        assertThat(city)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void testSuccess_byName() {
        Optional<City> city = repository.getCityByName("Mottola");

        assertThat(city)
                .isNotNull()
                .isPresent();
        assertThat(city.get().getCodiceCatastale()).isEqualTo("F784");
    }

    @Test
    void testError_NotFound_byName() {
        Optional<City> city = repository.getCityByName("New York");

        assertThat(city)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void testError_fileNotFound() {
        Optional<City> city = repository.getCityByName("New York");

        assertThat(city)
                .isNotNull()
                .isNotPresent();
    }
}
