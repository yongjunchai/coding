package greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class HuffmanCodes {

    public interface NodeBase
    {
        double getFrequency();
    }

    public static class LeafNode implements NodeBase
    {
        private char letter;
        private double frequency;

        public char getLetter() {
            return letter;
        }


        public double getFrequency() {
            return frequency;
        }


        public static LeafNode create(final char letter, final double frequency) {
            LeafNode leafNode = new LeafNode();
            leafNode.letter = letter;
            leafNode.frequency = frequency;
            return leafNode;
        }
    }

    public static class InternalNode implements NodeBase {
        private NodeBase leftChild;
        private NodeBase rightChild;
        private double frequency;

        public NodeBase getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(NodeBase leftChild) {
            this.leftChild = leftChild;
        }

        public NodeBase getRightChild() {
            return rightChild;
        }

        public void setRightChild(NodeBase rightChild) {
            this.rightChild = rightChild;
        }

        public void setFrequency(double frequency) {
            this.frequency = frequency;
        }

        @Override
        public double getFrequency() {
            return frequency;
        }
    }

    public static class NodeComparator implements Comparator<NodeBase>
    {
        @Override
        public int compare(NodeBase left, NodeBase right) {
            if (left.getFrequency() < right.getFrequency()) {
                return -1;
            }
            if (left.getFrequency() == right.getFrequency()) {
                return 0;
            }
            return 1;
        }
    }

    /**
     * return the root node the huffman tree
     * */
    public InternalNode encodeUsingHeap(final List<LeafNode> letterFrequencies) {
        if (null == letterFrequencies || letterFrequencies.size() < 2) {
            return null;
        }
        PriorityQueue<NodeBase> priorityQueueMin = new PriorityQueue<>(new NodeComparator());
        //init the heap
        for (LeafNode leafNode : letterFrequencies) {
            priorityQueueMin.add(leafNode);
        }
        while (priorityQueueMin.size() > 1) {
            NodeBase firstMin = priorityQueueMin.poll();
            NodeBase sndMin = priorityQueueMin.poll();
            InternalNode internalNode = new InternalNode();
            internalNode.setLeftChild(firstMin);
            internalNode.setRightChild(sndMin);
            internalNode.setFrequency(firstMin.getFrequency() + sndMin.getFrequency());
            priorityQueueMin.add(internalNode);
        }
        return (InternalNode) priorityQueueMin.poll();
    }

    private NodeBase getSmallest(final Queue<? extends NodeBase> leftQueue, final Queue<? extends NodeBase> rightQueue) {
        NodeBase rightSmallest = rightQueue.peek();
        NodeBase leftSmallest = leftQueue.peek();
        if (null != rightSmallest && null != leftSmallest) {
            if (leftSmallest.getFrequency() < rightSmallest.getFrequency()) {
                leftQueue.poll();
                return leftSmallest;
            }
            rightQueue.poll();
            return rightSmallest;
        }
        if (null != rightSmallest) {
            rightQueue.poll();
            return rightSmallest;
        }
        leftQueue.poll();
        return leftSmallest;
    }

    /**
     * return the root node the huffman tree
     * */
    public InternalNode encodingUsingQueue(final List<LeafNode> letterFrequencies) {
        if (null == letterFrequencies || letterFrequencies.size() < 2) {
            return null;
        }
        LeafNode[] leafNodes = new LeafNode[letterFrequencies.size()];
        int size = letterFrequencies.size();
        int index = 0;
        for(LeafNode leafNode : letterFrequencies) {
            leafNodes[index] = leafNode;
            ++ index;
        }
        //init: sorted queue is sorted in increasing order
        //forest is empty

        Arrays.sort(leafNodes, new NodeComparator());
        Queue<LeafNode> sortedQueue = new LinkedList<>();
        Queue<InternalNode> forest = new LinkedList<>();
        for (int i = 0; i < size; ++ i) {
            sortedQueue.add(leafNodes[i]);
        }
        //invariant to maintain: items in forest are in increasing order
        /**
         assumed the last item at f is the sum of s1 and s2, both s1 and s2 may be the sum of multiple items.
         since s1 and s2 are the smallest two of the two queues, s1 and s2 will be less than the head item of the f and the sorted queue.
         the new item picked up is the sum of the s3 and s4.  since s1 < s3,, s1 < s4, s2 < s3, and s2 < s4,
         (s1 + s2) < (s3 + s4), f is in increasing order still.
         * */
        while (sortedQueue.size() > 0 || forest.size() > 1) {
            NodeBase firstSmallest = getSmallest(sortedQueue, forest);
            NodeBase sndSmallest = getSmallest(sortedQueue, forest);
            InternalNode internalNode = new InternalNode();
            internalNode.setLeftChild(firstSmallest);
            internalNode.setRightChild(sndSmallest);
            internalNode.setFrequency(firstSmallest.getFrequency() + sndSmallest.getFrequency());
            forest.add(internalNode);
        }
        return forest.poll();
    }

    /**
     * left branch: 0
     * right branch: 1
     * */
    public Map<Character, String> getEncoding(final NodeBase nodeBase) {
        Map<Character, String> encodingMap = new HashMap<>();
        if (null == nodeBase) {
            return encodingMap;
        }
        StringBuilder builder = new StringBuilder();
        collectEncodingRecursive(nodeBase,  builder, encodingMap);
        return encodingMap;
    }

    private static class StackFrame {
        public NodeBase node;
        public String encoding;

        public static StackFrame create(final NodeBase nodeBase, final String encoding) {
            StackFrame stackFrame = new StackFrame();
            stackFrame.node = nodeBase;
            stackFrame.encoding = encoding;
            return stackFrame;
        }
    }

    public Map<Character, String> getEncodingIterative(final NodeBase nodeBase) {
        Map<Character, String> encodingMap = new HashMap<>();
        if (null == nodeBase) {
            return encodingMap;
        }
        Stack<StackFrame> stack = new Stack<>();
        stack.push(StackFrame.create(nodeBase, ""));
        while (! stack.isEmpty()) {
            StackFrame stackFrame = stack.pop();
            if (stackFrame.node instanceof LeafNode) {
                LeafNode leafNode = (LeafNode) stackFrame.node;
                encodingMap.put(leafNode.getLetter(), stackFrame.encoding);
                continue;
            }
            InternalNode internalNode = (InternalNode) stackFrame.node;
            if (internalNode.getLeftChild() != null) {
                stack.push(StackFrame.create(internalNode.getLeftChild(), stackFrame.encoding + "0"));
            }
            if (internalNode.getRightChild() != null) {
                stack.push(StackFrame.create(internalNode.getRightChild(), stackFrame.encoding + "1"));
            }
        }
        return encodingMap;
    }

    private void collectEncodingRecursive(final NodeBase nodeBase, final StringBuilder curEncoding, Map<Character, String> encodingMap)
    {
        if (nodeBase instanceof LeafNode) {
            encodingMap.put(((LeafNode)nodeBase).getLetter(), curEncoding.toString());
            return;
        }
        if (! (nodeBase instanceof InternalNode)) {
            return;
        }
        InternalNode internalNode = (InternalNode) nodeBase;
        if (internalNode.getLeftChild() != null) {
            collectEncodingRecursive(internalNode.getLeftChild(), curEncoding.append("0"), encodingMap);
            curEncoding.delete(curEncoding.length() - 1, curEncoding.length());
        }
        if (internalNode.getRightChild() != null) {
            collectEncodingRecursive(internalNode.getRightChild(), curEncoding.append("1"), encodingMap);
            curEncoding.delete(curEncoding.length() - 1, curEncoding.length());
        }
    }

}
