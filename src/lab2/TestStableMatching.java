package lab2;

import java.util.*;

public class TestStableMatching {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 读取 N
        int N = scanner.nextInt();
        scanner.nextLine(); // 消费换行符

        // 读取男孩和女孩名字
        List<String> boys = Arrays.asList(scanner.nextLine().split(" "));
        List<String> girls = Arrays.asList(scanner.nextLine().split(" "));

        // 读取男孩偏好列表
        Map<String, List<String>> boyPreferences = new HashMap<>();
        for (int i = 0; i < N; i++) {
            String[] pref = scanner.nextLine().split(" ");
            boyPreferences.put(boys.get(i), Arrays.asList(pref));
        }

        // 读取女孩偏好列表
        Map<String, List<String>> girlPreferences = new HashMap<>();
        for (int i = 0; i < N; i++) {
            String[] pref = scanner.nextLine().split(" ");
            girlPreferences.put(girls.get(i), Arrays.asList(pref));
        }

        // 创建排名映射
        Map<String, Map<String, Integer>> boyRankings = new HashMap<>();
        for (String boy : boys) {
            List<String> pref = boyPreferences.get(boy);
            Map<String, Integer> ranking = new HashMap<>();
            for (int i = 0; i < pref.size(); i++) {
                ranking.put(pref.get(i), i); // 排名从 0 开始，0 表示最喜欢
            }
            boyRankings.put(boy, ranking);
        }

        Map<String, Map<String, Integer>> girlRankings = new HashMap<>();
        for (String girl : girls) {
            List<String> pref = girlPreferences.get(girl);
            Map<String, Integer> ranking = new HashMap<>();
            for (int i = 0; i < pref.size(); i++) {
                ranking.put(pref.get(i), i);
            }
            girlRankings.put(girl, ranking);
        }

        // 读取匹配结果
        Map<String, String> boyToGirl = new HashMap<>();
        Map<String, String> girlToBoy = new HashMap<>();
        for (int i = 0; i < N; i++) {
            String[] match = scanner.nextLine().split(" ");
            String boy = match[0];
            String girl = match[1];
            boyToGirl.put(boy, girl);
            girlToBoy.put(girl, boy);
        }

        // 检查是否存在不稳定对
        boolean isStable = true;
        for (String boy : boys) {
            String G_b = boyToGirl.get(boy); // B 的当前伴侣
            List<String> boyPref = boyPreferences.get(boy);
            int index = boyPref.indexOf(G_b); // G_b 在偏好列表中的位置
            for (int i = 0; i < index; i++) {
                String G = boyPref.get(i); // B 更喜欢的女孩
                String B_g = girlToBoy.get(G); // G 的当前伴侣
                // 如果 G 更喜欢 B 胜过 B_g
                if (girlRankings.get(G).get(boy) < girlRankings.get(G).get(B_g)) {
                    isStable = false;
                    System.out.println("不稳定");
                    return; // 找到不稳定对，立即结束
                }
            }
        }

        // 如果没有不稳定对，则匹配稳定
        if (isStable) {
            System.out.println("稳定");
        }

        scanner.close();
    }
}
