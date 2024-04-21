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
	<acme:input-textbox code="any.trainingModule.form.label.code" path="code" readonly="true"/>
	<acme:input-moment code="any.trainingModule.form.label.creationMoment" path="creationMoment" readonly="true"/>
	<acme:input-textbox code="any.trainingModule.form.label.details" path="details" readonly="true"/>
	<acme:input-select code="any.trainingModule.form.label.difficultyLevel" path="difficultyLevel" choices="${difficultyLevelOptions}"/>
	<acme:input-moment code="any.trainingModule.form.label.updateMoment" path="updateMoment" readonly="true"/>
	<acme:input-url code="any.trainingModule.form.label.link" path="link" readonly="true"/>
	<acme:input-integer code="any.trainingModule.form.label.totalTime" path="totalTime" readonly="true"/>
	<acme:input-select code="any.training-module.form.label.project" path="project" choices="${projects}"/>		
</acme:form>