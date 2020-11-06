package com.bt02.aplication;

import java.util.Scanner;

public class Aplication {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("nhap n:");
		int n = sc.nextInt();
		int s = 0;
		for (int i = 1; i <= n; i++) {
			s = s + (i * i);
		}
		System.out.println("Tich la:"+s);
	}

}
