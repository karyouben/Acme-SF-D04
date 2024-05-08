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
	<acme:message code="administrator.dashboard.form.title.link-and-email-notices-ratio"/>
</h2>

<table class="table table-sm">
	<tr>
		<th>
			<acme:message code="administrator.dashboard.form.label.link-and-email-notices-ratio"/>
		</th>
		<td>
			<acme:print value="${linkAndEmailNoticesRatio}"/>
		</td>
	</tr>	
</table>

<h2>
	<acme:message code="administrator.dashboard.form.title.critical-objectives-ratio"/>
</h2>

<table class="table table-sm">
	<tr>
		<th>
			<acme:message code="administrator.dashboard.form.label.title"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.critical-objectives-ratio"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.critical-no-objectives-ratio"/>
		</th>
	</tr>
	<tr>
		<td>
			<acme:message code="administrator.dashboard.form.label.critical-and-non-critical-objectives-ratio"/>
		</td>
		<td>
			<acme:print value="${criticalObjectivesRatio}"/>
		</td>
		<td>
			<acme:print value="${nonCriticalObjectivesRatio}"/>
		</td>
	</tr>	
</table>

<h2>
	<acme:message code="administrator.dashboard.form.title.statistics-risk"/>
</h2>

<table class="table table-sm">
	<tr>
		<th>
			<acme:message code="administrator.dashboard.form.label.statistics-risk-title"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.statistics-risk-average"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.statistics-risk-deviation"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.statistics-risk-maximum"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.statistics-risk-minimum"/>
		</th>
	</tr>
	<tr>
		<td>
			<acme:message code="administrator.dashboard.form.label.statistics-object-risk"/>
		</td>
		<td>
			<acme:print value="${riskValueStatistics.getAverageString()}"/>
		</td>
		<td>
			<acme:print value="${riskValueStatistics.getDeviationString()}"/>
		</td>
		<td>
			<acme:print value="${riskValueStatistics.getMaximumString()}"/>
		</td>
		<td>
			<acme:print value="${riskValueStatistics.getMinimumString()}"/>
		</td>
	</tr>	
</table>

<h2>
	<acme:message code="administrator.dashboard.form.title.statistics-notice"/>
</h2>

<table class="table table-sm">
	<tr>
		<th>
			<acme:message code="administrator.dashboard.form.label.statistics-notice-title"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.statistics-notice-average"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.statistics-notice-deviation"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.statistics-notice-maximum"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.statistics-notice-minimum"/>
		</th>
	</tr>
	<tr>
		<td>
			<acme:message code="administrator.dashboard.form.label.statistics-object-notice"/>
		</td>
		<td>
			<acme:print value="${claimPosted10Statistics.getAverageString()}"/>
		</td>
		<td>
			<acme:print value="${claimPosted10Statistics.getDeviationString()}"/>
		</td>
		<td>
			<acme:print value="${claimPosted10Statistics.getMaximumString()}"/>
		</td>
		<td>
			<acme:print value="${claimPosted10Statistics.getMinimumString()}"/>
		</td>
	</tr>	
</table>	

<h2>
	<acme:message code="administrator.dashboard.form.title.statistics-users"/>
</h2>

<table class="table table-sm">
	<tr>
		<th>
			<acme:message code="administrator.dashboard.form.label.statistics-title-users"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.totalAdministrators"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.totalAuditors"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.totalConsumers"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.totalDevelopers"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.totalManagers"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.totalProviders"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.totalSponsors"/>
		</th>
		<th>
			<acme:message code="administrator.dashboard.form.label.totalClients"/>
		</th>
	</tr>
	<tr>
		<td>
			<acme:message code="administrator.dashboard.form.label.statistics-object-users"/>
		</td>
		<td>
			<acme:print value="${totalAdministrators}"/>
		</td>
		<td>
			<acme:print value="${totalAuditors}"/>
		</td>
		<td>
			<acme:print value="${totalConsumers}"/>
		</td>
		<td>
			<acme:print value="${totalDevelopers}"/>
		</td>
		<td>
			<acme:print value="${totalManagers}"/>
		</td>
		<td>
			<acme:print value="${totalProviders}"/>
		</td>
		<td>
			<acme:print value="${totalSponsors}"/>
		</td>
		<td>
			<acme:print value="${totalClients}"/>
		</td>
	</tr>	
</table>

<acme:return/>	