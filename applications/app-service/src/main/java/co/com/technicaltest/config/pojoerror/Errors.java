package co.com.technicaltest.config.pojoerror;


import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder(toBuilder = true)
public class Errors {
    private final UUID id;
    private final String href;
    private final String title;
    private final String code;
    private final Integer status;
    private final String detail;
}
