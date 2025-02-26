package co.com.technicaltest.model.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BankAccountException extends RuntimeException {

    private final int status;
    private final String code;
    private final String title;
    private final String message;

    public BankAccountException(final int status, final String code, final String title, final String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.title = title;
        this.message = message;

    }

    @Override
    public String getMessage() {
        return message;
    }
}
