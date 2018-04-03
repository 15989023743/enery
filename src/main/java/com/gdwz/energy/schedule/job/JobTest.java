package com.gdwz.energy.schedule.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

@PersistJobDataAfterExecution//执行后保存相关参数
@DisallowConcurrentExecution//不允许并发执行（单线程）
public class JobTest implements Job {
	private static int index = 0;
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		index++;
		System.out.println(Thread.currentThread().getId()+"start:"+context.getTrigger().getKey().getName()+index);
		System.out.println(Thread.currentThread().getId()+"stop:"+context.getTrigger().getKey().getName()+index);
	}

}
