package classes.Exceptions;

public class CriterionDoesNotExistException extends Throwable {
    public CriterionDoesNotExistException() {
        super();
    }

    public CriterionDoesNotExistException(String msg) {
        super(msg);
    }
}
