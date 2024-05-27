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
	<acme:input-moment code="administrator.banner.list.label.startDisplayPeriod" path="startDisplayPeriod"/>
	<acme:input-moment code="administrator.banner.list.label.endDisplayPeriod" path="endDisplayPeriod"/>
	<acme:input-textbox code="administrator.banner.list.label.slogan" path="slogan"/>
	<acme:input-url code="administrator.banner.list.label.linkPicture" path="linkPicture"/>
	<acme:input-url code="administrator.banner.list.label.linkWebDoc" path="linkWebDoc"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete'}">
			<acme:input-moment code="administrator.banner.form.label.instantiation" path="instantiation" readonly="true"/>
			<acme:submit code="administrator.banner.list.button.update" action="/administrator/banner/update"/>
			<acme:submit code="administrator.banner.list.button.delete" action="/administrator/banner/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.banner.list.button.create" action="/administrator/banner/create"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:input-moment code="administrator.banner.form.label.instantiation" path="instantiation" readonly="true"/>
		</jstl:otherwise>
	</jstl:choose>
</acme:form>
