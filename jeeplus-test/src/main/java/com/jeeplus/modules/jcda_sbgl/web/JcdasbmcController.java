/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbgl.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeeplus.cmd.equipment.EquipmentCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.jcda_sbgl.entity.Jcdasbmc;
import com.jeeplus.modules.jcda_sbgl.service.JcdasbmcService;

/**
 * 设备名称Controller
 * @author ww
 * @version 2019-04-07
 */
@Controller
@RequestMapping(value = "${adminPath}/jcda_sbgl/jcdasbmc")
public class JcdasbmcController extends BaseController {

	@Autowired
	private JcdasbmcService jcdasbmcService;
	
	@ModelAttribute
	public Jcdasbmc get(@RequestParam(required=false) String id) {
		Jcdasbmc entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jcdasbmcService.get(id);
		}
		if (entity == null){
			entity = new Jcdasbmc();
		}
		return entity;
	}
	
	/**
	 * 设备名称列表页面
	 */
	@RequiresPermissions("jcda_sbgl:jcdasbmc:list")
	@RequestMapping(value = {"list", ""})
	public String list(Jcdasbmc jcdasbmc, Model model) {
		model.addAttribute("jcdasbmc", jcdasbmc);
		return "modules/jcda_sbgl/jcdasbmcList";
	}
	
	@ResponseBody
	@RequiresPermissions(value={"jcda_sbgl:jcdasbmc:add","jcda_sbgl:jcdasbmc:edit"},logical=Logical.OR)
	@RequestMapping(value = "getDeviceKey")
	public AjaxJson getDeviceKey(String ip) {
		AjaxJson j = new AjaxJson();
		String key = EquipmentCmd.getDeviceKey(ip);
		if(key==null) {
			j.setSuccess(false);
			j.setMsg("获取失败，请检查IP是否正确或设备是否装置成功");
		}else {
			j.setSuccess(true);
			j.setMsg(key);
		}
		return j;
	}
	@ResponseBody
	@RequiresPermissions(value={"jcda_sbgl:jcdasbmc:add","jcda_sbgl:jcdasbmc:edit"},logical=Logical.OR)
	@RequestMapping(value = "setPass")
	public AjaxJson setPass(String ip,String rid,String pass) {
		AjaxJson j = new AjaxJson();
		boolean flag = jcdasbmcService.setPass(rid, ip, pass);
		if(flag) {
			j.setSuccess(true);
			j.setMsg("设置成功");
		}else {
			j.setSuccess(false);
			j.setMsg("设置失败，请检查IP是否正确或设备是否装置成功");
		}
		return j;
	}
	
	@ResponseBody
	@RequiresPermissions(value={"jcda_sbgl:jcdasbmc:add","jcda_sbgl:jcdasbmc:edit"},logical=Logical.OR)
	@RequestMapping(value = "setTime")
	public AjaxJson setTime(String rid) {
		AjaxJson j = new AjaxJson();
		try{
			jcdasbmcService.setTime(rid);
			j.setSuccess(true);
			j.setMsg("设置成功");
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(e.getMessage());
		}
		return j;
	}
	/**
	 * 设备名称列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbgl:jcdasbmc:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Jcdasbmc jcdasbmc, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Jcdasbmc> page = jcdasbmcService.findPage(new Page<Jcdasbmc>(request, response), jcdasbmc); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑设备名称表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"jcda_sbgl:jcdasbmc:view","jcda_sbgl:jcdasbmc:add","jcda_sbgl:jcdasbmc:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Jcdasbmc jcdasbmc, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("jcdasbmc", jcdasbmc);
		return "modules/jcda_sbgl/jcdasbmcForm";
	}

	/**
	 * 保存设备名称
	 */
	@ResponseBody
	@RequiresPermissions(value={"jcda_sbgl:jcdasbmc:add","jcda_sbgl:jcdasbmc:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Jcdasbmc jcdasbmc, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(jcdasbmc);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		jcdasbmcService.save(jcdasbmc);//保存
		j.setSuccess(true);
		j.setMsg("保存设备名称成功");
		return j;
	}

	
	/**
	 * 批量删除设备名称
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbgl:jcdasbmc:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			jcdasbmcService.delete(jcdasbmcService.get(id));
		}
		j.setMsg("删除设备名称成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbgl:jcdasbmc:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Jcdasbmc jcdasbmc, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备名称"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Jcdasbmc> page = jcdasbmcService.findPage(new Page<Jcdasbmc>(request, response, -1), jcdasbmc);
    		new ExportExcel("设备名称", Jcdasbmc.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出设备名称记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbgl:jcdasbmc:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Jcdasbmc> list = ei.getDataList(Jcdasbmc.class);
			for (Jcdasbmc jcdasbmc : list){
				try{
					jcdasbmcService.save(jcdasbmc);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条设备名称记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条设备名称记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入设备名称失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入设备名称数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbgl:jcdasbmc:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备名称数据导入模板.xlsx";
    		List<Jcdasbmc> list = Lists.newArrayList(); 
    		new ExportExcel("设备名称数据", Jcdasbmc.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}