import org.testng.Assert;
import org.testng.annotations.Test;

public class SequenceAlignmentTest {


    @Test
    public void sequenceAlignment() {

        final String left = "abcdef";
        final String right = "def";

        int gapPenality = 1;
        int mismatchPenality = 2;

        int minPenality = 3;

        SequenceAlignment sequenceAlignment = new SequenceAlignment();
        SequenceAlignment.Result result = sequenceAlignment.sequenceAlign(left.toCharArray(), right.toCharArray(), gapPenality, mismatchPenality);
        Assert.assertTrue(result.totalPenality == minPenality);

    }
}
