<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.risk.form.label.reference" path="reference"/>
	<acme:input-moment code="administrator.risk.form.label.identificationDate" path="identificationDate"/>
	<acme:input-double code="administrator.risk.form.label.impact" path="impact"/>
	<acme:input-double code="administrator.risk.form.label.probability" path="probability"/>
	<acme:input-select code="administrator.risk.form.label.project" path="project" choices="${projects}"/>
	
	<jstl:if test="${_command == 'show'}">
		<acme:input-double code="administrator.risk.form.label.value" path="derivedValue" readonly="true"/>
	</jstl:if>
	<acme:input-textbox code="administrator.risk.form.label.description" path="description"/>
	<acme:input-url code="administrator.risk.form.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="administrator.risk.form.button.update" action="/administrator/risk/update"/>
			<acme:submit code="administrator.risk.form.button.delete" action="/administrator/risk/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.risk.form.button.create" action="/administrator/risk/create"/>
		</jstl:when>	
	</jstl:choose>
		
</acme:form>