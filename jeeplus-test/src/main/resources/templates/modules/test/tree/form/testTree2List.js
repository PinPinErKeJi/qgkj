<script>
	    var $testTree2TreeTable=null;  
		$(document).ready(function() {
			$testTree2TreeTable=$('#testTree2TreeTable').treeTable({  
		    	   theme:'vsStyle',	           
					expandLevel : 2,
					column:0,
					checkbox: false,
		            url:'${ctx}/test/tree/form/testTree2/getChildren?parentId=',  
		            callback:function(item) { 
		            	 var treeTableTpl= $("#testTree2TreeTableTpl").html();
		            	 item.dict = {};
	           	  var result = laytpl(treeTableTpl).render({
								  row: item
							});
		                return result;                   
		            },  
		            beforeClick: function($testTree2TreeTable, id) { 
		                //异步获取数据 这里模拟替换处理  
		                $testTree2TreeTable.refreshPoint(id);  
		            },  
		            beforeExpand : function($testTree2TreeTable, id) {   
		            },  
		            afterExpand : function($testTree2TreeTable, id) {  
		            },  
		            beforeClose : function($testTree2TreeTable, id) {    
		            	
		            }  
		        });
		        
		        $testTree2TreeTable.initParents('${parentIds!}', "0");//在保存编辑时定位展开当前节点
		       
		});
		
		function del(con,id){  
			jp.confirm('确认要删除组织机构吗？', function(){
				jp.loading();
	       	  	$.get("${ctx}/test/tree/form/testTree2/delete?id="+id, function(data){
	       	  		if(data.success){
	       	  			$testTree2TreeTable.del(id);
	       	  			jp.success(data.msg);
	       	  		}else{
	       	  			jp.error(data.msg);
	       	  		}
	       	  	})
	       	   
       		});
	
		}

		function refreshNode(data) {//刷新节点
            var current_id = data.body.testTree2.id;
			var target = $testTree2TreeTable.get(current_id);
			var old_parent_id = target.attr("pid") == undefined?'1':target.attr("pid");
			var current_parent_id = data.body.testTree2.parentId;
			var current_parent_ids = data.body.testTree2.parentIds;
			if(old_parent_id == current_parent_id){
				if(current_parent_id == '0'){
					$testTree2TreeTable.refreshPoint(-1);
				}else{
					$testTree2TreeTable.refreshPoint(current_parent_id);
				}
			}else{
				$testTree2TreeTable.del(current_id);//刷新删除旧节点
				$testTree2TreeTable.initParents(current_parent_ids, "0");
			}
        }

		function add(){//新增
			jp.go('${ctx}/test/tree/form/testTree2/form/add');
		}
		function edit(id){//编辑
			jp.go('${ctx}/test/tree/form/testTree2/form/edit?id='+id);
		}
		function view(id){//查看
			jp.go('${ctx}/test/tree/form/testTree2/form/view?id='+id);
		}
		function addChild(id){//添加下级机构
			jp.go('${ctx}/test/tree/form/testTree2/form/add?parent.id='+id);
		}
		function refresh(){//刷新
		    jp.block("#testTree2TreeTable");
		    $testTree2TreeTable.refresh();
		    jp.unblock("#testTree2TreeTable", 500);
		}
</script>
<script type="text/html" id="testTree2TreeTableTpl">
			<td>

					 <% if(shiro.hasPermission("test:tree:form:testTree2:edit") ){ %>
						<a  onclick="edit('{{d.row.id}}')">
		   			<% }else if(shiro.hasPermission("test:tree:form:testTree2:view") ){ %>
						<a  onclick="view('{{d.row.id}}')">
					<% } else { %>
						<a>
					<% } %>

				{{d.row.name === undefined ? "": d.row.name}}
			</a></td>
			<td>
				{{d.row.remarks === undefined ? "": d.row.remarks}}
			</td>
			<td>
				<div class="btn-group jp-dropdown-button">
			 		<button type="button" class="jp-btn-sm jp-btn jp-dropdown-trigger" data-toggle="dropdown">
						<span><i class="fa fa-cog"></i></span>
		                		<i class="jp-icon jp-icon-down"></i>
					</button>
				  <ul class="dropdown-menu" role="menu">
					 <% if(shiro.hasPermission("test:tree:form:testTree2:view") ){ %>
						<li><a onclick="view('{{d.row.id}}')"><i class="fa fa-search-plus"></i> 查看</a></li>
					<% } %>
					 <% if(shiro.hasPermission("test:tree:form:testTree2:edit") ){ %>
						<li><a onclick="edit('{{d.row.id}}')"><i class="fa fa-edit"></i> 修改</a></li>
		   			<% } %>
		   			 <% if(shiro.hasPermission("test:tree:form:testTree2:del") ){ %>
		   				<li><a  onclick="return del(this, '{{d.row.id}}')"><i class="fa fa-trash"></i> 删除</a></li>
					<% } %>
		   			 <% if(shiro.hasPermission("test:tree:form:testTree2:add") ){ %>
						<li><a onclick="addChild( '{{d.row.id}}')"><i class="fa fa-plus"></i> 添加下级组织机构</a></li>
					<% } %>
				  </ul>
				</div>
			</td>
	</script>