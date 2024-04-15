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
	<acme:input-textbox code="developer.trainingModule.form.label.code" path="code"/>
	<acme:input-moment code="developer.trainingModule.form.label.creationMoment" path="creationMoment"/>
	<acme:input-textbox code="developer.trainingModule.form.label.details" path="details"/>
	<acme:input-select code="developer.trainingModule.form.label.difficultyLevel" path="difficultyLevel" choices="${difficultyLevelOptions}"/>
	<acme:input-moment code="developer.trainingModule.form.label.updateMoment" path="updateMoment"/>
	<acme:input-url code="developer.trainingModule.form.label.link" path="link"/>
	<acme:input-integer code="developer.trainingModule.form.label.totalTime" path="totalTime"/>
	<acme:input-select code="developer.training-module.form.label.project" path="project" choices="${projects}"/>
	
						
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="developer.trainingModule.form.button.add-trainingsessions" action="/developer/training-session/create?trainingModuleId=${id}"/>
			<acme:button code="developer.trainingModule.form.button.trainingsessions" action="/developer/training-session/list-by-training-module?trainingModuleId=${id}"/>
		
			<acme:submit code="developer.trainingModule.form.button.update" action="/developer/training-module/update"/>
			<acme:submit code="developer.trainingModule.form.button.delete" action="/developer/training-module/delete"/>
			<acme:button code="developer.trainingModule.form.button.publish" action="/developer/training-module/publish?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.trainingModule.form.button.create" action="/developer/training-module/create"/>
		</jstl:when>		
		<jstl:otherwise>
			<acme:button code="developer.trainingModule.form.button.add-trainingsessions" action="/developer/training-session/create?trainingModuleId=${id}"/>
			<acme:button code="developer.trainingModule.form.button.trainingsessions" action="/developer/training-session/list-by-training-module?trainingModuleId=${id}"/>
		</jstl:otherwise>		
	</jstl:choose>	
				
</acme:form>