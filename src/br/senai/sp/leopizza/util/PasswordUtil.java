package br.senai.sp.leopizza.util;

import java.security.MessageDigest;

public class PasswordUtil {
	public static String criptografa256(String senha) {
		String criptografada = "";
		try {
			MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algoritmo.digest(senha.getBytes("UTF-8"));
			for (byte b : messageDigest) {
				criptografada += String.format("%02X", 0xFF & b);
			}
			return criptografada;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
