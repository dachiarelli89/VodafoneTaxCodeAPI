package com.davidechiarelli.taxcodeapi.utils;

import com.davidechiarelli.taxcodeapi.Constants;

import java.time.Month;
import java.util.Map;
import java.util.Optional;

public class Utils {
    private Utils(){}
    public static boolean isVowel(char aChar){
        return aChar == 'a' || aChar == 'e' || aChar == 'i' || aChar == 'o' || aChar == 'u';
    }

    public static Month getMonth(String aChar){
        return Constants.MONTH_MAP.get(aChar.charAt(0));
    }

    public static char getCharOfMonth(Month month){
        Optional<Map.Entry<Character, Month>> optionalCharacter = Constants.MONTH_MAP.entrySet().stream().filter(value -> value.getValue().equals(month)).findFirst();
        return optionalCharacter.isPresent() ? optionalCharacter.get().getKey() : ' ';
    }

    public static boolean isANameChar(char aLetter){
        String letterString = String.valueOf(aLetter);
        return letterString.matches("\\w+\\.?");
    }
}
