package utils;

import org.testng.annotations.Test;
import utils.Utility;

public class UtilityTest {

    @Test
    public void testMatrix() {

        Utility.FetchValue<Integer> fetchValue = (t) -> Integer.toString(t);
        int rows = 5;
        int columns = 12;
        Integer[][] a = new Integer[rows][columns];
        for(int i = 0; i < rows; ++ i) {
            for (int j = 0; j < columns; ++ j) {
                a[i][j] = j;//i * columns + j;
            }
        }
        Utility.dump(a, fetchValue);
    }
}
