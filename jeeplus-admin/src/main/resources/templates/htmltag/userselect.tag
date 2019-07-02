	<input id="${id}Id" name="${name}"  type="hidden" value="${value!}"/>


	<span class="jp-input-search jp-input-search-enter-button jp-input-affix-wrapper">
		<input class="jp-input"  id="${id}Name" name="${labelName!}" ${allowInput!true==true?'':'readonly="readonly"'}  type="text" value="${labelValue!}" data-msg-required="${dataMsgRequired!}" type="text">
		<span class="jp-input-suffix">
			<button type="button"   id="${id}Button" class="jp-btn jp-input-search-button jp-btn-primary  ${disabled!} ${hideBtn!'false'=='true' ? 'hide' : ''}"><i class="jp-icon jp-icon-search"></i></button>
			  <% if(allowClear!'false' == 'true'){ %>
	             	 <button type="button" id="${id}DelButton" class="close" data-dismiss="alert" style="position: absolute; top: -1px; right: 53px; z-index: 999; display: block;">×</button>

	            <% } %>
		</span>
	</span>
	 <label id="${id}Name-error" class="error" for="${id}Name" style="display:none"></label>
<script type="text/javascript">
	$("#${id}Button, #${id}Name").click(function(){
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		// 正常打开	
		
		jp.openUserSelectDialog(${isMultiSelected!false? true:false},function(ids, names){
			$("#${id}Id").val(ids.replace(/u_/ig,""));
			$("#${id}Name").val(names);
			$("#${id}Name").focus();
		})
	
	});
	
	$("#${id}DelButton").click(function(){
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		// 清除	
		$("#${id}Id").val("");
		$("#${id}Name").val("");
		$("#${id}Name").focus();
	
	});
</script>