package cx.commom.sample.TreeNode;

//回环  abcdcba  absba true;
// anc false;
public class jude {
    public static boolean judge(String str) {
        // write code here
        char[] chars = str.toCharArray();
        int n = chars.length;
        for (int i = 0; i <= n / 2; i++) {
            if (chars[i] != chars[n - i - 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        //System.out.println(judge("abdhekt"));
        System.out.println(judge("abbcfcbba"));
    }
}
