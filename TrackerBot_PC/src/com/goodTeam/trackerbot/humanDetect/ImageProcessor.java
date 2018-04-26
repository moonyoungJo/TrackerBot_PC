package com.goodTeam.trackerbot.humanDetect;

import java.awt.image.BufferedImage;

import com.goodTeam.trackerbot.gui.ImageViewer;
import com.goodTeam.trackerbot.network.ImageFileClient;

/*
 * 영상처리를 하고 ImageViewer로 처리 결과를 출력하는 클래스
 */
public class ImageProcessor implements Runnable {

	private ImageFileClient imageFileClient;	//통신을 위한 클래스
	private HumanDetectModule humanDetectModule;	
	private ImageViewer imageViewer;
	
	public ImageProcessor(ImageViewer imageViewer){
		this.imageViewer = imageViewer;
		imageFileClient = new ImageFileClient();
		humanDetectModule = new HumanDetectModule();
	}
	@Override
	public void run() {
		BufferedImage img = null;
		imageFileClient.startConnetion();
		
		imageFileClient.requestImg();
		imageFileClient.receiveImg();
		img = imageFileClient.readImg();
		
		while(true){
			System.out.println("요청");
			imageFileClient.requestImg();
			imageViewer.setImage(humanDetectModule.getHumanDetectResult(img));
			System.out.println("처리끝");
			imageFileClient.receiveImg();
			System.out.println("받음");
			img = imageFileClient.readImg();
		}
	}

}
