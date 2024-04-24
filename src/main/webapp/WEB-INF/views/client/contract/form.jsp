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
	<acme:input-textbox code="any.contract.form.label.code" path="code" />
	<acme:input-textbox code="any.contract.form.label.providerName"
		path="providerName" />
	<acme:input-textbox code="any.contract.form.label.customerName"
		path="customerName" />
	<acme:input-textbox code="any.contract.form.label.goals" path="goals" />
	<acme:input-money code="any.contract.form.label.budget" path="budget" />
	<acme:input-select code="client.contract.form.label.project"
		path="project" choices="${projects}" />


	<jstl:choose>
		<jstl:when
			test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
										<acme:input-moment code="any.contract.form.label.instantiation"
		path="instantiation" readonly = "true"/>
			<acme:button code="client.contract.form.button.add-progress"
				action="/client/progress/create?contractId=${id}" />
			<acme:button code="client.contract.form.button.progress"
				action="/client/progress/list-by-contract?contractId=${id}" />
			<acme:submit code="client.contract.form.button.update"
				action="/client/contract/update" />
			<acme:submit code="client.contract.form.button.delete"
				action="/client/contract/delete" />
			<acme:submit code="client.contract.form.button.publish"
				action="/client/contract/publish?id=${id}" />
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="client.contract.form.button.create"
				action="/client/contract/create" />
		</jstl:when>
		<jstl:otherwise> 
			<acme:input-moment code="any.contract.form.label.instantiation"
		path="instantiation" readonly = "true"/>
			<acme:button code="client.contract.form.button.progress"
				action="/client/progress/list-by-contract?contractId=${id}" />
		</jstl:otherwise>
	</jstl:choose>

</acme:form>