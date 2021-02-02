package cx.commom.sample.niuke;

import java.util.*;

//存在一个问题 3 5 5 len=3 5-3+1=3,其实是有问题的
public class exercise2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        ArrayList<Integer> len = new ArrayList<>();
        int currLen = 1;
        for (int i = 0; i < arr.length; i++) {
            int max = arr[i];
            int min = arr[i];
            for (int j = i; j < arr.length; j++) {
                if (arr[i] != arr[j]) {
                    max = Math.max(max, arr[j]);
                    min = Math.min(min, arr[j]);
                    currLen = currLen + 1;
                } else {
                    break;
                }
                if (currLen == max - min + 1) {
                    len.add(currLen);
                    //System.out.println(currLen + "," + i + "," + j);
                }
            }
            currLen = 1;
        }
        if (len.size() > 0) {
            Collections.sort(len);
            Collections.reverse(len);
            System.out.println(len.get(0));
        } else {
            System.out.println(1);
        }

    }
}
