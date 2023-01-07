package com.eminyilmazz.smoketracker.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Validated
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestSmokeDto implements Serializable {
    private Long smokedDate;
    private Long smokedTime;
    @NotEmpty private String activity;
    private int quantity;
}
