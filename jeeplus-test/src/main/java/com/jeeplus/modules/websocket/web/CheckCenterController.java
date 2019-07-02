package com.jeeplus.modules.websocket.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.api.bean.ApiResult;
import com.jeeplus.modules.websocket.entiry.ApiReturnObject;
import com.jeeplus.modules.websocket.websocket.WebSocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author WangGuipin
 * @ClassName CheckCenterController
 * @Description TODO
 * @create 2019-05-21 11:44
 * @desc 消息推送
 **/
@Controller
@RequestMapping("CheckCenterController")
public class CheckCenterController {

    //页面请求
    //页面请求
    @RequestMapping("/socket/{cid}")
    public String  socket(@PathVariable String cid) {
        ModelAndView mav=new ModelAndView("/socket");
        mav.addObject("cid", cid);
        return "index";
    }


    //推送数据接口
    @ResponseBody
    @RequestMapping("/socket/push")
    public ApiResult<ApiReturnObject> pushToWeb(String cid, HttpServletRequest request) {
        ApiResult apiResult = new ApiResult();
        try {

            String str = getBody(request);
            JSONObject jsondata= JSON.parseObject(str);
            //System.out.println(jsondata);
            WebSocketServer.sendInfo(jsondata.toString(),cid);
            apiResult.setCode(0);
            apiResult.setSuccess(true);
            apiResult.setMessage("success");
            apiResult.setContent(str);
            return apiResult;
        } catch (IOException e) {
            e.printStackTrace();
            apiResult.setCode(-1);
            apiResult.setSuccess(false);
            apiResult.setMessage("请求异常");
            apiResult.setContent(cid);
            return apiResult;
        }
    }

    /**
     * 获取request中的数据
     * @param request
     * @return
     */
    public static String getBody(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            String line = null;
            InputStream inputStream;
            inputStream = request.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            //reader = request.getReader();
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
