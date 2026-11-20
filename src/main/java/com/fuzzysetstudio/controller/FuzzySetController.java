package com.fuzzysetstudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fuzzysetstudio.controller.dto.request.RequestOperationDto;
import com.fuzzysetstudio.controller.dto.response.FuzzySetDto;
import com.fuzzysetstudio.domain.FuzzySet;
import com.fuzzysetstudio.service.FuzzySetService;

import javax.validation.Valid;

import static java.util.Objects.isNull;
import static com.fuzzysetstudio.controller.dto.FuzzySetMapper.toDomain;
import static com.fuzzysetstudio.controller.dto.FuzzySetMapper.toDto;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/fuzzyset")
public class FuzzySetController {
    private final FuzzySetService fuzzySetService;

    @Autowired
    public FuzzySetController(FuzzySetService fuzzySetService){
        this.fuzzySetService = fuzzySetService;
    }

    @PostMapping("/calculate")
    public FuzzySetDto getCalculate(@RequestBody @Valid RequestOperationDto req){
        FuzzySet a = toDomain(req.setA());
        FuzzySet b;
        if (!isNull(req.setB())) {
            b = toDomain(req.setB());
        }
        else {
            b = null;
        }

        FuzzySet result = fuzzySetService.calculate(
                req.operationType(),
                a,
                b,
                req.step()
        );

        return toDto(result);

    }
}
