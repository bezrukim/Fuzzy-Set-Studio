package com.fuzzysetstudio.controller.dto.response;

import org.jetbrains.annotations.NotNull;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public record FuzzyRelationDto(
    @NotEmpty List<Double> x,
    @NotEmpty List<Double> y,
    @NotEmpty
    List<@NotNull @Valid List<
            @NotNull @javax.validation.constraints.DecimalMin("0.0")
            @javax.validation.constraints.DecimalMax("1.0") Double
            >> R
) {}
