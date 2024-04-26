
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.invoice.list.label.code" path="code"  width="40%"/>
	<acme:list-column code="sponsor.invoice.list.label.dueDate" path="dueDate" width="40%" />
	<acme:list-column code="sponsor.invoice.list.label.quantity" path="quantity" width="20%" />

</acme:list>
<acme:button code="sponsor.invoice.create" action="/sponsor/invoice/create?masterId=${masterId}"/>