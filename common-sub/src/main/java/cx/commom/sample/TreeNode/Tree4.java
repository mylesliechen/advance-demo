package cx.commom.sample.TreeNode;

public class Tree4 {
    //给出一棵有n个结点的标准的完全二叉树（即，若父结点编号为x，则它的两个子结点的编号分别为x \times 2x×2和x \times 2 + 1x×2+1），定义每个结点的价值为xdep_xxdep
    //x
    // ，即每个点的编号乘以每个点的深度（深度即为从根结点到这一结点最短路径经过的点的个数，定义根结点1的深度为1）。
    //
    //请你求解这棵树中每个结点的价值和（由于答案可能会很大，请你对998244353取模），即\sum\limits_{i=1}^{n}idep_i \ mod\ 998244353
    //i=1
    //∑
    //n
    //​
    // idep
    //i
    //​
    //  mod 998244353。
    //
    //完全二叉树：若设二叉树的深度为k，除第 k 层外，其它各层 (1～k-1) 的结点数都达到最大个数，第 k 层所有的结点都连续集中在最左边。

    public static long tree4(long n) {
        // write code here
        int k = 1;//层数
        long count = 0;
        while (n > 0) {
            long levelCount = 0;
            for (int i = 1 << (k - 1); i <= (1 << k) - 1; i++) {
                if (n <= 0) {
                    break;
                }
                levelCount += i;
                n--;
            }

            count = levelCount % 998244353 * k + count;
            k++;
        }
        return count % 998244353;
    }

    //long dep = 1;
    //long sum = 0;
    //int mod = 998244353;
    //long res = 0;
    //long i = 1;
    //    for(i = 1; i*2<=n; i*=2){
    //    sum = (i+i*2-1)*i/2%mod;
    //    res += sum*dep%mod;
    //    dep++;
    //}
    //sum = (i+n)*(n-i+1)/2%mod;
    //res = (res + sum*dep)%mod;
    //    return res;
    public static void main(String[] args) {
        System.out.println(tree4(829830147));
    }
}
