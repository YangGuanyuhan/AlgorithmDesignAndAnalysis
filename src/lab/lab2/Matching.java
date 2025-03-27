package lab.lab2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Matching {
    public static void main(String[] args) {
        long startTime = System.nanoTime();

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String[] men = new String[n];
        String[] women = new String[n];
        int[] menMatch = new int[n];
        int[] womenMatch = new int[n];
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
        in.close();
        long midTime = System.nanoTime();


        int[][] inverseWomenPref = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverseWomenPref[i][womenPrefIndex[i][j]] = j;
            }
        }


        for (int i = 0; i < n; i++) {
            menMatch[i] = -1;
            womenMatch[i] = -1;
        }

        stableMatching(n, menMatch, womenMatch, menPrefIndex, inverseWomenPref);


        for (int i = 0; i < n; i++) {
            System.out.println(men[i] + " " + women[menMatch[i]]);
        }

        long endTime = System.nanoTime();

        System.out.println("Time: " + (midTime - startTime) / 1000000 + "ms");
        System.out.println("Time: " + (endTime - midTime) / 1000000 + "ms");
    }

    public static void stableMatching(int n, int[] menMatch, int[] womenMatch, int[][] menPrefIndex, int[][] inverseWomenPref) {

        int[] nextProposal = new int[n];

        Queue<Integer> freeMen = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            freeMen.offer(i);
        }

        while (!freeMen.isEmpty()) {
            int freeMan = freeMen.poll();
            int w = menPrefIndex[freeMan][nextProposal[freeMan]++];

            if (womenMatch[w] == -1) {

                menMatch[freeMan] = w;
                womenMatch[w] = freeMan;
            } else {
                int currentMan = womenMatch[w];

                if (inverseWomenPref[w][freeMan] < inverseWomenPref[w][currentMan]) {
                    menMatch[freeMan] = w;
                    womenMatch[w] = freeMan;

                    freeMen.offer(currentMan);
                } else {

                    freeMen.offer(freeMan);
                }
            }
        }
    }
}
