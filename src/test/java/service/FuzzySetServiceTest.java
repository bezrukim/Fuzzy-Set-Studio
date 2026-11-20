package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fuzzysetstudio.domain.FuzzySet;
import com.fuzzysetstudio.domain.Point;
import com.fuzzysetstudio.operations.set.IFuzzySetOperation;
import com.fuzzysetstudio.operations.set.FuzzySetOperationType;
import com.fuzzysetstudio.operations.set.NegationOperation;
import com.fuzzysetstudio.service.FuzzySetService;

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
