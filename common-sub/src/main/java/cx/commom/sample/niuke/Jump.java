package cx.commom.sample.niuke;

//一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
//4 5 每一步都只选1，2
public class Jump {

    public static void main(String[] args) {
        System.out.println(JumpFloor(6));
    }

    public static int JumpFloor(int target) {
        int[] f = new int[target + 1];

        if (target == 1) {
            return 1;
        }
        if (target == 2) {
            return 2;
        }
        f[0] = 0;
        f[1] = 1;
        f[2] = 2;
        for (int i = 3; i <= target; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f[target];
    }
}
