<script>
		$(document).ready(function() {
			var to = false;
			$('#search_q').keyup(function () {
				if(to) { clearTimeout(to); }
				to = setTimeout(function () {
					var v = $('#search_q').val();
					$('#syjgjsTree').jstree(true).search(v);
				}, 250);
			});
			$('#syjgjsTree').jstree({
				'core' : {
					"multiple": false,
                    "animation": 0,
                     "themes": {"icons": true, "stripes": false},
					'data' : {
						"url" : "${ctx}/jcda_ryxx/syjg/treeData",
						"dataType" : "json" 
					}
				},
				"conditionalselect" : function (node, event) {
					return false;
				},
				'plugins': ["contextmenu", 'types', 'wholerow', "search"],
				"contextmenu": {
					"items": function (node) {
						var tmp = $.jstree.defaults.contextmenu.items();
						delete tmp.create.action;
						delete tmp.rename.action;
						tmp.rename = null;
						tmp.create = {
							"label": "添加下级人员信息",
							"action": function (data) {
								var inst = jQuery.jstree.reference(data.reference),
									obj = inst.get_node(data.reference);
								jp.openSaveDialog('添加下级人员信息', '${ctx}/jcda_ryxx/syjg/form/edit?parent.id=' + obj.id + "&parent.name=" + obj.text, '800px', '500px');
							}
						};
						tmp.remove = {
							"label": "删除人员信息",
							"action": function (data) {
								var inst = jQuery.jstree.reference(data.reference),
									obj = inst.get_node(data.reference);
								jp.confirm('确认要删除人员信息吗？', function(){
									jp.loading();
									$.get("${ctx}/jcda_ryxx/syjg/delete?id="+obj.id, function(data){
										if(data.success){
											$('#syjgjsTree').jstree("refresh");
											jp.success(data.msg);
										}else{
											jp.error(data.msg);
										}
									})

								});
							}
						}
						tmp.ccp = {
							"label": "编辑人员信息",
							"action": function (data) {
								var inst = jQuery.jstree.reference(data.reference),
									obj = inst.get_node(data.reference);
								var parentId = inst.get_parent(data.reference);
								var parent = inst.get_node(parentId);
								jp.openSaveDialog('编辑人员信息', '${ctx}/jcda_ryxx/syjg/form/edit?id=' + obj.id, '800px', '500px');
							}
						}
						return tmp;
					}

				},
				"types":{
					'default' : { 'icon' : 'fa fa-folder-o' },
					'1' : {'icon' : 'fa fa-home'},
					'2' : {'icon' : 'fa fa-umbrella' },
					'3' : { 'icon' : 'fa fa-group'},
					'4' : { 'icon' : 'fa fa-file-text-o' }
				}

			}).bind("activate_node.jstree", function (obj, e) {
				var node = $('#syjgjsTree').jstree(true).get_selected(true)[0];
				var opt = {
					silent: true,
					query:{
						'jg.id':node.id
					}
				};
				$("#jgId").val(node.id);
				$("#jgName").val(node.text);
				$('#jcdaygxxTable').bootstrapTable('refresh',opt);
			}).on('loaded.jstree', function() {
				$("#syjgjsTree").jstree('open_all');
			});
		});

		function refreshTree() {
            		$('#syjgjsTree').jstree("refresh");
        	}
	</script>