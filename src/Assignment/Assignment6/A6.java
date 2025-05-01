package Assignment.Assignment6;

import java.util.Scanner;

public class A6 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();  // 仍然使用 int，因为数组长度不能是 long
        long[] arr = new long[n];
        long[] temp = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextLong();  // 使用 nextLong() 读取 long 输入
        }
        long inversions = countInversions(arr, temp, 0, n - 1);
        System.out.println(inversions);
    }

    public static long countInversions(long[] arr, long[] temp, int left, int right) {
        long inversions = 0;
        if (left < right) {
            int mid = (left + right) / 2;
            long leftinv = countInversions(arr, temp, left, mid);
            long rightinv = countInversions(arr, temp, mid + 1, right);
            long mergeinv = merge(arr, temp, left, mid, right);
            inversions = leftinv + rightinv + mergeinv;
        }
        return inversions;
    }

    private static long merge(long[] array, long[] tempArray, int left, int mid, int right) {
        long invCount = 0;
        if (array == null || array.length == 0) {
            return 0;
        }
        if (left >= right) {
            return 0;
        }
        System.arraycopy(array, left, tempArray, left, right + 1 - left);
        int i = left;
        int j = mid + 1;
        int k = left;
        while (i <= mid && j <= right) {
            if (tempArray[i] <= tempArray[j]) {
                array[k] = tempArray[i];
                i++;
            } else {
                array[k] = tempArray[j];
                invCount += (mid - i + 1);
                j++;
            }
            k++;
        }

        while (i <= mid) {
            array[k] = tempArray[i];
            i++;
            k++;
        }
        while (j <= right) {
            array[k] = tempArray[j];
            j++;
            k++;
        }
        return invCount;
    }
}

