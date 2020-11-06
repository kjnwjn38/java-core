package com.bt05.aplication;

import java.util.Scanner;

public class Aplication {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("nhap n:");
		int n = sc.nextInt();
		double s = 0;
		for (int i = 1; i <= n; i++) {
			s += 1.0 / (2 * i);
		}
		System.out.println("tong la:" + s);

	}

}
