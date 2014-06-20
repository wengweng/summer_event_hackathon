package com.icesabi.android_hackathon;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Application;

import com.icesabi.android_hackathon.ui.fragment.BuddiesFragment;

import domain.MessageAnswer;
import domain.MessageQuestion;

public class MainApplication extends Application {
	private Thread serverThread;
	private Thread clientThread;
	private boolean waitingForQuestion = true;
	private boolean isClient = false;
	private Socket client;
	private Socket server;
	public BuddiesFragment fragment;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		System.out.println("app started");
		
		serverThread = new Thread(new Runnable() {
			private boolean run = true;
			
			@Override
			public void run() {
				try {
					ServerSocket socket = new ServerSocket(8888);
					server = socket.accept();
					server.setKeepAlive(true);
					
					while(run) {
						ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
						processMessage(ois);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}


		});
		
		serverThread.start();
	}
	
	public void connectToServer(final InetAddress host) {
		isClient = true;
		waitingForQuestion = false;
		client = new Socket();
		
		clientThread = new Thread(new Runnable() {
			private boolean run = true;
			
			@Override
			public void run() {
				try {
					client.bind(null);
					client.connect((new InetSocketAddress(host, 8888)), 500);
					client.setKeepAlive(true);
					
					while(run) {
						ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
						processMessage(ois);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}


		});
		clientThread.start();

	}
	
	private void processMessage(ObjectInputStream ois)
			throws OptionalDataException, IOException {
		try {
			Object message = ois.readObject();
			
			if(waitingForQuestion) {
				MessageQuestion mq = (MessageQuestion) message;
				fragment.onMessageQuestion(mq);
			}else {
				MessageAnswer ma = (MessageAnswer) message;
				fragment.onMessageAnswer(ma);
			}
			
			waitingForQuestion = !waitingForQuestion;
			
			System.out.println("message processed");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void sendQuestion(String question) {
		MessageQuestion message = new MessageQuestion();
		message.question = question;
		
		send(message);
	}
	
	public void sendAnswer(boolean answer) {
		MessageAnswer message = new MessageAnswer();
		message.answer = answer;
		send(message);
	}
	
	private void send(final Object object) {
		new Thread(new Runnable() {
			ObjectOutputStream oos;
			@Override
			public void run() {
				try {
					oos = new ObjectOutputStream((isClient? client: server).getOutputStream());
					oos.writeObject(object);
					System.out.println("message sent");
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}).start();	

	}
}
