<script>
$(document).ready(function() {
	$('#testDataMainDialogTable').bootstrapTable({
		 
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
    	       //显示详情按钮
    	       detailView: true,
    	       	//显示详细内容函数
	           detailFormatter: "detailFormatter",
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否显示行间隔色
               striped: false,
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
               url: "${ctx}/test/onetomany/dialog/testDataMainDialog/data",
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
		        field: 'tuser.name',
		        title: '归属用户',
		        sortable: true,
		        sortName: 'tuser.name'
		        ,formatter:function(value, row , index){
		             <% if(shiro.hasPermission("test:onetomany:dialog:testDataMainDialog:edit") ){ %>
					   if(!value){
						  return "<a onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("test:onetomany:dialog:testDataMainDialog:view")){ %>
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
		        field: 'office.name',
		        title: '归属部门',
		        sortable: true,
		        sortName: 'office.name'
		       
		    }
			,{
		        field: 'area.name',
		        title: '归属区域',
		        sortable: true,
		        sortName: 'area.name'
		       
		    }
			,{
		        field: 'name',
		        title: '名称',
		        sortable: true,
		        sortName: 'name'
		       
		    }
			,{
		        field: 'sex',
		        title: '性别',
		        sortable: true,
		        sortName: 'sex',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('sex'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'inDate',
		        title: '加入日期',
		        sortable: true,
		        sortName: 'inDate'
		       
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
					<% if(shiro.hasPermission("test:onetomany:dialog:testDataMainDialog:view")){ %>
					   ' <a class="view">查看</a>',
					   ' <div class="jp-divider jp-divider-vertical"></div>',
				   <% } %>
				   <% if(shiro.hasPermission("test:onetomany:dialog:testDataMainDialog:edit")){ %>
					   ' <a class="edit">编辑</a>',
					   ' <div class="jp-divider jp-divider-vertical"></div>',
				   <% } %>
				   <% if(shiro.hasPermission("test:onetomany:dialog:testDataMainDialog:del")){ %>
					   ' <a class="del">删除</a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#testDataMainDialogTable').bootstrapTable("toggleView");
		}
	  
	  $('#testDataMainDialogTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#testDataMainDialogTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#testDataMainDialogTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/test/onetomany/dialog/testDataMainDialog/import",function (data) {
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
            jp.downloadFile('${ctx}/test/onetomany/dialog/testDataMainDialog/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#testDataMainDialogTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#testDataMainDialogTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/test/onetomany/dialog/testDataMainDialog/export?'+values);
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

	 $('#inDate').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    			format:"yyyy-mm-dd"
		});
		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#testDataMainDialogTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该票务代理记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/test/onetomany/dialog/testDataMainDialog/delete?ids=" + ids, function(data){
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
      $('#testDataMainDialogTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增票务代理', "${ctx}/test/onetomany/dialog/testDataMainDialog/form/add",'800px', '500px');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑票务代理', "${ctx}/test/onetomany/dialog/testDataMainDialog/form/edit?id="+id,'800px', '500px');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看票务代理', "${ctx}/test/onetomany/dialog/testDataMainDialog/form/view?id="+id,'800px', '500px');
  }
 //子表展示
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#testDataMainDialogChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/test/onetomany/dialog/testDataMainDialog/detail?id="+row.id, function(testDataMainDialog){
    	var testDataMainDialogChild1RowIdx = 0, testDataMainDialogChild1Tpl = $("#testDataMainDialogChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  testDataMainDialog.testDataChild11List;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			addRow('#testDataMainDialogChild-'+row.id+'-1-List', testDataMainDialogChild1RowIdx, testDataMainDialogChild1Tpl, data1[i]);
			testDataMainDialogChild1RowIdx = testDataMainDialogChild1RowIdx + 1;
		}
				
    	var testDataMainDialogChild2RowIdx = 0, testDataMainDialogChild2Tpl = $("#testDataMainDialogChild2Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data2 =  testDataMainDialog.testDataChild12List;
		for (var i=0; i<data2.length; i++){
			data2[i].dict = {};
			addRow('#testDataMainDialogChild-'+row.id+'-2-List', testDataMainDialogChild2RowIdx, testDataMainDialogChild2Tpl, data2[i]);
			testDataMainDialogChild2RowIdx = testDataMainDialogChild2RowIdx + 1;
		}
				
    	var testDataMainDialogChild3RowIdx = 0, testDataMainDialogChild3Tpl = $("#testDataMainDialogChild3Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data3 =  testDataMainDialog.testDataChild13List;
		for (var i=0; i<data3.length; i++){
			data3[i].dict = {};
			addRow('#testDataMainDialogChild-'+row.id+'-3-List', testDataMainDialogChild3RowIdx, testDataMainDialogChild3Tpl, data3[i]);
			testDataMainDialogChild3RowIdx = testDataMainDialogChild3RowIdx + 1;
		}
				
      	  			
      })
     
        return html;
    }
  
	function addRow(list, idx, tpl, row){
		$(list).append(Mustache.render(tpl, {
			idx: idx, delBtn: true, row: row
		}));
	}
</script>
<script type="text/template" id="testDataMainDialogChildrenTpl">//<!--
	<div class="tabs-container">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-{{idx}}-1" aria-expanded="true">火车票</a></li>
				<li><a data-toggle="tab" href="#tab-{{idx}}-2" aria-expanded="true">飞机票</a></li>
				<li><a data-toggle="tab" href="#tab-{{idx}}-3" aria-expanded="true">汽车票</a></li>
		</ul>
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade in active">
						<table class="table table-bordered">
						<thead>
							<tr>
								<th>出发地</th>
								<th>目的地</th>
								<th>出发时间</th>
								<th>到达时间</th>
								<th>代理价格</th>
								<th>文件</th>
								<th>备注信息</th>
							</tr>
						</thead>
						<tbody id="testDataMainDialogChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
				<div id="tab-{{idx}}-2" class="tab-pane fade">
					<table class="ani table">
						<thead>
							<tr>
								<th>出发地</th>
								<th>目的地</th>
								<th>出发时间</th>
								<th>代理价格</th>
								<th>备注信息</th>
							</tr>
						</thead>
						<tbody id="testDataMainDialogChild-{{idx}}-2-List">
						</tbody>
					</table>
				</div>
				<div id="tab-{{idx}}-3" class="tab-pane fade">
					<table class="ani table">
						<thead>
							<tr>
								<th>出发地</th>
								<th>目的地</th>
								<th>代理价格</th>
								<th>备注信息</th>
							</tr>
						</thead>
						<tbody id="testDataMainDialogChild-{{idx}}-3-List">
						</tbody>
					</table>
				</div>
		</div>//-->
	</script>
	<script type="text/template" id="testDataMainDialogChild1Tpl">//<!--
				<tr>
					<td>
						{{row.startArea.name}}
					</td>
					<td>
						{{row.endArea.name}}
					</td>
					<td>
						{{row.starttime}}
					</td>
					<td>
						{{row.endtime}}
					</td>
					<td>
						{{row.price}}
					</td>
					<td>
						{{row.file}}
					</td>
					<td>
						{{row.remarks}}
					</td>
				</tr>//-->
	</script>
	<script type="text/template" id="testDataMainDialogChild2Tpl">//<!--
				<tr>
					<td>
						{{row.startArea.name}}
					</td>
					<td>
						{{row.endArea.name}}
					</td>
					<td>
						{{row.startTime}}
					</td>
					<td>
						{{row.price}}
					</td>
					<td>
						{{row.remarks}}
					</td>
				</tr>//-->
	</script>
	<script type="text/template" id="testDataMainDialogChild3Tpl">//<!--
				<tr>
					<td>
						{{row.startArea.name}}
					</td>
					<td>
						{{row.endArea.name}}
					</td>
					<td>
						{{row.price}}
					</td>
					<td>
						{{row.remarks}}
					</td>
				</tr>//-->
	</script>
