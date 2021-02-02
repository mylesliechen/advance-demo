package cx.commom.sample.niuke;

import java.util.Scanner;

public class exercise1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();//n行
        int m = scanner.nextInt();//m列
        int k = scanner.nextInt();
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (scanner.nextInt() == k) {
                    flag = true;
                }
            }

        }
        System.out.println(flag ? "Yes" : "No");
    }
}
