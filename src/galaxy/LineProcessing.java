/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxy;

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

    public List<String> inputLine() {
        System.out.println("Welcome to Galaxy Merchant Trading Guide, please drop your line and blank new line to finish input!");
        List<String> inputList = new ArrayList<>();
        String inputLine = null;

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            inputLine = scanner.nextLine();
            if (inputLine.length() == 0) {
                break;
            }
            inputList.add(inputLine);
        }
        return inputList;
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
        for (int i = 0; i < isIndex-1; i++) {
            String assump = assumptions.get(splitLine[i]);
            roman = roman.concat(assump);
        }
        int number = converter.romanToDecimal(roman);
        double element = Double.valueOf(splitLine[isIndex+1]) / number;
        elements.put(splitLine[isIndex-1], Double.toString(element));
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
