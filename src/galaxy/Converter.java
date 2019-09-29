/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxy;

/**
 * @author Alhafiz Putra
 *
 * This class is to convert Roman String to Arabic
 *
 */
public class Converter {

    public static String romanNumberValidator = "^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";

    private ErrorMessage errorMessage;

    public Converter() {
        errorMessage = new ErrorMessage();
    }

    int value(char r) {
        if (r == 'I') {
            return 1;
        }
        if (r == 'V') {
            return 5;
        }
        if (r == 'X') {
            return 10;
        }
        if (r == 'L') {
            return 50;
        }
        if (r == 'C') {
            return 100;
        }
        if (r == 'D') {
            return 500;
        }
        if (r == 'M') {
            return 1000;
        }
        return -1;
    }
    
    public boolean isValid(String str) {
        if (str.matches(romanNumberValidator)) {
            return true;
        }
        return false;
    }

    // Finds decimal value of a given romal numeral 
    public int romanToDecimal(String str) {
//        System.out.println("romanToDecimal");
        // Initialize result 
        int res = 0;

        for (int i = 0; i < str.length(); i++) {
            // Getting value of symbol s[i] 
            int s1 = value(str.charAt(i));

            // Getting value of symbol s[i+1] 
            if (i + 1 < str.length()) {
                int s2 = value(str.charAt(i + 1));

                // Comparing both values 
                if (s1 >= s2) {
                    // Value of current symbol is greater 
                    // or equalto the next symbol 
                    res = res + s1;
                } else {
                    res = res + s2 - s1;
                    i++; // Value of current symbol is 
                    // less than the next symbol 
                }
            } else {
                res = res + s1;
                i++;
            }
        }

        return res;
    }
}
