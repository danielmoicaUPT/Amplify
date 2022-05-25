package exceptions;

public class PasswordsMisatchException extends Exception{
    public PasswordsMisatchException() {
        super(String.format("Passwords don't correspond."));
    }
}
