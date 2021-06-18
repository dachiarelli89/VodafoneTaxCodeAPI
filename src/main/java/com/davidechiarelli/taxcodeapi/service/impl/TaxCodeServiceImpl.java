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


/**
 * This is a service class wich exposes 2 public methods calculateTaxCode and parseUser. The first calculate an italian tax code using user data,
 * the second parse a tax code.
 *
 */
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

        // Calculate each piece of tax code
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

        // It's imposibble to estrapolate name and surname, so these fields are copied as is
        user.setLastName(taxCode.substring(0, 3));
        user.setFirstName(taxCode.substring(3, 6));

        user.setDateOfBirth(calculateDOB(taxCode));
        //Get number in day of birth position (used for gender)
        int dayOfBirth = calculateDay(taxCode.substring(9, 11));
        user.setGender(dayOfBirth > 31 ? "F" : "M");

        // Get city using belfiore code
        Optional<City> cityCSV = cityRepository.getCityByBelfioreCode(taxCode.substring(11, 15));

        // If city exists set city name and province
        if (cityCSV.isPresent()) {
            user.setCityOfBirth(cityCSV.get().getDenominazione());
            user.setProvinceOfBirth(cityCSV.get().getSiglaProvincia());
        } else {
            throw new BadCityFormatException("Failed to find city defined with code --> " + taxCode.substring(11, 15));
        }

        // Using previous chars, this calculates last control letter
        char controlLetter = generateControlLetters(new StringBuilder(taxCode.substring(0, 15))).charAt(0);
        // and checks if it is equal to the control letter
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
            // This piece calculate the year, but it cannot determine the real century (ex. '05' could be 1905 but also 2005)
            // for semplicity I consider that the century is 900'
            year = Integer.parseInt(taxCode.substring(6, 8)) + 1900;
        } catch (Exception e) {
            throw new BadDateFormatException("Cannot parse year " + taxCode.substring(6, 8), e);
        }

        try {
            month = Utils.getMonth(taxCode.substring(8, 9).toLowerCase()).getValue();
        } catch (Exception e) {
            throw new BadDateFormatException("Cannot parse month " + taxCode.substring(8, 9), e);
        }

        // Day could be greater than 31, so thi tax code is associated to a female
        int tempDay = calculateDay(taxCode.substring(9, 11));
        day = tempDay > 31 ? tempDay - 40 : tempDay;

        try {
            // Try to parse date, using LocalDate class
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            throw new BadDateFormatException("Cannot parse date", e);
        }
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

        // Iterate on temporary tax value and sum even and odd position chars using conversion maps in Constants
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
        // This action allows to clean last name leaving only alhpanumeric chars
        String lastName = Utils.cleanString(Utils.cleanString(user.getLastName()));

        if (StringUtils.isBlank(lastName)) {
            throw new BadRequestException(String.format("Last name %s doesn't contain any valid chars after cleaninig", lastName));
        }

        StringBuilder lastNameLetters = new StringBuilder();

        // If surname lenght is lower than 3 fill the name with X
        if (lastName.length() < 3) {
            return String.format("%-3s", lastName).replace(' ', 'X').toUpperCase();
        }

        // Iterate on surname considering only consonant, until the size of the string is 3
        for (char letter :
                lastName.toCharArray()) {
            if (lastNameLetters.length() == 3)
                break;
            else {
                if (!Utils.isVowel(letter)) {
                    lastNameLetters.append(letter);
                }
            }
        }

        // If the string size is lower than 3 then fills with the remaning vowels
        if (lastNameLetters.length() < 3) {
            fillWithVowel(lastNameLetters, lastName);
        }

        return lastNameLetters.toString().toUpperCase();
    }

    private String generateFirstNameLetters(User user) {
        // This action allows to clean first name leaving only alhpanumeric chars
        String firstName = Utils.cleanString(Utils.cleanString(user.getFirstName()));

        if (StringUtils.isBlank(firstName)) {
            throw new BadRequestException(String.format("First name %s doesn't contain any valid chars after cleaninig", firstName));
        }

        final StringBuilder firstNameLetters = new StringBuilder();
        List<Character> consonants = new ArrayList<>();

        // If surname lenght is lower than 3 fill the name with X
        if (firstName.length() < 3) {
            return String.format("%-3s", firstName).replace(' ', 'X').toUpperCase();
        }

        // Get consonants number
        for (char letter :
                firstName.toCharArray()) {
            if (!Utils.isVowel(letter)) {
                consonants.add(letter);
            }
        }

        // and use it to apply these rules
        if (consonants.size() > 3) {
            firstNameLetters.append(consonants.get(0)).append(consonants.get(2)).append(consonants.get(3));
        } else if (consonants.size() == 3) {
            firstNameLetters.append(consonants.get(0)).append(consonants.get(1)).append(consonants.get(2));
        } else {
            // if first name has less then 3 consonants so it concatenates them
            consonants.forEach(firstNameLetters::append);
        }
        // and fills with vowels
        if (firstNameLetters.length() < 3) {
            fillWithVowel(firstNameLetters, firstName);
        }

        return firstNameLetters.toString().toUpperCase();
    }

    // This methods fills nameLetters with vowels in name until its lenght is 3
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
