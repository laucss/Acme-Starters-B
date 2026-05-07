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
	<acme:form-textbox code="member.project-member.form.label.username" path="member.userAccount.username" readonly="true"/>
	<acme:form-textbox code="member.project-member.form.label.name" path="member.userAccount.identity.name" readonly="true"/>
	<acme:form-textbox code="member.project-member.form.label.surname" path="member.userAccount.identity.surname" readonly="true"/>
	<acme:form-textarea code="member.project-member.form.label.email" path="member.userAccount.identity.email" readonly="true"/>
	<acme:form-textbox code="member.project-member.form.label.role" path="role" readonly="true"/>
	
	<jstl:choose>	 
		<jstl:when test="${rol == 'FUNDRAISER'}">
			<acme:form-textarea code="member.project-member.form.label.bank" path="bank" readonly="true"/>
			<acme:form-textarea code="member.project-member.form.label.statement" path="statement" readonly="true"/>
			<acme:form-checkbox code="member.project-member.form.label.agent" path="agent" readonly="true"/>	
		</jstl:when>
		
		<jstl:when test="${rol == 'INVENTOR'}">
			<acme:form-textarea code="member.project-member.form.label.bio" path="bio" readonly="true"/>
			<acme:form-textarea code="member.project-member.form.label.keyWords" path="keyWords" readonly="true"/>
			<acme:form-checkbox code="member.project-member.form.label.licensed" path="licensed" readonly="true"/>	
		</jstl:when>
		
		<jstl:when test="${rol == 'SPOKESPERSON'}">
			<acme:form-textarea code="member.project-member.form.label.cv" path="cv" readonly="true"/>
			<acme:form-textarea code="member.project-member.form.label.achievements" path="achievements" readonly="true"/>
			<acme:form-checkbox code="member.project-member.form.label.licensed" path="licensed" readonly="true"/>	
		</jstl:when>

	</jstl:choose>

	
		
</acme:form>
