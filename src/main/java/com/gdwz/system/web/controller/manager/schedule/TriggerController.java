package com.gdwz.system.web.controller.manager.schedule;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdwz.core.enums.EAclLevel;
import com.gdwz.energy.schedule.quartz.JobService;
import com.gdwz.energy.schedule.quartz.form.TriggerForm;

@Controller
@RequestMapping(value="/manager/schedule/trigger/",description="触发器管理")
public class TriggerController {  
   
    @Autowired  
    private JobService jobService;  
    @RequestMapping(value="list",description="查询列表",aclLevel=EAclLevel.RESTRICTE)
    public String show(HttpServletRequest request) {
        request.setAttribute("triggers", jobService.getTriggersInfo());  
        
        /*
		try {
			Iterator<Map<String, String>> iterSlabs = ServicesFactory
					.getBean(MemcachedManager.class).getMemCachedClient()
					.getStatsByItem("items").values().iterator();
			Set<String> set = new HashSet<String>();
			while (iterSlabs.hasNext()) {
				Map<String, String> slab = iterSlabs.next();
				for (String key : slab.keySet()) {
					String index = key.split(":")[1];
					set.add(index);
				}
			}
			//统计
			List<String> list = new LinkedList<String>();
			for (String v : set) {
				String commond = " cachedump 20 0 ";
				System.out.println(commond);
				Iterator<Map<String, String>> iterItems = ServicesFactory
						.getBean(MemcachedManager.class).getMemCachedClient()
						.getStatsByItem(commond,2000).values().iterator();
				while (iterItems.hasNext()) {
					Map<String, String> items = iterItems.next();
					list.addAll(items.keySet());
					for(String key:items.keySet()){
						System.out.println(key);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}*/
		return "manager/schedule/trigger/list";
    }  
      
    @RequestMapping("/{name}/{group}/pause")
    public String pause(@PathVariable String name,@PathVariable String group){  
        jobService.pauseTrigger(name, group);
        return "redirect:/manager/schedule/trigger/list.htm";  
    }  
  
    @RequestMapping("/{name}/{group}/del")
    public String del(@PathVariable String name, @PathVariable String group){  
        jobService.delTrigger(name, group);
        return "redirect:/manager/schedule/trigger/list.htm";  
    }  
  
    @RequestMapping("/{name}/{group}/{cron}/modify")
    public String modify(@PathVariable String name, @PathVariable String group ,@PathVariable String cron){  
        jobService.modifyTrigger(name, group, cron);  
        return "redirect:/manager/schedule/trigger/list.htm";  
    }
      
    @RequestMapping("/{name}/{group}/resume")  
    public String resume(@PathVariable String name, @PathVariable String group){  
        jobService.resumeTrigger(name, group) ;
        return "redirect:/manager/schedule/trigger/list.htm";  
    }  
    
    /*
    public void getTriggers(HttpServletRequest request){  
        List<TriggerForm> triggers = jobService.getTriggersInfo();  
        request.setAttribute("triggers", triggers);  
    }*/
      
      
}