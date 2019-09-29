/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxy;

/**
 * @author Alhafiz Putra
 *
 * This class is to store line inputed
 *
 */
public class Lines {

    private String line;
    private LineType lineType;

     public Lines() {
         
    }
     
    public Lines(String line, LineType lineType) {
        this.line = line;
        this.lineType = lineType;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public LineType getLineType() {
        return lineType;
    }

    public void setLineType(LineType lineType) {
        this.lineType = lineType;
    }

    @Override
    public String toString() {
        return "Lines{" + "line=" + line + ", lineType=" + lineType.toString() + '}';
    }
}
