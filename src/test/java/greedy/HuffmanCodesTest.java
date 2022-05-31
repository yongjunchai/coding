package greedy;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import greedy.HuffmanCodes.LeafNode;
import utils.Utility;

public class HuffmanCodesTest {

    private static class Entry {
        public List<LeafNode> leafNodes = new ArrayList<>();

    }

    private Entry getTestData1() {
        Entry entry = new Entry();
        entry.leafNodes.addAll(
            Arrays.asList(
                LeafNode.create('A', 0.6),
                LeafNode.create('B', 0.25),
                LeafNode.create('C', 0.1),
                LeafNode.create('D', 0.05)
            )
        );
        return entry;
    }

    private Entry getTestData2() {
        Entry entry = new Entry();
        entry.leafNodes.addAll(
                Arrays.asList(
                        LeafNode.create('A', 0.25),
                        LeafNode.create('B', 0.25),
                        LeafNode.create('C', 0.25),
                        LeafNode.create('D', 0.25)
                )
        );
        return entry;
    }

    private List<Entry> getTestData() {
        return Arrays.asList(getTestData1(), getTestData2(), new Entry());
    }

    @Test
    public void huffmanCodesTest()
    {
        List<Entry> entries = getTestData();
        for (Entry entry : entries) {
            System.out.println("new test entry start. ");
            HuffmanCodes huffmanCodes = new HuffmanCodes();
            HuffmanCodes.InternalNode internalNodeHeap = huffmanCodes.encodeUsingHeap(entry.leafNodes);
            HuffmanCodes.InternalNode internalNodeQueue = huffmanCodes.encodingUsingQueue(entry.leafNodes);
            if (Utility.isEmpty(entry.leafNodes)) {
                Assert.assertTrue(internalNodeQueue == null);
                Assert.assertTrue(internalNodeHeap == null);
                continue;
            }
            Map<Character, String> heapCharMap = huffmanCodes.getEncoding(internalNodeHeap);
            Map<Character, String> queueCharMap = huffmanCodes.getEncoding(internalNodeQueue);
            Map<Character, String> badCharMap = huffmanCodes.getEncoding(null);
            Assert.assertTrue(Utility.isEmpty(badCharMap));
            for (LeafNode leafNode : entry.leafNodes) {
                String heapEncoded = heapCharMap.get(leafNode.getLetter());
                String queueEncoded = queueCharMap.get(leafNode.getLetter());
                System.out.printf("%s --> %s\n", leafNode.getLetter(), heapEncoded);
                Assert.assertTrue(heapEncoded.length() == queueEncoded.length());
            }
        }
    }
}
