<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="AssignaturaFields" className="org.fundaciobit.demogenapp.model.fields.AssignaturaFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,AssignaturaFields.NOM)}">
        <tr id="assignatura_nom_rowid">
          <td id="assignatura_nom_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AssignaturaFields.NOM])?'assignatura.nom':__theForm.labels[AssignaturaFields.NOM]}" /> &nbsp;(*)
             </label>
              <c:if test="${not empty __theForm.help[AssignaturaFields.NOM]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AssignaturaFields.NOM]}" ></i>
              </c:if>
            </td>
          <td id="assignatura_nom_columnvalueid">
            <form:errors path="assignatura.nom" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,AssignaturaFields.NOM)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,AssignaturaFields.NOM)? ' uneditable-input' : ''}"  style="" maxlength="100" path="assignatura.nom"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AssignaturaFields.CREDITS)}">
        <tr id="assignatura_credits_rowid">
          <td id="assignatura_credits_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AssignaturaFields.CREDITS])?'assignatura.credits':__theForm.labels[AssignaturaFields.CREDITS]}" /> &nbsp;(*)
             </label>
              <c:if test="${not empty __theForm.help[AssignaturaFields.CREDITS]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AssignaturaFields.CREDITS]}" ></i>
              </c:if>
            </td>
          <td id="assignatura_credits_columnvalueid">
            <form:errors path="assignatura.credits" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,AssignaturaFields.CREDITS)? 'true' : 'false'}" cssClass="w-25 form-control  ${gen:contains(__theForm.readOnlyFields ,AssignaturaFields.CREDITS)? ' uneditable-input' : ''}"  style=""  path="assignatura.credits"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AssignaturaFields.DIASETMANA)}">
        <tr id="assignatura_diaSetmana_rowid">
          <td id="assignatura_diaSetmana_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AssignaturaFields.DIASETMANA])?'assignatura.diaSetmana':__theForm.labels[AssignaturaFields.DIASETMANA]}" />
             </label>
              <c:if test="${not empty __theForm.help[AssignaturaFields.DIASETMANA]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AssignaturaFields.DIASETMANA]}" ></i>
              </c:if>
            </td>
          <td id="assignatura_diaSetmana_columnvalueid">
          <form:errors path="assignatura.diaSetmana" cssClass="errorField alert alert-danger" />
          <c:if test="${gen:contains(__theForm.readOnlyFields ,AssignaturaFields.DIASETMANA)}" >
          <form:hidden path="assignatura.diaSetmana"/>
          <input type="text" readonly="true" class="form-control col-md-9-optional uneditable-input" value="${gen:findValue(__theForm.assignatura.diaSetmana,__theForm.listOfValuesForDiaSetmana)}"  />
          </c:if>
          <c:if test="${!gen:contains(__theForm.readOnlyFields ,AssignaturaFields.DIASETMANA)}" >
          <c:set var="containEmptyValue"  value="false" />
          <form:select id="assignatura_diaSetmana"  onchange="if(typeof onChangeDiaSetmana == 'function') {  onChangeDiaSetmana(this); };"  cssClass="form-control col-md-9-optional" path="assignatura.diaSetmana">
            <c:forEach items="${__theForm.listOfValuesForDiaSetmana}" var="tmp">
                <form:option value="${tmp.key}">${tmp.value}</form:option>
                <c:if test="${empty tmp.key}">
                  <c:set var="containEmptyValue"  value="true" />
                </c:if>
            </c:forEach>
            <%-- El camp pot ser null, per la qual cosa afegim una entrada buida si no s'ha definit abans --%>
            <c:if test="${not containEmptyValue}">
              <c:if test="${empty __theForm.assignatura.diaSetmana }">
                  <form:option value="" selected="true" ></form:option>
              </c:if>
              <c:if test="${not empty __theForm.assignatura.diaSetmana }">
                  <form:option value="" ></form:option>
              </c:if>
            </c:if>
          </form:select>
          </c:if>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AssignaturaFields.HORA)}">
        <tr id="assignatura_hora_rowid">
          <td id="assignatura_hora_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AssignaturaFields.HORA])?'assignatura.hora':__theForm.labels[AssignaturaFields.HORA]}" />
             </label>
              <c:if test="${not empty __theForm.help[AssignaturaFields.HORA]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AssignaturaFields.HORA]}" ></i>
              </c:if>
            </td>
          <td id="assignatura_hora_columnvalueid">
    <form:errors path="assignatura.hora" cssClass="errorField alert alert-danger" />
            <div class="form-group"  style="margin-bottom: 0px;" >
                <div class="input-group date" id="assignatura_hora" data-target-input="nearest">
                      <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,AssignaturaFields.HORA)? 'true' : 'false'}" cssClass="form-control datetimepicker-input"  data-target="#assignatura_hora" path="assignatura.hora" />
                    <c:if test="${!gen:contains(__theForm.readOnlyFields ,AssignaturaFields.HORA)}" >
                    <div class="input-group-append"  data-target="#assignatura_hora"  data-toggle="datetimepicker">
                        <div class="input-group-text"><i class="far fa-clock"></i></div>
                    </div>
                    </c:if>
                </div>
            </div>
        <script type="text/javascript">
            $(function () {
                $('#assignatura_hora').datetimepicker({
                    format: '${gen:getJSTimePattern()}',
                    locale: '${lang}',
                    icons: {
                       time: 'far fa-clock'
                    }
                });
            });
        </script>           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AssignaturaFields.DESCRIPCIO)}">
        <tr id="assignatura_descripcio_rowid">
          <td id="assignatura_descripcio_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AssignaturaFields.DESCRIPCIO])?'assignatura.descripcio':__theForm.labels[AssignaturaFields.DESCRIPCIO]}" />
             </label>
              <c:if test="${not empty __theForm.help[AssignaturaFields.DESCRIPCIO]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AssignaturaFields.DESCRIPCIO]}" ></i>
              </c:if>
            </td>
          <td id="assignatura_descripcio_columnvalueid">
              <form:errors path="assignatura.descripcio" cssClass="errorField alert alert-danger" />
  <table style="width:100%">
  <tr>
  <td>
       <form:textarea rows="3" wrap="soft" style="overflow:auto;display: inline;resize:both;" cssClass="form-control col-md-9-optional" readonly="${ gen:contains(__theForm.readOnlyFields ,AssignaturaFields.DESCRIPCIO)? 'true' : 'false'}" path="assignatura.descripcio"  />
   </td>
   <td style="width:40px">
      <div id="dropdownMenuButton_descripcio" style="vertical-align:top;display:inline;position:relative;">
        <button  class="btn btn-secondary btn-sm dropdown-toggle" type="button" style="margin-left:0px;"><span class="caret"></span></button>
        <div id="dropdownMenuContainer_descripcio" class="dropdown-menu dropdown-menu-right">
          <a class="dropdown-item" href="#" onclick="javascript:var ta=document.getElementById('assignatura.descripcio'); ta.wrap='off';" >No Wrap</a>
          <a class="dropdown-item"  href="#" onclick="javascript:var ta=document.getElementById('assignatura.descripcio'); ta.wrap='soft';">Soft Wrap</a>
          <a class="dropdown-item" href="#" onclick="javascript:var ta=document.getElementById('assignatura.descripcio'); ta.wrap='hard';">Hard Wrap</a>
        </div>
      </div>
      <script type="text/javascript">
			$('#dropdownMenuButton_descripcio').on('click', function(){
					var valor = ($('#dropdownMenuContainer_descripcio').css('display') != 'none') ? 'none' : 'block';
                 $('#dropdownMenuContainer_descripcio').css('display', valor);
                 return false;
				});
      </script>   </td>
   </tr>
   </table>
           </td>
        </tr>
        </c:if>
        
