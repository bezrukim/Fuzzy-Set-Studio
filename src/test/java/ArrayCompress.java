import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArrayCompress {
    public static void compressNumbers(ArrayList<Integer> uncompress){
        if (uncompress.isEmpty()){
            return;
        }

        int previous = uncompress.getFirst();
        for (int i = 1; i < uncompress.size(); ++i){
            int current = uncompress.get(i);
            if (previous == current){
                uncompress.remove(i);
                i--;
                continue;
            }
            previous = current;
        }
    }
}

class ArrayCompressTest{
    @Test
    void removeDuplicate(){
        ArrayList<Integer> uncompress = new ArrayList<>(List.of(1, 2, 2, 3, 3, 1, 2, 3));
        ArrayCompress.compressNumbers(uncompress);
        assertEquals(List.of(1, 2, 3, 1, 2, 3), uncompress);
    }

    @Test
    void withoutDuplicate(){
        ArrayList<Integer> uncompress = new ArrayList<>(List.of(1, 2, 3, 4));
        ArrayCompress.compressNumbers(uncompress);

        assertEquals(List.of(1, 2, 3, 4), uncompress);
    }

    @Test
    void onlyOneDuplicateElement(){
        ArrayList<Integer> uncompress = new ArrayList<>(List.of(1, 1, 1, 1));
        ArrayCompress.compressNumbers(uncompress);

        assertEquals(List.of(1), uncompress);
    }

    @Test
    void onlyOneElement(){
        ArrayList<Integer> uncompress = new ArrayList<>(List.of(1));
        ArrayCompress.compressNumbers(uncompress);

        assertEquals(List.of(1), uncompress);
    }

    @Test
    void ThrowExceptionTest(){
        ArrayList<Integer> uncompress = new ArrayList<>();
        ArrayCompress.compressNumbers(uncompress);
        assertEquals(List.of(), uncompress);
    }
}

