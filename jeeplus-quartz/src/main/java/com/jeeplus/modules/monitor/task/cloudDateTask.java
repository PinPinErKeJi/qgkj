package com.jeeplus.modules.monitor.task;
import java.util.HashMap;
import java.util.Map;

import org.jeeplus.cmd.HttpTool;
import org.quartz.DisallowConcurrentExecution;
import com.jeeplus.modules.monitor.entity.Task;
@DisallowConcurrentExecution
public class cloudDateTask extends Task{
	/**
	 * 云服务器同步设备考勤信息数据
	 */
	@Override
	public void run() {
		/**
		 * 同步到云服务器设备考勤信息数据
		 */
		HttpTool.get("http://127.0.0.1:8005/api/CloudDate/cloudData",null);

	}

	
	
}
