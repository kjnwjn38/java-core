package com.bt03.aplication;

import java.util.Scanner;

public class Aplication {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("nhap n:");
		int n=sc.nextInt();
		double s=1;
		for(int i=2;i<=n;i++) {
			s=s+(1.0/i);
		}
		System.out.println("S la:"+s);
	}

}
