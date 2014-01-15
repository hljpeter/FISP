package com.synesoft.fisp.app.common.utils;

import java.util.Random;

public class GenRdmPwd extends Object {

	private final static String[] str = { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
			"a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v",
			"b", "n", "m" };

	public static String genRandmon(int len) {
		final int maxNum = 36;// 26个字母+10个数字
		int i; // 生成的随机数
		int count = 0;// 生成密码的长度
		StringBuffer pwd = new StringBuffer();
		Random r = new Random();
		while (count < len) {
			i = Math.abs(r.nextInt(maxNum));
			pwd.append(str[i]);
			count++;
		}
		return pwd.toString();
	}

	public static void main(String[] args) {
		System.out.println("最终生成的随机数为：" + GenRdmPwd.genRandmon(8));
	}

}
