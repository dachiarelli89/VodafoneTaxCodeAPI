package com.davidechiarelli.taxcodeapi.service;


import com.davidechiarelli.taxcodeapi.exception.BadCityFormatException;
import com.davidechiarelli.taxcodeapi.exception.BadRequestException;
import com.davidechiarelli.taxcodeapi.model.City;
import com.davidechiarelli.taxcodeapi.model.User;
import com.davidechiarelli.taxcodeapi.repository.impl.CityReposytoryImpl;
import com.davidechiarelli.taxcodeapi.service.impl.TaxCodeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

class User2TaxCodeServiceTest {

    TaxCodeService service;
    CityReposytoryImpl cityRepository = mock(CityReposytoryImpl.class);

    @BeforeEach
    void setUp() {
        service = new TaxCodeServiceImpl(cityRepository);
    }

    @Test
    void testSuccess_user1(){
        User user = getUser1();
        when(cityRepository.getCityByName(any())).thenReturn(getCity1());

        // NREPLA89M12H501H

        String taxCode = service.calculateTaxCode(user);

        assertThat(taxCode)
                .isNotEmpty()
                .hasSize(16);
        assertThat(taxCode.substring(0, 3)).isEqualTo("NRE");
        assertThat(taxCode.substring(3, 6)).isEqualTo("PLA");
        assertThat(taxCode.substring(6, 8)).isEqualTo("89");
        assertThat(taxCode.substring(8, 9)).isEqualTo("M");
        assertThat(taxCode.substring(9, 11)).isEqualTo("12");
        assertThat(taxCode.substring(11, 15)).isEqualTo("H501");
        assertThat(taxCode.substring(15, 16)).isEqualTo("H");
    }

    @Test
    void testSuccess_user2(){
        User user = getUser2();
        when(cityRepository.getCityByName(any())).thenReturn(getCity2());

        // BNCGPP95A71L736S

        String taxCode = service.calculateTaxCode(user);

        assertThat(taxCode)
                .isNotEmpty()
                .hasSize(16);
        assertThat(taxCode.substring(0, 3)).isEqualTo("BNC");
        assertThat(taxCode.substring(3, 6)).isEqualTo("GPP");
        assertThat(taxCode.substring(6, 8)).isEqualTo("95");
        assertThat(taxCode.substring(8, 9)).isEqualTo("A");
        assertThat(taxCode.substring(9, 11)).isEqualTo("71");
        assertThat(taxCode.substring(11, 15)).isEqualTo("L736");
        assertThat(taxCode.substring(15, 16)).isEqualTo("S");
    }

    @Test
    void testSuccess_user3(){
        User user = getUser3();
        when(cityRepository.getCityByName(any())).thenReturn(getCity3());

        // XIAYUX40H63A794P

        String taxCode = service.calculateTaxCode(user);


        assertThat(taxCode)
                .isNotEmpty()
                .hasSize(16);
        assertThat(taxCode.substring(0, 3)).isEqualTo("XIA");
        assertThat(taxCode.substring(3, 6)).isEqualTo("YUX");
        assertThat(taxCode.substring(6, 8)).isEqualTo("40");
        assertThat(taxCode.substring(8, 9)).isEqualTo("H");
        assertThat(taxCode.substring(9, 11)).isEqualTo("63");
        assertThat(taxCode.substring(11, 15)).isEqualTo("A794");
        assertThat(taxCode.substring(15, 16)).isEqualTo("P");
    }

    @Test
    void testSuccess_user4(){
        User user = getUser4();
        when(cityRepository.getCityByName(any())).thenReturn(getCity4());

        // ZUXSUX90C45A794V

        String taxCode = service.calculateTaxCode(user);


        assertThat(taxCode)
                .isNotEmpty()
                .hasSize(16);
        assertThat(taxCode.substring(0, 3)).isEqualTo("ZUX");
        assertThat(taxCode.substring(3, 6)).isEqualTo("SUX");
        assertThat(taxCode.substring(6, 8)).isEqualTo("90");
        assertThat(taxCode.substring(8, 9)).isEqualTo("C");
        assertThat(taxCode.substring(9, 11)).isEqualTo("45");
        assertThat(taxCode.substring(11, 15)).isEqualTo("A794");
        assertThat(taxCode.substring(15, 16)).isEqualTo("V");
    }

    @Test
    void testSuccess_user5(){
        User user = getUser5();
        when(cityRepository.getCityByName(any())).thenReturn(getCity5());

        String taxCode = service.calculateTaxCode(user);


        assertThat(taxCode)
                .isNotEmpty()
                .hasSize(16);
        assertThat(taxCode.substring(0, 3)).isEqualTo("CHR");
        assertThat(taxCode.substring(3, 6)).isEqualTo("DVD");
        assertThat(taxCode.substring(6, 8)).isEqualTo("89");
        assertThat(taxCode.substring(8, 9)).isEqualTo("T");
        assertThat(taxCode.substring(9, 11)).isEqualTo("18");
        assertThat(taxCode.substring(11, 15)).isEqualTo("F784");
        assertThat(taxCode.substring(15, 16)).isEqualTo("H");
    }

    @Test
    void testSuccess_user6(){
        User user = getUser6();
        when(cityRepository.getCityByName(any())).thenReturn(getCity6());

        String taxCode = service.calculateTaxCode(user);

        // ZIXSNU69M58L219K

        assertThat(taxCode)
                .isNotEmpty()
                .hasSize(16);
        assertThat(taxCode.substring(0, 3)).isEqualTo("ZIX");
        assertThat(taxCode.substring(3, 6)).isEqualTo("SNU");
        assertThat(taxCode.substring(6, 8)).isEqualTo("69");
        assertThat(taxCode.substring(8, 9)).isEqualTo("M");
        assertThat(taxCode.substring(9, 11)).isEqualTo("58");
        assertThat(taxCode.substring(11, 15)).isEqualTo("L219");
        assertThat(taxCode.substring(15, 16)).isEqualTo("K");
    }

    @Test
    void testSuccess_user7(){
        User user = getUser7();
        when(cityRepository.getCityByName(any())).thenReturn(getCity7());

        String taxCode = service.calculateTaxCode(user);

        // GVNPCC00B03C933P

        assertThat(taxCode)
                .isNotEmpty()
                .hasSize(16);
        assertThat(taxCode.substring(0, 3)).isEqualTo("GVN");
        assertThat(taxCode.substring(3, 6)).isEqualTo("PCC");
        assertThat(taxCode.substring(6, 8)).isEqualTo("00");
        assertThat(taxCode.substring(8, 9)).isEqualTo("B");
        assertThat(taxCode.substring(9, 11)).isEqualTo("03");
        assertThat(taxCode.substring(11, 15)).isEqualTo("C933");
        assertThat(taxCode.substring(15, 16)).isEqualTo("P");
    }

    @Test
    void testError_noValidCharInFirstName(){
        User user = getUser8();
        when(cityRepository.getCityByName(any())).thenReturn(getCity8());

        assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> service.calculateTaxCode(user));
    }

    @Test
    void testError_noValidCharInLastName(){
        User user = getUser9();
        when(cityRepository.getCityByName(any())).thenReturn(getCity9());

        assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> service.calculateTaxCode(user));
    }

    @Test
    void testSuccess_user10(){
        User user = getUser10();
        when(cityRepository.getCityByName(any())).thenReturn(getCity10());

        String taxCode = service.calculateTaxCode(user);

        // DGLGCI80A01G337L

        assertThat(taxCode)
                .isNotEmpty()
                .hasSize(16);
        assertThat(taxCode.substring(0, 3)).isEqualTo("DGL");
        assertThat(taxCode.substring(3, 6)).isEqualTo("GCI");
        assertThat(taxCode.substring(6, 8)).isEqualTo("80");
        assertThat(taxCode.substring(8, 9)).isEqualTo("A");
        assertThat(taxCode.substring(9, 11)).isEqualTo("01");
        assertThat(taxCode.substring(11, 15)).isEqualTo("G337");
        assertThat(taxCode.substring(15, 16)).isEqualTo("L");
    }

    @Test
    void testSuccess_user11(){
        User user = getUser11();
        when(cityRepository.getCityByName(any())).thenReturn(getCity11());

        String taxCode = service.calculateTaxCode(user);

        // GGILAE80A01G337F

        assertThat(taxCode)
                .isNotEmpty()
                .hasSize(16);
        assertThat(taxCode.substring(0, 3)).isEqualTo("GGI");
        assertThat(taxCode.substring(3, 6)).isEqualTo("LAE");
        assertThat(taxCode.substring(6, 8)).isEqualTo("80");
        assertThat(taxCode.substring(8, 9)).isEqualTo("A");
        assertThat(taxCode.substring(9, 11)).isEqualTo("01");
        assertThat(taxCode.substring(11, 15)).isEqualTo("G337");
        assertThat(taxCode.substring(15, 16)).isEqualTo("F");
    }

    @Test
    void testError_cityNotFound(){
        User user = getUser3();
        when(cityRepository.getCityByName(any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BadCityFormatException.class).isThrownBy(() -> service.calculateTaxCode(user));
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

    private User getUser1(){
        return new User("Paolo",
                "Neri",
                "M",
                LocalDate.of(1989, 8, 12),
                "Roma",
                "RM");
    }

    private Optional<City> getCity1(){
        City city = new City();
        city.setDenominazione("Roma");
        city.setCodiceCatastale("H501");
        return Optional.of(city);
    }

    private User getUser2(){
        return new User("Giuseppina",
                "Bianchi",
                "F",
                LocalDate.of(1995, 1, 31),
                "Venezia",
                "VE");
    }

    private Optional<City> getCity2(){
        City city = new City();
        city.setDenominazione("Venezia");
        city.setCodiceCatastale("L736");
        return Optional.of(city);
    }

    private User getUser3(){
        return new User("Yu",
                "Xia",
                "F",
                LocalDate.of(1940, 6, 23),
                "Bergamo",
                "BG");
    }

    private Optional<City> getCity3(){
        City city = new City();
        city.setDenominazione("Bergamo");
        city.setCodiceCatastale("A794");
        return Optional.of(city);
    }

    private User getUser4(){
        return new User("Su",
                "Zu",
                "F",
                LocalDate.of(1990, 3, 5),
                "Bergamo",
                "BG");
    }

    private Optional<City> getCity4(){
        City city = new City();
        city.setDenominazione("Bergamo");
        city.setCodiceCatastale("A794");
        return Optional.of(city);
    }

    private User getUser5(){
        return new User("Davide",
                "Chiarelli",
                "M",
                LocalDate.of(1989, 12, 18),
                "Mottola",
                "TA");
    }

    private Optional<City> getCity5(){
        City city = new City();
        city.setDenominazione("Milano");
        city.setCodiceCatastale("F784");
        return Optional.of(city);
    }

    private User getUser6(){
        return new User("Sun",
                "Zi",
                "F",
                LocalDate.of(1969, 8, 18),
                "Torino",
                "TO");
    }

    private Optional<City> getCity6(){
        City city = new City();
        city.setDenominazione("Torino");
        city.setCodiceCatastale("L219");
        return Optional.of(city);
    }

    private User getUser7(){
        return new User("Peducci",
                "Giovanni",
                "M",
                LocalDate.of(2000, 2, 3),
                "Genova",
                "GE");
    }

    private Optional<City> getCity7(){
        City city = new City();
        city.setDenominazione("Como");
        city.setCodiceCatastale("C933");
        return Optional.of(city);
    }

    private User getUser8(){
        return new User("%/?^\"£",
                "Giovanni",
                "M",
                LocalDate.of(2000, 2, 3),
                "Genova",
                "GE");
    }

    private Optional<City> getCity8(){
        return getCity7();
    }

    private User getUser9(){
        return new User("Carlo",
                "--.==&&/",
                "M",
                LocalDate.of(2000, 2, 3),
                "Genova",
                "GE");
    }

    private Optional<City> getCity9(){
        return getCity7();
    }

    private User getUser10(){
        return new User("GICÒ",
                "D'AGLIÒ",
                "M",
                LocalDate.of(1980, 1, 1),
                "Parma",
                "PR");
    }

    private Optional<City> getCity10(){
        City city = new City();
        city.setDenominazione("Parma");
        city.setCodiceCatastale("G337");
        return Optional.of(city);
    }

    private User getUser11(){
        return new User("Alea",
                "Gigi",
                "M",
                LocalDate.of(1980, 1, 1),
                "Parma",
                "PR");
    }

    private Optional<City> getCity11(){
        City city = new City();
        city.setDenominazione("Parma");
        city.setCodiceCatastale("G337");
        return Optional.of(city);
    }
}
