
<div class="radio-list">
    <%
        for(item in items){
    %>
    <label class="radio-inline p-0">
        <div class="radio radio-info">
            <input name="${path}" id="${path}${itemLP.index}" ${item.value == value! ?"checked":""} type="radio"  class="${class!}"  value="${item[itemValue]}"/>
            <label for="${path}${itemLP.index}">${item[itemLabel]}</label>
        </div>
    </label>
    <% }%>
</div>
