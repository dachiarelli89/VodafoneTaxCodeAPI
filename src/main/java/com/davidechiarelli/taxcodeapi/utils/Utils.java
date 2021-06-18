package com.davidechiarelli.taxcodeapi.utils;

import com.davidechiarelli.taxcodeapi.Constants;

import java.text.Normalizer;
import java.time.Month;
import java.util.Map;
import java.util.Optional;

public class Utils {
    private Utils(){}

    public static boolean isVowel(char aChar){
        return aChar == 'a' || aChar == 'e' || aChar == 'i' || aChar == 'o' || aChar == 'u'
                || aChar == 'A' || aChar == 'E' || aChar == 'I' || aChar == 'O' || aChar == 'U';
    }

    // This method convert a Month object into the tax code month char
    public static Month getMonth(String aChar){
        return Constants.MONTH_MAP.get(aChar.charAt(0));
    }

    public static char getCharOfMonth(Month month){
        Optional<Map.Entry<Character, Month>> optionalCharacter = Constants.MONTH_MAP.entrySet().stream().filter(value -> value.getValue().equals(month)).findFirst();
        return optionalCharacter.isPresent() ? optionalCharacter.get().getKey() : ' ';
    }

    // This method clen a string leaving only alphanumeric chars
    public static String cleanString(String original) {
        String clean = Normalizer.normalize(original, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        clean = clean.replaceAll("[^a-zA-Z]", "");
        return clean;
    }
}
