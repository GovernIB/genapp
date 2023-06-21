<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="CustomersFields" className="org.fundaciobit.genappsqltutorial.model.fields.CustomersFields"/>

  <%-- HIDDEN PARAMS: FILTER BY --%> 
  <form:hidden path="visibleFilterBy"/>

  <%-- FILTRAR PER - INICI --%>
  
  <c:set var="displayFilterDiv" value="${__theFilterForm.visibleFilterBy?'':'display:none;'}" />  
  
  <div id="FilterDiv" class="wellgroupfilter formbox" style="${displayFilterDiv} margin-bottom:3px; margin-left: 1px; padding:3px;">

      <div class="page-header">
        <fmt:message key="genapp.form.filterby"/>
        
        <div class="float-right">

           <a class="float-right" style="margin-left:10px" href="#"> <i title="<fmt:message key="genapp.form.hidefilter"/>" onclick="document.getElementById('FilterDiv').style.display='none'; document.getElementById('FilterButton').style.display='inline';" class="far fa-window-close"></i></a>
           <input style="margin-left: 3px" class="btn btn-sm btn-warning float-right" type="button" onclick="clear_form_elements(this.form)" value="<fmt:message key="genapp.form.clean"/>"/>
           <input style="margin-left: 3px" class="btn btn-sm btn-warning float-right" type="reset" value="<fmt:message key="genapp.form.reset"/>"/>
           <input style="margin-left: 3px" class="btn btn-sm btn-primary float-right" type="submit" value="<fmt:message key="genapp.form.search"/>"/>

        </div>
      </div>
      <div class="form-inline">
      
      <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
      <c:if test="${ __entry.key < 0 && not empty __entry.value.searchBy }">
      <div class="input-group" style="padding-right: 4px;padding-bottom: 4px;">
        <span class="add-on"><fmt:message key="${__entry.value.codeName}" />:</span>
        <fmt:message key="genapp.form.searchby" var="cercaperAF" >
          <fmt:param>
            <fmt:message key="${__entry.value.codeName}" />
          </fmt:param>
        </fmt:message>
        <c:choose>
          <c:when test="${gen:isFieldSearchInRange(__entry.value.searchBy)}">
            <span class="add-on"><fmt:message key="genapp.from" /></span>
            <input id="${__entry.value.searchBy.fullName}" name="${__entry.value.searchBy.fullName}" class="input-small input-medium" type="text" value="${__entry.value.searchByValue}"/>
            <span class="add-on"><fmt:message key="genapp.to" /></span>
            <input id="${__entry.value.searchBy.fullName}Fins" name="${__entry.value.searchBy.fullName}Fins" class="input-small input-medium search-query" type="text" value="${__entry.value.searchByValueFins}"/>
          </c:when>
          <c:otherwise>
            <input id="${__entry.value.searchBy.fullName}" name="${__entry.value.searchBy.fullName}" class="search-query input-medium" placeholder="${cercaperAF}" type="text" value="${__entry.value.searchByValue}"/>
          </c:otherwise>
        </c:choose>
      </div>
      </c:if>
      </c:forEach>


        <c:if test="${gen:contains(__theFilterForm.filterByFields ,CustomersFields.CUSTOMERID)}">
            <%-- FILTRE NUMERO --%>      
            <div class="input-group" style="padding-right: 4px;padding-bottom: 4px;">
              <span class="add-on"><fmt:message key="customers.customerid" />:</span>

              <span class="add-on">&nbsp;<fmt:message key="genapp.from" /></span>
              
              <form:input cssClass="input-append input-small" path="customeridDesde" />


              <span class="add-on">&nbsp;<fmt:message key="genapp.to" />&nbsp;</span>

              <form:input cssClass="input-append input-small search-query" path="customeridFins" />

            </div>


        </c:if>
        <c:if test="${gen:contains(__theFilterForm.filterByFields ,CustomersFields.CUSTOMERNAME)}">
            <%-- FILTRE STRING --%>
            <div class="input-prepend" style="padding-right: 4px;padding-bottom: 4px;">
              <fmt:message key="customers.customername" var="customername" />
              <fmt:message key="genapp.form.searchby" var="cercapercustomername" >                
                 <fmt:param value="${customername}"/>
              </fmt:message>
              <span class="add-on"><c:out value="${customername}" />:</span>
              <form:input cssClass="search-query input-medium" placeholder="${cercapercustomername}" path="customername" />
            </div>


        </c:if>
        <c:if test="${gen:contains(__theFilterForm.filterByFields ,CustomersFields.CONTACTNAME)}">
            <%-- FILTRE STRING --%>
            <div class="input-prepend" style="padding-right: 4px;padding-bottom: 4px;">
              <fmt:message key="customers.contactname" var="contactname" />
              <fmt:message key="genapp.form.searchby" var="cercapercontactname" >                
                 <fmt:param value="${contactname}"/>
              </fmt:message>
              <span class="add-on"><c:out value="${contactname}" />:</span>
              <form:input cssClass="search-query input-medium" placeholder="${cercapercontactname}" path="contactname" />
            </div>


        </c:if>
        <c:if test="${gen:contains(__theFilterForm.filterByFields ,CustomersFields.ADDRESS)}">
            <%-- FILTRE STRING --%>
            <div class="input-prepend" style="padding-right: 4px;padding-bottom: 4px;">
              <fmt:message key="customers.address" var="address" />
              <fmt:message key="genapp.form.searchby" var="cercaperaddress" >                
                 <fmt:param value="${address}"/>
              </fmt:message>
              <span class="add-on"><c:out value="${address}" />:</span>
              <form:input cssClass="search-query input-medium" placeholder="${cercaperaddress}" path="address" />
            </div>


        </c:if>
        <c:if test="${gen:contains(__theFilterForm.filterByFields ,CustomersFields.CITY)}">
            <%-- FILTRE STRING --%>
            <div class="input-prepend" style="padding-right: 4px;padding-bottom: 4px;">
              <fmt:message key="customers.city" var="city" />
              <fmt:message key="genapp.form.searchby" var="cercapercity" >                
                 <fmt:param value="${city}"/>
              </fmt:message>
              <span class="add-on"><c:out value="${city}" />:</span>
              <form:input cssClass="search-query input-medium" placeholder="${cercapercity}" path="city" />
            </div>


        </c:if>
        <c:if test="${gen:contains(__theFilterForm.filterByFields ,CustomersFields.COUNTRY)}">
            <%-- FILTRE STRING --%>
            <div class="input-prepend" style="padding-right: 4px;padding-bottom: 4px;">
              <fmt:message key="customers.country" var="country" />
              <fmt:message key="genapp.form.searchby" var="cercapercountry" >                
                 <fmt:param value="${country}"/>
              </fmt:message>
              <span class="add-on"><c:out value="${country}" />:</span>
              <form:input cssClass="search-query input-medium" placeholder="${cercapercountry}" path="country" />
            </div>


        </c:if>
        <c:if test="${gen:contains(__theFilterForm.filterByFields ,CustomersFields.POSTALCODE)}">
            <%-- FILTRE STRING --%>
            <div class="input-prepend" style="padding-right: 4px;padding-bottom: 4px;">
              <fmt:message key="customers.postalcode" var="postalcode" />
              <fmt:message key="genapp.form.searchby" var="cercaperpostalcode" >                
                 <fmt:param value="${postalcode}"/>
              </fmt:message>
              <span class="add-on"><c:out value="${postalcode}" />:</span>
              <form:input cssClass="search-query input-medium" placeholder="${cercaperpostalcode}" path="postalcode" />
            </div>


        </c:if>

      <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
      <c:if test="${ __entry.key >= 0 && not empty __entry.value.searchBy }">
      <div class="input-group" style="padding-right: 4px;padding-bottom: 4px;">
        <span class="add-on"><fmt:message key="${__entry.value.codeName}" />:</span>
        <fmt:message key="genapp.form.searchby" var="cercaperAF" >
          <fmt:param>
            <fmt:message key="${__entry.value.codeName}" />
          </fmt:param>
        </fmt:message>
        <c:choose>
          <c:when test="${gen:isFieldSearchInRange(__entry.value.searchBy)}">
            <span class="add-on"><fmt:message key="genapp.from" /></span>
            <input id="${__entry.value.searchBy.fullName}" name="${__entry.value.searchBy.fullName}" class="input-small input-medium" type="text" value="${__entry.value.searchByValue}"/>
            <span class="add-on"><fmt:message key="genapp.to" /></span>
            <input id="${__entry.value.searchBy.fullName}Fins" name="${__entry.value.searchBy.fullName}Fins" class="input-small input-medium search-query" type="text" value="${__entry.value.searchByValueFins}"/>
          </c:when>
          <c:otherwise>
            <input id="${__entry.value.searchBy.fullName}" name="${__entry.value.searchBy.fullName}" class="search-query input-medium" placeholder="${cercaperAF}" type="text" value="${__entry.value.searchByValue}"/>
          </c:otherwise>
        </c:choose>
      </div>
      </c:if>
      </c:forEach>
      </div>
    </div>



    <%-- FILTRAR PER - FINAL --%>
  