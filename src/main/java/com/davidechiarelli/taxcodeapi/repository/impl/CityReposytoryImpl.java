package com.davidechiarelli.taxcodeapi.repository.impl;

import com.davidechiarelli.taxcodeapi.model.City;
import com.davidechiarelli.taxcodeapi.repository.CityRepository;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class CityReposytoryImpl implements CityRepository {
    private final Logger log = LogManager.getLogger(getClass());

    @Override
    public List<City> getAllCities() {
        try {
            ColumnPositionMappingStrategy<City> ms = new ColumnPositionMappingStrategy<>();
            ms.setType(City.class);

            InputStream resource = new ClassPathResource("files/cities.csv").getInputStream();
            Reader anagReader = new BufferedReader(new InputStreamReader(resource));

            List<City> listCitiesCsv;

            CsvToBean<City> cb = new CsvToBeanBuilder<City>(anagReader)
                    .withType(City.class)
                    .withMappingStrategy(ms)
                    .withSkipLines(3)
                    .withSeparator(';')
                    .withIgnoreQuotations(true)
                    .build();

            listCitiesCsv = cb.parse();

            return listCitiesCsv;
        } catch (IOException e) {
            log.error(e);
            return Collections.emptyList();
        }
    }

    public Optional<City> getCityByBelfioreCode(String code) {
        List<City> cities = getAllCities();
        return cities.stream().filter(city -> city.getCodiceCatastale().equalsIgnoreCase(code)).findFirst();
    }

    @Override
    public Optional<City> getCityByName(String city) {
        List<City> cities = getAllCities();

        return cities.stream().filter(cityEntity -> cityEntity.getDenominazione().equalsIgnoreCase(city)).findFirst();
    }
}
