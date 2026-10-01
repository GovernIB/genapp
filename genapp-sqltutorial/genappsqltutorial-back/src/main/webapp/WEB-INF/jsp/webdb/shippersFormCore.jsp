<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="ShippersFields" className="org.fundaciobit.genappsqltutorial.model.fields.ShippersFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,ShippersFields.SHIPPERNAME)}">
        <tr id="shippers_shippername_rowid">
          <td id="shippers_shippername_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[ShippersFields.SHIPPERNAME])?'shippers.shippername':__theForm.labels[ShippersFields.SHIPPERNAME]}" />
             </label>
              <c:if test="${not empty __theForm.help[ShippersFields.SHIPPERNAME]}">
              <i class="fas fa-info-circle" title="${__theForm.help[ShippersFields.SHIPPERNAME]}" ></i>
              </c:if>
            </td>
          <td id="shippers_shippername_columnvalueid">
            <form:errors path="shippers.shippername" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,ShippersFields.SHIPPERNAME)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,ShippersFields.SHIPPERNAME)? ' uneditable-input' : ''}"  style="" maxlength="255" path="shippers.shippername"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,ShippersFields.PHONE)}">
        <tr id="shippers_phone_rowid">
          <td id="shippers_phone_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[ShippersFields.PHONE])?'shippers.phone':__theForm.labels[ShippersFields.PHONE]}" />
             </label>
              <c:if test="${not empty __theForm.help[ShippersFields.PHONE]}">
              <i class="fas fa-info-circle" title="${__theForm.help[ShippersFields.PHONE]}" ></i>
              </c:if>
            </td>
          <td id="shippers_phone_columnvalueid">
            <form:errors path="shippers.phone" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,ShippersFields.PHONE)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,ShippersFields.PHONE)? ' uneditable-input' : ''}"  style="" maxlength="255" path="shippers.phone"   />

           </td>
        </tr>
        </c:if>
        
