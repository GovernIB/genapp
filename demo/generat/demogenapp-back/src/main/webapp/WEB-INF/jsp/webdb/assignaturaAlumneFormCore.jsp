<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="AssignaturaAlumneFields" className="org.fundaciobit.demogenapp.model.fields.AssignaturaAlumneFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,AssignaturaAlumneFields.ALUMNEID)}">
        <tr id="assignaturaAlumne_alumneID_rowid">
          <td id="assignaturaAlumne_alumneID_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AssignaturaAlumneFields.ALUMNEID])?'assignaturaAlumne.alumneID':__theForm.labels[AssignaturaAlumneFields.ALUMNEID]}" /> &nbsp;(*)
             </label>
              <c:if test="${not empty __theForm.help[AssignaturaAlumneFields.ALUMNEID]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AssignaturaAlumneFields.ALUMNEID]}" ></i>
              </c:if>
            </td>
          <td id="assignaturaAlumne_alumneID_columnvalueid">
          <form:errors path="assignaturaAlumne.alumneID" cssClass="errorField alert alert-danger" />
          <c:if test="${gen:contains(__theForm.readOnlyFields ,AssignaturaAlumneFields.ALUMNEID)}" >
          <form:hidden path="assignaturaAlumne.alumneID"/>
          <input type="text" readonly="true" class="form-control col-md-9-optional uneditable-input" value="${gen:findValue(__theForm.assignaturaAlumne.alumneID,__theForm.listOfAlumneForAlumneID)}"  />
          </c:if>
          <c:if test="${!gen:contains(__theForm.readOnlyFields ,AssignaturaAlumneFields.ALUMNEID)}" >
          <c:set var="containEmptyValue"  value="false" />
          <form:select id="assignaturaAlumne_alumneID"  onchange="if(typeof onChangeAlumneID == 'function') {  onChangeAlumneID(this); };"  cssClass="form-control col-md-9-optional" path="assignaturaAlumne.alumneID">
            <c:forEach items="${__theForm.listOfAlumneForAlumneID}" var="tmp">
                <form:option value="${tmp.key}">${tmp.value}</form:option>
                <c:if test="${empty tmp.key}">
                  <c:set var="containEmptyValue"  value="true" />
                </c:if>
            </c:forEach>
          </form:select>
          </c:if>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AssignaturaAlumneFields.ASSIGNATURAID)}">
        <tr id="assignaturaAlumne_assignaturaID_rowid">
          <td id="assignaturaAlumne_assignaturaID_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AssignaturaAlumneFields.ASSIGNATURAID])?'assignaturaAlumne.assignaturaID':__theForm.labels[AssignaturaAlumneFields.ASSIGNATURAID]}" /> &nbsp;(*)
             </label>
              <c:if test="${not empty __theForm.help[AssignaturaAlumneFields.ASSIGNATURAID]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AssignaturaAlumneFields.ASSIGNATURAID]}" ></i>
              </c:if>
            </td>
          <td id="assignaturaAlumne_assignaturaID_columnvalueid">
          <form:errors path="assignaturaAlumne.assignaturaID" cssClass="errorField alert alert-danger" />
          <c:if test="${gen:contains(__theForm.readOnlyFields ,AssignaturaAlumneFields.ASSIGNATURAID)}" >
          <form:hidden path="assignaturaAlumne.assignaturaID"/>
          <input type="text" readonly="true" class="form-control col-md-9-optional uneditable-input" value="${gen:findValue(__theForm.assignaturaAlumne.assignaturaID,__theForm.listOfAssignaturaForAssignaturaID)}"  />
          </c:if>
          <c:if test="${!gen:contains(__theForm.readOnlyFields ,AssignaturaAlumneFields.ASSIGNATURAID)}" >
          <c:set var="containEmptyValue"  value="false" />
          <form:select id="assignaturaAlumne_assignaturaID"  onchange="if(typeof onChangeAssignaturaID == 'function') {  onChangeAssignaturaID(this); };"  cssClass="form-control col-md-9-optional" path="assignaturaAlumne.assignaturaID">
            <c:forEach items="${__theForm.listOfAssignaturaForAssignaturaID}" var="tmp">
                <form:option value="${tmp.key}">${tmp.value}</form:option>
                <c:if test="${empty tmp.key}">
                  <c:set var="containEmptyValue"  value="true" />
                </c:if>
            </c:forEach>
          </form:select>
          </c:if>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AssignaturaAlumneFields.NOTA)}">
        <tr id="assignaturaAlumne_nota_rowid">
          <td id="assignaturaAlumne_nota_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AssignaturaAlumneFields.NOTA])?'assignaturaAlumne.nota':__theForm.labels[AssignaturaAlumneFields.NOTA]}" />
             </label>
              <c:if test="${not empty __theForm.help[AssignaturaAlumneFields.NOTA]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AssignaturaAlumneFields.NOTA]}" ></i>
              </c:if>
            </td>
          <td id="assignaturaAlumne_nota_columnvalueid">
            <form:errors path="assignaturaAlumne.nota" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,AssignaturaAlumneFields.NOTA)? 'true' : 'false'}" cssClass="w-50 form-control  ${gen:contains(__theForm.readOnlyFields ,AssignaturaAlumneFields.NOTA)? ' uneditable-input' : ''}"  style=""  path="assignaturaAlumne.nota"   />

           </td>
        </tr>
        </c:if>
        
