package com.goodTeam.trackerbot.network;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

/*
 * 라즈베리파이의 카메라 이미지를 받는 클래스
 */
public class ImageFileClient {
	final public static String BOT_ADDRESS = "168.131.151.110";
	final public static int BOT_PORT = 5555;
	final public static String CARMERA_IMG_PATH = "data/carmera.jpg";
	InputStream inputStream;
	BufferedInputStream bufferInput;
	FileOutputStream fileOutput;
	byte[] imageSize = new byte[6];
	byte[] buff = new byte[6000];
	
	OutputStream outputStream;
	BufferedOutputStream bufferOutput;

	private Socket clientSocket;
	
	public void startConnetion(){
		try {
			clientSocket = new Socket(BOT_ADDRESS, BOT_PORT);
			inputStream = clientSocket.getInputStream();
			bufferInput = new BufferedInputStream(inputStream);
			
			outputStream = clientSocket.getOutputStream();
			bufferOutput = new BufferedOutputStream(outputStream);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BufferedImage getImage()
	{
		try {
			bufferOutput.write(new byte[4]);
			bufferOutput.flush();
			
			fileOutput = new FileOutputStream(CARMERA_IMG_PATH);
			// 파일 읽기
			int input;
			bufferInput.read(imageSize);
			int size = Integer.parseInt(new String(imageSize,"utf-8"));
			System.out.println(size);
			
			
			while(size > 0){
				int inputSize = bufferInput.read(buff);
				fileOutput.write(buff, 0, inputSize);
				size = size - inputSize;
			}
			fileOutput.close();
			
			//while ((input = bufferInput.read()) != -1) {
				//fileOutput.write(input);
			//}
			//fileOutput.close();
		
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
	public void shutDownConnection(){
		try {
			inputStream.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
