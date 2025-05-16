package Assignment.Assignment8;

import java.util.Scanner;

public class A8 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int len = 1 << N;

        Complex8A[] x = new Complex8A[len];
        for (int i = 0; i < len; i++) {
            double real = in.nextDouble();
            double imag = 0;
            x[i] = new Complex8A(real, imag);
        }
        Complex8A[] y = fft(x);
        for (int i = 0; i < len; i++) {
            double magnitude = Math.sqrt(y[i].real * y[i].real + y[i].imag * y[i].imag);
            System.out.printf("%.10f\n", magnitude);
        }


        in.close();
    }

    public static Complex8A[] fft(Complex8A[] x) {
        int N = x.length;
        if (N <= 1) {
            return new Complex8A[]{x[0]};
        }
        Complex8A[] even = new Complex8A[N / 2];
        Complex8A[] odd = new Complex8A[N / 2];
        for (int i = 0; i < N / 2; i++) {
            even[i] = x[i * 2];
            odd[i] = x[i * 2 + 1];
        }

        Complex8A[] e = fft(even);
        Complex8A[] d = fft(odd);

        Complex8A[] y = new Complex8A[N];
        for (int k = 0; k < N / 2; k++) {
            double t = 2 * Math.PI * k / N;
            Complex8A wk = new Complex8A(Math.cos(t), Math.sin(t));
            Complex8A wkdk = wk.multiply(d[k]);
            y[k] = e[k].add(wkdk);
            y[k + N / 2] = e[k].subtract(wkdk);
        }
        return y;
    }
}


class Complex8A {
    double real;
    double imag;

    Complex8A(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    Complex8A add(Complex8A other) {
        return new Complex8A(this.real + other.real, this.imag + other.imag);
    }

    Complex8A subtract(Complex8A other) {
        return new Complex8A(this.real - other.real, this.imag - other.imag);
    }

    Complex8A multiply(Complex8A other) {
        return new Complex8A(this.real * other.real - this.imag * other.imag, this.real * other.imag + this.imag * other.real);
    }
}

