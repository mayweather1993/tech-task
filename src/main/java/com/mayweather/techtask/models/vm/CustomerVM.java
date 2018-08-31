package com.mayweather.techtask.models.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mayweather.techtask.models.Sex;
import com.mayweather.techtask.models.domain.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerVM implements Serializable {

    private static final long serialVersionUID = -1642769850931769897L;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 32)
    private String name;

    @NotNull
    @Size(min = 2, max = 32)
    private String surname;

    @NotNull
    private Instant birthday;

    @NotNull
    private Sex sex;

    @NotNull
    private int identityCode;

    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdDate;
    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant lastModifiedDate;

    private List<OrderEntity> orders = new ArrayList<>();
}
