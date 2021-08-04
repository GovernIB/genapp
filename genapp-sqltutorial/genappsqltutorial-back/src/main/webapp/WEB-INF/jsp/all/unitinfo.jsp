<%@page import="org.springframework.security.core.Authentication"
%><%@page import="org.springframework.context.i18n.LocaleContextHolder"
%><%@ page language="java" 
%><%@ include file="/WEB-INF/jsp/moduls/includes.jsp" 
%>
<script>


/**
 * Global variable to store the ids of the status of the current dragged ace editor.
 */
window.draggingAceEditor = {};

function makeAceEditorResizable(editor){
    var id_editor = editor.container.id;
    var id_dragbar = '#' + id_editor + '_dragbar';
    var id_wrapper = '#' + id_editor + '_wrapper';
    var wpoffset = 0;
    window.draggingAceEditor[id_editor] = false;

    $(id_dragbar).mousedown(function(e) {
        e.preventDefault();

        window.draggingAceEditor[id_editor] = true;
    
        var _editor = $('#' + id_editor);
        var top_offset = _editor.offset().top - wpoffset;
    
        // Set editor opacity to 0 to make transparent so our wrapper div shows
        _editor.css('opacity', 0);
    
        // handle mouse movement
        $(document).mousemove(function(e){
            var actualY = e.pageY - wpoffset;
            // editor height
            var eheight = actualY - top_offset;
            
            // Set wrapper height
            $(id_wrapper).css('height', eheight);
            
            // Set dragbar opacity while dragging (set to 0 to not show)
            $(id_dragbar).css('opacity', 0.15);
        });
    });
    
    $(document).mouseup(function(e){
        if (window.draggingAceEditor[id_editor])
        {
            var ctx_editor = $('#' + id_editor);
    
            var actualY = e.pageY - wpoffset;
            var top_offset = ctx_editor.offset().top - wpoffset;
            var eheight = actualY - top_offset;
    
            $( document ).unbind('mousemove');
            
            // Set dragbar opacity back to 1
            $(id_dragbar).css('opacity', 1);
            
            // Set height on actual editor element, and opacity back to 1
            ctx_editor.css('height', eheight).css('opacity', 1);
            
            // Trigger ace editor resize()
            editor.resize();

            window.draggingAceEditor[id_editor] = false;
        }
    });
}



</script>



<div class="clear"></div>
<div class="spacer"></div>

<h2>${unitInfo.index }: ${unitInfo.titol} </h2>

<ul>
 <%--   <li> Index: <b>${unitInfo.index }</b> </li>
    <li> T&iacute;tol: <b>${unitInfo.titol}</b> </li>  
    <li> Descripci&oacute;:  </li>
  
    <li> Nom Curt: <b>${unitInfo.nomCurt }</b> </li>
    <li> Nom de la Classe: <b>${unitInfo.classeNom }</b> </li>
     --%>
    <li> Classe: <b>${unitInfo.classe }</b> </li>
    <li> URL W3schools: <b><a href="${unitInfo.url}" target="_blank">${unitInfo.url}</a></b> </li>
</ul>

<b>${unitInfo.descripcio }</b>

<script src="<c:url value="/js/ace/ace.js"/>" type="text/javascript" charset="utf-8"></script>

<c:forEach var="p" items="${unitInfo.parts}">
    <br/>
    <hr/>

    <h4><c:out value="${p.titol}"></c:out></h4>
    <p style="box-sizing: border-box;color: rgb(0, 0, 0);font-family: Verdana, sans-serif;font-size: 15px;line-height: 22.5px;margin-bottom: 18px;">
    <c:out value="${p.descripcio}"></c:out>
    </p>
    SQL: <code>${p.sql}</code><br/>
    GenQL:<br/>
    <%-- 
    <textarea rows="4" cols="90"><c:out value="${p.sourcecode}" escapeXml="true"/></textarea>
    --%>
    <div>
    <div id="editor_${p.metode}" style="height:${(((fn:length(p.sourcecode)/40) + 2) * 20) }px; width:800px;"><c:out value="${p.sourcecode}" escapeXml="true" /></div>
    </div>

    <script>
        var editor_${p.metode} = ace.edit("editor_${p.metode}");
        editor_${p.metode}.setTheme("ace/theme/monokai");
        //editor_${p.metode}.setTheme("ace/theme/eclipse");
        editor_${p.metode}.session.setMode("ace/mode/java");
        editor_${p.metode}.setOptions({
            //fontFamily: 'Inconsolata',
            fontSize: '12pt',
            //enableBasicAutocompletion: true,
            //enableLiveAutocompletion: true,
            wrap: true
        });
        editor_${p.metode}.resize();
        editor_${p.metode}.setAutoScrollEditorIntoView(true);
        
        makeAceEditorResizable(editor_${p.metode});
        
    </script>
    <button class="btn btn-primary" onclick="alert('hola')">Executar test</button>     
    <br/>
    <br/>
</c:forEach>
