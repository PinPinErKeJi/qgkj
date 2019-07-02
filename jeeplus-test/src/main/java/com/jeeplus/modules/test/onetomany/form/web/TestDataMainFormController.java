/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.test.onetomany.form.web;

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
import com.jeeplus.modules.test.onetomany.form.entity.TestDataMainForm;
import com.jeeplus.modules.test.onetomany.form.service.TestDataMainFormService;

/**
 * 票务代理Controller
 * @author liugf
 * @version 2019-01-02
 */
@Controller
@RequestMapping(value = "${adminPath}/test/onetomany/form/testDataMainForm")
public class TestDataMainFormController extends BaseController {

	@Autowired
	private TestDataMainFormService testDataMainFormService;
	
	@ModelAttribute
	public TestDataMainForm get(@RequestParam(required=false) String id) {
		TestDataMainForm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testDataMainFormService.get(id);
		}
		if (entity == null){
			entity = new TestDataMainForm();
		}
		return entity;
	}
	
	/**
	 * 票务代理列表页面
	 */
	@RequiresPermissions("test:onetomany:form:testDataMainForm:list")
	@RequestMapping(value = {"list", ""})
	public String list(TestDataMainForm testDataMainForm, Model model) {
		model.addAttribute("testDataMainForm", testDataMainForm);
		return "modules/test/onetomany/form/testDataMainFormList";
	}
	
		/**
	 * 票务代理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:form:testDataMainForm:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TestDataMainForm testDataMainForm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestDataMainForm> page = testDataMainFormService.findPage(new Page<TestDataMainForm>(request, response), testDataMainForm); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑票务代理表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"test:onetomany:form:testDataMainForm:view","test:onetomany:form:testDataMainForm:add","test:onetomany:form:testDataMainForm:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, TestDataMainForm testDataMainForm, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("testDataMainForm", testDataMainForm);
		return "modules/test/onetomany/form/testDataMainFormForm";
	}

	/**
	 * 保存票务代理
	 */
	@ResponseBody
	@RequiresPermissions(value={"test:onetomany:form:testDataMainForm:add","test:onetomany:form:testDataMainForm:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TestDataMainForm testDataMainForm, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(testDataMainForm);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		testDataMainFormService.save(testDataMainForm);//保存
		j.setSuccess(true);
		j.setMsg("保存票务代理成功");
		return j;
	}

	
	/**
	 * 批量删除票务代理
	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:form:testDataMainForm:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			testDataMainFormService.delete(testDataMainFormService.get(id));
		}
		j.setMsg("删除票务代理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:form:testDataMainForm:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(TestDataMainForm testDataMainForm, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "票务代理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TestDataMainForm> page = testDataMainFormService.findPage(new Page<TestDataMainForm>(request, response, -1), testDataMainForm);
    		new ExportExcel("票务代理", TestDataMainForm.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出票务代理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public TestDataMainForm detail(String id) {
		return testDataMainFormService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:form:testDataMainForm:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TestDataMainForm> list = ei.getDataList(TestDataMainForm.class);
			for (TestDataMainForm testDataMainForm : list){
				try{
					testDataMainFormService.save(testDataMainForm);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条票务代理记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条票务代理记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入票务代理失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入票务代理数据模板
	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:form:testDataMainForm:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "票务代理数据导入模板.xlsx";
    		List<TestDataMainForm> list = Lists.newArrayList(); 
    		new ExportExcel("票务代理数据", TestDataMainForm.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}