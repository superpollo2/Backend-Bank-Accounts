package co.com.technicaltest.jpa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "transaction")
public class TransferOperationEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, name = "transaction_type")
    private String transactionType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false , name = "data_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "origin_account_id")
    private AccountEntity originAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private AccountEntity destinationAccount;

    @PrePersist
    protected void onCreate() {
        this.dateTime = LocalDateTime.now();
    }

}
