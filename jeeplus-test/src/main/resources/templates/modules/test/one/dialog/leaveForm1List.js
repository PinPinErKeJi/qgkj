<script>
$(document).ready(function() {
	$('#leaveForm1Table').bootstrapTable({
		 
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
               rightFixedColumns: true, //右侧冻结列
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
               url: "${ctx}/test/one/dialog/leaveForm1/data",
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
		        field: 'office.name',
		        title: '归属部门',
		        sortable: true,
		        sortName: 'office.name'
		        ,formatter:function(value, row , index){
		             <% if(shiro.hasPermission("test:one:dialog:leaveForm1:edit") ){ %>
					   if(!value){
						  return "<a onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("test:one:dialog:leaveForm1:view")){ %>
					   if(!value){
						  return "<a onclick='view(\""+row.id+"\")'>-</a>";
                       }else{
                          return "<a onclick='view(\""+row.id+"\")'>"+value+"</a>";
                       }
                     <% }else{ %>
					      return value;
					 <% } %>
		        	
		        }
		       
		    }
			,{
		        field: 'tuser.name',
		        title: '员工',
		        sortable: true,
		        sortName: 'tuser.name'
		       
		    }
			,{
		        field: 'area',
		        title: '归属区域',
		        sortable: true,
		        sortName: 'area'
		       
		    }
			,{
		        field: 'beginDate',
		        title: '请假开始日期',
		        sortable: true,
		        sortName: 'beginDate'
		       
		    }
			,{
		        field: 'endDate',
		        title: '请假结束日期',
		        sortable: true,
		        sortName: 'endDate'
		       
		    }
			,{
		        field: 'remarks',
		        title: '备注信息',
		        sortable: true,
		        sortName: 'remarks'
		       
		    }
			,{
			   field: 'operate',
			   title: '操作',
			   align: 'center',
			   class: 'text-nowrap',
			   events: {
				   'click .view': function (e, value, row, index) {
					   view(row.id);
				   },
				   'click .edit': function (e, value, row, index) {
					   edit(row.id)
				   },
				   'click .del': function (e, value, row, index) {
					   del(row.id);

				   }
			   },
			   formatter:  function operateFormatter(value, row, index) {
				   return [
					<% if(shiro.hasPermission("test:one:dialog:leaveForm1:view")){ %>
					   ' <a class="view">查看</a>',
					   ' <div class="jp-divider jp-divider-vertical"></div>',
				   <% } %>
				   <% if(shiro.hasPermission("test:one:dialog:leaveForm1:edit")){ %>
					   ' <a class="edit">编辑</a>',
					   ' <div class="jp-divider jp-divider-vertical"></div>',
				   <% } %>
				   <% if(shiro.hasPermission("test:one:dialog:leaveForm1:del")){ %>
					   ' <a class="del">删除</a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#leaveForm1Table').bootstrapTable("toggleView");
		}
	  
	  $('#leaveForm1Table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#leaveForm1Table').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#leaveForm1Table').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/test/one/dialog/leaveForm1/import",function (data) {
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
            jp.downloadFile('${ctx}/test/one/dialog/leaveForm1/import/template');
		})

	$("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#leaveForm1Table').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#leaveForm1Table').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/test/one/dialog/leaveForm1/export?'+values);
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

	 $('#beginDate').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    		format:"yyyy-mm-dd"
		});
	$('#endDate').datetimepicker({
		 	format:'Y-m-d H:i'
		});
		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#leaveForm1Table").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该请假表单记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/test/one/dialog/leaveForm1/delete?ids=" + ids, function(data){
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
      $('#leaveForm1Table').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增请假表单', "${ctx}/test/one/dialog/leaveForm1/form/add",'800px', '500px');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑请假表单', "${ctx}/test/one/dialog/leaveForm1/form/edit?id="+id,'800px', '500px');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看请假表单', "${ctx}/test/one/dialog/leaveForm1/form/view?id="+id,'800px', '500px');
  }
</script>