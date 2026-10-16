<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp" 
%>
<div class="clear"></div>
<div class="spacer"></div>

<div id="propietatGlobal_listheader" class="filterLine lead" style="margin-bottom: 10px">
    <span style="font-size: 1.25rem; font-weight: bold;">${title}</span>
</div>
<c:if test="${not empty subtitle}">
<h6 style="line-height: 10px; margin-top: -10px; margin-bottom: 10px; font-style: italic;">
    ${subtitle}
</h6>
</c:if>

<div>
    <div style="width: 100%;">
        <div class="row" style="margin-left: 0px;">
            <table class="table table-sm table-bordered table-striped table-genapp table-genapp-list" style="width: auto;">
                <thead>
                    <tr>
                        <td></td>
                        <th>Key</th>
                        <th>Value</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
<c:forEach items="${keyValueList}" var="keyValueItem">
                    <tr>
                        <td>${empty keyValueItem.pre ? '&nbsp;' : keyValueItem.pre }</td>
                        <td>${keyValueItem.key}</td>
                        <td>${keyValueItem.value}</td>
                        <td>${empty keyValueItem.post ? '&nbsp;' : keyValueItem.post }</td>
                    </tr>
</c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
