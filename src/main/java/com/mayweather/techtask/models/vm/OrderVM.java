package com.mayweather.techtask.models.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mayweather.techtask.models.Currency;
import com.mayweather.techtask.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVM implements Serializable {
    private static final long serialVersionUID = -3589096796857534992L;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull
    private Status status;

    @NotNull
    private Double price;

    @NotNull
    private Currency currency;

    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdDate;
    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant lastModifiedDate;

    private Long customerId;
}
