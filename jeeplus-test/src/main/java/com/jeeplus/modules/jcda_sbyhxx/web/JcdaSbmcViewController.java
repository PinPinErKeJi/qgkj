/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbyhxx.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeeplus.modules.jcda_sbyhxx.entity.JcdaSbmcView;
import com.jeeplus.modules.jcda_sbyhxx.service.JcdaSbmcViewService;

/**
 * 设备用户信息Controller
 * @author ww
 * @version 2019-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/jcda_sbyhxx/jcdaSbmcView")
public class JcdaSbmcViewController extends BaseController {

	@Autowired
	private JcdaSbmcViewService jcdaSbmcViewService;
	
	@ModelAttribute
	public JcdaSbmcView get(@RequestParam(required=false) String id) {
		JcdaSbmcView entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jcdaSbmcViewService.get(id);
		}
		if (entity == null){
			entity = new JcdaSbmcView();
		}
		return entity;
	}
	
	/**
	 * 设备用户信息列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(JcdaSbmcView jcdaSbmcView,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/jcda_sbyhxx/jcdaSbmcViewList";
	}

	/**
	 * 查看，增加，编辑设备用户信息表单页面
	 * params:
	 * 	mode: add, edit, view,addChild 代表四种种模式的页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, JcdaSbmcView jcdaSbmcView, Model model) {
		if (jcdaSbmcView.getParent()!=null && StringUtils.isNotBlank(jcdaSbmcView.getParent().getId())){
			jcdaSbmcView.setParent(jcdaSbmcViewService.get(jcdaSbmcView.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(jcdaSbmcView.getId())){
				JcdaSbmcView jcdaSbmcViewChild = new JcdaSbmcView();
				jcdaSbmcViewChild.setParent(new JcdaSbmcView(jcdaSbmcView.getParent().getId()));
				List<JcdaSbmcView> list = jcdaSbmcViewService.findList(jcdaSbmcView); 
				if (list.size() > 0){
					jcdaSbmcView.setSort(list.get(list.size()-1).getSort());
					if (jcdaSbmcView.getSort() != null){
						jcdaSbmcView.setSort(jcdaSbmcView.getSort() + 30);
					}
				}
			}
		}
		if (jcdaSbmcView.getSort() == null){
			jcdaSbmcView.setSort(30);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("jcdaSbmcView", jcdaSbmcView);
		return "modules/jcda_sbyhxx/jcdaSbmcViewForm";
	}

	/**
	 * 保存设备用户信息
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(JcdaSbmcView jcdaSbmcView, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(jcdaSbmcView);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		jcdaSbmcViewService.save(jcdaSbmcView);//保存
		j.setSuccess(true);
		j.put("jcdaSbmcView", jcdaSbmcView);
		j.setMsg("保存设备用户信息成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<JcdaSbmcView> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return jcdaSbmcViewService.getChildren(parentId);
	}
	
	/**
	 * 删除设备用户信息
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(JcdaSbmcView jcdaSbmcView) {
		AjaxJson j = new AjaxJson();
		jcdaSbmcViewService.delete(jcdaSbmcView);
		j.setSuccess(true);
		j.setMsg("删除设备用户信息成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<JcdaSbmcView> list = jcdaSbmcViewService.findList(new JcdaSbmcView());
		for (int i=0; i<list.size(); i++){
			JcdaSbmcView e = list.get(i);
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