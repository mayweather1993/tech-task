package com.mayweather.techtask.models.domain;

import com.mayweather.techtask.models.Auditable;
import com.mayweather.techtask.models.Currency;
import com.mayweather.techtask.models.Status;
import com.mayweather.techtask.repository.listeners.AuditListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "orders")
@Data
@EntityListeners(AuditListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity implements Serializable, Auditable {

    private static final long serialVersionUID = 5942287230929490297L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "price")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;
}
