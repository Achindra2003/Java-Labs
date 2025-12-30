package exception;

// UNCHECKED: Represents logical domain errors (Invalid inputs, Business rules)
// Must be PUBLIC to be seen by PharmacyApp
public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}