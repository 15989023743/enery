package com.gdwz.system.web.controller.manager.schedule;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.StdSchedulerFactory;

import com.gdwz.core.web.controller.SimpleController;

/**
 * 
 * @author jerry
 *
 */
public abstract class ScheduleBaseController extends SimpleController {
	protected static final Log LOG = LogFactory.getLog(ScheduleBaseController.class);

	String jobName="";
	String jobGroup="";
	
	protected String triggerGroup = "";
	protected String description = new String();
	protected String triggerName = new String();
	protected String startTime = new String();
	protected Date startTimeAsDate = new Date();
	protected String stopTime = new String();
	protected Date stopTimeAsDate = new Date();
	
	public static final String CURRENT_SCHEDULER_PROP = "currentScheduler";

	public static Scheduler createSchedulerAndUpdateApplicationContext(HttpServletRequest request,String schedulerName) {
		  Scheduler currentScheduler = null;
		   try {
				if(schedulerName != null && schedulerName.length() > 0) {
					currentScheduler = new StdSchedulerFactory().getScheduler(schedulerName);
				}else {
					Collection<Scheduler> schedulers = SchedulerRepository.getInstance().lookupAll();
					if(schedulers!=null&&schedulers.size()>0){
						currentScheduler = schedulers.iterator().next();
					}else{
						currentScheduler = StdSchedulerFactory.getDefaultScheduler();
					}
				}
				request.getSession().getServletContext().setAttribute(CURRENT_SCHEDULER_PROP, currentScheduler);
		   } catch (SchedulerException e) {
			   LOG.error("Problem creating scheduler",e);
		   }
		
		return currentScheduler;
	}
	
	public static Scheduler getCurrentScheduler(HttpServletRequest request,String schedulerName)    {
		   Scheduler currentScheduler = null;// = ServicesFactory.getBean("quartzScheduler");//(Scheduler)request.getSession().getServletContext().getAttribute(CURRENT_SCHEDULER_PROP);
		   if (currentScheduler == null)   {
			   currentScheduler = createSchedulerAndUpdateApplicationContext(request,schedulerName);
		   }
		   return currentScheduler;
	   }

	public static Scheduler getCurrentScheduler(HttpServletRequest request)    {
		   return getCurrentScheduler(request,null);
    }

	
}
