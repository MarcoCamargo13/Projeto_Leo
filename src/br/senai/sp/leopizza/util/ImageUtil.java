package br.senai.sp.leopizza.util;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageUtil {
	public static ImageIcon redimensiona(ImageIcon iconOriginal, int largura, int altura) {
		// Obter a Image do ImageIcon
		Image imgOriginal = iconOriginal.getImage();
		// Criar a Image redimensionada a partir do imgOriginal
		Image redimensionada = imgOriginal.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
		// Retorna um ImageIcon
		return new ImageIcon(redimensionada);
	}
}
