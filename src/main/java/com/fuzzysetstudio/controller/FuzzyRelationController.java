package main.java.com.fuzzysetstudio.controller;

import main.java.com.fuzzysetstudio.controller.dto.FuzzyRelationMapper;
import main.java.com.fuzzysetstudio.controller.dto.FuzzySetMapper;
import main.java.com.fuzzysetstudio.controller.dto.request.RequestCompose;
import main.java.com.fuzzysetstudio.controller.dto.request.RequestRelationsImage;
import main.java.com.fuzzysetstudio.controller.dto.response.FuzzyRelationDto;
import main.java.com.fuzzysetstudio.controller.dto.response.FuzzySetDto;
import main.java.com.fuzzysetstudio.domain.FuzzyRelation;
import main.java.com.fuzzysetstudio.domain.FuzzySet;
import main.java.com.fuzzysetstudio.service.FuzzyRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;


@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/fuzzyrelation")
public class FuzzyRelationController {
    private final FuzzyRelationService fuzzyRelationService;

    @Autowired
    public FuzzyRelationController(FuzzyRelationService fuzzyRelationService){
        this.fuzzyRelationService = fuzzyRelationService;
    }

    @PostMapping("/calculate/img")
    public FuzzySetDto getCalculate(@RequestBody @Valid RequestRelationsImage req){
        FuzzyRelation fuzzyRelation = FuzzyRelationMapper.toDomain(req.relationDto());
        FuzzySet fuzzySet = FuzzySetMapper.toDomain(req.setDto());

        FuzzySet result = fuzzyRelationService.calculateImage(
                req.type(),
                fuzzyRelation,
                fuzzySet
        );
        return FuzzySetMapper.toDto(result);
    }

    @PostMapping("/calculate/compose")
    public FuzzyRelationDto getCalculate(@RequestBody @Valid RequestCompose req){
        List<FuzzyRelation> fuzzyRelationList = req.relationDtoList().stream().map(FuzzyRelationMapper::toDomain).toList();
        FuzzyRelation result = fuzzyRelationService.calculateCompose(fuzzyRelationList);

        return FuzzyRelationMapper.toDto(result);
    }
}
