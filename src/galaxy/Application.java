/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxy;

import java.util.List;

/**
 * @author Alhafiz Putra
 *
 * This class is the starting point of the application.
 *
 */
public class Application {

    public static void main(String[] args) {
        LineProcessing lineProcessing = new LineProcessing();
        
        //input line from console, iterate till blank line
        List<String> inputList = lineProcessing.inputLine();
        
        //read input(line) list, validate, process and show output
        lineProcessing.processLine(inputList);
        
    }

}
