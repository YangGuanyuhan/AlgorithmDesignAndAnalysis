package lab1;

import java.util.Scanner;

public class TowerOfHanoi {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        hanoiTower(n,'A','C','B');


    }

    public static void hanoiTower(int n, char from, char to, char aux) {
        if (n == 1) {
            System.out.println("Move disk 1 from rod " + from + " to rod " + to);
            return;
        }
        hanoiTower(n-1,from,aux,to);
        System.out.println("Move disk "+n+" from rod " + from + " to rod " + to);
        hanoiTower(n-1,aux,to,from);
    }
}

