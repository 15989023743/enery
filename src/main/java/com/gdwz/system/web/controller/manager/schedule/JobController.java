package com.gdwz.system.web.controller.manager.schedule;

import javax.servlet.http.HttpServletRequest;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.web.controller.SimpleController;
import com.gdwz.energy.schedule.quartz.JobService;

@Controller
@RequestMapping(value="/manager/schedule/jobdetail/",description="触发器管理")
public class JobController extends SimpleController {  
      
    private boolean init = false;  
      
    @Autowired  
    private JobService jobService;  
    @RequestMapping(value="list",description="查询列表",aclLevel=EAclLevel.RESTRICTE)
    public String show(HttpServletRequest request){
        if (!init) {
            //jobService.stopScheduler();  
        }
        request.setAttribute("triggers", jobService.getJobsInfo());  
        return "manager/schedule/jobdetail/list";
    }  
    //暂停
    @RequestMapping("/{name}/{group}/pause")
    public String pause(@PathVariable String name,@PathVariable String group){  
        jobService.pauseJob(name, group);
        return "redirect:/manager/schedule/jobdetail/list.htm";  
    }  
  
    //删除
    @RequestMapping("/{name}/{group}/del")
    public String del(@PathVariable String name, @PathVariable String group){  
        jobService.delJob(name, group);
        return "redirect:/manager/schedule/jobdetail/list.htm";  
    }  
  
    @RequestMapping("/{name}/{group}/{cron}/modify")
    public String modify(@PathVariable String name, @PathVariable String group ,@PathVariable String cron){  
        jobService.modifyTrigger(name, group, cron);  
        return "redirect:/manager/schedule/jobdetail/list.htm";  
    }  
  
    //立即触发
    @RequestMapping("/{name}/{group}/startNow")  
    public String stratNow(@PathVariable String name, @PathVariable String group){  
        jobService.startNowJob(name, group);  
        return "redirect:/manager/schedule/jobdetail/list.htm";  
    }  
    //恢复触发
    @RequestMapping("/{name}/{group}/resume")  
    public String resume(@PathVariable String name, @PathVariable String group){  
        jobService.resumeJob(name, group);
        return "redirect:/manager/schedule/jobdetail/list.htm";  
    }
    
    private void onEntity(String jobName,String jobGroup,HttpServletRequest request){
    	try {
    		JobDetail entity;
    		if (jobName!=null && jobGroup!=null &&jobName.length() > 0 && jobGroup.length() > 0) {
    			entity = jobService.getCurrentScheduler().getJobDetail(JobKey.jobKey(jobName, jobGroup));
    		}else{
    			entity = new JobDetailImpl();
    		}
    		request.setAttribute(ENTITY_ATTRIBUTE_NAME, entity);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }
    
    @RequestMapping(value="create",aclLevel=EAclLevel.RESTRICTE)
    public String createJob(String jobName,String jobGroup,HttpServletRequest request){
    	this.onEntity(jobName, jobGroup, request);
    	return "manager/schedule/jobdetail/input";
    }
    
    @RequestMapping(value="update",aclLevel=EAclLevel.RESTRICTE)
    public String editJob(String jobName,String jobGroup,HttpServletRequest request){
    	this.onEntity(jobName, jobGroup, request);
    	return "manager/schedule/jobdetail/input";
    }
    
    @RequestMapping(value="save",aclLevel=EAclLevel.RESTRICTE)
    public String saveJob(JobDetailImpl jobDetail,String className,String parameterNames[],String parameterValues[],HttpServletRequest request){
    	try {
    		
			jobDetail.setJobClass((Class<? extends Job>)Class.forName(className));
    		
			for (int i = 0; i < parameterNames.length; i++) {
				if (parameterNames[i].trim().length() > 0
						&& parameterValues[i].trim().length() > 0) {
					jobDetail.getJobDataMap().put(parameterNames[i].trim(),
							parameterValues[i].trim());
				}
			}
			jobService.createJob(jobDetail);

		} catch (ClassNotFoundException e) {
			request.setAttribute(MESSAGE_NAME, className + " class is not found");
			request.setAttribute(ENTITY_ATTRIBUTE_NAME, jobDetail);
			return "manager/schedule/jobdetail/input";
		} catch (SchedulerException e) {
			request.setAttribute(MESSAGE_NAME, e.getMessage());
			request.setAttribute(ENTITY_ATTRIBUTE_NAME, jobDetail);
			return "manager/schedule/jobdetail/input";
		}
    	return this.doReturn(request);
    }
    
	@RequestMapping("return")
	public String doReturn(HttpServletRequest request){
		return getRedirectReturnUrl(request);
	}
    
    
}