package cx.commom.sample.niuke;

import java.util.Scanner;

//给定排序数组arr和整数k，不重复打印arr中所有相加和为k的不降序二元组
//例如, arr = [-8, -4, -3, 0, 1, 2, 4, 5, 8, 9], k = 10，打印结果为：
public class exercise3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        long k = in.nextLong();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[i] + arr[j] > k) {
                    return;
                }
                if (arr[i] + arr[j] == k) {
                    System.out.println(arr[i] + " " + arr[j]);
                }
            }
        }
    }
}
