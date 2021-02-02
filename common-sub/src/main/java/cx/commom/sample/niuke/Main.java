package cx.commom.sample.niuke;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //思路：可整合数组满足：最大值-最小值+1=长度
        //两边遍历，对每一个子数组进行判断
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }
        ArrayList<Integer> length = new ArrayList<>();//存放可整合子串的长度
        length.add(1);
        for (int i = 0; i < arr.length; i++) {
            int max = arr[i];
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                max = Math.max(max, arr[j]);
                min = Math.min(min, arr[j]);//从arr[i]到arr[j]，找到最大值和最小值
                if (max - min == j - i) {//如果最大值-最小值+1=j-i+1，则是可整合数组
                    length.add(max - min + 1);
                    System.out.println(max - min + 1 + "," + i + "," + j);
                }
            }
        }
        Collections.sort(length);//升序
        Collections.reverse(length);//逆序
        System.out.println(length.get(0));//输出最大长度
    }

}
