package com.jeeplus.modules.api.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jeeplus.modules.api.bean.ApiPage;
import com.jeeplus.modules.api.bean.ApiResult;
import com.jeeplus.modules.jcda_ygda.entity.EmployeeInfo;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.api.bean.JcdasbkqxxBean;
import com.jeeplus.modules.jcda_sbkqxx.entity.Jcdasbkqxx;
import com.jeeplus.modules.jcda_sbkqxx.service.JcdasbkqxxService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@RestController
@RequestMapping("api/CloudDate")
public class CloudDateController extends BaseController{
	/**
	 * 同步到云服务器设备考勤信息数据
	 */
	@Autowired
	private JcdasbkqxxService jcdasbkqxxService;
	
	
	@RequestMapping(value = "cloudData",method=RequestMethod.GET)
	public Map<String, Object> cloudData(Model model) throws Exception {
		String str = "false";
		List<JcdasbkqxxBean> strData = Lists.newArrayList();
		Map<String,Object> map = Maps.newHashMap();
		try {
			strData = jcdasbkqxxService.findAtendance();//.toString();
		    map.put("data",strData);
		   
		    	
		    String jsonArray = JSON.toJSONString(strData);
		    System.out.println(jsonArray);
		    /*
		     * 调用云服务接口
		     */
		    String returnStr = sendHttpPost("http://114.55.130.67:8080/qigukeji/api/CloudDate/AttendanceInformation",jsonArray);
		    
		    /*
		     * 取出状态信息
		     */
		    JSONObject jsonObject2=JSONObject.fromObject(returnStr);
		    str = jsonObject2.getString("success");
		    return map;
		} catch(Exception e){
			str = "false";
			return map;
		}finally {
			/*
			 * 修改考勤数据状态
			 */
		    if(str == "true") {
		    	strData.forEach(e->{
		    		jcdasbkqxxService.updateSbkState(e.getId());
		    	});
		    	
		    }
		}
	    //System.out.println("+++++我是返回值"+returnStr);
		
	}

	/**
	 * 云服务器对外提供接口，数据同步接口
	 */
	@ResponseBody
	@RequestMapping(value = "AttendanceInformation",method=RequestMethod.POST)
	public AjaxJson AttendanceInformation(HttpServletRequest requet, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		requet.setCharacterEncoding("UTF-8");
		String json = getBody(requet);
		System.out.println(json);
		//使用JSONArray
		JSONArray jsonArray=JSONArray.fromObject(json);
		try {
			for(int i =0;i<jsonArray.size();i++) {
				
				//获得jsonArray的第一个元素
		        Object o=jsonArray.get(i);
		        JSONObject jsonObject2=JSONObject.fromObject(o);
		        Jcdasbkqxx jcdasbkqxx = (Jcdasbkqxx) JSONObject.toBean(jsonObject2, Jcdasbkqxx.class);
		        jcdasbkqxx.setId(null);
		        
				//新增或编辑表单保存
				jcdasbkqxxService.save(jcdasbkqxx);//保存
				j.setSuccess(true);
				j.setMsg("保存设备考勤信息成功");
			}
		} catch (Exception e) {
			j.setSuccess(false);
		}
		return j;
	}
	
	/*
	 * 对外提供App数据调用接口
	 */

	@ResponseBody
	@RequestMapping(value ="AppSbkqData",method=RequestMethod.POST)
	public ApiResult <ApiPage<JcdasbkqxxBean>>AppSbkqData(String sb_id, String code, Date beginDate, Date endDate,Integer page,Integer pageSize){
		ApiResult apiResult  = new ApiResult();
		if(page==null||page<1) {
			page=1;
		}
		if(pageSize==null || pageSize <1) {
			pageSize = 10;
		}
		ApiPage<JcdasbkqxxBean> apiPage = new ApiPage<>();

		try {
			int rows = jcdasbkqxxService.rowCount();
			logger.debug("我是总条数"+rows);
			apiPage.setPage(page).setTotal(rows%pageSize==0?rows/pageSize:rows/pageSize+1).setRows(rows);
			if(rows==0) {
				apiPage.setData(new ArrayList<>());
			}else {
				List<JcdasbkqxxBean> AppSbkq = jcdasbkqxxService.findApp(sb_id,code,beginDate,endDate,page,pageSize);
				apiPage.setData(AppSbkq);
				apiResult.setSuccess(true).setContent(apiPage).setCode(0).setMessage("success");
			}
			return apiResult;
		}catch (Exception e){
			apiResult.setSuccess(false);
			apiResult.setCode(-1);
			apiResult.setMessage("请求异常");
			return apiResult;
		}
	}
	/*
	 * 接口post请求 body中传输数据
	 */
	public static String sendHttpPost(String url, String body) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/json");
		httpPost.setEntity(new StringEntity(body,"UTF-8"));

		CloseableHttpResponse response = httpClient.execute(httpPost);
		System.out.println(response.getStatusLine().getStatusCode() + "\n");
		HttpEntity entity = response.getEntity();
		String responseContent = EntityUtils.toString(entity, "UTF-8"); 
		System.out.println(responseContent);

		response.close();
		httpClient.close();
		return responseContent;
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
