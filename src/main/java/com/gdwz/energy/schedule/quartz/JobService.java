package com.gdwz.energy.schedule.quartz;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gdwz.energy.schedule.quartz.form.JobDetailForm;
import com.gdwz.energy.schedule.quartz.form.TriggerForm;
import com.gdwz.utils.data.CalendarUtils;

@Repository("jobService")  
public class JobService {  
  
    @Autowired(required=false)
    private Scheduler scheduler;
      
    public List<JobDetailForm> getJobsInfo(){
        try {  
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();  
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);  
              
            List<JobDetailForm> jobDetails = new ArrayList<JobDetailForm>();  
              
            for (JobKey key : jobKeys) {
            	JobDetail jobDetail  = scheduler.getJobDetail(key);
            	JobDetailForm jobDetailForm = new JobDetailForm();
            	jobDetailForm.setName(jobDetail.getKey().getName());
            	jobDetailForm.setGroupName(jobDetail.getKey().getGroup());
            	jobDetailForm.setDescription(jobDetail.getDescription());
            	jobDetailForm.setJobClass(jobDetail.getJobClass().getName());
            	jobDetailForm.setDurable(jobDetail.isDurable());//是否持久化
            	jobDetailForm.setRequestsRecovery(jobDetail.requestsRecovery());//是否可恢复
            	jobDetailForm.setNonconcurrent(jobDetail.isConcurrentExectionDisallowed());//非并发执行
            	jobDetailForm.setPersistJobDataAfterExecution(jobDetail.isPersistJobDataAfterExecution());//
            	
                jobDetails.add(jobDetailForm);
            }
            return jobDetails;  
        } catch (SchedulerException e) {  
            e.printStackTrace();  
        }  
           
         return null;  
    }  
      
    //获取所有的触发器  
    public List<TriggerForm> getTriggersInfo(){  
        try {  
            GroupMatcher<TriggerKey> matcher = GroupMatcher.anyTriggerGroup();  
            Set<TriggerKey> Keys = scheduler.getTriggerKeys(matcher);  
            List<TriggerForm> triggers = new ArrayList<TriggerForm>();  
              
            for (TriggerKey key : Keys) {
                Trigger trigger = scheduler.getTrigger(key);
                
                TriggerForm triggerForm = new TriggerForm();
                triggerForm.setTriggerName(trigger.getKey().getName());
                triggerForm.setTriggerGroup(trigger.getKey().getGroup());
                triggerForm.setTriggerState(scheduler.getTriggerState(key).name());
                triggerForm.setDescription(trigger.getDescription());
                
                triggerForm.setJobGroup(trigger.getJobKey().getGroup());
                triggerForm.setJobName(trigger.getJobKey().getName());
                
                triggerForm.setMisFireInstruction(trigger.getMisfireInstruction());
                triggerForm.setDescription(trigger.getDescription());  

                triggerForm.setStartTime(CalendarUtils.datetimeToString(trigger.getStartTime()));
                triggerForm.setStopTime(CalendarUtils.datetimeToString(trigger.getEndTime()));
                triggerForm.setNextFireTime(CalendarUtils.datetimeToString(trigger.getNextFireTime()));
				triggerForm.setPreviousFireTime(CalendarUtils.datetimeToString(trigger.getPreviousFireTime()));
				triggerForm.setFinalFireTime(CalendarUtils.datetimeToString(trigger.getFinalFireTime()));
				
                if (trigger instanceof SimpleTrigger) {
    				triggerForm.setType("simple");
                    SimpleTrigger simple = (SimpleTrigger) trigger;  
                    triggerForm.setExpression("重复次数:"+ (simple.getRepeatCount() == -1 ?   
                            "无限" : simple.getRepeatCount()) +",重复间隔:"+(simple.getRepeatInterval()/1000L));
                }
                else if (trigger instanceof CronTrigger) {
                    CronTrigger cron = (CronTrigger) trigger;
    				triggerForm.setType("cron");
                    triggerForm.setExpression(cron.getCronExpression());
                }
                triggers.add(triggerForm);
            }  
            return triggers;  
        } catch (SchedulerException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
    //暂停触发器
    public void pauseTrigger(String name, String group){
        TriggerKey triggerKey = TriggerKey.triggerKey(name, group);   
        try {  
            scheduler.pauseTrigger(triggerKey);
        } catch (SchedulerException e) {  
            e.printStackTrace();  
        }  
    }
    
    //恢复触发器 
    public void resumeTrigger(String name, String group){
        TriggerKey triggerKey = TriggerKey.triggerKey(name, group); 
        try {  
            scheduler.resumeTrigger(triggerKey);
        } catch (SchedulerException e) {  
            e.printStackTrace();  
        }  
    }
    
    //暂停任务  
    public void pauseJob(String name, String group){
        JobKey key = JobKey.jobKey(name, group); 
        try {  
            scheduler.pauseJob(key);  
        } catch (SchedulerException e) {  
            e.printStackTrace();  
        }  
    }
      
    //恢复任务  
    public void resumeJob(String name, String group){  
        JobKey key = JobKey.jobKey(name, group); 
        try {  
            scheduler.resumeJob(key);  
        } catch (SchedulerException e) {  
            e.printStackTrace();  
        }  
    }  
      
    //立马执行一次任务  
    public void startNowJob(String name, String group){  
        try {  
            JobKey key = JobKey.jobKey(name, group);
            JobDetail job = JobBuilder.newJob(scheduler.getJobDetail(key).getJobClass())  
                    .requestRecovery()
                    .storeDurably()
                    .build();
            scheduler.addJob(job, false);
            scheduler.triggerJob(job.getKey());
        } catch (SchedulerException e) {
            e.printStackTrace();  
        }  
    }  
    
    //删除触发器 
    public void delTrigger(String name, String group){  
    	TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
        try {  
            scheduler.unscheduleJob(triggerKey);
        } catch (SchedulerException e) {  
            e.printStackTrace();  
        }  
    }  
    
    //删除任务  
    public void delJob(String name, String group){  
        JobKey key = JobKey.jobKey(name, group);   
        try {  
            scheduler.deleteJob(key);  
        } catch (SchedulerException e) {  
            e.printStackTrace();  
        }  
    }  
      
    //修改触发器时间  
    public void modifyTrigger(String name,String group,String cron){  
        try {  
            TriggerKey key = TriggerKey.triggerKey(name, group);  
            Trigger trigger = scheduler.getTrigger(key);  
              
            CronTrigger newTrigger = (CronTrigger) TriggerBuilder.newTrigger()  
                    .withIdentity(key)  
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))  
                    .build();  
            scheduler.rescheduleJob(key, newTrigger);  
        } catch (SchedulerException e) {  
            e.printStackTrace();  
        }  
          
    }  
      
    //暂停调度器  
    public void stopScheduler(){  
         try {  
            if (!scheduler.isInStandbyMode()) {  
                scheduler.standby();  
            }  
        } catch (SchedulerException e) {  
            e.printStackTrace();  
        }  
    }  

    /**
     * 
     * @param jobDetail
     * @throws SchedulerException 
     */
    public void createJob(JobDetail jobDetail) throws SchedulerException{
		this.scheduler.addJob(jobDetail, true);
    }
    
    /**
     * 
     * @return
     */
    public Scheduler getCurrentScheduler(){
    	return this.scheduler;
    }
    
}