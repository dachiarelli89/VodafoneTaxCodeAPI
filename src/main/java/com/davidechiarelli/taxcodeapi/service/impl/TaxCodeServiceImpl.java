package com.davidechiarelli.taxcodeapi.service.impl;

import com.davidechiarelli.taxcodeapi.Constants;
import com.davidechiarelli.taxcodeapi.exception.BadCityFormatException;
import com.davidechiarelli.taxcodeapi.exception.BadDateFormatException;
import com.davidechiarelli.taxcodeapi.exception.BadRequestException;
import com.davidechiarelli.taxcodeapi.exception.UnprocessableDataException;
import com.davidechiarelli.taxcodeapi.model.City;
import com.davidechiarelli.taxcodeapi.model.User;
import com.davidechiarelli.taxcodeapi.repository.CityRepository;
import com.davidechiarelli.taxcodeapi.repository.impl.CityReposytoryImpl;
import com.davidechiarelli.taxcodeapi.service.TaxCodeService;
import com.davidechiarelli.taxcodeapi.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaxCodeServiceImpl implements TaxCodeService {
    Logger log = LoggerFactory.getLogger(TaxCodeServiceImpl.class);
    private CityRepository cityRepository;

    @Autowired
    public TaxCodeServiceImpl(CityReposytoryImpl cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public String calculateTaxCode(User user) {
        StringBuilder taxCode = new StringBuilder();

        log.info(String.format("Start calculating tax code for --> %s", user.toString()));

        taxCode.append(generateLastNameLetters(user));
        taxCode.append(generateFirstNameLetters(user));
        taxCode.append(generateYearOfBirthLetters(user));
        taxCode.append(generateMonthOfBirthLetters(user));
        taxCode.append(generateDayOfBirthLetters(user));
        taxCode.append(generateCityOfBirthLetters(user));
        taxCode.append(generateControlLetters(taxCode));

        log.info(String.format("Finish calculating tax code --> %s", taxCode.toString()));

        return taxCode.toString();
    }

    @Override
    public User parseUser(String taxCode) {
        User user = new User();
        taxCode = taxCode.toUpperCase();

        log.info(String.format("Start parsing tax code --> %s", taxCode));

        user.setLastName(taxCode.substring(0, 3));
        user.setFirstName(taxCode.substring(3, 6));

        int dayOfBirth = calculateDay(taxCode.substring(9, 11));
        user.setDateOfBirth(calculateDOB(taxCode));
        user.setGender(dayOfBirth > 31 ? "F" : "M");

        Optional<City> cityCSV = cityRepository.getCityByBelfioreCode(taxCode.substring(11, 15));

        if (cityCSV.isPresent()) {
            user.setCityOfBirth(cityCSV.get().getDenominazione());
            user.setProvinceOfBirth(cityCSV.get().getSiglaProvincia());
        } else {
            throw new BadCityFormatException("Failed to find city defined with code --> " + taxCode.substring(11, 15));
        }

        char controlLetter = generateControlLetters(new StringBuilder(taxCode.substring(0, 15))).charAt(0);

        if (taxCode.charAt(15) != controlLetter) {
            throw new UnprocessableDataException(String.format("Last Tax code char '%s' doesn't match with actual control letter '%s'", taxCode.charAt(15), controlLetter));
        }

        log.info(String.format("Finish parsing tax code --> %s", user.toString()));

        return user;
    }

    private LocalDate calculateDOB(String taxCode) {
        int year;
        int month;
        int day;

        try {
            year = Integer.parseInt(taxCode.substring(6, 8)) + 1900;
        } catch (Exception e) {
            throw new BadDateFormatException("Cannot parse year " + taxCode.substring(6, 8), e);
        }

        try {
            month = Utils.getMonth(taxCode.substring(8, 9).toLowerCase()).getValue();
        } catch (Exception e) {
            throw new BadDateFormatException("Cannot parse month " + taxCode.substring(8, 9), e);
        }

        int tempDay = calculateDay(taxCode.substring(9, 11));
        day = tempDay > 31 ? tempDay - 40 : tempDay;

        return LocalDate.of(year, month, day);
    }

    private int calculateDay(String dayString) {
        try {
            return Integer.parseInt(dayString);
        } catch (NumberFormatException e) {
            throw new BadDateFormatException("Cannot parse day " + dayString, e);
        }
    }

    private String generateControlLetters(StringBuilder taxCode) {
        char[] charsOfTaxCode = taxCode.toString().toCharArray();
        int sumOfValues = 0;
        int divisionMod = 0;

        for (int counter = 0; counter < charsOfTaxCode.length; counter++) {
            if ((counter + 1) % 2 == 0) {
                sumOfValues += Constants.EVEN_CHAR_MAP.get(charsOfTaxCode[counter]);
            } else {
                sumOfValues += Constants.ODD_CHAR_MAP.get(charsOfTaxCode[counter]);
            }
        }

        divisionMod = sumOfValues % 26;

        return String.valueOf(Constants.CONTROL_CHAR_MAP.get(divisionMod));
    }

    private String generateCityOfBirthLetters(User user) {
        String city = user.getCityOfBirth();
        Optional<City> cityModel = cityRepository.getCityByName(city);

        if (cityModel.isPresent())
            return cityModel.get().getCodiceCatastale().toUpperCase();
        else {
            throw new BadCityFormatException("City " + city + " doesn't exist.");
        }
    }

    private String generateDayOfBirthLetters(User user) {
        return user.getGender().equalsIgnoreCase("M") ?
                String.format("%02d", user.getDateOfBirth().getDayOfMonth()) :
                String.valueOf(user.getDateOfBirth().getDayOfMonth() + 40);
    }

    private String generateYearOfBirthLetters(User user) {
        String year = Integer.toString(user.getDateOfBirth().getYear());
        return year.substring(2, 4);
    }

    private String generateMonthOfBirthLetters(User user) {
        char monthLetter = Utils.getCharOfMonth(user.getDateOfBirth().getMonth());
        return String.valueOf(monthLetter).toUpperCase();
    }

    private String generateLastNameLetters(User user) {
        String lastName = Utils.unaccent(Utils.unaccent(user.getLastName()));

        if (StringUtils.isBlank(lastName)) {
            throw new BadRequestException(String.format("Last name %s doesn't contain any valid chars after cleaninig", lastName));
        }

        StringBuilder lastNameLetters = new StringBuilder();

        if (lastName.length() < 3) {
            return String.format("%-3s", lastName).replace(' ', 'X').toUpperCase();
        }

        for (char letter :
                lastName.toCharArray()) {
            if (lastNameLetters.length() == 3)
                break;
            else {
                if (letter != ' ' && !Utils.isVowel(letter)) {
                    lastNameLetters.append(letter);
                }
            }
        }

        if (lastNameLetters.length() < 3) {
            fillWithVowel(lastNameLetters, lastName);
        }

        return lastNameLetters.toString().toUpperCase();
    }

    private String generateFirstNameLetters(User user) {
        String firstName = Utils.unaccent(Utils.unaccent(user.getFirstName()));

        if (StringUtils.isBlank(firstName)) {
            throw new BadRequestException(String.format("First name %s doesn't contain any valid chars after cleaninig", firstName));
        }

        final StringBuilder firstNameLetters = new StringBuilder();
        List<Character> consonants = new ArrayList<>();

        if (firstName.length() < 3) {
            return String.format("%-3s", firstName).replace(' ', 'X').toUpperCase();
        }

        for (char letter :
                firstName.toCharArray()) {
            if (!Utils.isVowel(letter)) {
                consonants.add(letter);
            }
        }

        if (consonants.size() > 3) {
            firstNameLetters.append(consonants.get(0)).append(consonants.get(2)).append(consonants.get(3));
        } else if (consonants.size() == 3) {
            firstNameLetters.append(consonants.get(0)).append(consonants.get(1)).append(consonants.get(2));
        } else {
            consonants.forEach(firstNameLetters::append);
        }

        if (firstNameLetters.length() < 3) {
            fillWithVowel(firstNameLetters, firstName);
        }

        return firstNameLetters.toString().toUpperCase();
    }

    private StringBuilder fillWithVowel(StringBuilder nameLetters, String name) {
        for (char letter :
                name.toCharArray()) {
            if (nameLetters.length() == 3)
                break;
            else {
                if (Utils.isVowel(letter)) {
                    nameLetters.append(letter);
                }
            }
        }

        return nameLetters;
    }
}
