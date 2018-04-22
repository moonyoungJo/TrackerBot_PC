package com.goodTeam.trackerbot.humanDetect;

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
		while(true){
			imageViewer.setImage(humanDetectModule.getHumanDetectResult(imageFileClient.getImage()));
		}
	}

}
