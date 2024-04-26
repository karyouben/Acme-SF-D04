

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.average-sponsorship-cost"/>
		</th>
		<td>
			<acme:print value="${sponsorshipAmountStats.getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.min-sponsorship-cost"/>
		</th>
		<td>
			<acme:print value="${sponsorshipAmountStats.getMinimum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.max-sponsorship-cost"/>
		</th>
		<td>
			<acme:print value="${sponsorshipAmountStats.getMaximum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.lin-dev-sponsorship-cost"/>
		</th>
		<td>
			<acme:print value="${sponsorshipAmountStats.getDeviation()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.average-invoice-cost"/>
		</th>
		<td>
			<acme:print value="${invoicesQuantityStats.getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.max-invoice-cost"/>
		</th>
		<td>
			<acme:print value="${invoicesQuantityStats.getMaximum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.min-invoice-cost"/>
		</th>
		<td>
			<acme:print value="${invoicesQuantityStats.getMinimum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.lin-dev-invoice-cost"/>
		</th>
		<td>
			<acme:print value="${invoicesQuantityStats.getDeviation()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.21orless-invoice"/>
		</th>
		<td>
			<acme:print value="${numInvoicesWithTaxLessOrEqualThan21}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.link-sponsorship"/>
		</th>
		<td>
			<acme:print value="${numSponsorshipsWithLink}"/>
		</td>
	</tr>
</table>


<acme:return/>