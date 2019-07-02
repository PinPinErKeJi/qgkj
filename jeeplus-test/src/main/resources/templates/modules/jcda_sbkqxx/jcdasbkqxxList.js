<script>
$(document).ready(function() {
	$('#jcdasbkqxxTable').bootstrapTable({
		 
		  //请求方法
               method: 'post',
               //类型json
               dataType: "json",
               contentType: "application/x-www-form-urlencoded",
			   //显示检索按钮
		       showSearch: true,
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: true,
               //显示 内容列下拉框
    	       showColumns: true,
    	       //显示到处按钮
    	       showExport: true,
    	       //显示切换分页按钮
    	       showPaginationSwitch: true,
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否显示行间隔色
               striped: false,
               rightFixedColumns: false, //右侧冻结列
               rightFixedNumber: 1,
               //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性(*)
               cache: false,    
               //是否显示分页(*)
               pagination: true,
               //分页方式: client客户端分页，server服务端分页(*)
               sidePagination: "server",
                //排序方式 
               sortOrder: "asc",  
               //初始化加载第一页，默认第一页
               pageNumber:1,   
               //每页的记录行数(*)
               pageSize: 10,  
               //可供选择的每页的行数(*)
               pageList: [10, 25, 50, 100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/jcda_sbkqxx/jcdasbkqxx/data",
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',   
               ////查询参数,每次调用是会带上这个参数，可自定义                         
               queryParams : function(params) {
               	var searchParam = $("#searchForm").serializeJSON();
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
               	if(params.sort && params.order){
                    searchParam.orderBy = params.sort+ " "+  params.order;
                 }
                 return searchParam;
               },
               onShowSearch: function () {
               	 $("#import-collapse").hide();
				 $("#search-collapse").fadeToggle();
               },
               columns: [{
		        checkbox: true
		       
		    }
			,{
		        field: 'sb.name',
		        title: '设备名称',
		        sortable: true,
		        sortName: 'sb.name'
		    }
			,{
		        field: 'sbxlh',
		        title: '设备序列号',
		        sortable: true,
		        sortName: 'sbxlh'
		       
		    }
			,{
		        field: 'zdy3',
		        title: '来源',
		        sortable: true,
		        sortName: 'a.zdy3'
		       
		    }
			,{
		        field: 'name',
		        title: '用户名称',
		        sortable: true,
		        sortName: 'name'
		       
		    }
			,{
		        field: 'date',
		        title: '时间',
		        sortable: true,
		        sortName: 'date'
		    }
			,{
		        field: 'zdy4',
		        title: '图片',
		        sortable: true,
		        sortName: 'a.zdy4'
		        ,formatter:  function operateFormatter(value, row, index) {
		        	if(value){
		        		return '<img   onclick="jp.showPic(\''+value+'\')"'+' height="40px" src="'+value+'">';
		        	}else{
		        		return "--";
		        	}
		        }
		    }
			,{
		        field: 'zdy2',
		        title: '识别的人员类型',
		        sortable: true,
		        sortName: 'a.zdy2'
	        	,formatter:  function operateFormatter(value, row, index) {
		        	switch (value) {
					case "0":
						return "时间段内";
						break;
					case "1":
						return "时间段外";
						break;
					case "2":
						return "陌生人/识别失败";
						break;

					default:
						return '--';
						break;
					}
		        }
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#jcdasbkqxxTable').bootstrapTable("toggleView");
		}
	  
	  $('#jcdasbkqxxTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#jcdasbkqxxTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#jcdasbkqxxTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/jcda_sbkqxx/jcdasbkqxx/import",function (data) {
			 if(data.success){
				 jp.toastr_success(data.msg);
				 refresh();
			 }else{
				 jp.toastr_error(data.msg);
			 }
			 jp.unblock('#import-collapse',200);
		 })
	  })

	 $("#btnDownloadTpl").click(function(){//下载模板文件
            jp.downloadFile('${ctx}/jcda_sbkqxx/jcdasbkqxx/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#jcdasbkqxxTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#jcdasbkqxxTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/jcda_sbkqxx/jcdasbkqxx/export?'+values);
	  })

	  $("#search").click("click", function() {// 绑定查询按扭
		  refresh();
		});

	 $("#reset").click("click", function() { //绑定重置按钮
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  refresh();
		});
	 $('#beginDate').datetimepicker({
	        format:'Y-m-d H:i:s'
	    });
	    $('#endDate').datetimepicker({
	        format:'Y-m-d H:i:s'
	    });
		
	});
/** 同步 */
function synchronize(){
	var sbId = $('#sbId').val();
	if(sbId){
		var startTime = $('#beginDate').val();
		var endTime = $('#endDate').val();
		var index =jp.loading("正在同步....");
		jp.post('${ctx}//jcda_sbkqxx/jcdasbkqxx/synchronize',{"sbId":sbId,"startTime":startTime,"endTime":endTime},function(data){
			if(data.success){
				refresh();
				jp.toastr_success(data.msg);
			}else{
				jp.alert(data.msg);
			}
			jp.close(index);
		});
	}else{
		jp.alert('请选择左面要同步的设备.');
	}
}
/**
 * 计算
 * @returns
 */
function calculate(){
	var startTime = $('#beginDate').val();
	var endTime = $('#endDate').val();
	var index =jp.loading("正在计算....");
	jp.post('${ctx}//jcda_sbkqxx/jcdasbkqxx/calculate',{"startTime":startTime,"endTime":endTime},function(data){
		if(data.success){
			refresh();
			jp.toastr_success(data.msg);
		}else{
			jp.alert(data.msg);
		}
		jp.close(index);
	});
}
	//获取选中行
  function getIdSelections() {
        return $.map($("#jcdasbkqxxTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该设备考勤信息记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/jcda_sbkqxx/jcdasbkqxx/delete?ids=" + ids, function(data){
				if(data.success){
					refresh();
					jp.toastr_success(data.msg);
				}else{
					jp.toastr_error(data.msg);
				}
				jp.close(index);
			})

	 })
  }


    //刷新列表
  function refresh() {
      $('#jcdasbkqxxTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增设备考勤信息', "${ctx}/jcda_sbkqxx/jcdasbkqxx/form/add",'800px', '500px');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑设备考勤信息', "${ctx}/jcda_sbkqxx/jcdasbkqxx/form/edit?id="+id,'800px', '500px');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看设备考勤信息', "${ctx}/jcda_sbkqxx/jcdasbkqxx/form/view?id="+id,'800px', '500px');
  }
</script>