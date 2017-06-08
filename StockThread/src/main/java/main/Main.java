package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import service.SpiderRunnable;
import service.SpiderTimerTask;

public class Main {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-context.xml");
		for (int i = 1; i < 35; i++) {
			Thread thread = new Thread(new SpiderTimerTask(applicationContext.getBean(SpiderRunnable.class, i)));
			thread.start();
		}
	}
}
