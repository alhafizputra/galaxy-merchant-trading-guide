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
    public String homManyPattern = "^how many [c|C]redits is ([A-Za-z\\s]+)\\?$";

    public List<LineType> init() {
        List<LineType> lineTypeList = new ArrayList<>();

        lineTypeList.add(new LineType("ASSUMPTION", assumptionPattern));
        lineTypeList.add(new LineType("CREDITS", creditsPattern));
        lineTypeList.add(new LineType("HOW_MUCH", howMuchPattern));
        lineTypeList.add(new LineType("HOW_MANY", homManyPattern));
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
