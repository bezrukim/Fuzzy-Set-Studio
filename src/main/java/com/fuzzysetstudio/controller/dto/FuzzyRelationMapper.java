package main.java.com.fuzzysetstudio.controller.dto;

import main.java.com.fuzzysetstudio.controller.dto.response.FuzzyRelationDto;
import main.java.com.fuzzysetstudio.controller.dto.response.FuzzySetDto;
import main.java.com.fuzzysetstudio.controller.dto.response.PointDto;
import main.java.com.fuzzysetstudio.domain.FuzzyRelation;
import main.java.com.fuzzysetstudio.domain.FuzzySet;
import main.java.com.fuzzysetstudio.domain.Point;

import javax.validation.Valid;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class FuzzyRelationMapper {
    public static FuzzyRelation toDomain(@Valid FuzzyRelationDto fuzzyRelationDto){
        double[] X = fuzzyRelationDto.x().stream().mapToDouble(Double::doubleValue).toArray();
        double[] Y = fuzzyRelationDto.y().stream().mapToDouble(Double::doubleValue).toArray();
        double[][] R = fuzzyRelationDto.R().stream()
                .map(row -> row.stream()
                        .mapToDouble(Double::doubleValue)
                        .toArray())
                .toArray(double[][]::new);

        return new FuzzyRelation(R, X, Y);
    }

    public static FuzzyRelationDto toDto(FuzzyRelation fuzzyRelation){
        List<Double> X = Arrays.stream(fuzzyRelation.getX())
                .boxed()
                .toList();

        List<Double> Y = Arrays.stream(fuzzyRelation.getY())
                .boxed()
                .toList();

        List<List<Double>> R = Arrays.stream(fuzzyRelation.getR())
                .map(row -> Arrays.stream(row).boxed().toList())
                .toList();

        return new FuzzyRelationDto(X, Y, R);
    }
}
