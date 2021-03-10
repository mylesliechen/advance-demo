package cx.commom.sample.TreeNode;

import cx.commom.sample.TreeNode.TreeNode;
    //           8
    //         /  \
    //        6    10
    //       / \   / \
    //      5   7  9 11
    //        镜像二叉树
    //          8
    //        /   \
    //       10    6
    //       / \   / \
    //      11 9  7   5
public class Mirror {
    public void Mirror(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        Mirror(root.left);
        Mirror(root.right);
    }
}
