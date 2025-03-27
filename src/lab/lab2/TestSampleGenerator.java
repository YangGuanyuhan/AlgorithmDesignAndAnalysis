package lab.lab2;

import java.util.*;

public class TestSampleGenerator {
    public static void main(String[] args) {

        int n = 1000; // 生成的男生和女生的数量

        // 构造男生和女生名字列表
        List<String> boys = new ArrayList<>();
        List<String> girls = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            boys.add("M" + i);
            girls.add("W" + i);
        }

        // 输出格式：第一行 N
        System.out.println(n);

        // 输出男生名字（空格分隔）
        for (int i = 0; i < boys.size(); i++) {
            System.out.print(boys.get(i));
            if (i < boys.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        // 输出女生名字（空格分隔）
        for (int i = 0; i < girls.size(); i++) {
            System.out.print(girls.get(i));
            if (i < girls.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        Random random = new Random();

        // 为每个男生生成对女生的偏好列表（随机排列）
        for (int i = 0; i < n; i++) {
            List<String> girlPreference = new ArrayList<>(girls);
            Collections.shuffle(girlPreference, random);
            for (int j = 0; j < girlPreference.size(); j++) {
                System.out.print(girlPreference.get(j));
                if (j < girlPreference.size() - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        // 为每个女生生成对男生的偏好列表（随机排列）
        for (int i = 0; i < n; i++) {
            List<String> boyPreference = new ArrayList<>(boys);
            Collections.shuffle(boyPreference, random);
            for (int j = 0; j < boyPreference.size(); j++) {
                System.out.print(boyPreference.get(j));
                if (j < boyPreference.size() - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
