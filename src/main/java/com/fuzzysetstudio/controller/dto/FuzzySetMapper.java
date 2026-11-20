package main.java.com.fuzzysetstudio.controller.dto;

import main.java.com.fuzzysetstudio.controller.dto.response.FuzzySetDto;
import main.java.com.fuzzysetstudio.controller.dto.response.PointDto;
import main.java.com.fuzzysetstudio.domain.FuzzySet;
import main.java.com.fuzzysetstudio.domain.Point;
import org.jetbrains.annotations.NotNull;

import javax.validation.Valid;
import java.util.List;

public class FuzzySetMapper {
    public static FuzzySet toDomain(@NotNull @Valid FuzzySetDto fuzzySetDto){
        Point[] points = fuzzySetDto.points().stream()
                .map(p -> new Point(p.x(), p.y()))
                .toArray(Point[]::new);

        return new FuzzySet(points);
    }

    public static FuzzySetDto toDto(FuzzySet fuzzySet){
        List<PointDto> points = fuzzySet.getFuzzySetPoints().stream()
                .map(p -> new PointDto(p.getX(), p.getY()))
                .toList();

        return new FuzzySetDto(points);
    }
}
