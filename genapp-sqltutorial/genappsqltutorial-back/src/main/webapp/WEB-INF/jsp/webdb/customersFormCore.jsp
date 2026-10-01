<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="CustomersFields" className="org.fundaciobit.genappsqltutorial.model.fields.CustomersFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,CustomersFields.CUSTOMERNAME)}">
        <tr id="customers_customername_rowid">
          <td id="customers_customername_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[CustomersFields.CUSTOMERNAME])?'customers.customername':__theForm.labels[CustomersFields.CUSTOMERNAME]}" />
             </label>
              <c:if test="${not empty __theForm.help[CustomersFields.CUSTOMERNAME]}">
              <i class="fas fa-info-circle" title="${__theForm.help[CustomersFields.CUSTOMERNAME]}" ></i>
              </c:if>
            </td>
          <td id="customers_customername_columnvalueid">
            <form:errors path="customers.customername" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,CustomersFields.CUSTOMERNAME)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,CustomersFields.CUSTOMERNAME)? ' uneditable-input' : ''}"  style="" maxlength="255" path="customers.customername"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,CustomersFields.CONTACTNAME)}">
        <tr id="customers_contactname_rowid">
          <td id="customers_contactname_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[CustomersFields.CONTACTNAME])?'customers.contactname':__theForm.labels[CustomersFields.CONTACTNAME]}" />
             </label>
              <c:if test="${not empty __theForm.help[CustomersFields.CONTACTNAME]}">
              <i class="fas fa-info-circle" title="${__theForm.help[CustomersFields.CONTACTNAME]}" ></i>
              </c:if>
            </td>
          <td id="customers_contactname_columnvalueid">
            <form:errors path="customers.contactname" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,CustomersFields.CONTACTNAME)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,CustomersFields.CONTACTNAME)? ' uneditable-input' : ''}"  style="" maxlength="255" path="customers.contactname"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,CustomersFields.ADDRESS)}">
        <tr id="customers_address_rowid">
          <td id="customers_address_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[CustomersFields.ADDRESS])?'customers.address':__theForm.labels[CustomersFields.ADDRESS]}" />
             </label>
              <c:if test="${not empty __theForm.help[CustomersFields.ADDRESS]}">
              <i class="fas fa-info-circle" title="${__theForm.help[CustomersFields.ADDRESS]}" ></i>
              </c:if>
            </td>
          <td id="customers_address_columnvalueid">
              <form:errors path="customers.address" cssClass="errorField alert alert-danger" />
  <table style="width:100%">
  <tr>
  <td>
       <form:textarea rows="3" wrap="soft" style="overflow:auto;display: inline;resize:both;" cssClass="form-control col-md-9-optional" readonly="${ gen:contains(__theForm.readOnlyFields ,CustomersFields.ADDRESS)? 'true' : 'false'}" path="customers.address"  />
   </td>
   <td style="width:40px">
      <div id="dropdownMenuButton_address" style="vertical-align:top;display:inline;position:relative;">
        <button  class="btn btn-secondary btn-sm dropdown-toggle" type="button" style="margin-left:0px;"><span class="caret"></span></button>
        <div id="dropdownMenuContainer_address" class="dropdown-menu dropdown-menu-right">
          <a class="dropdown-item" href="#" onclick="javascript:var ta=document.getElementById('customers.address'); ta.wrap='off';" >No Wrap</a>
          <a class="dropdown-item"  href="#" onclick="javascript:var ta=document.getElementById('customers.address'); ta.wrap='soft';">Soft Wrap</a>
          <a class="dropdown-item" href="#" onclick="javascript:var ta=document.getElementById('customers.address'); ta.wrap='hard';">Hard Wrap</a>
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
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,CustomersFields.CITY)}">
        <tr id="customers_city_rowid">
          <td id="customers_city_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[CustomersFields.CITY])?'customers.city':__theForm.labels[CustomersFields.CITY]}" />
             </label>
              <c:if test="${not empty __theForm.help[CustomersFields.CITY]}">
              <i class="fas fa-info-circle" title="${__theForm.help[CustomersFields.CITY]}" ></i>
              </c:if>
            </td>
          <td id="customers_city_columnvalueid">
            <form:errors path="customers.city" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,CustomersFields.CITY)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,CustomersFields.CITY)? ' uneditable-input' : ''}"  style="" maxlength="255" path="customers.city"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,CustomersFields.COUNTRY)}">
        <tr id="customers_country_rowid">
          <td id="customers_country_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[CustomersFields.COUNTRY])?'customers.country':__theForm.labels[CustomersFields.COUNTRY]}" />
             </label>
              <c:if test="${not empty __theForm.help[CustomersFields.COUNTRY]}">
              <i class="fas fa-info-circle" title="${__theForm.help[CustomersFields.COUNTRY]}" ></i>
              </c:if>
            </td>
          <td id="customers_country_columnvalueid">
            <form:errors path="customers.country" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,CustomersFields.COUNTRY)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,CustomersFields.COUNTRY)? ' uneditable-input' : ''}"  style="" maxlength="255" path="customers.country"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,CustomersFields.POSTALCODE)}">
        <tr id="customers_postalcode_rowid">
          <td id="customers_postalcode_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[CustomersFields.POSTALCODE])?'customers.postalcode':__theForm.labels[CustomersFields.POSTALCODE]}" />
             </label>
              <c:if test="${not empty __theForm.help[CustomersFields.POSTALCODE]}">
              <i class="fas fa-info-circle" title="${__theForm.help[CustomersFields.POSTALCODE]}" ></i>
              </c:if>
            </td>
          <td id="customers_postalcode_columnvalueid">
            <form:errors path="customers.postalcode" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,CustomersFields.POSTALCODE)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,CustomersFields.POSTALCODE)? ' uneditable-input' : ''}"  style="" maxlength="255" path="customers.postalcode"   />

           </td>
        </tr>
        </c:if>
        
