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
	<jstl:choose>
		<jstl:when test="${_command == 'show'}">
			<h3>
				<acme:print value="${title}"/>
			</h3>
			<acme:submit code="manager.assignment.form.button.delete" action="/manager/assignment/delete"/>
		</jstl:when>		

		<jstl:when test="${_command == 'create'}">
			<acme:input-select code="manager.assignment.form.label.userStory" path="userStory" choices="${userStories}"/>	
			<acme:submit code="manager.assignment.form.button.create" action="/manager/assignment/create?projectId=${projectId}"/>
		</jstl:when>		
	</jstl:choose>	
				
</acme:form>
