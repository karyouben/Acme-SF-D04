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

<h2>
	<acme:message code="client.dashboard.form.title.total-contracts-completness-less-25"/>
</h2>

<table class="table table-sm">
	<tr>
		<th>
			<acme:message code="client.dashboard.form.label.total-contracts-completness-less-25"/>
		</th>
		<td>
			<acme:print value="${totalNumProgressLogLessThan25}"/>
		</td>
	</tr>	
</table>

<h2>
	<acme:message code="client.dashboard.form.title.total-contracts-completness-between-25-50"/>
</h2>

<table class="table table-sm">
	<tr>
		<th>
			<acme:message code="client.dashboard.form.label.total-contracts-completness-between-25-50"/>
		</th>
		<td>
			<acme:print value="${totalNumProgressLogLessBetween25And50}"/>
		</td>
	</tr>	
</table>

<h2>
	<acme:message code="client.dashboard.form.title.total-contracts-completness-between-50-75"/>
</h2>

<table class="table table-sm">
	<tr>
		<th>
			<acme:message code="client.dashboard.form.label.total-contracts-completness-between-50-75"/>
		</th>
		<td>
			<acme:print value="${totalNumProgressLogLessBetween50And75}"/>
		</td>
	</tr>	
</table>

<h2>
	<acme:message code="client.dashboard.form.title.total-contracts-completness-above-75"/>
</h2>

<table class="table table-sm">
	<tr>
		<th>
			<acme:message code="client.dashboard.form.label.total-contracts-completness-above-75"/>
		</th>
		<td>
			<acme:print value="${totalNumProgressLogAbove75}"/>
		</td>
	</tr>	
</table>	

<h2>
	<acme:message code="client.dashboard.form.title.statistics"/>
</h2>

<table class="table table-sm">
	<tr>
		<th>
			<acme:message code="client.dashboard.form.label.statistics-object"/>
		</th>
		<th>
			<acme:message code="client.dashboard.form.label.statistics-average"/>
		</th>
		<th>
			<acme:message code="client.dashboard.form.label.statistics-deviation"/>
		</th>
		<th>
			<acme:message code="client.dashboard.form.label.statistics-maximum"/>
		</th>
		<th>
			<acme:message code="client.dashboard.form.label.statistics-minimum"/>
		</th>
	</tr>
	<tr>
		<td>
			<acme:message code="client.dashboard.form.label.statistics-object-contract"/>
		</td>
		<td>
			<acme:print value="${contractTimeStatistics.getAverageString()} hours"/>
		</td>
		<td>
			<acme:print value="${contractTimeStatistics.getDeviationString()} hours"/>
		</td>
		<td>
			<acme:print value="${contractTimeStatistics.getMaximumString()} hours"/>
		</td>
		<td>
			<acme:print value="${contractTimeStatistics.getMinimumString()} hours"/>
		</td>
	</tr>	
</table>


<acme:return/>