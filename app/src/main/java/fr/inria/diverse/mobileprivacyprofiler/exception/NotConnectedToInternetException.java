package fr.inria.diverse.mobileprivacyprofiler.exception;

public class NotConnectedToInternetException extends Exception {

    public static final String  Message = "Please check your internet connection";
    public NotConnectedToInternetException() {
        super(Message);
    }

    // Constructor that accepts a message
    public NotConnectedToInternetException(String message)
    {
        super(message);
    }
}
