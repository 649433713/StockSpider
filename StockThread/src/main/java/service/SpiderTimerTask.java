package service;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.ApplicationContext;

/**
 * @author 凡
 * 定时任务
 */
public class SpiderTimerTask implements Runnable{

	
	private SpiderRunnable spiderRunnable;
	
	public SpiderTimerTask(SpiderRunnable bean) {
		spiderRunnable = bean;
	}

	@Override
	public void run() {
		Timer timer = new Timer();
		
		//每隔10s更新一次
		timer.scheduleAtFixedRate(spiderRunnable, 0, 10000); 
	}
}
