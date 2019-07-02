/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.kqgl_rykqb.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.kqgl_rykqb.entity.Kqglrykqb;
import com.jeeplus.modules.kqgl_rykqb.service.KqglrykqbService;

/**
 * 员工考勤表Controller
 * @author ww
 * @version 2019-04-17
 */
@Controller
@RequestMapping(value = "${adminPath}/kqgl_rykqb/kqglrykqb")
public class KqglrykqbController extends BaseController {

	@Autowired
	private KqglrykqbService kqglrykqbService;
	
	@ModelAttribute
	public Kqglrykqb get(@RequestParam(required=false) String id) {
		Kqglrykqb entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = kqglrykqbService.get(id);
		}
		if (entity == null){
			entity = new Kqglrykqb();
		}
		return entity;
	}
	
	/**
	 * 员工考勤表列表页面
	 */
	@RequiresPermissions("kqgl_rykqb:kqglrykqb:list")
	@RequestMapping(value = {"list", ""})
	public String list(Kqglrykqb kqglrykqb, Model model) {
		model.addAttribute("kqglrykqb", kqglrykqb);
		return "modules/kqgl_rykqb/kqglrykqbList";
	}
	
		/**
	 * 员工考勤表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("kqgl_rykqb:kqglrykqb:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Kqglrykqb kqglrykqb, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Kqglrykqb> page = kqglrykqbService.findPage(new Page<Kqglrykqb>(request, response), kqglrykqb); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑员工考勤表表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"kqgl_rykqb:kqglrykqb:view","kqgl_rykqb:kqglrykqb:add","kqgl_rykqb:kqglrykqb:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Kqglrykqb kqglrykqb, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("kqglrykqb", kqglrykqb);
		return "modules/kqgl_rykqb/kqglrykqbForm";
	}

	/**
	 * 保存员工考勤表
	 */
	@ResponseBody
	@RequiresPermissions(value={"kqgl_rykqb:kqglrykqb:add","kqgl_rykqb:kqglrykqb:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Kqglrykqb kqglrykqb, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(kqglrykqb);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		kqglrykqbService.save(kqglrykqb);//保存
		j.setSuccess(true);
		j.setMsg("保存员工考勤表成功");
		return j;
	}

	
	/**
	 * 批量删除员工考勤表
	 */
	@ResponseBody
	@RequiresPermissions("kqgl_rykqb:kqglrykqb:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			kqglrykqbService.delete(kqglrykqbService.get(id));
		}
		j.setMsg("删除员工考勤表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("kqgl_rykqb:kqglrykqb:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Kqglrykqb kqglrykqb, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工考勤表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Kqglrykqb> page = kqglrykqbService.findPage(new Page<Kqglrykqb>(request, response, -1), kqglrykqb);
    		new ExportExcel("员工考勤表", Kqglrykqb.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出员工考勤表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("kqgl_rykqb:kqglrykqb:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Kqglrykqb> list = ei.getDataList(Kqglrykqb.class);
			for (Kqglrykqb kqglrykqb : list){
				try{
					kqglrykqbService.save(kqglrykqb);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条员工考勤表记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条员工考勤表记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入员工考勤表失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入员工考勤表数据模板
	 */
	@ResponseBody
	@RequiresPermissions("kqgl_rykqb:kqglrykqb:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工考勤表数据导入模板.xlsx";
    		List<Kqglrykqb> list = Lists.newArrayList(); 
    		new ExportExcel("员工考勤表数据", Kqglrykqb.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}