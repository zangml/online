package com.koala.learn.utils.treat;


import com.koala.learn.utils.Complex;

/**
 * Created by koala on 2018/1/2.
 */
public class FFTUtils {
    //快速傅里叶变换java实现如下，输入：离散信号值（实数），输出：经过傅里叶变换得到的一组频率（实数）
    public static Complex[] fft(Complex[] x) {
        int N ;
        N = x.length;

        // base case
        if (N == 1) return new Complex[]{x[0]};

        // radix 2 Cooley-Tukey FFT
        if (N % 2 != 0) {
            return dft(x);
        }

        // fft of even terms
        Complex[] even = new Complex[N / 2];
        for (int k = 0; k < N / 2; k++) {
            even[k] = x[2 * k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd = even;  // reuse the array
        for (int k = 0; k < N / 2; k++) {
            odd[k] = x[2 * k + 1];
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[N];
        for (int k = 0; k < N / 2; k++) {
            double kth;
            kth= -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k] = q[k].plus(wk.times(r[k]));
            y[k + N / 2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }

    public static Complex[] dft(Complex[] x) {
        int n = x.length;

        // 1个信号exp(-2i*n*PI)=1
        if (n == 1)
            return x;

        Complex[] y = new Complex[n];
        for (int i = 0; i < n; i++) {
            y[i] = new Complex(0, 0);
            for (int k = 0; k < n; k++) {
                //使用欧拉公式e^(-i*2pi*k/N) = cos(-2pi*k/N) + i*sin(-2pi*k/N)
                double p = -2 * k * Math.PI / n;
                Complex m = new Complex(Math.cos(p), Math.sin(p));
                y[i]=y[i].plus(x[k].times(m));
            }
        }
        return y;
    }

    public static void show(Complex[] x, String title) {
        System.out.println(title);
        System.out.println("-------------------");
        for (int i = 0; i < x.length; i++) {
            System.out.println(x[i]);
        }
        System.out.println();
    }

    public static  void main(String[] args) {
        //int N = Integer.parseInt(args[0]);
        int N = 10;
        Complex[] x = new Complex[N];

        // original data
        for (int i = 0; i < N; i++) {
            x[i] = new Complex(i, 0);
            // x[i] = new Complex(-2 * Math.random() + 1, 0);
        }
        show(x, "x");

        // FFT of original data
        FFTUtils test =new FFTUtils();

        Complex[] y = test.fft(x);
        show(y, "y = fft(x)");

    }
}
