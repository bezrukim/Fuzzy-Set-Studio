package main.java.com.fuzzysetstudio.controller.dto.response;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public record FuzzySetDto(
        @NotEmpty(message = "Set must be not empty")
        @Valid
        List<PointDto> points
) {}
