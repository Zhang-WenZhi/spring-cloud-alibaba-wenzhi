package com.wenzhi.leetcode_service.util;

import cn.hutool.core.lang.Pair;

public class Solution {
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        return f(root).getKey();
    }

    private Pair<TreeNode, Integer> f(TreeNode root) {
        if (root == null) {
            return new Pair<>(root, 0);
        }

        Pair<TreeNode, Integer> left = f(root.left);
        Pair<TreeNode, Integer> right = f(root.right);

        if (left.getValue() > right.getValue()) {
            return new Pair<>(left.getKey(), left.getValue() + 1);
        }
        if (left.getValue() < right.getValue()) {
            return new Pair<>(right.getKey(), right.getValue() + 1);
        }
        return new Pair<>(root, left.getValue() + 1);
    }

    public static void main(String[] args) {
        String root = "[3,5,1,6,2,0,8,null,null,7,4]";
        TreeNode rootNode = TreeNodeToString.stringToTreeNode(root);
        Solution solution = new Solution();
        TreeNode lcaDeepestLeaves = solution.lcaDeepestLeaves(rootNode);
        System.out.println(TreeNodeToString.treeNodeToString(lcaDeepestLeaves));
    }
}
