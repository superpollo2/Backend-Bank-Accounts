package co.com.technicaltest.model.util;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class PaginatedResult<T>{
    private final List<T> content;
    private final int page;
    private final int size;
    private final long totalElements;
}
