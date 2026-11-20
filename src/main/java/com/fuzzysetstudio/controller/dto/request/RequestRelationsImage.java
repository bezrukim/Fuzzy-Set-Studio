package com.fuzzysetstudio.controller.dto.request;

import com.fuzzysetstudio.controller.dto.response.FuzzyRelationDto;
import com.fuzzysetstudio.controller.dto.response.FuzzySetDto;
import com.fuzzysetstudio.operations.relation.FuzzyRelationOperationType;
import org.jetbrains.annotations.NotNull;

import javax.validation.Valid;

public record RequestRelationsImage(
        @NotNull @Valid FuzzyRelationOperationType type,
        @NotNull @Valid FuzzyRelationDto relationDto,
        @NotNull @Valid FuzzySetDto setDto
        ) {}
