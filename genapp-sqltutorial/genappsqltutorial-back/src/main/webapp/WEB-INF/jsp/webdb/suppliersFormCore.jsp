<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="SuppliersFields" className="org.fundaciobit.genappsqltutorial.model.fields.SuppliersFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,SuppliersFields.SUPPLIERNAME)}">
        <tr id="suppliers_suppliername_rowid">
          <td id="suppliers_suppliername_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[SuppliersFields.SUPPLIERNAME])?'suppliers.suppliername':__theForm.labels[SuppliersFields.SUPPLIERNAME]}" />
             </label>
              <c:if test="${not empty __theForm.help[SuppliersFields.SUPPLIERNAME]}">
              <i class="fas fa-info-circle" title="${__theForm.help[SuppliersFields.SUPPLIERNAME]}" ></i>
              </c:if>
            </td>
          <td id="suppliers_suppliername_columnvalueid">
            <form:errors path="suppliers.suppliername" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,SuppliersFields.SUPPLIERNAME)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,SuppliersFields.SUPPLIERNAME)? ' uneditable-input' : ''}"  style="" maxlength="255" path="suppliers.suppliername"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,SuppliersFields.CONTACTNAME)}">
        <tr id="suppliers_contactname_rowid">
          <td id="suppliers_contactname_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[SuppliersFields.CONTACTNAME])?'suppliers.contactname':__theForm.labels[SuppliersFields.CONTACTNAME]}" />
             </label>
              <c:if test="${not empty __theForm.help[SuppliersFields.CONTACTNAME]}">
              <i class="fas fa-info-circle" title="${__theForm.help[SuppliersFields.CONTACTNAME]}" ></i>
              </c:if>
            </td>
          <td id="suppliers_contactname_columnvalueid">
            <form:errors path="suppliers.contactname" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,SuppliersFields.CONTACTNAME)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,SuppliersFields.CONTACTNAME)? ' uneditable-input' : ''}"  style="" maxlength="255" path="suppliers.contactname"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,SuppliersFields.ADDRESS)}">
        <tr id="suppliers_address_rowid">
          <td id="suppliers_address_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[SuppliersFields.ADDRESS])?'suppliers.address':__theForm.labels[SuppliersFields.ADDRESS]}" />
             </label>
              <c:if test="${not empty __theForm.help[SuppliersFields.ADDRESS]}">
              <i class="fas fa-info-circle" title="${__theForm.help[SuppliersFields.ADDRESS]}" ></i>
              </c:if>
            </td>
          <td id="suppliers_address_columnvalueid">
              <form:errors path="suppliers.address" cssClass="errorField alert alert-danger" />
  <table style="width:100%">
  <tr>
  <td>
       <form:textarea rows="3" wrap="soft" style="overflow:auto;display: inline;resize:both;" cssClass="form-control col-md-9-optional" readonly="${ gen:contains(__theForm.readOnlyFields ,SuppliersFields.ADDRESS)? 'true' : 'false'}" path="suppliers.address"  />
   </td>
   <td style="width:40px">
      <div id="dropdownMenuButton_address" style="vertical-align:top;display:inline;position:relative;">
        <button  class="btn btn-secondary btn-sm dropdown-toggle" type="button" style="margin-left:0px;"><span class="caret"></span></button>
        <div id="dropdownMenuContainer_address" class="dropdown-menu dropdown-menu-right">
          <a class="dropdown-item" href="#" onclick="javascript:var ta=document.getElementById('suppliers.address'); ta.wrap='off';" >No Wrap</a>
          <a class="dropdown-item"  href="#" onclick="javascript:var ta=document.getElementById('suppliers.address'); ta.wrap='soft';">Soft Wrap</a>
          <a class="dropdown-item" href="#" onclick="javascript:var ta=document.getElementById('suppliers.address'); ta.wrap='hard';">Hard Wrap</a>
        </div>
      </div>
      <script type="text/javascript">
			$('#dropdownMenuButton_address').on('click', function(){
					var valor = ($('#dropdownMenuContainer_address').css('display') != 'none') ? 'none' : 'block';
                 $('#dropdownMenuContainer_address').css('display', valor);
                 return false;
				});
      </script>   </td>
   </tr>
   </table>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,SuppliersFields.CITY)}">
        <tr id="suppliers_city_rowid">
          <td id="suppliers_city_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[SuppliersFields.CITY])?'suppliers.city':__theForm.labels[SuppliersFields.CITY]}" />
             </label>
              <c:if test="${not empty __theForm.help[SuppliersFields.CITY]}">
              <i class="fas fa-info-circle" title="${__theForm.help[SuppliersFields.CITY]}" ></i>
              </c:if>
            </td>
          <td id="suppliers_city_columnvalueid">
            <form:errors path="suppliers.city" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,SuppliersFields.CITY)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,SuppliersFields.CITY)? ' uneditable-input' : ''}"  style="" maxlength="255" path="suppliers.city"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,SuppliersFields.POSTALCODE)}">
        <tr id="suppliers_postalcode_rowid">
          <td id="suppliers_postalcode_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[SuppliersFields.POSTALCODE])?'suppliers.postalcode':__theForm.labels[SuppliersFields.POSTALCODE]}" />
             </label>
              <c:if test="${not empty __theForm.help[SuppliersFields.POSTALCODE]}">
              <i class="fas fa-info-circle" title="${__theForm.help[SuppliersFields.POSTALCODE]}" ></i>
              </c:if>
            </td>
          <td id="suppliers_postalcode_columnvalueid">
            <form:errors path="suppliers.postalcode" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,SuppliersFields.POSTALCODE)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,SuppliersFields.POSTALCODE)? ' uneditable-input' : ''}"  style="" maxlength="255" path="suppliers.postalcode"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,SuppliersFields.COUNTRY)}">
        <tr id="suppliers_country_rowid">
          <td id="suppliers_country_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[SuppliersFields.COUNTRY])?'suppliers.country':__theForm.labels[SuppliersFields.COUNTRY]}" />
             </label>
              <c:if test="${not empty __theForm.help[SuppliersFields.COUNTRY]}">
              <i class="fas fa-info-circle" title="${__theForm.help[SuppliersFields.COUNTRY]}" ></i>
              </c:if>
            </td>
          <td id="suppliers_country_columnvalueid">
            <form:errors path="suppliers.country" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,SuppliersFields.COUNTRY)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,SuppliersFields.COUNTRY)? ' uneditable-input' : ''}"  style="" maxlength="255" path="suppliers.country"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,SuppliersFields.PHONE)}">
        <tr id="suppliers_phone_rowid">
          <td id="suppliers_phone_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[SuppliersFields.PHONE])?'suppliers.phone':__theForm.labels[SuppliersFields.PHONE]}" />
             </label>
              <c:if test="${not empty __theForm.help[SuppliersFields.PHONE]}">
              <i class="fas fa-info-circle" title="${__theForm.help[SuppliersFields.PHONE]}" ></i>
              </c:if>
            </td>
          <td id="suppliers_phone_columnvalueid">
            <form:errors path="suppliers.phone" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,SuppliersFields.PHONE)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,SuppliersFields.PHONE)? ' uneditable-input' : ''}"  style="" maxlength="255" path="suppliers.phone"   />

           </td>
        </tr>
        </c:if>
        
