
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.sponsorship.list.label.code" path="code"  width="40%"/>
	<acme:list-column code="authenticated.sponsorship.list.label.type" path="type" width="40%" />
	<acme:list-column code="authenticated.sponsorship.list.label.amount" path="amount" width="20%" />


</acme:list>