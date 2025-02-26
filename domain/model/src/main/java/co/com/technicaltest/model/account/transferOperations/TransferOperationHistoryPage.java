package co.com.technicaltest.model.account.transferOperations;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransferOperationHistoryPage {
    private String accountNumber;
    private Integer page;
    private Integer size;
}
