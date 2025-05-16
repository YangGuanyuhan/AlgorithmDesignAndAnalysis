package Assignment.Assignment7;

import java.util.ArrayList;
import java.util.Scanner;

public class B7 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();//numbers of rectangles
        rectangleB7[] rectangles = new rectangleB7[n];
        for (int i = 0; i < n; i++) {
            int left = in.nextInt();
            int right = in.nextInt();
            int height = in.nextInt();
            rectangles[i] = new rectangleB7(left, right, height);
        }
        ArrayList<pointB7> skyline = divide(rectangles, 0, n - 1);
        for (int i = 0; i < skyline.size(); i++) {
            pointB7 p = skyline.get(i);

            System.out.println(p.x + " " + p.y);

        }


        in.close();
    }

    public static ArrayList<pointB7> divide(rectangleB7[] rectangles, int left, int right) {
        if (left == right) {
            ArrayList<pointB7> baseSkyline = new ArrayList<>();
            baseSkyline.add(new pointB7(rectangles[left].left, rectangles[left].height));
            baseSkyline.add(new pointB7(rectangles[left].right, 0));
            return baseSkyline;
        }
        int mid = left + (right - left) / 2;
        ArrayList<pointB7> leftSkyline = divide(rectangles, left, mid);
        ArrayList<pointB7> rightSkyline = divide(rectangles, mid + 1, right);
        return merge(leftSkyline, rightSkyline);

    }

    public static ArrayList<pointB7> merge(ArrayList<pointB7> leftSkyline, ArrayList<pointB7> rightSkyline) {
        ArrayList<pointB7> mergedSkyline = new ArrayList<>();
        int h1 = 0;
        int h2 = 0;
        int i = 0;
        int j = 0;

        // 当任一轮廓中还有点未处理时继续
        while (i < leftSkyline.size() || j < rightSkyline.size()) {
            // 获取当前左右轮廓的下一个点的 x 坐标，如果已处理完则用最大值标记
            int x1 = (i < leftSkyline.size()) ? leftSkyline.get(i).x : Integer.MAX_VALUE;
            int x2 = (j < rightSkyline.size()) ? rightSkyline.get(j).x : Integer.MAX_VALUE;
            int currentX; // 当前处理的 x 坐标

            // 选择 x 坐标较小的点进行处理
            if (x1 < x2) {
                currentX = x1;
                h1 = leftSkyline.get(i).y; // 更新左轮廓高度
                i++;
            } else if (x2 < x1) {
                currentX = x2;
                h2 = rightSkyline.get(j).y; // 更新右轮廓高度
                j++;
            } else { // x1 == x2, 两个轮廓在同一点 x 坐标发生变化
                currentX = x1; // 或 x2
                h1 = leftSkyline.get(i).y;
                h2 = rightSkyline.get(j).y;
                i++;
                j++;
            }

            int maxHeight = Math.max(h1, h2); // 当前 x 坐标处的最大高度

            // 如果 mergedSkyline 为空，或者当前最大高度与 mergedSkyline 中最后一个点的高度不同
            // 这是添加新点的基本条件，确保高度确实发生了变化
            if (mergedSkyline.isEmpty() || mergedSkyline.get(mergedSkyline.size() - 1).y != maxHeight) {
                // 特殊处理：如果 currentX 与 mergedSkyline 中最后一个点的 x 坐标相同
                // (这通常发生在 x1 == x2 的情况，或者一个点正好落在前一个点的垂直线上)
                // 此时我们应该更新最后一个点的高度，而不是添加一个新点。
                if (!mergedSkyline.isEmpty() && mergedSkyline.get(mergedSkyline.size() - 1).x == currentX) {
                    mergedSkyline.get(mergedSkyline.size() - 1).y = maxHeight;

                    // 在更新后，如果这个点的高度变得和它之前的点一样了，说明这个点是多余的，需要移除。
                    // 例如：原有 [(0,10), (5,20)]，当前处理 (5,10)。
                    // 更新后变为 [(0,10), (5,10)]。此时 (5,10) 和 (0,10) 高度相同，(5,10) 多余。
                    if (mergedSkyline.size() >= 2 &&
                            mergedSkyline.get(mergedSkyline.size() - 2).y == mergedSkyline.get(mergedSkyline.size() - 1).y) {

                        mergedSkyline.remove(mergedSkyline.size() - 1);
                    }
                } else {
                    // 否则，添加一个新的关键点
                    mergedSkyline.add(new pointB7(currentX, maxHeight));
                }
            }
        }
        return mergedSkyline;
    }
}


class rectangleB7 {
    int left;
    int right;
    int height;

    public rectangleB7(int left, int right, int height) {
        this.left = left;
        this.right = right;
        this.height = height;
    }
}

class pointB7 {
    int x;
    int y;

    public pointB7(int x, int y) {
        this.x = x;
        this.y = y;
    }
}


