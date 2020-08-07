package com.number.converter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.number.converter.dto.NumConvertDto;

import java.util.HashMap;

/*
Returns a dto that represents in english words the number that was passed to it
and the status, "ok" on success and "failed" on failure
*/


@Service
public class NumConvertService {
    //pre-filled hashMaps of words to be used
    HashMap<Character, String> underTenMap = fillUnderTenMap();
    HashMap<Character, String> teenMap = fillTeenMap();
    HashMap<Character, String> underHundredMap = fillUnderHundred();
    HashMap<Integer, String> overHundredMap = fillOverHundredMap();

    @Autowired
    public NumConvertService() {
    }

    public NumConvertDto convertNumToString(String number) {
        try {
            boolean isNegative = false;

            //handle negatives - get ascii value for "-"
            //get rid of it to continue with the number
            //will append "negative" at the end of the method
            //if isNegative is true
            if (number.charAt(0) == '-') {
                isNegative = true;
                number = number.substring(1);
            }

            //get case "0" out of the way early
            if (number.replaceAll("0+", "0").equals("0")) {
                String status = "ok";
                String result = "zero";

                return new NumConvertDto(status, result);
            }

            StringBuilder numToStr = new StringBuilder();

            //get rid of leading zeros
            number = number.replaceAll( "^0+(?!$)", "");

            //create a temporary stringBuffer object to work with
            StringBuilder tempStr = new StringBuilder(number);

            //validate that string passed can be converted to long
            //(not int since we can pass very long numbers
            try {
                if (!tempStr.toString().matches("\\d+")) {
                    throw new Exception();
                }

                if (tempStr.length() > 27) {
                    String status = "Failed: number too large";
                    return new NumConvertDto(status, "");
                }
            } catch (Exception e) {
                String status = "Failed: check your input and try again";
                return new NumConvertDto(status, "");
            }

            //pad the beginning of the number with zeros
            //if there are fewer than 3 digits
            while (tempStr.length() % 3 != 0) {
                tempStr.insert(0, '0');
            }

            int exponent = tempStr.length() / 3;
            int iterator = 0;

            //evaluate numbers in groups of 3,
            //then assign a thousand - septillion value
            //based on how many groups of 3 there are
            while (iterator < tempStr.length()) {
                numToStr.append(convertUnderThousand(tempStr.substring(iterator, iterator + 3)));
                numToStr.append(" " + overHundredMap.get(exponent));

                exponent--;
                iterator += 3;

                if (exponent != 0) {
                    numToStr.append(" ");
                }
            }

            if (isNegative) {
                numToStr.insert(0, "negative ");
            }

            //clean up the string & get rid of any extra spaces
            String finalStr = numToStr.toString().replaceAll("\\s+", " ").trim();
            String status = "ok";

            return new NumConvertDto(status, finalStr);
        } catch (Exception e) {
            //Catch miscellaneous exceptions
            String status = "failed";
            return new NumConvertDto(status, "");
        }
    }

    //Method to convert numbers 1-999
    private StringBuilder convertUnderThousand(String number) {
        StringBuilder numToStr = new StringBuilder(number);

        //prepend zeros
        while (numToStr.length() < 3) {
            numToStr.insert(0, '0');
        }

        number = numToStr.toString();
        //clear numToStr
        numToStr.setLength(0);

        char[] digits = number.toCharArray();

        // Hundredth place
        if (digits[0] != '0') {
            numToStr.append(underTenMap.get(digits[0]));
            numToStr.append(" hundred");
        }

        //
        if (digits[1] == '1') {
            numToStr.append(" " + teenMap.get(digits[2]));
        } else if (digits[1] != '0') {
            numToStr.append(" " + underHundredMap.get(digits[1]));
            if (digits[2] != '0') {
                numToStr.append(" " + underTenMap.get(digits[2]));
            }
        } else {
            if (digits[2] != '0') {
                numToStr.append(" " + underTenMap.get(digits[2]));
            }
        }

        return numToStr;
    }

    //map of numbers 1-9
    private HashMap fillUnderTenMap() {
        HashMap<Character, String> map = new HashMap<>();
        map.put('1', "one");
        map.put('2', "two");
        map.put('3', "three");
        map.put('4', "four");
        map.put('5', "five");
        map.put('6', "six");
        map.put('7', "seven");
        map.put('8', "eight");
        map.put('9', "nine");

        return map;
    }

    //map of numbers 10 - 19
    private HashMap fillTeenMap() {
        HashMap<Character, String> map = new HashMap<>();

        map.put('0', "ten");
        map.put('1', "eleven");
        map.put('2', "twelve");
        map.put('3', "thirteen");
        map.put('4', "fourteen");
        map.put('5', "fifteen");
        map.put('6', "sixteen");
        map.put('7', "seventeen");
        map.put('8', "eighteen");
        map.put('9', "nineteen");

        return map;
    }

    //map of strings for values less than 100
    private HashMap fillUnderHundred() {
        HashMap<Character, String> map = new HashMap<>();

        map.put('2', "twenty");
        map.put('3', "thirty");
        map.put('4', "forty");
        map.put('5', "fifty");
        map.put('6', "sixty");
        map.put('7', "seventy");
        map.put('8', "eighty");
        map.put('9', "ninety");

        return map;
    }

    //Can rinse and repeat this with larger numbers but
    //this should get the idea across

    //map of numbers over a thousand
    private HashMap fillOverHundredMap() {
        HashMap<Integer, String> map = new HashMap<>();

        map.put(1, "");
        map.put(2, "thousand");
        map.put(3, "million");
        map.put(4, "billion");
        map.put(5, "trillion");
        map.put(6, "quadrillion");
        map.put(7, "quintillion");
        map.put(8, "sextillion");
        map.put(9, "septillion");

        return map;
    }
}
