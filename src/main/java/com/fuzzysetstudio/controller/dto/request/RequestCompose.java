package main.java.com.fuzzysetstudio.controller.dto.request;

import main.java.com.fuzzysetstudio.controller.dto.response.FuzzyRelationDto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public record RequestCompose(
        @NotEmpty
        @Valid
        List<FuzzyRelationDto> relationDtoList
) {}
