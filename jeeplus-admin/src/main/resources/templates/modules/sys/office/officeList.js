<script>
var $treeTable = $('#treeTable').bootstrapTreeTable({
	type: 'get',                   // 请求方式（*）
	url: "${ctx}/sys/office/data",            // 请求后台的URL（*）
	ajaxParams : {},               // 请求数据的ajax的data属性
	toolbar: "#toolbar",      //顶部工具条
	expandColumn : 1,              // 在哪一列上面显示展开按钮
	columns: [{
		field: 'selectItem',
		checkbox: true
	},{
		title: '机构名称',
		field: 'name'
	},{
		title: '归属区域',
		field: 'area.name'
	},{
		title: '机构编码',
		field: 'code'
	},{
		title: '机构类型',
		field: 'typeLabel'
	},{
		title: '备注',
		field: 'remarks'
	},
		{
			title: '操作',
			width:'300px',
			align: "center",
			formatter: function(value,row, index) {
				var actions = []
			<% if(shiro.hasPermission("sys:office:view")){ %>
					actions.push('<a onclick=\'jp.openViewDialog("查看机构", "${ctx}/sys/office/form?id='+row.id+'","800px", "600px")\'>查看</a> ')
					actions.push('<div class="jp-divider jp-divider-vertical"></div>')
					<% } %>
			<% if(shiro.hasPermission("sys:office:edit")){ %>
					actions.push('<a onclick=\'jp.openSaveDialog("修改机构", "${ctx}/sys/office/form?id='+row.id+'","800px", "600px")\'>修改</a> ')
					actions.push('<div class="jp-divider jp-divider-vertical"></div>')
					<% } %>
			<% if(shiro.hasPermission("sys:office:del")){ %>
					actions.push('<a onclick=\'return del("'+row.id+'")\'>删除</a> ')
					actions.push('<div class="jp-divider jp-divider-vertical"></div>')
					<% } %>
			<% if(shiro.hasPermission("sys:office:add")){ %>
					actions.push('<a onclick=\'jp.openSaveDialog("添加下级机构", "${ctx}/sys/office/form?parent.id='+row.id+'","800px", "600px")\'>添加下级机构</a> ');
				<% } %>

				return actions.join('');
			}
		}]
});

function del(id){
	jp.confirm('确认要删除机构吗？', function(){
		jp.loading();
		$.get("${ctx}/sys/office/delete?id="+id, function(data){
			if(data.success){
				$treeTable.bootstrapTreeTable('refresh');
				jp.success(data.msg);
			}else{
				jp.error(data.msg);
			}
		})

	});

}

var _expandFlag_all = false;
$("#expandAllBtn").click(function(){
	if(_expandFlag_all){
		$treeTable.bootstrapTreeTable('expandAll');
	}else{
		$treeTable.bootstrapTreeTable('collapseAll');
	}
	_expandFlag_all = _expandFlag_all?false:true;
})
	function refresh() {
		$treeTable.bootstrapTreeTable('refresh');
	}
</script>
