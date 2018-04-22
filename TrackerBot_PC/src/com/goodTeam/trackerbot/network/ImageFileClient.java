package com.goodTeam.trackerbot.network;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

/*
 * 라즈베리파이의 카메라 이미지를 받는 클래스
 */
public class ImageFileClient {
	final public static String BOT_ADDRESS = "168.131.151.110";
	final public static int BOT_PORT = 5555;
	final public static String CARMERA_IMG_PATH = "data/carmera.jpg";

	private Socket clientSocket;

	public BufferedImage getImage()
	{
		try {
			clientSocket = new Socket(BOT_ADDRESS, BOT_PORT);
			
			InputStream in = clientSocket.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(in);
			FileOutputStream fos = new FileOutputStream(CARMERA_IMG_PATH);

			// 파일 읽기
			int input;

			while ((input = bis.read()) != -1) {
				fos.write(input);
			}
			fos.close();
			in.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedImage ret = null;
		try {
			ret = ImageIO.read(new File(CARMERA_IMG_PATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
}
