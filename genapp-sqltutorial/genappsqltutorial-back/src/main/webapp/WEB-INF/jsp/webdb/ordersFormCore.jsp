<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="OrdersFields" className="org.fundaciobit.genappsqltutorial.model.fields.OrdersFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,OrdersFields.CUSTOMERID)}">
        <tr id="orders_customerid_rowid">
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[OrdersFields.CUSTOMERID])?'orders.customerid':__theForm.labels[OrdersFields.CUSTOMERID]}" />
              <c:if test="${not empty __theForm.help[OrdersFields.CUSTOMERID]}">
              <i class="fas fa-info-circle" title="${__theForm.help[OrdersFields.CUSTOMERID]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
          <form:errors path="orders.customerid" cssClass="errorField alert alert-danger" />
          <c:if test="${gen:contains(__theForm.readOnlyFields ,OrdersFields.CUSTOMERID)}" >
          <form:hidden path="orders.customerid"/>
          <input type="text" readonly="true" class="form-control input-xxlarge uneditable-input" value="${gen:findValue(__theForm.orders.customerid,__theForm.listOfCustomersForCustomerid)}"  />
          </c:if>
          <c:if test="${!gen:contains(__theForm.readOnlyFields ,OrdersFields.CUSTOMERID)}" >
          <c:set var="containEmptyValue"  value="false" />
          <form:select id="orders_customerid"  onchange="if(typeof onChangeCustomerid == 'function') {  onChangeCustomerid(this); };"  cssClass="form-control col-md-8" path="orders.customerid">
            <c:forEach items="${__theForm.listOfCustomersForCustomerid}" var="tmp">
                <form:option value="${tmp.key}">${tmp.value}</form:option>
                <c:if test="${empty tmp.key}">
                  <c:set var="containEmptyValue"  value="true" />
                </c:if>
            </c:forEach>
            <%-- El camp pot ser null, per la qual cosa afegim una entrada buida si no s'ha definit abans --%>
            <c:if test="${not containEmptyValue}">
              <c:if test="${empty __theForm.orders.customerid }">
                  <form:option value="" selected="true" ></form:option>
              </c:if>
              <c:if test="${not empty __theForm.orders.customerid }">
                  <form:option value="" ></form:option>
              </c:if>
            </c:if>
          </form:select>
          </c:if>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,OrdersFields.EMPLOYEEID)}">
        <tr id="orders_employeeid_rowid">
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[OrdersFields.EMPLOYEEID])?'orders.employeeid':__theForm.labels[OrdersFields.EMPLOYEEID]}" />
              <c:if test="${not empty __theForm.help[OrdersFields.EMPLOYEEID]}">
              <i class="fas fa-info-circle" title="${__theForm.help[OrdersFields.EMPLOYEEID]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
          <form:errors path="orders.employeeid" cssClass="errorField alert alert-danger" />
          <c:if test="${gen:contains(__theForm.readOnlyFields ,OrdersFields.EMPLOYEEID)}" >
          <form:hidden path="orders.employeeid"/>
          <input type="text" readonly="true" class="form-control input-xxlarge uneditable-input" value="${gen:findValue(__theForm.orders.employeeid,__theForm.listOfEmployeesForEmployeeid)}"  />
          </c:if>
          <c:if test="${!gen:contains(__theForm.readOnlyFields ,OrdersFields.EMPLOYEEID)}" >
          <c:set var="containEmptyValue"  value="false" />
          <form:select id="orders_employeeid"  onchange="if(typeof onChangeEmployeeid == 'function') {  onChangeEmployeeid(this); };"  cssClass="form-control col-md-8" path="orders.employeeid">
            <c:forEach items="${__theForm.listOfEmployeesForEmployeeid}" var="tmp">
                <form:option value="${tmp.key}">${tmp.value}</form:option>
                <c:if test="${empty tmp.key}">
                  <c:set var="containEmptyValue"  value="true" />
                </c:if>
            </c:forEach>
            <%-- El camp pot ser null, per la qual cosa afegim una entrada buida si no s'ha definit abans --%>
            <c:if test="${not containEmptyValue}">
              <c:if test="${empty __theForm.orders.employeeid }">
                  <form:option value="" selected="true" ></form:option>
              </c:if>
              <c:if test="${not empty __theForm.orders.employeeid }">
                  <form:option value="" ></form:option>
              </c:if>
            </c:if>
          </form:select>
          </c:if>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,OrdersFields.ORDERDATE)}">
        <tr id="orders_orderdate_rowid">
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[OrdersFields.ORDERDATE])?'orders.orderdate':__theForm.labels[OrdersFields.ORDERDATE]}" />
              <c:if test="${not empty __theForm.help[OrdersFields.ORDERDATE]}">
              <i class="fas fa-info-circle" title="${__theForm.help[OrdersFields.ORDERDATE]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
    <form:errors path="orders.orderdate" cssClass="errorField alert alert-danger" />
    <div class="container">
      <div class="row">
            <div class="form-group">
                <div class="input-group date" id="orders_orderdate" data-target-input="nearest">
                      <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,OrdersFields.ORDERDATE)? 'true' : 'false'}" cssClass="form-control datetimepicker-input"  data-target="#orders_orderdate" path="orders.orderdate" />
                    <c:if test="${!gen:contains(__theForm.readOnlyFields ,OrdersFields.ORDERDATE)}" >
                    <div class="input-group-append"  data-target="#orders_orderdate"  data-toggle="datetimepicker">
                        <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                    </div>
                    </c:if>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            $(function () {
                $('#orders_orderdate').datetimepicker({
                    format: '${gen:getJSDatePattern()}',
                    locale: '${lang}',
                    icons: {
                       time: 'fa fa-calendar'
                    }
                });
            });
        </script>      </div>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,OrdersFields.SHIPPERID)}">
        <tr id="orders_shipperid_rowid">
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[OrdersFields.SHIPPERID])?'orders.shipperid':__theForm.labels[OrdersFields.SHIPPERID]}" />
              <c:if test="${not empty __theForm.help[OrdersFields.SHIPPERID]}">
              <i class="fas fa-info-circle" title="${__theForm.help[OrdersFields.SHIPPERID]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
          <form:errors path="orders.shipperid" cssClass="errorField alert alert-danger" />
          <c:if test="${gen:contains(__theForm.readOnlyFields ,OrdersFields.SHIPPERID)}" >
          <form:hidden path="orders.shipperid"/>
          <input type="text" readonly="true" class="form-control input-xxlarge uneditable-input" value="${gen:findValue(__theForm.orders.shipperid,__theForm.listOfSuppliersForShipperid)}"  />
          </c:if>
          <c:if test="${!gen:contains(__theForm.readOnlyFields ,OrdersFields.SHIPPERID)}" >
          <c:set var="containEmptyValue"  value="false" />
          <form:select id="orders_shipperid"  onchange="if(typeof onChangeShipperid == 'function') {  onChangeShipperid(this); };"  cssClass="form-control col-md-8" path="orders.shipperid">
            <c:forEach items="${__theForm.listOfSuppliersForShipperid}" var="tmp">
                <form:option value="${tmp.key}">${tmp.value}</form:option>
                <c:if test="${empty tmp.key}">
                  <c:set var="containEmptyValue"  value="true" />
                </c:if>
            </c:forEach>
            <%-- El camp pot ser null, per la qual cosa afegim una entrada buida si no s'ha definit abans --%>
            <c:if test="${not containEmptyValue}">
              <c:if test="${empty __theForm.orders.shipperid }">
                  <form:option value="" selected="true" ></form:option>
              </c:if>
              <c:if test="${not empty __theForm.orders.shipperid }">
                  <form:option value="" ></form:option>
              </c:if>
            </c:if>
          </form:select>
          </c:if>
           </td>
        </tr>
        </c:if>
        
