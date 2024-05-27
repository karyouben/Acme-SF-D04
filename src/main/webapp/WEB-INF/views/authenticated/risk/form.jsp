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
	<acme:input-textbox code="authenticated.risk.form.label.reference" path="reference"/>
	<acme:input-moment code="authenticated.risk.form.label.identificationDate" path="identificationDate"/>
	<acme:input-double code="authenticated.risk.form.label.impact" path="impact"/>
	<acme:input-double code="authenticated.risk.form.label.probability" path="probability"/>
	<acme:input-double code="authenticated.risk.form.label.value" path="value"/>
	<acme:input-textbox code="authenticated.risk.form.label.description" path="description"/>
	<acme:input-url code="authenticated.risk.form.label.link" path="link"/>
	<acme:input-select code="authenticated.risk.form.label.project" path="project" choices="${projects}"/>
</acme:form>
