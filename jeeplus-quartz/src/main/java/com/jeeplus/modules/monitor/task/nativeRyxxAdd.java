package com.jeeplus.modules.monitor.task;

import com.jeeplus.modules.monitor.entity.Task;
import org.jeeplus.cmd.HttpTool;

/**
 * @author WangGuipin
 * @ClassName nativeRyxxAdd
 * @Description TODO
 * @create 2019-05-14 22:43
 * @desc 本地同步服务器数据
 **/
public class nativeRyxxAdd extends Task {

    @Override
    public void run() {
        /*
		本地同步服务器数据
		 */
        HttpTool.get("http://127.0.0.1:8005/api/Ryxx/nativeRyxxAdd",null);
    }
}
