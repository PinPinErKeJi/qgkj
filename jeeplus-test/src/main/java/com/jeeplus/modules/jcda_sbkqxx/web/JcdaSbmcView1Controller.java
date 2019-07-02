/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbkqxx.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.jcda_sbkqxx.entity.JcdaSbmcView1;
import com.jeeplus.modules.jcda_sbkqxx.service.JcdaSbmcView1Service;

/**
 * 设备考勤信息Controller
 * @author ww
 * @version 2019-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/jcda_sbkqxx/jcdaSbmcView1")
public class JcdaSbmcView1Controller extends BaseController {

	@Autowired
	private JcdaSbmcView1Service jcdaSbmcView1Service;
	
	@ModelAttribute
	public JcdaSbmcView1 get(@RequestParam(required=false) String id) {
		JcdaSbmcView1 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jcdaSbmcView1Service.get(id);
		}
		if (entity == null){
			entity = new JcdaSbmcView1();
		}
		return entity;
	}
	
	/**
	 * 设备考勤信息列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(JcdaSbmcView1 jcdaSbmcView1,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/jcda_sbkqxx/jcdaSbmcView1List";
	}

	/**
	 * 查看，增加，编辑设备考勤信息表单页面
	 * params:
	 * 	mode: add, edit, view,addChild 代表四种种模式的页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, JcdaSbmcView1 jcdaSbmcView1, Model model) {
		if (jcdaSbmcView1.getParent()!=null && StringUtils.isNotBlank(jcdaSbmcView1.getParent().getId())){
			jcdaSbmcView1.setParent(jcdaSbmcView1Service.get(jcdaSbmcView1.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(jcdaSbmcView1.getId())){
				JcdaSbmcView1 jcdaSbmcView1Child = new JcdaSbmcView1();
				jcdaSbmcView1Child.setParent(new JcdaSbmcView1(jcdaSbmcView1.getParent().getId()));
				List<JcdaSbmcView1> list = jcdaSbmcView1Service.findList(jcdaSbmcView1); 
				if (list.size() > 0){
					jcdaSbmcView1.setSort(list.get(list.size()-1).getSort());
					if (jcdaSbmcView1.getSort() != null){
						jcdaSbmcView1.setSort(jcdaSbmcView1.getSort() + 30);
					}
				}
			}
		}
		if (jcdaSbmcView1.getSort() == null){
			jcdaSbmcView1.setSort(30);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("jcdaSbmcView1", jcdaSbmcView1);
		return "modules/jcda_sbkqxx/jcdaSbmcView1Form";
	}

	/**
	 * 保存设备考勤信息
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(JcdaSbmcView1 jcdaSbmcView1, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(jcdaSbmcView1);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		jcdaSbmcView1Service.save(jcdaSbmcView1);//保存
		j.setSuccess(true);
		j.put("jcdaSbmcView1", jcdaSbmcView1);
		j.setMsg("保存设备考勤信息成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<JcdaSbmcView1> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return jcdaSbmcView1Service.getChildren(parentId);
	}
	
	/**
	 * 删除设备考勤信息
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(JcdaSbmcView1 jcdaSbmcView1) {
		AjaxJson j = new AjaxJson();
		jcdaSbmcView1Service.delete(jcdaSbmcView1);
		j.setSuccess(true);
		j.setMsg("删除设备考勤信息成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<JcdaSbmcView1> list = jcdaSbmcView1Service.findList(new JcdaSbmcView1());
		for (int i=0; i<list.size(); i++){
			JcdaSbmcView1 e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("text", e.getName());
				if(StringUtils.isBlank(e.getParentId()) || "0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}