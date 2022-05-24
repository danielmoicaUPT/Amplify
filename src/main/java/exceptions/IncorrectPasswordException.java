package exceptions;

public class IncorrectPasswordException extends Exception{
    public IncorrectPasswordException() {
        super(String.format("The password for the selected account is incorrect."));
    }
}
