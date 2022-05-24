package exceptions;

public class UsernameOrPasswordIncorrectException extends Exception{
    public UsernameOrPasswordIncorrectException() {
        super(String.format("Username or password don't match with any users"));
    }
}
