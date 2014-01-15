package com.synesoft.fisp.app.common.utils.encryp;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.synesoft.fisp.app.common.constants.CommonConst;

/**
 * @author zhongHubo
 * @date 2013年10月16日 10:45:15
 * @version 1.0
 * @Description DES对称加密算法
 * @System FISP
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class EncrypDES {
	
	//KeyGenerator 提供对称密钥生成器的功能，支持各种算法
	private KeyGenerator keygen;
	//SecretKey 负责保存对称密钥
	private static SecretKey deskey;
	//Cipher负责完成加密或解密工作
	private Cipher c;
	//该字节数组负责保存加密的结果
	private byte[] cipherByte;
	
	public EncrypDES() throws NoSuchAlgorithmException, NoSuchPaddingException{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		//实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
		keygen = KeyGenerator.getInstance("DES");
		//生成密钥
		if(deskey == null) {
			deskey = keygen.generateKey();
		}
		//生成Cipher对象,指定其支持的DES算法
		c = Cipher.getInstance("DES");
	}
	
	/**
	 * 对字符串加密
	 * 
	 * @param str
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] Encrytor(String str) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
		c.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] src = str.getBytes();
		// 加密，结果保存进cipherByte
		cipherByte = c.doFinal(src);
		return cipherByte;
	}

	/**
	 * 对字符串解密
	 * 
	 * @param buff
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] Decryptor(byte[] buff) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
		c.init(Cipher.DECRYPT_MODE, deskey);
		cipherByte = c.doFinal(buff);
		return cipherByte;
	}

	/**
	 * @param args
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	public static void main(String[] args) throws Exception {
		EncrypDES de1 = new EncrypDES();
		String msg ="user:zhb@!$datetime:2013年10月16日 11:10:28";
		byte[] encontent = de1.Encrytor(msg);
		byte[] decontent = de1.Decryptor(encontent);
		System.out.println("key1:" + deskey);
		System.out.println("明文是:" + msg);
		System.out.println("加密后:" + new String(encontent));
		System.out.println("解密后:" + new String(decontent));
		
		System.out.println("\n");
		
		EncrypDES de2 = new EncrypDES();
		String msg2 ="user:admin@!$datetime:2013年10月16日 11:12:37";
		byte[] encontent2 = de2.Encrytor(msg2);
		
		String str = "";
		for (int i = 0; i < encontent2.length; i++) {
			str += encontent2[i] + CommonConst.comb_seperator;
		}
		
		System.out.println(str);
		
		String[] strs = str.split(CommonConst.comb_unseperator);
		byte[] tmp = new byte[strs.length];
		for (int i = 0; i < strs.length; i++) {
			tmp[i] = Byte.parseByte(strs[i]);
		}
		
		byte[] decontent2 = de2.Decryptor(tmp);
		System.out.println("key2:" + deskey);
		System.out.println("明文是:" + msg2);
		System.out.println("加密后:" + new String(encontent2));
		System.out.println("解密后:" + new String(decontent2));
	}

}

