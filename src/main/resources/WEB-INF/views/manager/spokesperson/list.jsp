<%--
- list.jsp
-
- Copyright (C) 2012-2026 Rafael Corchuelo.
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

<acme:list>
	<acme:list-column code="manager.spokesperson.list.label.username" path="userAccount.username" width="20%"/>
	<acme:list-column code="manager.spokesperson.list.label.cv" path="cv" width="60%"/>
	<acme:list-column code="manager.spokesperson.list.label.licensed" path="licensed" width="20%"/>
</acme:list>

<jstl:if test="${draftMode}">
	<acme:button code="member.project-member.list.button.addSpokespersons" action="/manager/project-member/create?projectId=${projectId}&role=SPOKESPERSON"/>
	<acme:button code="member.project-member.list.button.deteleSpokespersons" action="/manager/project-member/delete?projectId=${projectId}&role=SPOKESPERSON"/>
</jstl:if>

