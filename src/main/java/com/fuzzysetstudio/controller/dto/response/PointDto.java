package main.java.com.fuzzysetstudio.controller.dto.response;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;


public record PointDto(
        double x,
        @DecimalMin("0.0") @DecimalMax("1.0") double y
) {}

