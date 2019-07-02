<script>
	    var $categoryTreeTable=null;  
		$(document).ready(function() {
			$categoryTreeTable=$('#categoryTreeTable').treeTable({  
		    	   theme:'vsStyle',	           
					expandLevel : 2,
					column:0,
					checkbox: false,
		            url:'${ctx}/test/shop/category/getChildren?parentId=',  
		            callback:function(item) { 
		            	 var treeTableTpl= $("#categoryTreeTableTpl").html();
		            	 item.dict = {};

		            	 var result = laytpl(treeTableTpl).render({
								  row: item
							});
		                return result;                   
		            },  
		            beforeClick: function($categoryTreeTable, id) { 
		                //异步获取数据 这里模拟替换处理  
		                $categoryTreeTable.refreshPoint(id);  
		            },  
		            beforeExpand : function($categoryTreeTable, id) {   
		            },  
		            afterExpand : function($categoryTreeTable, id) {  
		            },  
		            beforeClose : function($categoryTreeTable, id) {    
		            	
		            }  
		        });
		        
		        $categoryTreeTable.initParents('${parentIds!}', "0");//在保存编辑时定位展开当前节点
		       
		});
		
		function del(con,id){  
			jp.confirm('确认要删除商品类型吗？', function(){
				jp.loading();
	       	  	$.get("${ctx}/test/shop/category/delete?id="+id, function(data){
	       	  		if(data.success){
	       	  			$categoryTreeTable.del(id);
	       	  			jp.success(data.msg);
	       	  		}else{
	       	  			jp.error(data.msg);
	       	  		}
	       	  	})
	       	   
       		});
	
		}

		function refreshNode(data) {//刷新节点
            var current_id = data.body.category.id;
			var target = $categoryTreeTable.get(current_id);
			var old_parent_id = target.attr("pid") == undefined?'1':target.attr("pid");
			var current_parent_id = data.body.category.parentId;
			var current_parent_ids = data.body.category.parentIds;
			if(old_parent_id == current_parent_id){
				if(current_parent_id == '0'){
					$categoryTreeTable.refreshPoint(-1);
				}else{
					$categoryTreeTable.refreshPoint(current_parent_id);
				}
			}else{
				$categoryTreeTable.del(current_id);//刷新删除旧节点
				$categoryTreeTable.initParents(current_parent_ids, "0");
			}
        }

		function add(){//新增
			jp.openSaveDialog('新建商品类型', '${ctx}/test/shop/category/form/add','800px', '500px');
		}
		function edit(id){//编辑
			jp.openSaveDialog('修改商品类型', '${ctx}/test/shop/category/form/edit?id='+id,'800px', '500px');
		}
		function view(id){//查看
			jp.openViewDialog('查看商品类型', '${ctx}/test/shop/category/form/view?id='+id,'800px', '500px');
		}
		function addChild(id){//添加下级机构
			jp.openSaveDialog('添加下级商品类型', '${ctx}/test/shop/category/form/add?parent.id='+id,'800px', '500px')
		}
		function refresh(){//刷新
		    jp.block("#categoryTreeTable");
		    $categoryTreeTable.refresh();
			jp.unblock("#categoryTreeTable", 500);
		}
</script>
<script type="text/html" id="categoryTreeTableTpl">
			<td>

					 <% if(shiro.hasPermission("test:shop:category:edit") ){ %>
						<a  href="#" onclick="edit('{{d.row.id}}')">
		   			<% }else if(shiro.hasPermission("test:shop:category:view") ){ %>
						<a  href="#" onclick="view('{{d.row.id}}')">
					<% } else { %>
						<a  href="#">
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
					 <% if(shiro.hasPermission("test:shop:category:view") ){ %>
						<li><a onclick="view('{{d.row.id}}')"><i class="fa fa-search-plus"></i> 查看</a></li>
					<% } %>
					 <% if(shiro.hasPermission("test:shop:category:edit") ){ %>
						<li><a onclick="edit('{{d.row.id}}')"><i class="fa fa-edit"></i> 修改</a></li>
		   			<% } %>
		   			 <% if(shiro.hasPermission("test:shop:category:del") ){ %>
		   				<li><a  onclick="return del(this, '{{d.row.id}}')"><i class="fa fa-trash"></i> 删除</a></li>
					<% } %>
		   			 <% if(shiro.hasPermission("test:shop:category:add") ){ %>
						<li><a onclick="addChild( '{{d.row.id}}')"><i class="fa fa-plus"></i> 添加下级商品类型</a></li>
					<% } %>
				  </ul>
				</div>
			</td>
	</script>