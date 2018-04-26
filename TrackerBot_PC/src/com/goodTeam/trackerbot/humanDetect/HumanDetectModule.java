package com.goodTeam.trackerbot.humanDetect;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Arrays;
import java.util.LinkedList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

public class HumanDetectModule {
	
	private CascadeClassifier upperBodyCascade;
	private CascadeClassifier faceCascade;
	private CascadeClassifier lowerbodyCascade;
	private CascadeClassifier profilefaceCascade;
	private CascadeClassifier fullbodyCascade;
	private int absoluteFaceSize;
	
	public HumanDetectModule(){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		lowerbodyCascade = new CascadeClassifier("data/haarcascade_lowerbody.xml");
		faceCascade = new CascadeClassifier("data/haarcascade_frontalface_alt.xml");
		profilefaceCascade = new CascadeClassifier("data/haarcascade_profileface.xml");
		upperBodyCascade = new CascadeClassifier("data/haarcascade_upperbody.xml");
		fullbodyCascade = new CascadeClassifier("data/haarcascade_fullbody.xml");
		absoluteFaceSize = 0;
	}
	//인체 탐지
	public BufferedImage getHumanDetectResult(BufferedImage inputImage){
		Mat frame = bufferedImageToMat(inputImage);
		Mat grayFrame = new Mat();
		Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(grayFrame, grayFrame);
		
		// compute minimum face size (320% of the frame height, in our case)
		if (this.absoluteFaceSize == 0)
		{
			int height = grayFrame.rows();
			if (Math.round(height * 0.03f) > 0)
			{
				this.absoluteFaceSize = Math.round(height * 0.03f);
			}
		}		
		Rect[] bodysArray = null;
		// each rectangle in faces is a face: draw them!
		bodysArray = detectFace(grayFrame);
		drawRect(bodysArray, frame);
		
		bodysArray = detectUpper(grayFrame);
		drawRect(bodysArray, frame);
		
		bodysArray = detectLower(grayFrame);
		drawRect(bodysArray, frame);
		
		bodysArray = detectProfileFace(grayFrame);
		drawRect(bodysArray, frame);
		
		bodysArray = detectFullBody(grayFrame);
		drawRect(bodysArray, frame);
		
		
		return matToBufferedImage(frame);
	}
	//todo... 사각형의 빈도수를 계산해서 로봇이 가야할 방향 정하기
	private Rect[] analyzeRects(Rect[] rects){
		//Arrays.sort(rects, );
		return null;
	}
	
	private void drawRect(Rect[] bodysArray, Mat frame){
		for (int i = 0; i < bodysArray.length; i++){
			Imgproc.rectangle(frame, bodysArray[i].tl(), bodysArray[i].br(), new Scalar(0, 255, 0), 3);
		}
	}
	
	//detect human
	private Rect[] detectFace(Mat grayFrame){
		MatOfRect bodys = new MatOfRect();
		
		this.faceCascade.detectMultiScale(grayFrame, bodys, 1.1, 1, 0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());
		
		return bodys.toArray();
	}
	private Rect[] detectProfileFace(Mat grayFrame){
		MatOfRect bodys = new MatOfRect();
		
		this.profilefaceCascade.detectMultiScale(grayFrame, bodys, 1.1, 1, 0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());
		
		return bodys.toArray();
	}
	private Rect[] detectUpper(Mat grayFrame){
		MatOfRect bodys = new MatOfRect();
		
		this.upperBodyCascade.detectMultiScale(grayFrame, bodys, 1.1, 1, 0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());
		
		return bodys.toArray();
	}
	private Rect[] detectLower(Mat grayFrame){
		MatOfRect bodys = new MatOfRect();
		
		this.lowerbodyCascade.detectMultiScale(grayFrame, bodys, 1.1, 1, 0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());
		
		return bodys.toArray();
	}
	private Rect[] detectFullBody(Mat grayFrame){
		MatOfRect bodys = new MatOfRect();
		
		this.fullbodyCascade.detectMultiScale(grayFrame, bodys, 1.1, 1, 0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());
		
		return bodys.toArray();
	}
	
	
	//bufferedImage to mat
	private static Mat bufferedImageToMat(BufferedImage bi) {
		Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
		byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		mat.put(0, 0, data);
		return mat;
	}
	//mat -> bufferedImage
	private static BufferedImage matToBufferedImage(Mat original)
	{
		// init
		BufferedImage image = null;
		int width = original.width(), height = original.height(), channels = original.channels();
		byte[] sourcePixels = new byte[width * height * channels];
		original.get(0, 0, sourcePixels);
		
		if (original.channels() > 1)
		{
			image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		}
		else
		{
			image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		}
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
		
		return image;
	}
}
