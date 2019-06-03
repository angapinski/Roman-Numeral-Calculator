package Converter.App;

import java.util.LinkedHashMap;
import java.util.Map;

public class NumeralConverter {
    private final static LinkedHashMap<String, Integer> NUMERAL_VALUES = createValuesMap();
    private final static int MIN_ARABIC_NUMBER = 1;
    private final static int MAX_ARABIC_NUMBER = 3999;
    
    /**
     * Generates the Roman Numeral to Arabic number values map.
     */
    private static LinkedHashMap<String, Integer> createValuesMap(){
        LinkedHashMap<String, Integer> numeralValuesOutput = new LinkedHashMap<>();
        
        numeralValuesOutput.put("M", 1000);
        numeralValuesOutput.put("CM", 900);
        numeralValuesOutput.put("D", 500);
        numeralValuesOutput.put("CD", 400);
        numeralValuesOutput.put("C", 100);
        numeralValuesOutput.put("XC", 90);
        numeralValuesOutput.put("L", 50);
        numeralValuesOutput.put("XL", 40);
        numeralValuesOutput.put("X", 10);
        numeralValuesOutput.put("IX", 9);
        numeralValuesOutput.put("V", 5);
        numeralValuesOutput.put("IV", 4);
        numeralValuesOutput.put("I", 1);
        
        return numeralValuesOutput;
    }
    
    /**
     * Returns a a String containing the Roman Numeral equivalent to the specified arabicNumber
     * Validation is performed before conversion to ensure accuracy.
     * @param arabicNumber an Arabic number >= 1
     * @return a String containing the Roman Numeral equivalent to the specified arabicNumber
     */
    public static String convertNumberToNumeral(int arabicNumber) throws IllegalArgumentException{
        validateArabicNumber(arabicNumber);
        
        int tempNumber = arabicNumber;
        StringBuilder result = new StringBuilder();
        
        while(tempNumber != 0){
            for(Map.Entry<String, Integer> entry : NUMERAL_VALUES.entrySet()){
                if(tempNumber / entry.getValue() > 0){
                    result.append(entry.getKey());
                    tempNumber -= entry.getValue();
                    break;
                }
            }
        }
        
        return result.toString();
    }
    
    /**
     * Returns an Integer containing the Arabic number equivalent to the specified Roman Numeral
     * Validation is performed before conversion to ensure accuracy.
     * @param romanNumeral a Roman Numeral to convert
     * @return an Integer containing the Arabic number equivalent to the specified Roman Numeral
     */
    public static int convertNumeralToNumber(String romanNumeral) throws IllegalArgumentException{
        validateNumeral(romanNumeral);
        
        int result = 0;
        
        for(int i = 0; i < romanNumeral.length(); i++){
            char currentChar = romanNumeral.charAt(i);
            
            //If there is a next character, determine if there is numeral subtraction
            if(i + 1 < romanNumeral.length()){
                char nextChar = romanNumeral.charAt(i + 1);
                
                if(NUMERAL_VALUES.get(String.valueOf(currentChar)) < NUMERAL_VALUES.get(String.valueOf(nextChar))){
                    result += NUMERAL_VALUES.get(String.format("%s%s", currentChar, nextChar));
                    i++;
                    continue;
                }
            }

            result += NUMERAL_VALUES.get(String.valueOf(currentChar));
        }

        return result;
    }
    
    /**
     * Validates Roman numerals by checking for valid characters, count of consecutive characters and valid subtractions
     * @param romanNumeral a Roman Numeral to validate
     */
    private static void validateNumeral(String romanNumeral)throws IllegalArgumentException{
        int consecutiveCharCount = 1;
        
        if(romanNumeral == null){
            throw new IllegalArgumentException("Numeral to convert cannot be null");
        }
        
        for(int i = 0; i < romanNumeral.length(); i++){
            char currentChar = romanNumeral.charAt(i);
            
            //Test to ensure the numeral is valid
            if(!NUMERAL_VALUES.containsKey(String.valueOf(currentChar))){
                throw new IllegalArgumentException("Invalid numeral");
            }
            
            //Validate Subtractions
            if(i + 1 < romanNumeral.length()){
                char nextChar = romanNumeral.charAt(i + 1);
                
                if(!NUMERAL_VALUES.containsKey(String.valueOf(nextChar))){
                        throw new IllegalArgumentException("Invalid numeral");
                }
                
                if(NUMERAL_VALUES.get(String.valueOf(currentChar)) < NUMERAL_VALUES.get(String.valueOf(nextChar))){
                    if(!NUMERAL_VALUES.containsKey(String.format("%s%s", currentChar, nextChar))){
                        throw new IllegalArgumentException("Invalid numeral subtraction");
                    }
                }
                
                 //Validate count of subsequent characters
                if(currentChar == nextChar){
                    consecutiveCharCount ++;
                    int numeralValue =  NUMERAL_VALUES.get(String.valueOf(currentChar));
                    
                    if(consecutiveCharCount > 1 && integerContains(numeralValue, 5) || consecutiveCharCount > 3){
                        throw new IllegalArgumentException("Invalid number of consecutive numerals");
                    }
                }
                else{
                    consecutiveCharCount = 1;
                }
            }
        }
    }
    
    /**
     * Determines if an integer contains the specified digit
     * @param baseNumber the number that may contain the following parameter
     * @param contains the number to test if baseNumber contains
     * @return true if baseNumber contains the int to test else returns false
     */
    private static boolean integerContains(int baseNumber, int numToTest){
        return String.valueOf(baseNumber).contains(String.valueOf(numToTest));
    }
    
    /**
     * Validates that the number falls within the range of possible numerals this application can produce
     * @param romanNumeral a Roman Numeral to validate
     */
    private static void validateArabicNumber(int number) throws IllegalArgumentException{
        if(number < MIN_ARABIC_NUMBER || number > MAX_ARABIC_NUMBER){
            throw new IllegalArgumentException("Invalid arabic number. Valid numbers are 1 - 3999");
        }
    }
}
