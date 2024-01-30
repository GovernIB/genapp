<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="AlumneFields" className="org.fundaciobit.demogenapp.model.fields.AlumneFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,AlumneFields.NOM)}">
        <tr id="alumne_nom_rowid">
          <td id="alumne_nom_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AlumneFields.NOM])?'alumne.nom':__theForm.labels[AlumneFields.NOM]}" /> &nbsp;(*)
             </label>
              <c:if test="${not empty __theForm.help[AlumneFields.NOM]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AlumneFields.NOM]}" ></i>
              </c:if>
            </td>
          <td id="alumne_nom_columnvalueid">
            <form:errors path="alumne.nom" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,AlumneFields.NOM)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,AlumneFields.NOM)? ' uneditable-input' : ''}"  style="" maxlength="100" path="alumne.nom"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AlumneFields.IDIOMAID)}">
        <tr id="alumne_idiomaID_rowid">
          <td id="alumne_idiomaID_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AlumneFields.IDIOMAID])?'alumne.idiomaID':__theForm.labels[AlumneFields.IDIOMAID]}" /> &nbsp;(*)
             </label>
              <c:if test="${not empty __theForm.help[AlumneFields.IDIOMAID]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AlumneFields.IDIOMAID]}" ></i>
              </c:if>
            </td>
          <td id="alumne_idiomaID_columnvalueid">
          <form:errors path="alumne.idiomaID" cssClass="errorField alert alert-danger" />
          <c:if test="${gen:contains(__theForm.readOnlyFields ,AlumneFields.IDIOMAID)}" >
          <form:hidden path="alumne.idiomaID"/>
          <input type="text" readonly="true" class="form-control col-md-9-optional uneditable-input" value="${gen:findValue(__theForm.alumne.idiomaID,__theForm.listOfIdiomaForIdiomaID)}"  />
          </c:if>
          <c:if test="${!gen:contains(__theForm.readOnlyFields ,AlumneFields.IDIOMAID)}" >
          <c:set var="containEmptyValue"  value="false" />
          <form:select id="alumne_idiomaID"  onchange="if(typeof onChangeIdiomaID == 'function') {  onChangeIdiomaID(this); };"  cssClass="form-control col-md-9-optional" path="alumne.idiomaID">
            <c:forEach items="${__theForm.listOfIdiomaForIdiomaID}" var="tmp">
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
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AlumneFields.DATANAIXEMENT)}">
        <tr id="alumne_dataNaixement_rowid">
          <td id="alumne_dataNaixement_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AlumneFields.DATANAIXEMENT])?'alumne.dataNaixement':__theForm.labels[AlumneFields.DATANAIXEMENT]}" /> &nbsp;(*)
             </label>
              <c:if test="${not empty __theForm.help[AlumneFields.DATANAIXEMENT]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AlumneFields.DATANAIXEMENT]}" ></i>
              </c:if>
            </td>
          <td id="alumne_dataNaixement_columnvalueid">
    <form:errors path="alumne.dataNaixement" cssClass="errorField alert alert-danger" />
            <div class="form-group"  style="margin-bottom: 0px;" >
                <div class="input-group date" id="alumne_dataNaixement" data-target-input="nearest">
                      <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,AlumneFields.DATANAIXEMENT)? 'true' : 'false'}" cssClass="form-control datetimepicker-input"  data-target="#alumne_dataNaixement" path="alumne.dataNaixement" />
                    <c:if test="${!gen:contains(__theForm.readOnlyFields ,AlumneFields.DATANAIXEMENT)}" >
                    <div class="input-group-append"  data-target="#alumne_dataNaixement"  data-toggle="datetimepicker">
                        <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                    </div>
                    </c:if>
                </div>
            </div>
        <script type="text/javascript">
            $(function () {
                $('#alumne_dataNaixement').datetimepicker({
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
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AlumneFields.SEXE)}">
        <tr id="alumne_sexe_rowid">
          <td id="alumne_sexe_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AlumneFields.SEXE])?'alumne.sexe':__theForm.labels[AlumneFields.SEXE]}" />
             </label>
              <c:if test="${not empty __theForm.help[AlumneFields.SEXE]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AlumneFields.SEXE]}" ></i>
              </c:if>
            </td>
          <td id="alumne_sexe_columnvalueid">
          <form:errors path="alumne.sexe" cssClass="errorField alert alert-danger" />
          <c:if test="${gen:contains(__theForm.readOnlyFields ,AlumneFields.SEXE)}" >
          <form:hidden path="alumne.sexe"/>
          <input type="text" readonly="true" class="form-control col-md-9-optional uneditable-input" value="${gen:findValue(__theForm.alumne.sexe,__theForm.listOfValuesForSexe)}"  />
          </c:if>
          <c:if test="${!gen:contains(__theForm.readOnlyFields ,AlumneFields.SEXE)}" >
          <c:set var="containEmptyValue"  value="false" />
          <form:select id="alumne_sexe"  onchange="if(typeof onChangeSexe == 'function') {  onChangeSexe(this); };"  cssClass="form-control col-md-9-optional" path="alumne.sexe">
            <c:forEach items="${__theForm.listOfValuesForSexe}" var="tmp">
                <form:option value="${tmp.key}">${tmp.value}</form:option>
                <c:if test="${empty tmp.key}">
                  <c:set var="containEmptyValue"  value="true" />
                </c:if>
            </c:forEach>
            <%-- El camp pot ser null, per la qual cosa afegim una entrada buida si no s'ha definit abans --%>
            <c:if test="${not containEmptyValue}">
              <c:if test="${empty __theForm.alumne.sexe }">
                  <form:option value="" selected="true" ></form:option>
              </c:if>
              <c:if test="${not empty __theForm.alumne.sexe }">
                  <form:option value="" ></form:option>
              </c:if>
            </c:if>
          </form:select>
          </c:if>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AlumneFields.ACTIU)}">
        <tr id="alumne_actiu_rowid">
          <td id="alumne_actiu_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AlumneFields.ACTIU])?'alumne.actiu':__theForm.labels[AlumneFields.ACTIU]}" />
             </label>
              <c:if test="${not empty __theForm.help[AlumneFields.ACTIU]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AlumneFields.ACTIU]}" ></i>
              </c:if>
            </td>
          <td id="alumne_actiu_columnvalueid">
          <c:if test="${!gen:contains(__theForm.readOnlyFields ,AlumneFields.ACTIU)}" >
              <form:errors path="alumne.actiu" cssClass="errorField alert alert-danger" />
              <form:checkbox cssClass="" onclick="javascript:return ${ gen:contains(__theForm.readOnlyFields ,AlumneFields.ACTIU)? 'false' : 'true'}" path="alumne.actiu" />
          </c:if>
          <c:if test="${gen:contains(__theForm.readOnlyFields ,AlumneFields.ACTIU)}" >
                <fmt:message key="genapp.checkbox.${__theForm.alumne.actiu}" />
          </c:if>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AlumneFields.DARRERACCES)}">
        <tr id="alumne_darrerAcces_rowid">
          <td id="alumne_darrerAcces_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AlumneFields.DARRERACCES])?'alumne.darrerAcces':__theForm.labels[AlumneFields.DARRERACCES]}" />
             </label>
              <c:if test="${not empty __theForm.help[AlumneFields.DARRERACCES]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AlumneFields.DARRERACCES]}" ></i>
              </c:if>
            </td>
          <td id="alumne_darrerAcces_columnvalueid">
    <form:errors path="alumne.darrerAcces" cssClass="errorField alert alert-danger" />
            <div class="form-group"  style="margin-bottom: 0px;" >
                <div class="input-group date" id="alumne_darrerAcces" data-target-input="nearest">
                      <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,AlumneFields.DARRERACCES)? 'true' : 'false'}" cssClass="form-control datetimepicker-input"  data-target="#alumne_darrerAcces" path="alumne.darrerAcces" />
                    <c:if test="${!gen:contains(__theForm.readOnlyFields ,AlumneFields.DARRERACCES)}" >
                    <div class="input-group-append"  data-target="#alumne_darrerAcces"  data-toggle="datetimepicker">
                        <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                    </div>
                    </c:if>
                </div>
            </div>
        <script type="text/javascript">
            $(function () {
                $('#alumne_darrerAcces').datetimepicker({
                    format: '${gen:getJSDateTimePattern()}',
                    locale: '${lang}',
                    icons: {
                       time: 'far fa-clock'
                    }
                });
            });
        </script>           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AlumneFields.FOTOID)}">
        <tr id="alumne_fotoID_rowid">
          <td id="alumne_fotoID_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AlumneFields.FOTOID])?'alumne.fotoID':__theForm.labels[AlumneFields.FOTOID]}" />
             </label>
              <c:if test="${not empty __theForm.help[AlumneFields.FOTOID]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AlumneFields.FOTOID]}" ></i>
              </c:if>
            </td>
          <td id="alumne_fotoID_columnvalueid">
              <form:errors path="alumne.fotoID" cssClass="errorField alert alert-danger" />
            <c:if test="${gen:contains(__theForm.readOnlyFields ,AlumneFields.FOTOID)}" >
              <a target="_blank" href="<c:url value="${dem:fileUrl(__theForm.alumne.foto)}"/>">${__theForm.alumne.foto.nom}</a>
            </c:if>
            <c:if test="${!gen:contains(__theForm.readOnlyFields ,AlumneFields.FOTOID)}" >
              <div class="input-group col-md-9-optional" style="padding: 0px">
                <div class="custom-file">
                  <form:input  readonly="${ gen:contains(__theForm.readOnlyFields ,AlumneFields.FOTOID)? 'true' : 'false'}" cssClass="custom-file-input form-control  ${gen:contains(__theForm.readOnlyFields ,AlumneFields.FOTOID)? ' uneditable-input' : ''}"   path="fotoID" type="file" />
                  <label class="custom-file-label" for="fotoID">
                  </label>
                </div>
                <c:choose>
                <c:when test="${not empty __theForm.alumne.foto}">
                <div class="input-group-append">
                  <span class="input-group-text" id="">
                  <small>              <a target="_blank" href="<c:url value="${dem:fileUrl(__theForm.alumne.foto)}"/>">${__theForm.alumne.foto.nom}</a>
</small>
                  </span>
                  <span class="input-group-text" id="">
                        <form:checkbox path="fotoIDDelete"/>
                        <small><fmt:message key="genapp.form.file.delete"/></small>
                  </span>
                </div>
                </c:when>
                <c:otherwise>
                <div class="input-group-append input-group-append-file">
                  <span class="input-group-text" id="fotoID-custom-file-label" style="display:none">
                  <small></small>
                  </span>
                </div>
                <script type="text/javascript">
					$('#fotoID').on('change', function(){
						var ruta = $('#fotoID').val(); 
						var rutaArray = ruta.split('\\');
						$('#fotoID-custom-file-label').css('display','block');
						$('#fotoID-custom-file-label small').html(rutaArray[rutaArray.length - 1]);
					});
				</script>                </c:otherwise>
                </c:choose>
              </div>
            </c:if>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AlumneFields.TITOLACADEMICID)}">
        <tr id="alumne_titolAcademicID_rowid">
          <td id="alumne_titolAcademicID_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AlumneFields.TITOLACADEMICID])?'alumne.titolAcademicID':__theForm.labels[AlumneFields.TITOLACADEMICID]}" />
             </label>
              <c:if test="${not empty __theForm.help[AlumneFields.TITOLACADEMICID]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AlumneFields.TITOLACADEMICID]}" ></i>
              </c:if>
            </td>
          <td id="alumne_titolAcademicID_columnvalueid">
       <form:errors path="alumne.titolAcademic" cssClass="errorField alert alert-danger" />
       <div class="row-fluid col-md-9-optional">
         <ul class="nav nav-tabs" style="margin: 0 15px -1px;">
             <c:forEach items="${__theForm.idiomesTraduccio}" var="idioma" varStatus="counter">
            <li class="nav-item ">
                 <a class="nav-link ${(counter.index == 0)? 'active':''}" href="#${counter.index}_tab_titolAcademic_${idioma.idiomaID}" data-toggle="tab">${idioma.nom}</a>
            </li>
          </c:forEach>
           
         </ul>
         <div class="tab-content well well-white" style="padding:8px;margin:0px;">
           <c:forEach items="${__theForm.idiomesTraduccio}" var="idioma" varStatus="counter">
           <div class="tab-pane ${(counter.index == 0)? 'active':'' }" id="${counter.index}_tab_titolAcademic_${idioma.idiomaID}">
               <form:errors path="alumne.titolAcademic.traduccions['${idioma.idiomaID}'].valor" cssClass="errorField alert alert-danger"/>
               <form:input path="alumne.titolAcademic.traduccions['${idioma.idiomaID}'].valor" cssClass="form-control  ${gen:contains(__theForm.readOnlyFields ,AlumneFields.TITOLACADEMICID)? ' uneditable-input' : ''}" readonly="${gen:contains(__theForm.readOnlyFields ,AlumneFields.TITOLACADEMICID)}" maxlength="4000" />
           </div>
           </c:forEach>
         </div>
       </div>

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AlumneFields.DESPERTADOR)}">
        <tr id="alumne_despertador_rowid">
          <td id="alumne_despertador_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AlumneFields.DESPERTADOR])?'alumne.despertador':__theForm.labels[AlumneFields.DESPERTADOR]}" />
             </label>
              <c:if test="${not empty __theForm.help[AlumneFields.DESPERTADOR]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AlumneFields.DESPERTADOR]}" ></i>
              </c:if>
            </td>
          <td id="alumne_despertador_columnvalueid">
    <form:errors path="alumne.despertador" cssClass="errorField alert alert-danger" />
            <div class="form-group"  style="margin-bottom: 0px;" >
                <div class="input-group date" id="alumne_despertador" data-target-input="nearest">
                      <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,AlumneFields.DESPERTADOR)? 'true' : 'false'}" cssClass="form-control datetimepicker-input"  data-target="#alumne_despertador" path="alumne.despertador" />
                    <c:if test="${!gen:contains(__theForm.readOnlyFields ,AlumneFields.DESPERTADOR)}" >
                    <div class="input-group-append"  data-target="#alumne_despertador"  data-toggle="datetimepicker">
                        <div class="input-group-text"><i class="far fa-clock"></i></div>
                    </div>
                    </c:if>
                </div>
            </div>
        <script type="text/javascript">
            $(function () {
                $('#alumne_despertador').datetimepicker({
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
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AlumneFields.PAGINAWEB)}">
        <tr id="alumne_paginaWeb_rowid">
          <td id="alumne_paginaWeb_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AlumneFields.PAGINAWEB])?'alumne.paginaWeb':__theForm.labels[AlumneFields.PAGINAWEB]}" />
             </label>
              <c:if test="${not empty __theForm.help[AlumneFields.PAGINAWEB]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AlumneFields.PAGINAWEB]}" ></i>
              </c:if>
            </td>
          <td id="alumne_paginaWeb_columnvalueid">
           <c:if test="${gen:contains(__theForm.readOnlyFields ,AlumneFields.PAGINAWEB)}">

             <c:if test="${ not empty __theForm.alumne.paginaWeb}">
               <a href="${__theForm.alumne.paginaWeb}" target="_blank">${__theForm.alumne.paginaWeb}</a>

             </c:if>
           </c:if>

           <c:if test="${not (gen:contains(__theForm.readOnlyFields ,AlumneFields.PAGINAWEB))}">

            <form:errors path="alumne.paginaWeb" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,AlumneFields.PAGINAWEB)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,AlumneFields.PAGINAWEB)? ' uneditable-input' : ''}"  style="" maxlength="255" path="alumne.paginaWeb"   />

           </c:if>

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,AlumneFields.DESCRIPCIO)}">
        <tr id="alumne_descripcio_rowid">
          <td id="alumne_descripcio_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[AlumneFields.DESCRIPCIO])?'alumne.descripcio':__theForm.labels[AlumneFields.DESCRIPCIO]}" />
             </label>
              <c:if test="${not empty __theForm.help[AlumneFields.DESCRIPCIO]}">
              <i class="fas fa-info-circle" title="${__theForm.help[AlumneFields.DESCRIPCIO]}" ></i>
              </c:if>
            </td>
          <td id="alumne_descripcio_columnvalueid">
              <form:errors path="alumne.descripcio" cssClass="errorField alert alert-danger" />
       <form:textarea cssClass=" ${gen:contains(__theForm.readOnlyFields ,AlumneFields.DESCRIPCIO)? 'mceEditorReadOnly':'mceEditor'}"  path="alumne.descripcio"  />
           </td>
        </tr>
        </c:if>
        
