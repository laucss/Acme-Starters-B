<%--
- form.jsp
-
- Copyright (C) 2012-2026 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this artefact. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="inventor.project.form.label.title" path="title"/>
	<acme:form-textbox code="inventor.project.form.label.keyWords" path="keyWords"/>
	<acme:form-textarea code="inventor.project.form.label.description" path="description"/>
	<acme:form-moment code="inventor.project.form.label.kickOff" path="kickOff"/>
	<acme:form-moment code="inventor.project.form.label.closeOut" path="closeOut"/>
	
	<jstl:choose>	 
		<jstl:when test="${_command == 'show'}">
			<acme:form-double code="inventor.project.form.label.personMonths" path="personMonths" readonly="true"/>
			
			<acme:button code="inventor.project.form.button.tactic" action="/inventor/tactic/list?projectId=${id}"/>
			<acme:button code="inventor.project.form.button.invention" action="/inventor/invention/list?projectId=${id}"/>
			<acme:button code="inventor.project.form.button.campaign" action="/inventor/campaign/list?projectId=${id}"/>
			
			<acme:button code="inventor.project.form.button.member" action="/inventor/project-member/list?projectId=${id}"/>
			
		</jstl:when>	
	</jstl:choose>


		
</acme:form>
