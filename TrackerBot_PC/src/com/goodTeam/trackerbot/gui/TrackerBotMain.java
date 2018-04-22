package com.goodTeam.trackerbot.gui;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.goodTeam.trackerbot.humanDetect.ImageProcessor;

/*
 *  트래커봇(라즈베리파이)를 먼저 실행시키고 프로그램을 실행시킬 것
 */
public class TrackerBotMain extends JFrame {
	private JPanel contentPane;
	private ImageViewer imageViewer;
	private ImageProcessor imageProcessor;
	private Thread imageProcessorThread;
	
	public TrackerBotMain() {
		// gui 창 종료시 이벤트
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				imageProcessorThread.interrupt();
				System.exit(0);
			}
		});
		
		//프레임 설정
		setTitle("TrackerBot");
		contentPane = new JPanel();
		setContentPane(contentPane);
		setSize(1024, 800);
		
		//패널
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		
		//이미지 뷰어
		imageViewer = new ImageViewer();
		contentPane.add(imageViewer, BorderLayout.CENTER);

		//이미지처리
		imageProcessor = new ImageProcessor(imageViewer);		
		
		setVisible(true);
	}
	public void startDetection(){
		imageProcessorThread = new Thread(imageProcessor);
		imageProcessorThread.start();
	}
	public static void main(String[] args){
		TrackerBotMain frame = new TrackerBotMain();
		frame.startDetection();
	}

}
