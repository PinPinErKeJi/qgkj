/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbgl.web;

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
import com.jeeplus.modules.jcda_sbgl.entity.Jcdasbglwz;
import com.jeeplus.modules.jcda_sbgl.service.JcdasbglwzService;

/**
 * 设备管理Controller
 * @author ww
 * @version 2019-04-07
 */
@Controller
@RequestMapping(value = "${adminPath}/jcda_sbgl/jcdasbglwz")
public class JcdasbglwzController extends BaseController {

	@Autowired
	private JcdasbglwzService jcdasbglwzService;
	
	@ModelAttribute
	public Jcdasbglwz get(@RequestParam(required=false) String id) {
		Jcdasbglwz entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jcdasbglwzService.get(id);
		}
		if (entity == null){
			entity = new Jcdasbglwz();
		}
		return entity;
	}
	
	/**
	 * 设备管理列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(Jcdasbglwz jcdasbglwz,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/jcda_sbgl/jcdasbglwzList";
	}

	/**
	 * 查看，增加，编辑设备管理表单页面
	 * params:
	 * 	mode: add, edit, view,addChild 代表四种种模式的页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Jcdasbglwz jcdasbglwz, Model model) {
		if (jcdasbglwz.getParent()!=null && StringUtils.isNotBlank(jcdasbglwz.getParent().getId())){
			jcdasbglwz.setParent(jcdasbglwzService.get(jcdasbglwz.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(jcdasbglwz.getId())){
				Jcdasbglwz jcdasbglwzChild = new Jcdasbglwz();
				jcdasbglwzChild.setParent(new Jcdasbglwz(jcdasbglwz.getParent().getId()));
				List<Jcdasbglwz> list = jcdasbglwzService.findList(jcdasbglwz); 
				if (list.size() > 0){
					jcdasbglwz.setSort(list.get(list.size()-1).getSort());
					if (jcdasbglwz.getSort() != null){
						jcdasbglwz.setSort(jcdasbglwz.getSort() + 30);
					}
				}
			}
		}
		if (jcdasbglwz.getSort() == null){
			jcdasbglwz.setSort(30);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("jcdasbglwz", jcdasbglwz);
		return "modules/jcda_sbgl/jcdasbglwzForm";
	}

	/**
	 * 保存设备管理
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(Jcdasbglwz jcdasbglwz, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(jcdasbglwz);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		jcdasbglwzService.save(jcdasbglwz);//保存
		j.setSuccess(true);
		j.put("jcdasbglwz", jcdasbglwz);
		j.setMsg("保存设备管理成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<Jcdasbglwz> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return jcdasbglwzService.getChildren(parentId);
	}
	
	/**
	 * 删除设备管理
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(Jcdasbglwz jcdasbglwz) {
		AjaxJson j = new AjaxJson();
		jcdasbglwzService.delete(jcdasbglwz);
		j.setSuccess(true);
		j.setMsg("删除设备管理成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Jcdasbglwz> list = jcdasbglwzService.findList(new Jcdasbglwz());
		for (int i=0; i<list.size(); i++){
			Jcdasbglwz e = list.get(i);
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