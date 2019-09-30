/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxy;

/**
 * @author Alhafiz Putra
 *
 * This class consist particular Error Message
 *
 */
public class ErrorMessage {

    public ErrorMessage() {

    }

    /**
     * This method prints the message for the particular error code
     */
    public String getMessage(ErrorCodes error) {
//        System.out.println("getMessage");
        String message = null;

        switch (error) {
            case NO_INPUT:
                message = "There is no input";
                break;
            case INCORRECT_LINE_TYPE:
                message = "Incorrect line type";
                break;
            case INVALID_ROMAN_STRING:
                message = "Wrong Roman number";
                break;
            case NO_QUESTION:
                message = "There is no question line you drop";
                break;
            case NO_IDEA:
                message = "I have no idea what you are talking about";
                break;

            default:
                break;
        }
        return message;
    }
}
