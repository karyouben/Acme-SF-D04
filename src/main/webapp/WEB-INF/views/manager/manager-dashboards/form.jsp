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
	<acme:message code="manager.dashboard.form.title.total-user-stories"/>
</h2>

<table class="table table-sm">
	<tr>
		<th>
			<acme:message code="manager.dashboard.form.label.total-must-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalMustUserStories}"/>
		</td>
	</tr>
	<tr>
		<th>
			<acme:message code="manager.dashboard.form.label.total-should-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalShouldUserStories}"/>
		</td>
	</tr>
	<tr>
		<th>
			<acme:message code="manager.dashboard.form.label.total-could-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalCouldUserStories}"/>
		</td>
	</tr>	
		<tr>
		<th>
			<acme:message code="manager.dashboard.form.label.total-wont-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalWontUserStories}"/>
		</td>
	</tr>	
</table>

<h2>
	<acme:message code="manager.dashboard.form.title.statistics"/>	
</h2>

<h3>
	<acme:message code="manager.dashboard.form.label.statistics-object-user-story"/>
</h3>

<table class="table table-sm">
	<tr>
		<th>
			<acme:message code="manager.dashboard.form.label.statistics-average"/>
		</th>
		<th>
			<acme:message code="manager.dashboard.form.label.statistics-deviation"/>
		</th>
		<th>
			<acme:message code="manager.dashboard.form.label.statistics-maximum"/>
		</th>
		<th>
			<acme:message code="manager.dashboard.form.label.statistics-minimum"/>
		</th>
	</tr>
	<tr>
		<td>
			<acme:print value="${userStoryCostStatistics.getAverageString()} hours"/>
		</td>
		<td>
			<acme:print value="${userStoryCostStatistics.getDeviationString()} hours"/>
		</td>
		<td>
			<acme:print value="${userStoryCostStatistics.getMaximumString()} hours"/>
		</td>
		<td>
			<acme:print value="${userStoryCostStatistics.getMinimumString()} hours"/>
		</td>
	</tr>
</table>

<h3>
	<acme:message code="manager.dashboard.form.label.statistics-object-user-story"/>
</h3>

<table class="table table-sm">
	<tr>
		<th>
			<acme:message code="manager.dashboard.form.label.statistics-currency"/>
		</th>
		<th>
			<acme:message code="manager.dashboard.form.label.statistics-average"/>
		</th>
		<th>
			<acme:message code="manager.dashboard.form.label.statistics-deviation"/>
		</th>
		<th>
			<acme:message code="manager.dashboard.form.label.statistics-maximum"/>
		</th>
		<th>
			<acme:message code="manager.dashboard.form.label.statistics-minimum"/>
		</th>
	</tr>
	
	<jstl:forEach var="entry" items="${projectCostStatistics}">
	  	<tr>
			<td>
				<acme:print value="${entry.key}"/>
			</td>
			<td>
				<acme:print value="${entry.value.getAverageString()}"/>
			</td>
			<td>
				<acme:print value="${entry.value.getDeviationString()}"/>
			</td>
			<td>
				<acme:print value="${entry.value.getMaximumString()}"/>
			</td>
			<td>
				<acme:print value="${entry.value.getMinimumString()}"/>
			</td>
		</tr>	
	</jstl:forEach>
</table>


<acme:return/>

