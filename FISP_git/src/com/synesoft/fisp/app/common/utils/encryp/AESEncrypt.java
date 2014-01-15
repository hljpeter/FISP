package com.synesoft.fisp.app.common.utils.encryp;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synesoft.fisp.app.common.constants.CommonConst;

/**
 * 
 * @file 	SHAEncrypt.java
 * @author 	michelle.wang
 * @date 	2013-12-5
 * @description TODO
 * @tag 	1.0.0
 *
 */
public class AESEncrypt {
	
	private static Logger logger = LoggerFactory.getLogger(AESEncrypt.class);
	
	/**
	 * 对加密内容进行加密
	 * @param content  加密内容
	 * @param pwd      加密密码
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static byte[] encrypt(String content,String pwd){
		logger.info("加密开始！");
		try {
			KeyGenerator kgen=KeyGenerator.getInstance("AES");
			kgen.init(128,new SecureRandom(pwd.getBytes()));
			//生成密钥
			SecretKey secretKey=kgen.generateKey();
			byte[] encodeFormat=secretKey.getEncoded();
			SecretKeySpec key=new SecretKeySpec(encodeFormat,"AES");
			//创建密码器
			Cipher cipher=Cipher.getInstance("AES");
			byte[] byteContent=content.getBytes(CommonConst.ENCODE);
			//加密方式初始化密码器
			cipher.init(cipher.ENCRYPT_MODE, key);
			//对加密内容进行加密
			byte[] result=cipher.doFinal(byteContent);
			return result;
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 对解密内容进行解密
	 * @param content 解密内容
	 * @param pwd     解密密码
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static byte[] decrypt(byte[] content,String pwd){
		logger.info("解密开始！");
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128,new SecureRandom(pwd.getBytes()));
			//生成密钥
			SecretKey secretKey=kgen.generateKey();
			byte[] encodeFormat=secretKey.getEncoded();
			SecretKeySpec key=new SecretKeySpec(encodeFormat,"AES");
			//创建密码器
			Cipher cipher= Cipher.getInstance("AES");
			//解密方式初始化密码器
			cipher.init(cipher.DECRYPT_MODE, key);
			//对解密内容进行解密
			byte[] result= cipher.doFinal(content);
			return result;
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String args[]){
		String content="admin";
		String password="1234567";
		//加密
		System.out.println("加密前内容:"+content);
		byte[] encryptResult=encrypt(content,password);
		System.out.println("加密后内容："+encryptResult);
		//解密
		System.out.println("解密前内容:"+encryptResult);
		byte[] decryptResult=decrypt(encryptResult,password);
		System.out.println("解密后内容："+new String(decryptResult));
	}
}
