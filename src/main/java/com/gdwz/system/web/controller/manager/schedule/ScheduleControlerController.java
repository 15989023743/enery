package com.gdwz.system.web.controller.manager.schedule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gdwz.core.enums.EAclLevel;
import com.gdwz.energy.schedule.quartz.form.ChooseSchedulerForm;
import com.gdwz.energy.schedule.quartz.form.JobDetailForm;
import com.gdwz.energy.schedule.quartz.form.ListenerDTO;
import com.gdwz.energy.schedule.quartz.form.SchedulerDTO;
import com.gdwz.utils.data.CalendarUtils;

/**
 * Process scheduler command, and populate schedule summary information 
 * @author jerry
 *
 */
@Controller
@RequestMapping(value="/manager/schedule/",description="调试器管理")
public class ScheduleControlerController extends ScheduleBaseController {

	@RequestMapping(value="chooseScheduler",description="",aclLevel=EAclLevel.SESSION)
	public ModelAndView execute(HttpServletRequest request,String command,String schedulerName,ChooseSchedulerForm scheduleInfo) {
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("command=" + command);
		}
		//获取当前选择的Scheduler
		Scheduler choosenScheduler = getCurrentScheduler(request,schedulerName);
		try {
			if(command!=null){
				if (command.equals("start")) {//启动
					if (choosenScheduler.isInStandbyMode()) {
						//choosenScheduler.standby();
						choosenScheduler.start();
						//SchedulerRepository.getInstance().bind(choosenScheduler);
						//choosenScheduler = createSchedulerAndUpdateApplicationContext(request,schedulerName);
					}
				} else if (command.equals("stop")) {//停止
					//choosenScheduler.shutdown();
					//choosenScheduler = StdSchedulerFactory.getDefaultScheduler();			
				} else if (command.equals("pause")) {//暂停
					choosenScheduler.standby();
				} else if (command.equals("waitAndStopScheduler")) {//停止（等待当前执行工作已经完成）
					//choosenScheduler.shutdown(true);
				} else if (command.equals("pauseAll")) {//暂停所有触发器
					choosenScheduler.pauseAll();
				} else if (command.equals("resumeAll")) {//恢复所有触发器
					choosenScheduler.resumeAll();
				}
			}
			//填充表单
			this.populateSchedulerForm(choosenScheduler, scheduleInfo);
		} catch (SchedulerException e) {
			LOG.error("error in Scheduler Controller,  command=:" + command, e);
		} catch (Exception e) {
			LOG.error("error in Scheduler Controller,  command=:" + command, e);
		}
		ModelAndView mv = createModeAndView();
		mv.addObject("entity", scheduleInfo);
		mv.setViewName("manager/schedule/chooseScheduler");
		return mv;
	}
	
	/**
	 * populate DTO with scheduler information summary.
	 * @param choosenScheduler
	 * @param form
	 * @throws Exception
	 */
	private void populateSchedulerForm(Scheduler choosenScheduler, ChooseSchedulerForm form) throws Exception {
	
		//获取所有调度器
		Collection<Scheduler> scheduleCollection =  new StdSchedulerFactory().getAllSchedulers();
		Iterator<Scheduler> itr = scheduleCollection.iterator();
	
		form.setSchedulers(new ArrayList<Scheduler>());
		try {
			form.setChoosenSchedulerName(choosenScheduler.getSchedulerName());//当前选择的调度器名称
			
			while (itr.hasNext()) {
				form.getSchedulers().add(itr.next());		
			}
		} catch (SchedulerException e) {
			throw new Exception(e);
		}
	
	
		SchedulerDTO schedForm = new SchedulerDTO();
		schedForm.setSchedulerName(choosenScheduler.getSchedulerName());
		schedForm.setNumJobsExecuted(String.valueOf(choosenScheduler.getMetaData().getNumberOfJobsExecuted()));
	
		if (choosenScheduler.getMetaData().isJobStoreSupportsPersistence()) {
			schedForm.setPersistenceType("database");
		} else {
			schedForm.setPersistenceType("memory");  // mp possible bugfix
		}
		schedForm.setRunningSince(CalendarUtils.datetimeToString(choosenScheduler.getMetaData().getRunningSince()));
		if (choosenScheduler.isShutdown()) {
			schedForm.setState("stopped");
		} else if (choosenScheduler.isInStandbyMode()) {
			schedForm.setState("paused");
		} else {
			schedForm.setState("started");
		}
		
		schedForm.setThreadPoolSize(String.valueOf(choosenScheduler.getMetaData().getThreadPoolSize()));
		schedForm.setVersion(choosenScheduler.getMetaData().getVersion());
		schedForm.setSummary(choosenScheduler.getMetaData().getSummary());
	
		List<JobExecutionContext> jobDetails = choosenScheduler.getCurrentlyExecutingJobs();
		form.setExecutingJobs(new ArrayList<JobDetailForm>());
		for (Iterator<JobExecutionContext> iter = jobDetails.iterator(); iter.hasNext();) {
			JobExecutionContext job = (JobExecutionContext) iter.next();
			JobDetail jobDetail = job.getJobDetail();
	
			JobDetailForm jobForm = new JobDetailForm();
			jobForm.setGroupName(jobDetail.getKey().getGroup());
			jobForm.setName(jobDetail.getKey().getName());
			jobForm.setDescription(jobDetail.getDescription());
			jobForm.setJobClass(jobDetail.getJobClass().getName());
			
			form.getExecutingJobs().add(jobForm);
		}
		
		//List<String> calendars = choosenScheduler.getCalendarNames();
	
		List<JobListener> jobListeners = choosenScheduler.getListenerManager().getJobListeners();//.getGlobalJobListeners();
		for (Iterator<JobListener> iter = jobListeners.iterator(); iter.hasNext();) {
			JobListener jobListener = (JobListener) iter.next();
			ListenerDTO listenerForm = new ListenerDTO();
			listenerForm.setName(jobListener.getName());
			listenerForm.setListenerClass(jobListener.getClass().getName());
			schedForm.getGlobalJobListeners().add(listenerForm);
		}
		
		
		// The section commented out below is not currently used, but may be used to show triggers that have been
		// added to jobs
		
		/* List triggerListeners = choosenScheduler.getGlobalTriggerListeners();
		for (Iterator iter = triggerListeners.iterator(); iter.hasNext();) {
			TriggerListener triggerListener = (TriggerListener) iter.next();
			ListenerForm listenerForm = new ListenerForm();
			listenerForm.setListenerName(triggerListener.getName());
			listenerForm.setListenerClass(triggerListener.getClass().getName());
			schedForm.getGlobalJobListeners().add(listenerForm);
		}
		
		Set jobListenerNames = choosenScheduler.getJobListenerNames();
		for (Iterator iter = jobListenerNames.iterator(); iter.hasNext();) {
			JobListener jobListener = choosenScheduler.getJobListener((String) iter.next());
			ListenerForm listenerForm = new ListenerForm();
			listenerForm.setListenerName(jobListener.getName());
			listenerForm.setListenerClass(jobListener.getClass().getName());
			schedForm.getRegisteredJobListeners().add(listenerForm);
		}
		
		Set triggerListenerNames = choosenScheduler.getTriggerListenerNames();
		for (Iterator iter = triggerListenerNames.iterator(); iter.hasNext();) {
			TriggerListener triggerListener = choosenScheduler.getTriggerListener((String) iter.next());
			ListenerForm listenerForm = new ListenerForm();
			listenerForm.setListenerName(triggerListener.getName());
			listenerForm.setListenerClass(triggerListener.getClass().getName());
			schedForm.getRegisteredTriggerListeners().add(listenerForm);
		}

		List schedulerListeners = choosenScheduler.getSchedulerListeners();
		for (Iterator iter = schedulerListeners.iterator(); iter.hasNext();) {
			SchedulerListener schedulerListener = (SchedulerListener) iter.next();
			ListenerForm listenerForm = new ListenerForm();
			listenerForm.setListenerClass(schedulerListener.getClass().getName());
			schedForm.getSchedulerListeners().add(listenerForm);
		}

		*/
		
		//TODO fix this
		form.setScheduler(schedForm);
	}
}