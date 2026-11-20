package main.java.com.fuzzysetstudio.controller.dto.request;

import org.jetbrains.annotations.NotNull;
import main.java.com.fuzzysetstudio.controller.dto.response.FuzzySetDto;
import main.java.com.fuzzysetstudio.operations.set.FuzzySetOperationType;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;

public record RequestOperationDto(
        @NotNull FuzzySetOperationType operationType,
        @NotNull @Valid FuzzySetDto setA,
        @Valid FuzzySetDto setB,
        @DecimalMin("1e-5") double step
) {}


