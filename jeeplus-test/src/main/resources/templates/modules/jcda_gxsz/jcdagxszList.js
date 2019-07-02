<script>
$(document).ready(function() {
	$('#jcdagxszTable').bootstrapTable({
		 
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
               url: "${ctx}/jcda_gxsz/jcdagxsz/data",
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
		        field: 'weekday',
		        title: '公休',
		        sortable: true,
		        sortName: 'weekday',
		        formatter:function(value, row , index){
		             <% if(shiro.hasPermission("jcda_gxsz:jcdagxsz:edit") ){ %>
						   return "<a onclick='edit(\""+row.id+"\")'>"+jp.getDictLabel(${fn.toJson(fn.getDictList('weekday'))}, value, "-")+"</a>";
                     <% }else if(shiro.hasPermission("jcda_gxsz:jcdagxsz:view")){ %>
                           return "<a onclick='view(\""+row.id+"\")'>"+jp.getDictLabel(${fn.toJson(fn.getDictList('weekday'))}, value, "-")+"</a>";
                     <% }else{ %>
					   	   return jp.getDictLabel(${fn.toJson(fn.getDictList('weekday'))}, value, "-");
					 <% } %>
		        }
		       
		    }
			,{
		        field: 'gxfs',
		        title: '公休范围',
		        sortable: true,
		        sortName: 'gxfs',
		        formatter:function(value, row , index){
		        	var valueArray = value.split(",");
		        	var labelArray = [];
		        	for(var i =0 ; i<valueArray.length-1; i++){
		        		labelArray[i] = jp.getDictLabel(${fn.toJson(fn.getDictList('gxfw'))}, valueArray[i], "-");
		        	}
		        	return labelArray.join(",");
		        }
		       
		    }
			,{
		        field: 'sbtkssjj',
		        title: '上午开始时间（进）',
		        sortable: true,
		        sortName: 'sbtkssjj'
		       
		    }
			,{
		        field: 'sbtjssjj',
		        title: '上午结束时间（进）',
		        sortable: true,
		        sortName: 'sbtjssjj'
		       
		    }
			,{
		        field: 'sbtkssjc',
		        title: '上午开始时间（出）',
		        sortable: true,
		        sortName: 'sbtkssjc'
		       
		    }
			,{
		        field: 'sbtjssjc',
		        title: '上午结束时间（出）',
		        sortable: true,
		        sortName: 'sbtjssjc'
		       
		    }
			,{
		        field: 'xwkssjj',
		        title: '下午开始时间（进）',
		        sortable: true,
		        sortName: 'xwkssjj'
		       
		    }
			,{
		        field: 'xwjssjj',
		        title: '下午结束时间（进）',
		        sortable: true,
		        sortName: 'xwjssjj'
		       
		    }
			,{
		        field: 'xwkssjc',
		        title: '下午开始时间（出）',
		        sortable: true,
		        sortName: 'xwkssjc'
		       
		    }
			,{
		        field: 'xwjssjc',
		        title: '下午结束时间（出）',
		        sortable: true,
		        sortName: 'xwjssjc'
		       
		    }
			,{
		        field: 'swsc',
		        title: '上午时长',
		        sortable: true,
		        sortName: 'swsc'
		       
		    }
			,{
		        field: 'swts',
		        title: '上午天数',
		        sortable: true,
		        sortName: 'swts'
		       
		    }
			,{
		        field: 'xwsc',
		        title: '下午时长',
		        sortable: true,
		        sortName: 'xwsc'
		       
		    }
			,{
		        field: 'xwts',
		        title: '下午天数',
		        sortable: true,
		        sortName: 'xwts'
		       
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
					<% if(shiro.hasPermission("jcda_gxsz:jcdagxsz:view")){ %>
					   ' <a class="view">查看</a>',
					   ' <div class="jp-divider jp-divider-vertical"></div>',
				   <% } %>
				   <% if(shiro.hasPermission("jcda_gxsz:jcdagxsz:edit")){ %>
					   ' <a class="edit">编辑</a>',
					   ' <div class="jp-divider jp-divider-vertical"></div>',
				   <% } %>
				   <% if(shiro.hasPermission("jcda_gxsz:jcdagxsz:del")){ %>
					   ' <a class="del">删除</a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#jcdagxszTable').bootstrapTable("toggleView");
		}
	  
	  $('#jcdagxszTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#jcdagxszTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#jcdagxszTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/jcda_gxsz/jcdagxsz/import",function (data) {
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
            jp.downloadFile('${ctx}/jcda_gxsz/jcdagxsz/import/template');
		})

	$("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#jcdagxszTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#jcdagxszTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/jcda_gxsz/jcdagxsz/export?'+values);
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
        return $.map($("#jcdagxszTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该公休设置记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/jcda_gxsz/jcdagxsz/delete?ids=" + ids, function(data){
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
      $('#jcdagxszTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增公休设置', "${ctx}/jcda_gxsz/jcdagxsz/form/add",'800px', '800px');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑公休设置', "${ctx}/jcda_gxsz/jcdagxsz/form/edit?id="+id,'800px', '800px');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看公休设置', "${ctx}/jcda_gxsz/jcdagxsz/form/view?id="+id,'800px', '800px');
  }
</script>