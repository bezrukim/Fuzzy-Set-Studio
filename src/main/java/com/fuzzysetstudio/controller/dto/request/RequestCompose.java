package com.fuzzysetstudio.controller.dto.request;

import com.fuzzysetstudio.controller.dto.response.FuzzyRelationDto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public record RequestCompose(
        @NotEmpty
        @Valid
        List<FuzzyRelationDto> relationDtoList
) {}
