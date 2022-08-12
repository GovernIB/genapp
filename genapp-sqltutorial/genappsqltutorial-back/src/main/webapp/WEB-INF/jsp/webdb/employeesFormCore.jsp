<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="EmployeesFields" className="org.fundaciobit.genappsqltutorial.model.fields.EmployeesFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,EmployeesFields.LASTNAME)}">
        <tr id="employees_lastname_rowid">
          <td id="employees_lastname_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[EmployeesFields.LASTNAME])?'employees.lastname':__theForm.labels[EmployeesFields.LASTNAME]}" />
             </label>
              <c:if test="${not empty __theForm.help[EmployeesFields.LASTNAME]}">
              <i class="fas fa-info-circle" title="${__theForm.help[EmployeesFields.LASTNAME]}" ></i>
              </c:if>
            </td>
          <td id="employees_lastname_columnvalueid">
            <form:errors path="employees.lastname" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EmployeesFields.LASTNAME)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,EmployeesFields.LASTNAME)? ' uneditable-input' : ''}"  style="" maxlength="255" path="employees.lastname"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EmployeesFields.FIRSTNAME)}">
        <tr id="employees_firstname_rowid">
          <td id="employees_firstname_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[EmployeesFields.FIRSTNAME])?'employees.firstname':__theForm.labels[EmployeesFields.FIRSTNAME]}" />
             </label>
              <c:if test="${not empty __theForm.help[EmployeesFields.FIRSTNAME]}">
              <i class="fas fa-info-circle" title="${__theForm.help[EmployeesFields.FIRSTNAME]}" ></i>
              </c:if>
            </td>
          <td id="employees_firstname_columnvalueid">
            <form:errors path="employees.firstname" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EmployeesFields.FIRSTNAME)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,EmployeesFields.FIRSTNAME)? ' uneditable-input' : ''}"  style="" maxlength="255" path="employees.firstname"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EmployeesFields.BIRTHDATE)}">
        <tr id="employees_birthdate_rowid">
          <td id="employees_birthdate_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[EmployeesFields.BIRTHDATE])?'employees.birthdate':__theForm.labels[EmployeesFields.BIRTHDATE]}" />
             </label>
              <c:if test="${not empty __theForm.help[EmployeesFields.BIRTHDATE]}">
              <i class="fas fa-info-circle" title="${__theForm.help[EmployeesFields.BIRTHDATE]}" ></i>
              </c:if>
            </td>
          <td id="employees_birthdate_columnvalueid">
    <form:errors path="employees.birthdate" cssClass="errorField alert alert-danger" />
            <div class="form-group">
                <div class="input-group date" id="employees_birthdate" data-target-input="nearest">
                      <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EmployeesFields.BIRTHDATE)? 'true' : 'false'}" cssClass="form-control datetimepicker-input"  data-target="#employees_birthdate" path="employees.birthdate" />
                    <c:if test="${!gen:contains(__theForm.readOnlyFields ,EmployeesFields.BIRTHDATE)}" >
                    <div class="input-group-append"  data-target="#employees_birthdate"  data-toggle="datetimepicker">
                        <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                    </div>
                    </c:if>
                </div>
            </div>
        <script type="text/javascript">
            $(function () {
                $('#employees_birthdate').datetimepicker({
                    format: '${gen:getJSDatePattern()}',
                    locale: '${lang}',
                    icons: {
                       time: 'far fa-clock'
                    }
                });
            });
        </script>           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EmployeesFields.PHOTO)}">
        <tr id="employees_photo_rowid">
          <td id="employees_photo_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[EmployeesFields.PHOTO])?'employees.photo':__theForm.labels[EmployeesFields.PHOTO]}" />
             </label>
              <c:if test="${not empty __theForm.help[EmployeesFields.PHOTO]}">
              <i class="fas fa-info-circle" title="${__theForm.help[EmployeesFields.PHOTO]}" ></i>
              </c:if>
            </td>
          <td id="employees_photo_columnvalueid">
            <form:errors path="employees.photo" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EmployeesFields.PHOTO)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,EmployeesFields.PHOTO)? ' uneditable-input' : ''}"  style="" maxlength="255" path="employees.photo"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EmployeesFields.NOTES)}">
        <tr id="employees_notes_rowid">
          <td id="employees_notes_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[EmployeesFields.NOTES])?'employees.notes':__theForm.labels[EmployeesFields.NOTES]}" />
             </label>
              <c:if test="${not empty __theForm.help[EmployeesFields.NOTES]}">
              <i class="fas fa-info-circle" title="${__theForm.help[EmployeesFields.NOTES]}" ></i>
              </c:if>
            </td>
          <td id="employees_notes_columnvalueid">
              <form:errors path="employees.notes" cssClass="errorField alert alert-danger" />
  <table style="width:100%">
  <tr>
  <td>
       <form:textarea rows="3" wrap="soft" style="overflow:auto;display: inline;resize:both;" cssClass="form-control col-md-9-optional" readonly="${ gen:contains(__theForm.readOnlyFields ,EmployeesFields.NOTES)? 'true' : 'false'}" path="employees.notes"  />
   </td>
   <td style="width:40px">
      <div id="dropdownMenuButton_notes" style="vertical-align:top;display:inline;position:relative;">
        <button  class="btn btn-secondary btn-sm dropdown-toggle" type="button" style="margin-left:0px;"><span class="caret"></span></button>
        <div id="dropdownMenuContainer_notes" class="dropdown-menu dropdown-menu-right">
          <a class="dropdown-item" href="#" onclick="javascript:var ta=document.getElementById('employees.notes'); ta.wrap='off';" >No Wrap</a>
          <a class="dropdown-item"  href="#" onclick="javascript:var ta=document.getElementById('employees.notes'); ta.wrap='soft';">Soft Wrap</a>
          <a class="dropdown-item" href="#" onclick="javascript:var ta=document.getElementById('employees.notes'); ta.wrap='hard';">Hard Wrap</a>
        </div>
      </div>
      <script type="text/javascript">
			$('#dropdownMenuButton_notes').on('click', function(){
					var valor = ($('#dropdownMenuContainer_notes').css('display') != 'none') ? 'none' : 'block';
                 $('#dropdownMenuContainer_notes').css('display', valor);
                 return false;
				});
      </script>   </td>
   </tr>
   </table>
           </td>
        </tr>
        </c:if>
        
