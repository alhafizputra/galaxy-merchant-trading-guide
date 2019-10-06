/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxy;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Alhafiz Putra
 *
 * This class is where input, validate, process and output processed
 *
 */
public class LineProcessing {

    List<String> outputList;

    private Map<String, String> assumptions;
    private Map<String, String> elements;

    private Converter converter;

    private ErrorCodes errorCodes;
    private ErrorMessage errorMessages;

    LineType lineType;
    List<LineType> lineTypeList;

    public LineProcessing() {
        errorCodes = null;
        errorMessages = new ErrorMessage();
        assumptions = new HashMap<String, String>();
        elements = new HashMap<String, String>();
        outputList = new ArrayList<>();
        converter = new Converter();

        lineType = new LineType();
        //this is to initialize particular line type and pattern
        lineTypeList = lineType.init();
    }

    public List<String> inputLine() throws IOException {
//        System.out.println("Welcome to Galaxy Merchant Trading Guide, please drop your line and blank new line to finish input!");
//        List<String> inputList = new ArrayList<>();
//        String inputLine = null;
//
//        Scanner scanner = new Scanner(System.in);
//
//        while (scanner.hasNextLine()) {
//            inputLine = scanner.nextLine();
//            if (inputLine.length() == 0) {
//                break;
//            }
//            inputList.add(inputLine);
//        }
        List<String> lines = new ArrayList<>();
        lines = Files.readAllLines(Paths.get("./src/galaxy/Input.txt"), StandardCharsets.UTF_8);
//        for (String line : lines) {
//            System.out.println("line : " + line);
//        }
        return lines;
    }

    public List<String> processLine(List<String> inputList) {
        if (inputList.isEmpty()) {
            errorCodes = ErrorCodes.NO_INPUT;
            outputList.add(errorMessages.getMessage(errorCodes));
        } else {
            List<Lines> lineList = new ArrayList<>();
            Lines lines = null;

            for (String line : inputList) {
                lineType = determineLineType(line);
                lines = new Lines(line, lineType);
                lineList.add(lines);

                switch (lines.getLineType().getLineType()) {
                    case "ASSUMPTION":
                        processAssumptions(line);
                        break;
                    case "CREDITS":
                        processCredits(line);
                        break;
                    case "HOW_MUCH":
                        processHowMuch(line);
                        break;
                    case "HOW_MANY":
                        processHowMany(line);
                        break;
                    case "IS_LARGER_CREDITS":
                        processIsLargerCredits(line);
                        break;
                    case "IS_SMALLER_CREDITS":
                        processIsSmallerCredits(line);
                        break;
                    case "IS_LARGER":
                        processIsLarger(line);
                        break;
                    case "IS_SMALLER":
                        processIsSmaller(line);
                        break;
                    case "HOW_MUCH_PLUS":
                        processHowMuchPlus(line);
                        break;
                    case "HOW_MUCH_SUBSTRACT":
                        processHowMuchSubstract(line);
                        break;
                    case "HOW_MANY_PLUS":
                        processHowManyPlus(line);
                        break;
                    case "HOW_MANY_SUBSTRACT":
                        processHowManySubstract(line);
                        break;
                    case "NO_MATCH":
                        processNoMatch(line);
                        break;
                    default: ;
                }
            }
        }

        return outputList;
    }

    public LineType determineLineType(String line) {
        lineType = null;
        for (LineType lt : lineTypeList) {
            if (lt.getLinePattern() != null) {
                if (line.matches(lt.getLinePattern())) {
                    lineType = lt;
                    break;
                }
            } else {
                lineType = lt;
            }
        }
        return lineType;
    }

    public void processAssumptions(String line) {
        String[] splitLine = line.trim().split("\\s+");
        assumptions.put(splitLine[0], splitLine[2]);
    }

    public void processCredits(String line) {
        String[] splitLine = line.replace("?", "").trim().split("\\s+");
        String roman = "";

        int isIndex = 0;
        for (int i = 0; i < splitLine.length; i++) {
            if (splitLine[i].equals("is")) {
                isIndex = i;
                break;
            }
        }
        for (int i = 0; i < isIndex - 1; i++) {
            String assump = assumptions.get(splitLine[i]);
            roman = roman.concat(assump);
        }
        int number = converter.romanToDecimal(roman);
        double element = Double.valueOf(splitLine[isIndex + 1]) / number;
        elements.put(splitLine[isIndex - 1], Double.toString(element));
    }

    public void processHowMuch(String line) {
        String[] splitLine = line.replace("?", "").trim().split("\\s+");
        String roman = "";
        String output = "";

        for (int i = 3; i < splitLine.length; i++) {
            String assump = assumptions.get(splitLine[i]);
            if (assump == null) {
                errorCodes = ErrorCodes.NO_IDEA;
                outputList.add(errorMessages.getMessage(errorCodes));
                return;
            }
            roman = roman.concat(assump);
            output = output.concat(splitLine[i].concat(" "));
        }
        boolean isValid = converter.isValid(roman);
        if (!isValid) {
            errorCodes = ErrorCodes.INVALID_ROMAN_STRING;
            outputList.add(errorMessages.getMessage(errorCodes));
            return;
        }
        int number = converter.romanToDecimal(roman);
        outputList.add(output.concat("is ").concat(Integer.toString(number)));
    }

    public void processHowMany(String line) {
        String[] splitLine = line.replace("?", "").trim().split("\\s+");
        String roman = "";
        String output = "";
        int number = 0;
        double element = 1;
        double credits = 0;
        for (int i = 4; i < splitLine.length; i++) {
            if (assumptions.get(splitLine[i]) != null) {
                String assump = assumptions.get(splitLine[i]);
                if (assump == null) {
                    errorCodes = ErrorCodes.NO_IDEA;
                    outputList.add(errorMessages.getMessage(errorCodes));
                    return;
                }
                roman = roman.concat(assump);
                number = converter.romanToDecimal(roman);
            } else if (elements.get(splitLine[i]) != null) {
                element *= Double.valueOf(elements.get(splitLine[i]));
            } else {
                errorCodes = ErrorCodes.NO_IDEA;
                outputList.add(errorMessages.getMessage(errorCodes));
                return;
            }
            output = output.concat(splitLine[i].concat(" "));
        }

        if (number != 0 && element != 0) {
            credits = number * element;
        } else if (number == 0 && element != 0) {
            credits = element;
        }

        outputList.add(output.concat("is ").concat(Double.toString(credits)).concat(" Credits"));
    }

    public void processIsLargerCredits(String line) {
        String[] splitLine = line.replace("is ", "").replace("?", "").trim().split(" larger than ");
//        for (int i = 0; i < splitLine.length; i++) {
//            System.out.println(splitLine[i]);
//        }
        double credits1 = howManyCredits(splitLine[0]);
        double credits2 = howManyCredits(splitLine[1]);
        if (credits1 > credits2) {
            outputList.add("Yes. ".concat(splitLine[0]).concat(" larger than ").concat(splitLine[1]));
        } else {
            outputList.add("No. ".concat(splitLine[0]).concat(" is not larger than ").concat(splitLine[1]));
        }
    }

    public void processIsSmallerCredits(String line) {
        String[] splitLine = line.replace("is ", "").replace("?", "").trim().split(" larger than ");
//        for (int i = 0; i < splitLine.length; i++) {
//            System.out.println(splitLine[i]);
//        }
        double credits1 = howManyCredits(splitLine[0]);
        double credits2 = howManyCredits(splitLine[1]);
        if (credits1 < credits2) {
            outputList.add("Yes. ".concat(splitLine[0]).concat(" larger than ").concat(splitLine[1]));
        } else {
            outputList.add("No. ".concat(splitLine[0]).concat(" is not larger than ").concat(splitLine[1]));
        }
    }

    public void processIsLarger(String line) {
        String[] splitLine = line.replace("is ", "").replace("?", "").trim().split(" larger than ");
        int number1 = howMuch(splitLine[0]);
        int number2 = howMuch(splitLine[1]);
        if (number1 > number2) {
            outputList.add("Yes. ".concat(splitLine[0]).concat(" is larger than ").concat(splitLine[1]));
        } else {
            outputList.add("No. ".concat(splitLine[0]).concat(" is not larger than ").concat(splitLine[1]));
        }
    }

    public void processIsSmaller(String line) {
        String[] splitLine = line.replace("is ", "").replace("?", "").trim().split(" smaller than ");
        int number1 = howMuch(splitLine[0]);
        int number2 = howMuch(splitLine[1]);
        if (number1 < number2) {
            outputList.add("Yes. ".concat(splitLine[0]).concat(" is smaller than ").concat(splitLine[1]));
        } else {
            outputList.add("No. ".concat(splitLine[0]).concat(" is not smaller than ").concat(splitLine[1]));
        }
    }

    public void processHowMuchPlus(String line) {
        String[] splitLine = line.replace("how much is", "").replace("?", "").trim().split(" plus ");
        int number1 = howMuch(splitLine[0]);
        int number2 = howMuch(splitLine[1]);
        int total = number1 + number2;
        outputList.add(splitLine[0].concat(" plus ").concat(splitLine[1]).concat(" is ").concat(String.valueOf(total)));
    }

    public void processHowMuchSubstract(String line) {
        String[] splitLine = line.replace("how much is", "").replace("?", "").trim().split(" substract ");
        int number1 = howMuch(splitLine[0]);
        int number2 = howMuch(splitLine[1]);
        int total = number1 - number2;
        outputList.add(splitLine[0].concat(" substract ").concat(splitLine[1]).concat(" is ").concat(String.valueOf(total)));
    }

    public void processHowManyPlus(String line) {
        String[] splitLine = line.replace("how many Credits is ", "").replace("?", "").trim().split(" plus ");
        double credits1 = howManyCredits(splitLine[0]);
        double credits2 = howManyCredits(splitLine[1]);
        double total = credits1 + credits2;
        outputList.add(splitLine[0].concat(" plus ").concat(splitLine[1]).concat(" is ").concat(String.valueOf(total)));
    }

    public void processHowManySubstract(String line) {
        String[] splitLine = line.replace("how many Credits is ", "").replace("?", "").trim().split(" substract ");
        double credits1 = howManyCredits(splitLine[0]);
        double credits2 = howManyCredits(splitLine[1]);
        double total = credits1 - credits2;
        outputList.add(splitLine[0].concat(" substract ").concat(splitLine[1]).concat(" is ").concat(String.valueOf(total)));
    }

    public double howManyCredits(String line) {
        String[] splitLine = line.replace("credits", "").trim().split("\\s+");
        String roman = "";
        int number = 0;
        double element = 1;
        double credits = 0;

        for (int i = 0; i < splitLine.length; i++) {
            if (assumptions.get(splitLine[i]) != null) {
                String assump = assumptions.get(splitLine[i]);
                if (assump == null) {
                    return 0;
                }
                roman = roman.concat(assump);
                number = converter.romanToDecimal(roman);
            } else if (elements.get(splitLine[i]) != null) {
                element *= Double.valueOf(elements.get(splitLine[i]));
            } else {
                return 0;
            }
        }

        if (number != 0 && element != 0) {
            credits = number * element;
        } else if (number == 0 && element != 0) {
            credits = element;
        }
        return credits;
    }

    public int howMuch(String line) {
        String[] splitLine = line.split("\\s+");
        String roman = "";
        String output = "";

        for (int i = 0; i < splitLine.length; i++) {
            String assump = assumptions.get(splitLine[i]);
            if (assump == null) {
                return 0;
            }
            roman = roman.concat(assump);
            output = output.concat(splitLine[i].concat(" "));
        }
        boolean isValid = converter.isValid(roman);
        if (!isValid) {
            return 0;
        }
        int number = converter.romanToDecimal(roman);
        return number;
    }

    public void processNoMatch(String line) {
        errorCodes = ErrorCodes.NO_IDEA;
        outputList.add(errorMessages.getMessage(errorCodes));
    }

    public void showOutput(List<String> outputList) {
        if (outputList.isEmpty()) {
            errorCodes = ErrorCodes.NO_QUESTION;
            outputList.add(errorMessages.getMessage(errorCodes));
        }

        for (String output : outputList) {
            System.out.println(output);
        }
    }
}
