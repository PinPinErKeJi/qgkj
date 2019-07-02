/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_ryxx.web;

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
import com.jeeplus.modules.jcda_ryxx.entity.Syjg;
import com.jeeplus.modules.jcda_ryxx.service.SyjgService;

/**
 * 人员信息Controller
 * @author ww
 * @version 2019-04-07
 */
@Controller
@RequestMapping(value = "${adminPath}/jcda_ryxx/syjg")
public class SyjgController extends BaseController {

	@Autowired
	private SyjgService syjgService;
	
	@ModelAttribute
	public Syjg get(@RequestParam(required=false) String id) {
		Syjg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = syjgService.get(id);
		}
		if (entity == null){
			entity = new Syjg();
		}
		return entity;
	}
	
	/**
	 * 人员信息列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(Syjg syjg,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/jcda_ryxx/syjgList";
	}

	/**
	 * 查看，增加，编辑人员信息表单页面
	 * params:
	 * 	mode: add, edit, view,addChild 代表四种种模式的页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Syjg syjg, Model model) {
		if (syjg.getParent()!=null && StringUtils.isNotBlank(syjg.getParent().getId())){
			syjg.setParent(syjgService.get(syjg.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(syjg.getId())){
				Syjg syjgChild = new Syjg();
				syjgChild.setParent(new Syjg(syjg.getParent().getId()));
				List<Syjg> list = syjgService.findList(syjg); 
				if (list.size() > 0){
					syjg.setSort(list.get(list.size()-1).getSort());
					if (syjg.getSort() != null){
						syjg.setSort(syjg.getSort() + 30);
					}
				}
			}
		}
		if (syjg.getSort() == null){
			syjg.setSort(30);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("syjg", syjg);
		return "modules/jcda_ryxx/syjgForm";
	}

	/**
	 * 保存人员信息
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(Syjg syjg, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(syjg);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		syjgService.save(syjg);//保存
		j.setSuccess(true);
		j.put("syjg", syjg);
		j.setMsg("保存人员信息成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<Syjg> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return syjgService.getChildren(parentId);
	}
	
	/**
	 * 删除人员信息
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(Syjg syjg) {
		AjaxJson j = new AjaxJson();
		syjgService.delete(syjg);
		j.setSuccess(true);
		j.setMsg("删除人员信息成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Syjg> list = syjgService.findList(new Syjg());
		for (int i=0; i<list.size(); i++){
			Syjg e = list.get(i);
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