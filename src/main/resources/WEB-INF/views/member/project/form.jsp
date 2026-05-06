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
	<acme:form-textbox code="member.project.form.label.title" path="title"/>
	<acme:form-textbox code="member.project.form.label.keyWords" path="keyWords"/>
	<acme:form-textarea code="member.project.form.label.description" path="description"/>
	<acme:form-moment code="member.project.form.label.kickOff" path="kickOff"/>
	<acme:form-moment code="member.project.form.label.closeOut" path="closeOut"/>
	
	<jstl:choose>	 
		<jstl:when test="${_command == 'show'}">
			<acme:form-double code="member.project.form.label.personMonths" path="personMonths" readonly="true"/>
			
			<acme:button code="member.project.form.button.strategy" action="/member/strategy/list?projectId=${id}"/>
			<acme:button code="member.project.form.button.invention" action="/member/invention/list?projectId=${id}"/>
			<acme:button code="member.project.form.button.campaign" action="/member/campaign/list?projectId=${id}"/>
			
			<acme:button code="member.project.form.button.member" action="/manager/project-member/list?projectId=${id}"/>
			
			<jstl:if test="${!draftMode}">
			
			<acme:button code="member.project.form.button.reports" action="/any/report/list?projectId=${id}"/>
			<acme:button code="member.project.form.button.sponsorships" action="/any/sponsorship/list?projectId=${id}"/>
	
			</jstl:if>
		</jstl:when>	
	</jstl:choose>


		
</acme:form>