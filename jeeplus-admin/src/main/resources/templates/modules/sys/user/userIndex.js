<script>
		$(document).ready(function() {
				//jsTree初始化
                var to = false;
                $('#search_q').keyup(function () {
                    if(to) { clearTimeout(to); }
                    to = setTimeout(function () {
                        var v = $('#search_q').val();
                        $('#jstree').jstree(true).search(v);
                    }, 250);
                });
                $('#jstree').jstree({
                    'core' : {
                        "multiple" : false,
                        "animation" : 0,
                        "themes" : {"icons":true , "stripes":false},
                        'data' : {
                            "url" : "${ctx}/sys/office/treeData",
                            "dataType" : "json"
                        }
                    },
                    "conditionalselect" : function (node, event) {
                        return false;
                    },
                    'plugins': [ 'types', "wholerow",  "search"],
                    "types":{
                        'default' : { 'icon' : 'fa fa-folder-o' }
                    }

                }).bind("activate_node.jstree", function (obj, e) {
                    var node = $('#jstree').jstree(true).get_selected(true)[0];

                    var id = node.id == '0' ? '' :node.id;
                    if(node.original.type == 1){//level=0 代表公司
                        $("#companyId").val(id);
                        $("#companyName").val(node.text);
                        $("#officeId").val("");
                        $("#officeName").val("");
                    }else{
                        $("#companyId").val("");
                        $("#companyName").val("");
                        $("#officeId").val(id);
                        $("#officeName").val(node.text);
                    }

                    $('#table').bootstrapTable('refresh');

                }).on('loaded.jstree', function() {
                    $("#jstree").jstree('open_all');
                });



                //表格初始化
			  $('#table').bootstrapTable({
				  
				  //请求方法
                  method: 'post',
                  //类型json
                  dataType: "json",
                  contentType: "application/x-www-form-urlencoded",
	                 //是否显示行间隔色
	                striped: false,
	                //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）     
	                cache: false,
				  //显示检索按钮
				  showSearch: true,
				  //显示刷新按钮
				  showRefresh: true,
				  //显示切换手机试图按钮
				  showToggle: true,
				  //显示 内容列下拉框
				  showColumns: true,
				  //显示切换分页按钮
				  showPaginationSwitch: true,
	                //是否显示分页（*）  
	                pagination: true,   
	                 //排序方式 
	                sortOrder: "asc",    
	                //初始化加载第一页，默认第一页
	                pageNumber:1,   
	                //每页的记录行数（*）   
	                pageSize: 10,  
	                //可供选择的每页的行数（*）    
	                pageList: [10, 25, 50, 100],
	                //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
	                url: "${ctx}/sys/user/list",
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
                          $("#import-collapse").hide()
                          $("#search-collapse").fadeToggle();

                      },
	                //分页方式：client客户端分页，server服务端分页（*）
	                sidePagination: "server",
	               
	                onClickRow: function(row, $el){
	                },
	                columns: [{
				        checkbox: true
				       
				    }, {
				        field: 'photo',
				        title: '头像',
				        formatter:function(value, row , index){
				        	if(value ==''){
				        		return '<span class="jp-avatar jp-avatar-circle jp-avatar-icon" style="background-color: rgb(135, 208, 104);"> <i class="jp-icon jp-icon-user"> </i> </span>';
				        	}else{
				        		return '<img   onclick="jp.showPic(\''+value+'\')"'+' height="40px" src="'+value+'">';
				        	}
				        	
				        }
				       
				    }, {
				        field: 'loginName',
				        title: '登录名',
				        sortable: true
				       
				    }, {
				        field: 'name',
				        title: '姓名',
				        sortable: true
				    }, {
				        field: 'phone',
				        title: '电话',
				        sortable: true
				    }, {
				        field: 'mobile',
				        title: '手机',
				        sortable: true
				    }, {
				        field: 'company.name',
				        title: '归属公司',
                        sortable: true,
						sortName: 'c.name'
				    }, {
				        field: 'office.name',
				        title: '归属部门',
                        sortable: true,
						sortName: 'o.name'
				    },{
                        field: 'roleNames',
                        title: '角色'
                    },{
                        field: 'operate',
                        title: '操作',
                        align: 'center',
                        class:'text-nowrap',
                        events: {
                            'click .view': function (e, value, row, index) {
                                view(row.id);
                            },
                            'click .edit': function (e, value, row, index) {
                                edit(row.id)
                            },
                            'click .del': function (e, value, row, index) {
                                deleteAll(row.id);

                            }
                        },
                        formatter:  function operateFormatter(value, row, index) {
                            return [
								<% if(shiro.hasPermission("sys:user:view")){ %>
										' <a  class="view"> 查看</a>',
										'<div class="jp-divider jp-divider-vertical"></div>',
									<% } %>
								<% if(shiro.hasPermission("sys:user:edit")){ %>
										'<a  class="edit"> 编辑</a>',
										'<div class="jp-divider jp-divider-vertical"></div>',
									<% } %>
								<% if(shiro.hasPermission("sys:user:del")){ %>
										' <a  class="del"> 删除</a>'
										<% } %>
								].join('');
                        }
                    }
                    ]

              });
			
			  
			  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端
				  $('#table').bootstrapTable("toggleView");
				}
			  
			  $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
		                'check-all.bs.table uncheck-all.bs.table', function () {
		            $('#remove').prop('disabled', ! $('#table').bootstrapTable('getSelections').length);
		            $('#edit').prop('disabled', $('#table').bootstrapTable('getSelections').length!=1);
		        });
	

            $("#import").click(function(){//显示导入面板
                $("#search-collapse").hide();
                $("#import-collapse").fadeToggle();
            })


            $("#btnImportExcel").click(function(){//导入Excel
                var importForm = $('#importForm')[0];

                jp.block('#import-collapse',"文件上传中...");
                jp.uploadFile(importForm,"${ctx}/sys/user/import",function (data) {
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
                jp.downloadFile('${ctx}/sys/user/import/template');
            })

            $("#export").click(function(){//导出Excel文件
                jp.downloadFile('${ctx}/sys/user/export');
            })


            $("#search").click("click", function() {// 绑定查询按扭
				  $('#table').bootstrapTable('refresh');
				});
			  $("#reset").click("click", function() {// 绑定查询按扭
				  $("#searchForm  input").val("");
				  $("#searchForm  select").val("");
				  $('#table').bootstrapTable('refresh');
				});
			  
		});
		
		  function getIdSelections() {
		        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
		            return row.id
		        });
		    }
		  
		  function deleteAll(ids){
			    if(!ids){
			    	ids =  getIdSelections();
			    }
				jp.confirm('确认要删除选中用户吗？',  function(){
					    jp.loading();
                 	  	$.get("${ctx}/sys/user/deleteAll?ids=" +ids, function(data){
                 	  		if(data.success){
                 	  			$('#table').bootstrapTable('refresh');
                	  			jp.success(data.msg);
                	  		}else{
                	  			jp.error(data.msg);
                	  		}
                 	  	})
				})
		  }
			function view(id){
				if(!id){
					id = getIdSelections();
				}
				jp.openViewDialog('查看用户', "${ctx}/sys/user/form?id=" + id,'800px', '680px');

			}
		  function edit(id){
			  if(!id){
				  id = getIdSelections();
			  }
			  jp.openSaveDialog('编辑用户', "${ctx}/sys/user/form?id=" + id,'800px', '680px');
			  
		  }
		  function refresh() {
              $('#table').bootstrapTable('refresh');
          }
	</script>