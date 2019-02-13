package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

	/*
	 * @Scheduled(cron="0 * * 9 * ?") public void CronJob() { SimpleDateFormat sdf =
	 * new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); Date now = new Date();
	 * String strDate = sdf.format(now);
	 * System.out.println("Java cron job expression:: " + strDate); }
	 */
	
	/**
	 * Execute after every second doesn't wait for previous task to complete
	 */
	/*
	 * @Scheduled(fixedRate=1000) public void fixedCronDelayjob() { SimpleDateFormat
	 * sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); Date now = new Date();
	 * String strDate = sdf.format(now);
	 * System.out.println("Fixed Rate Scheduler:: " + strDate); }
	 */
	
	/**
	 * Execute after initial delay of 5 sec from startup and then execute after every 3 sec 
	 * wait for previous task to get completed.
	 */
	@Scheduled(fixedDelay=3000,initialDelay=5000)
	public void fixedDelaySch() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();
	    String strDate = sdf.format(now);
	    System.out.println("Fixed Delay Scheduler:: " + strDate);
	}
}
