<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="CategoriesFields" className="org.fundaciobit.genappsqltutorial.model.fields.CategoriesFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,CategoriesFields.CATEGORYNAME)}">
        <tr id="categories_categoryName_rowid">
          <td id="categories_categoryName_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[CategoriesFields.CATEGORYNAME])?'categories.categoryName':__theForm.labels[CategoriesFields.CATEGORYNAME]}" />
             </label>
              <c:if test="${not empty __theForm.help[CategoriesFields.CATEGORYNAME]}">
              <i class="fas fa-info-circle" title="${__theForm.help[CategoriesFields.CATEGORYNAME]}" ></i>
              </c:if>
            </td>
          <td id="categories_categoryName_columnvalueid">
            <form:errors path="categories.categoryName" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,CategoriesFields.CATEGORYNAME)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,CategoriesFields.CATEGORYNAME)? ' uneditable-input' : ''}"  style="" maxlength="255" path="categories.categoryName"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,CategoriesFields.DESCRIPTION)}">
        <tr id="categories_description_rowid">
          <td id="categories_description_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[CategoriesFields.DESCRIPTION])?'categories.description':__theForm.labels[CategoriesFields.DESCRIPTION]}" />
             </label>
              <c:if test="${not empty __theForm.help[CategoriesFields.DESCRIPTION]}">
              <i class="fas fa-info-circle" title="${__theForm.help[CategoriesFields.DESCRIPTION]}" ></i>
              </c:if>
            </td>
          <td id="categories_description_columnvalueid">
              <form:errors path="categories.description" cssClass="errorField alert alert-danger" />
  <table style="width:100%">
  <tr>
  <td>
       <form:textarea rows="3" wrap="soft" style="overflow:auto;display: inline;resize:both;" cssClass="form-control col-md-9-optional" readonly="${ gen:contains(__theForm.readOnlyFields ,CategoriesFields.DESCRIPTION)? 'true' : 'false'}" path="categories.description"  />
   </td>
   <td style="width:40px">
      <div id="dropdownMenuButton_description" style="vertical-align:top;display:inline;position:relative;">
        <button  class="btn btn-secondary btn-sm dropdown-toggle" type="button" style="margin-left:0px;"><span class="caret"></span></button>
        <div id="dropdownMenuContainer_description" class="dropdown-menu dropdown-menu-right">
          <a class="dropdown-item" href="#" onclick="javascript:var ta=document.getElementById('categories.description'); ta.wrap='off';" >No Wrap</a>
          <a class="dropdown-item"  href="#" onclick="javascript:var ta=document.getElementById('categories.description'); ta.wrap='soft';">Soft Wrap</a>
          <a class="dropdown-item" href="#" onclick="javascript:var ta=document.getElementById('categories.description'); ta.wrap='hard';">Hard Wrap</a>
        </div>
      </div>
      <script type="text/javascript">
			$('#dropdownMenuButton_description').on('click', function(){
					var valor = ($('#dropdownMenuContainer_description').css('display') != 'none') ? 'none' : 'block';
                 $('#dropdownMenuContainer_description').css('display', valor);
                 return false;
				});
      </script>   </td>
   </tr>
   </table>
           </td>
        </tr>
        </c:if>
        
