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

	<acme:input-textbox code="developer.trainingsession.form.label.code" path="code"/>
	<acme:input-moment code="developer.trainingsession.form.label.startPeriod" path="startPeriod"/>
	<acme:input-moment code="developer.trainingsession.form.label.endPeriod" path="endPeriod"/>
	<acme:input-textbox code="developer.trainingsession.form.label.location" path="location"/>
	<acme:input-textbox code="developer.trainingsession.form.label.instructor" path="instructor"/>
	<acme:input-email code="developer.trainingsession.form.label.contactEmail" path="contactEmail"/>
	<acme:input-url code="developer.trainingsession.form.label.link" path="link"/>
		
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="developer.trainingsession.form.button.update" action="/developer/training-session/update"/>
			<acme:submit code="developer.trainingsession.form.button.delete" action="/developer/training-session/delete"/>
			<acme:button code="developer.trainingsession.form.button.publish" action="/developer/training-session/publish?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.trainingsession.form.button.create" action="/developer/training-session/create"/>
		</jstl:when>		
	</jstl:choose>
			
</acme:form>