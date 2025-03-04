package com.wenzhi.leetcode_service.task.execute;

import com.wenzhi.leetcode_service.util.PrettyPrintLinkedList;
import com.wenzhi.leetcode_service.util.TreeNode;
import org.springframework.stereotype.Service;

@Service
public class PrettyPrintLinkedListJob {
    public void execute() {
        // 创建一个简单的二叉树
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        // 调用prettyPrintTree方法，由于该方法主要是打印输出，我们无法直接断言其输出内容
        // 这里可以通过捕获标准输出来进行一些简单的验证，例如验证输出是否包含特定的节点值
        // 下面是一个简单的示例，使用System.setOut来捕获输出流，实际应用中可能需要更复杂的处理
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        PrettyPrintLinkedList.prettyPrintTree(root);

        String output = outContent.toString();
        System.out.println(output);

        // 恢复标准输出
        System.setOut(System.out);
    }
}
