<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.sponsorship.form.label.code" path="code"/>	
	<acme:input-moment code="any.sponsorship.form.label.moment" path="moment"/>	
	<acme:input-moment code="any.sponsorship.form.label.startPeriod" path="startPeriod"/>	
	<acme:input-moment code="any.sponsorship.form.label.endPeriod" path="endPeriod"/>	
	<acme:input-money code="any.sponsorship.form.label.amount" path="amount"/>	
	<acme:input-url code="any.sponsorship.form.label.link" path="link"/>	
	<acme:input-textbox code="any.sponsorship.form.label.email" path="email"/>	
	<acme:input-money code="sponsor.sponsorship.form.label.money" path="money" readonly="true"/>
</acme:form>