package exceptions;

public class PictureShouldBePngException extends Exception{
    public PictureShouldBePngException() {
        super(String.format("Profile picture should be a .png file"));
    }
}
