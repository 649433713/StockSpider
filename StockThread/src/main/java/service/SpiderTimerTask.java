package service;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.ApplicationContext;

public class SpiderTimerTask implements Runnable{

	
	private SpiderRunnable spiderRunnable;
	
	public SpiderTimerTask(SpiderRunnable bean) {
		spiderRunnable = bean;
	}

	@Override
	public void run() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(spiderRunnable, 0, 10000);
	}
}
