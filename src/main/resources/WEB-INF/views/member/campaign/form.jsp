<%--
- form.jsp
-
- Copyright (C) 2012-2026 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for member particular
- purposes.  The copyright owner does not offer member warranties or representations, nor do
- they accept member liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="member.campaign.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="member.campaign.form.label.name" path="name"/>
	<acme:form-textarea code="member.campaign.form.label.description" path="description"/>
	<acme:form-moment code="member.campaign.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="member.campaign.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="member.campaign.form.label.moreInfo" path="moreInfo"/>
	<acme:form-double code="member.campaign.form.label.monthsActive" path="monthsActive"/>
	<acme:form-money code="member.campaign.form.label.effort" path="effort"/>
	
	<acme:button code="member.campaign.form.button.milestones" action="/member/milestone/list?campaignId=${id}"/>
	<acme:button code="member.campaign.form.button.spokesperson" action="/member/spokesperson/show?id=${spokespersonId}"/>
	
	  <jstl:if test="${projectId != null}">
		<acme:submit code="member.campaign.button.unassign" action="/spokesperson/campaign/unassign?campaignId=${id}"/>
	 </jstl:if>
	
</acme:form>
