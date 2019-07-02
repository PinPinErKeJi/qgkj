


<select id="${path}" name="${path}" readonly="${readonly!false}" class="${class!}  jp-input jp-select jp-select-enabled">

    <% if(notAllowNull!false == false){ %>
    <option value="" label=""></option>
    <% } %>
    <%
        for(item in items){
    %>
    <option value="${item[itemValue]}" ${item[itemValue] == value!""?"selected":""} label="${item[itemLabel]}" class="jp-select-selection-selected-value"></option>

    <% }%>
</select>
<label id="${path!}-error" class="error" for="${path!}" style="display: none;"></label>