<script>
$(document).ready(function() {
	$('#kqglrykqbTable').bootstrapTable({
		 
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
               url: "${ctx}/kqgl_rykqb/kqglrykqb/data",
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
		        field: 'jg.name',
		        title: '机构',
		        sortable: true,
		        sortName: 'jg.name'
		        ,formatter:function(value, row , index){
		             <% if(shiro.hasPermission("kqgl_rykqb:kqglrykqb:edit") ){ %>
					   if(!value){
						  return "<a onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("kqgl_rykqb:kqglrykqb:view")){ %>
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
		        field: 'code',
		        title: '人员编码',
		        sortable: true,
		        sortName: 'a.code'
		       
		    }
			,{
		        field: 'xm.name',
		        title: '姓名',
		        sortable: true,
		        sortName: 'xm.name'
		       
		    }
			,{
		        field: 'sbmc',
		        title: '设备名称',
		        sortable: true,
		        sortName: 'a.sbmc'
		       
		    }
			,{
		        field: 'kqsj',
		        title: '考勤时间',
		        sortable: true,
		        sortName: 'a.kqsj'
		       
		    }
			,{
		        field: 'kqnyr',
		        title: '考勤年月日',
		        sortable: true,
		        sortName: 'a.kqnyr'
		       
		    }
			,{
		        field: 'xq',
		        title: '星期',
		        sortable: true,
		        sortName: 'a.xq'
		       
		    }
			,{
		        field: 'zp',
		        title: '照片',
	        	formatter:  function operateFormatter(value, row, index) {
		        	if(value){
		        		return '<img   onclick="jp.showPic(\''+value+'\')"'+' height="40px" src="'+value+'">';
		        	}else{
		        		return "--";
		        	}
		        }
		    }
			,{
		        field: 'zt',
		        title: '状态',
		        sortable: true,
		        sortName: 'a.zt'
		       
		    }
			,{
		        field: 'sc',
		        title: '时长',
		        sortable: true,
		        sortName: 'a.sc'
		       
		    }
			,{
		        field: 'ts',
		        title: '天数',
		        sortable: true,
		        sortName: 'a.ts'
		    }
			,{
		        field: 'bb',
		        title: '班别',
		        sortable: true,
		        sortName: 'a.bb',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('banci'))}, value, "-");
		        }
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
					<% if(shiro.hasPermission("kqgl_rykqb:kqglrykqb:view")){ %>
					   ' <a class="view">查看</a>',
					   ' <div class="jp-divider jp-divider-vertical"></div>',
				   <% } %>
				   <% if(shiro.hasPermission("kqgl_rykqb:kqglrykqb:edit")){ %>
					   ' <a class="edit">编辑</a>',
					   ' <div class="jp-divider jp-divider-vertical"></div>',
				   <% } %>
				   <% if(shiro.hasPermission("kqgl_rykqb:kqglrykqb:del")){ %>
					   ' <a class="del">删除</a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端
		  $('#kqglrykqbTable').bootstrapTable("toggleView");
		}
	  
	  $('#kqglrykqbTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#kqglrykqbTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#kqglrykqbTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/kqgl_rykqb/kqglrykqb/import",function (data) {
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
            jp.downloadFile('${ctx}/kqgl_rykqb/kqglrykqb/import/template');
		})

	$("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#kqglrykqbTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#kqglrykqbTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/kqgl_rykqb/kqglrykqb/export?'+values);
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

		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#kqglrykqbTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该员工考勤表记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/kqgl_rykqb/kqglrykqb/delete?ids=" + ids, function(data){
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
      $('#kqglrykqbTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增员工考勤表', "${ctx}/kqgl_rykqb/kqglrykqb/form/add",'800px', '800px');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑员工考勤表', "${ctx}/kqgl_rykqb/kqglrykqb/form/edit?id="+id,'800px', '800px');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看员工考勤表', "${ctx}/kqgl_rykqb/kqglrykqb/form/view?id="+id,'800px', '800px');
  }
</script>