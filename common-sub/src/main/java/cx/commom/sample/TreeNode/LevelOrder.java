package cx.commom.sample.TreeNode;

import java.util.*;

public class LevelOrder {
    public static ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) {
            queue.add(root);
        }
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            ArrayList<Integer> level = new ArrayList<>();
            int size = queue.size();//不能在for循环动态使用queue.size()
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node != null) {
                    level.add(node.val);
                    if (node.left != null) {
                        queue.add(node.left);
                    }
                    if (node.right != null) {
                        queue.add(node.right);
                    }
                }
            }
            res.add(level);
        }
        return res;
    }

    //之字形层序遍历，第一层左->右，下一层 右->左
    public static ArrayList<ArrayList<Integer>> zigzagLevelOrder(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) {
            queue.add(root);
        }
        boolean flag = false;//是否倒序
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            ArrayList<Integer> level = new ArrayList<>();
            int size = queue.size();//不能在for循环动态使用queue.size()
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node != null) {
                    level.add(node.val);
                    if (node.left != null) {
                        queue.add(node.left);
                    }
                    if (node.right != null) {
                        queue.add(node.right);
                    }
                }
            }
            if (flag) {
                Collections.reverse(level);
            }
            res.add(level);
            flag = !flag;
        }
        return res;
    }


    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode();
        treeNode.val = 1;
        TreeNode treeNode2 = new TreeNode();
        treeNode2.val = 2;
        treeNode2.left = null;
        treeNode2.right = null;
        TreeNode treeNode3 = new TreeNode();
        treeNode3.val = 3;

        treeNode.left = treeNode2;
        treeNode.right = treeNode3;

        System.out.println(levelOrder(treeNode));
        System.out.println(zigzagLevelOrder(treeNode));
    }
}


