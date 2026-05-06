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
	<acme:form-select code="manager.project-member.form.label.choices" path="choices" choices="${choices}" />
	
	<jstl:choose>	 
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.project-member.form.button.addMember" action="/manager/project-member/create?projectId=${projectId}$role=${role}"/>
		</jstl:when>	
		
		<jstl:when test="${_command == 'delete'}">
			<acme:submit code="manager.project-member.form.button.deleteMember" action="/manager/project-member/delete?projectId=${projectId}$role=${role}"/>
		</jstl:when>	
	</jstl:choose>

	
		
</acme:form>
