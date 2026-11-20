package main.java.com.fuzzysetstudio.controller.dto.request;

import main.java.com.fuzzysetstudio.controller.dto.response.FuzzyRelationDto;
import main.java.com.fuzzysetstudio.controller.dto.response.FuzzySetDto;
import main.java.com.fuzzysetstudio.domain.FuzzyRelation;
import main.java.com.fuzzysetstudio.operations.relation.FuzzyRelationOperationType;
import org.jetbrains.annotations.NotNull;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public record RequestRelationsImage(
        @NotNull @Valid FuzzyRelationOperationType type,
        @NotNull @Valid FuzzyRelationDto relationDto,
        @NotNull @Valid FuzzySetDto setDto
        ) {}
