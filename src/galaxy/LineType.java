/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alhafiz Putra
 *
 * This class contains line type and line pattern
 *
 */
public class LineType {

    public String assumptionPattern = "^([A-Za-z]+) is ([I|V|X|L|C|D|M])$";
    public String creditsPattern = "^([A-Za-z\\s]+) is ([0-9]+) ([c|C]redits)$";
    public String howMuchPattern = "^how much is (([A-Za-z\\s])+)\\?$";
    public String howManyPattern = "^how many [c|C]redits is ([A-Za-z\\s]+)\\?$";
    public String isLargerCreditsPattern = "^is (([A-Za-z\\s])+) [c|C]redits larger than ([A-Za-z\\s]+) [c|C]redits \\?$";
    public String isSmallerCreditsPattern = "^is (([A-Za-z\\s])+) [c|C]redits smaller than ([A-Za-z\\s]+) [c|C]redits \\?$";
    public String isLargerPattern = "^is (([A-Za-z\\s])+) larger than ([A-Za-z\\s]+)\\?$";
    public String isSmallerPattern = "^is (([A-Za-z\\s])+) smaller than ([A-Za-z\\s]+)\\?$";
    public String howMuchPlusPattern = "^how much is (([A-Za-z\\s])+) plus (([A-Za-z\\s])+)\\?$";
    public String howMuchSubstractPattern = "^how much is (([A-Za-z\\s])+) substract (([A-Za-z\\s])+)\\?$";
    public String howManyPlusPattern = "^how many [c|C]redits is ([A-Za-z\\s]+) plus ([A-Za-z\\s]+)\\?$";
    public String howManySubstractPattern = "^how many [c|C]redits is ([A-Za-z\\s]+) substract ([A-Za-z\\s]+)\\?$";

    public List<LineType> init() {
        List<LineType> lineTypeList = new ArrayList<>();

        lineTypeList.add(new LineType("ASSUMPTION", assumptionPattern));
        lineTypeList.add(new LineType("CREDITS", creditsPattern));
        lineTypeList.add(new LineType("HOW_MUCH_PLUS", howMuchPlusPattern));
        lineTypeList.add(new LineType("HOW_MUCH_SUBSTRACT", howMuchSubstractPattern));
        lineTypeList.add(new LineType("HOW_MANY_PLUS", howManyPlusPattern));
        lineTypeList.add(new LineType("HOW_MANY_SUBSTRACT", howManySubstractPattern));
        lineTypeList.add(new LineType("HOW_MUCH", howMuchPattern));
        lineTypeList.add(new LineType("HOW_MANY", howManyPattern));
        lineTypeList.add(new LineType("IS_LARGER_CREDITS", isLargerCreditsPattern));
        lineTypeList.add(new LineType("IS_SMALLER_CREDITS", isSmallerCreditsPattern));
        lineTypeList.add(new LineType("IS_LARGER", isLargerPattern));
        lineTypeList.add(new LineType("IS_SMALLER", isSmallerPattern));
        lineTypeList.add(new LineType("NO_MATCH", null));
        return lineTypeList;
    }

    private String lineType;
    private String linePattern;

    public LineType() {

    }

    public LineType(String lineType, String linePattern) {
        this.lineType = lineType;
        this.linePattern = linePattern;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getLinePattern() {
        return linePattern;
    }

    public void setLinePattern(String linePattern) {
        this.linePattern = linePattern;
    }

    @Override
    public String toString() {
        return "LineType{" + "lineType=" + lineType + '}';
    }

}
