<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	

	<acme:input-textbox code="client.progress.form.label.record" path="record"/>
	<acme:input-textbox code="client.progress.form.label.completeness" path="completeness"/>
	<acme:input-textbox code="client.progress.form.label.comment" path="comment"/>
	<acme:input-textbox code="client.progress.form.label.responsable" path="responsable"/>
		
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:input-moment code="client.progress.form.label.registration" path="registration" readonly = "true"	/>
			<acme:submit code="client.progress.form.button.update" action="/client/progress/update"/>
			<acme:submit code="client.progress.form.button.delete" action="/client/progress/delete"/>
			<acme:submit code="client.progress.form.button.publish" action="/client/progress/publish?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="client.progress.form.button.create" action="/client/progress/create?contractId=${contractId}"/>
		</jstl:when>	
		<jstl:otherwise> 
			<acme:input-moment code="client.progress.form.label.registration" path="registration" readonly = "true"/>
		</jstl:otherwise>	
	</jstl:choose>
			
</acme:form>