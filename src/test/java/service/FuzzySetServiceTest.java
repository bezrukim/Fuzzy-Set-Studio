package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import main.java.com.fuzzysetstudio.domain.FuzzySet;
import main.java.com.fuzzysetstudio.domain.Point;
import main.java.com.fuzzysetstudio.operations.set.IFuzzySetOperation;
import main.java.com.fuzzysetstudio.operations.set.FuzzySetOperationType;
import main.java.com.fuzzysetstudio.operations.set.NegationOperation;
import main.java.com.fuzzysetstudio.service.FuzzySetService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class FuzzySetServiceTest {
    @Spy
    private List<IFuzzySetOperation> operationList = List.of(
            new NegationOperation()
    );

    @InjectMocks
    private FuzzySetService fuzzySetService;

    @Test
    void fuzzySetServiceTest(){
        FuzzySet testSet = new FuzzySet(new Point(0, 0), new Point(1, 1),
        new Point(2, 1), new Point(3, 0));
        assertNotNull(fuzzySetService.calculate(FuzzySetOperationType.NEGATION, testSet, null, 0.01));
    }
}
