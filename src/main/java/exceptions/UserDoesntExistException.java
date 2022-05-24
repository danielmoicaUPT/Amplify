package exceptions;

public class UserDoesntExistException extends Exception{
    public UserDoesntExistException() {
        super(String.format("User doesn't exist!"));
    }
}
