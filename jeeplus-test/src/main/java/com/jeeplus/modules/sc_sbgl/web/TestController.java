package com.jeeplus.modules.sc_sbgl.web;

import com.jeeplus.modules.sc_sbgl.service.ScsbglService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangGuipin
 * @ClassName TestController
 * @Description TODO
 * @create 2019-06-03 9:55
 * @desc Test
 **/

@RestController
@RequestMapping("TestController")
public class TestController {
    @Autowired
    private ScsbglService scsbglService;

    @RequestMapping("Test")
    public int Test(){
        String sid = "111101";
       int count = scsbglService.selectSbCount(sid);
             return count;
    }
}
