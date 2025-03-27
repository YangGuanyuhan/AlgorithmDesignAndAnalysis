package lab.lab2;

import java.util.HashMap;
import java.util.Scanner;

public class BruteForceMatching {
    // 记录找到的稳定匹配数量
    private static int stableCount = 0;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String[] men = new String[n];
        String[] women = new String[n];
        HashMap<String, Integer> menMap = new HashMap<>();
        HashMap<String, Integer> womenMap = new HashMap<>();


        for (int i = 0; i < n; i++) {
            men[i] = in.next();
            menMap.put(men[i], i);
        }

        for (int i = 0; i < n; i++) {
            women[i] = in.next();
            womenMap.put(women[i], i);
        }


        int[][] menPrefIndex = new int[n][n];
        int[][] womenPrefIndex = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                menPrefIndex[i][j] = womenMap.get(in.next());
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                womenPrefIndex[i][j] = menMap.get(in.next());
            }
        }

        int[][] inverseWomenPref = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverseWomenPref[i][womenPrefIndex[i][j]] = j;
            }
        }

        int[] currentMatch = new int[n];
        for (int i = 0; i < currentMatch.length; i++) {
            currentMatch[i] = -1;

        }
        // used[i] 标记女生 i 是否已经被分配
        boolean[] used = new boolean[n];


        generate(0, n, currentMatch, used, menPrefIndex, inverseWomenPref, men, women);

        long endTime = System.nanoTime();
        System.out.println("Time: " + (endTime - startTime) / 1000000.0 + "ms");
    }

    /**
     * 递归生成所有可能的匹配方案，并判断是否稳定。
     *
     * @param index            当前为第 index 个男生分配女生
     * @param n                总人数
     * @param currentMatching  当前匹配方案（perm[i] 表示男生 i 匹配的女生下标）
     * @param used             标记哪些女生已经被使用
     * @param menPrefIndex     男生偏好数组
     * @param inverseWomenPref 女生偏好逆序数组
     * @param men              男生名字数组
     * @param women            女生名字数组
     */
    private static void generate(int index, int n, int[] currentMatching, boolean[] used,
                                 int[][] menPrefIndex, int[][] inverseWomenPref,
                                 String[] men, String[] women) {
        if (index == n) {
            if (isStable(currentMatching, menPrefIndex, inverseWomenPref)) {
                for (int i = 0; i < n; i++) {
                    System.out.println(men[i] + " " + women[currentMatching[i]]);
                }
                System.out.println();
            } else {
                return;
            }
        }
        for (int i = 0; i < n; i++) {
            if (used[i]) {
                continue;
            } else {
                used[i] = true;
                currentMatching[index] = i;
                generate(index + 1, n, currentMatching, used, menPrefIndex, inverseWomenPref, men, women);
                currentMatching[index] = -1;
                used[i] = false;
            }

        }
        return;

    }

    /**
     * 判断当前匹配是否稳定：
     * 对于每个男生 m，如果存在他偏好于当前匹配的女生 w，并且 w 也更喜欢 m（相对于她当前匹配的男生），则该匹配不稳定。
     *
     * @param currentMatching  当前匹配方案，perm[i] 表示男生 i 的伴侣的女生下标
     * @param menPrefIndex     男生偏好数组
     * @param inverseWomenPref 女生偏好逆序数组
     * @return 如果匹配稳定返回 true，否则返回 false
     */
    private static boolean isStable(int[] currentMatching, int[][] menPrefIndex, int[][] inverseWomenPref) {
        int n = currentMatching.length;
        // 建立女生的匹配信息：对于每个女生，记录她匹配的男生
        int[] womenMatch = new int[n];
        for (int i = 0; i < n; i++) {
            womenMatch[currentMatching[i]] = i;
        }

        // 对于每个男生，检查是否存在更偏爱的女生 w，
        // 并且 w 也偏爱 m（与她当前匹配的男生相比），则匹配不稳定
        for (int m = 0; m < n; m++) {
            int currentWoman = currentMatching[m];
            int rank = -1;
            // 找到当前匹配女生在男生 m 的偏好列表中的位置
            for (int j = 0; j < n; j++) {
                if (menPrefIndex[m][j] == currentWoman) {
                    rank = j;
                    break;
                }
            }
            // 遍历男生 m 更偏爱的每个女生
            for (int j = 0; j < rank; j++) {
                int preferredWoman = menPrefIndex[m][j];
                int preferredWomenCurrentPartner = womenMatch[preferredWoman];
                // 如果 preferredWoman 对男生 m 的排名比她当前匹配的男生高，则不稳定
                if (inverseWomenPref[preferredWoman][m] < inverseWomenPref[preferredWoman][preferredWomenCurrentPartner]) {
                    return false;
                }
            }
        }
        return true;
    }
}
