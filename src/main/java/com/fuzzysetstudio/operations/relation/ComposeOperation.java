package com.fuzzysetstudio.operations.relation;

import com.fuzzysetstudio.domain.FuzzyRelation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComposeOperation{
    public static FuzzyRelation apply(List<FuzzyRelation> relations) {
        if (relations == null || relations.isEmpty()) {
            throw new IllegalArgumentException("Список отношений пуст");
        }

        FuzzyRelation current = relations.getFirst();
        for (int idx = 1; idx < relations.size(); ++idx) {
            current = DirectImageOperation.ImageFuzzyRelation(current, relations.get(idx));
        }
        return current;
    }
}
