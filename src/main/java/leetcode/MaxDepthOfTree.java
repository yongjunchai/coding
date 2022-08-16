package leetcode;



/**
Definition for a binary tree node.
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
 */

import java.util.Stack;

public class MaxDepthOfTree {
    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private static class StackFrame {
        public int depth;
        public TreeNode treeNode;

        static StackFrame create(final int depth, final TreeNode treeNode) {
            StackFrame stackFrame = new StackFrame();
            stackFrame.depth = depth;
            stackFrame.treeNode = treeNode;
            return  stackFrame;
        }
    }


    public int maxDepth(TreeNode root) {
        if(null == root) {
            return 0;
        }
        Stack<StackFrame> stack = new Stack<>();
        stack.add(StackFrame.create(1, root));
        int maxDepth = 1;
        while (! stack.isEmpty()) {
            StackFrame stackFrame = stack.pop();
            if(null != stackFrame.treeNode.left) {
                if (stackFrame.depth + 1 > maxDepth) {
                    maxDepth = stackFrame.depth + 1;
                }
                stack.push(StackFrame.create(stackFrame.depth + 1, stackFrame.treeNode.left));
            }
            if (null != stackFrame.treeNode.right) {
                if (stackFrame.depth + 1 > maxDepth) {
                    maxDepth = stackFrame.depth + 1;
                }
                stack.push(StackFrame.create(stackFrame.depth + 1, stackFrame.treeNode.right));
            }
        }
        return maxDepth;
    }
}
