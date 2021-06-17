package com.davidechiarelli.taxcodeapi.service;


import com.davidechiarelli.taxcodeapi.exception.BadCityFormatException;
import com.davidechiarelli.taxcodeapi.exception.BadDateFormatException;
import com.davidechiarelli.taxcodeapi.model.City;
import com.davidechiarelli.taxcodeapi.model.User;
import com.davidechiarelli.taxcodeapi.repository.impl.CityReposytoryImpl;
import com.davidechiarelli.taxcodeapi.service.impl.TaxCodeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

class TaxCode2UserServiceTest {

    TaxCodeService service;
    CityReposytoryImpl cityRepository = mock(CityReposytoryImpl.class);

    @BeforeEach
    void setUp() {
        service = new TaxCodeServiceImpl(cityRepository);
    }

    @Test
    void testSuccess_male(){
        String taxCode = "VRDGPP84E13D862B";

        when(cityRepository.getCityByBelfioreCode(any())).thenReturn(getCityTest1());

        User user = service.parseUser(taxCode);

        assertThat(user).isNotNull();
        assertThat(user.getFirstName()).isEqualTo("GPP");
        assertThat(user.getLastName()).isEqualTo("VRD");
        assertThat(user.getDateOfBirth().getYear()).isEqualTo(1984);
        assertThat(user.getDateOfBirth().getMonth()).isEqualTo(Month.MAY);
        assertThat(user.getDateOfBirth().getDayOfMonth()).isEqualTo(13);
        assertThat(user.getGender()).isEqualTo("M");
        assertThat(user.getCityOfBirth()).isEqualTo("Galatina");
        assertThat(user.getProvinceOfBirth()).isEqualTo("LE");
    }

    @Test
    void testSuccess_female(){
        String taxCode = "NRECRL18R48E388O";

        when(cityRepository.getCityByBelfioreCode(any())).thenReturn(getCityTest2());

        User user = service.parseUser(taxCode);

        assertThat(user).isNotNull();
        assertThat(user.getFirstName()).isEqualTo("CRL");
        assertThat(user.getLastName()).isEqualTo("NRE");
        assertThat(user.getDateOfBirth().getYear()).isEqualTo(1918);
        assertThat(user.getDateOfBirth().getMonth()).isEqualTo(Month.OCTOBER);
        assertThat(user.getDateOfBirth().getDayOfMonth()).isEqualTo(8);
        assertThat(user.getGender()).isEqualTo("F");
        assertThat(user.getCityOfBirth()).isEqualTo("Jesi");
        assertThat(user.getProvinceOfBirth()).isEqualTo("AN");
    }

    @Test
    void testError_badcity(){
        String taxCode = "NRECRL18R48AAAAO";

        when(cityRepository.getCityByBelfioreCode(any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BadCityFormatException.class).isThrownBy(() -> {
            service.parseUser(taxCode);
        });
    }

    @Test
    void testError_baddate(){
        String taxCode = "VRDGPPAAE13D862B";

        when(cityRepository.getCityByBelfioreCode(any())).thenReturn(getCityTest2());

        assertThatExceptionOfType(BadDateFormatException.class).isThrownBy(() -> {
            service.parseUser(taxCode);
        });
    }

    @Test
    void testError_badmonth(){
        String taxCode = "VRDGPP84Z13D862B";

        when(cityRepository.getCityByBelfioreCode(any())).thenReturn(getCityTest2());

        assertThatExceptionOfType(BadDateFormatException.class).isThrownBy(() -> {
            service.parseUser(taxCode);
        });
    }

    @Test
    void testError_badday(){
        String taxCode = "VRDGPP84EAAD862B";

        when(cityRepository.getCityByBelfioreCode(any())).thenReturn(getCityTest2());

        assertThatExceptionOfType(BadDateFormatException.class).isThrownBy(() -> {
            service.parseUser(taxCode);
        });
    }

    private Optional<City> getCityTest1(){
        City city = new City();
        city.setDenominazione("Galatina");
        city.setSiglaProvincia("LE");
        return Optional.of(city);
    }

    private Optional<City> getCityTest2(){
        City city = new City();
        city.setDenominazione("Jesi");
        city.setSiglaProvincia("AN");
        return Optional.of(city);
    }
}
