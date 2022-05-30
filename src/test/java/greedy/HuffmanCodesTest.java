package greedy;

import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.PriorityQueue;

public class HuffmanCodesTest {

    @Test
    public void test()
    {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int i = 10; i >= 0; --i)
        {
            minHeap.add(i);
        }
//        while (minHeap.size() > 0) {
//            System.out.println(minHeap.poll());
//        }
        Iterator<Integer>  iterator = minHeap.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
