<%--
- form.jsp
-
- Copyright (C) 2012-2026 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this artefact. It has been tested carefully, but it is not guaranteed for member particular
- purposes.  The copyright owner does not offer member warranties or representations, nor do
- they accept member liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="member.strategy.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="member.strategy.form.label.name" path="name"/>
	<acme:form-textarea code="member.strategy.form.label.description" path="description"/>
	<acme:form-double code="member.strategy.form.label.startMoment" path="startMoment"/>
	<acme:form-textbox code="member.strategy.form.label.endMoment" path="endMoment"/>
	<acme:form-textbox code="member.strategy.form.label.moreInfo" path="moreInfo"/>
	<acme:form-textbox code="member.strategy.form.label.monthsActive" path="monthsActive"/>
	<acme:form-textbox code="member.strategy.form.label.expectedPercentage" path="expectedPercentage"/>
	
	<acme:button code="member.strategy.form.button.tactic" action="/member/tactic/list?strategyId=${id}"/>
	<acme:button code="member.strategy.form.button.fundraiser" action="/member/fundraiser/show?id=${fundraiserId}"/>
	
	<jstl:if test="${projectId != null}">
		<acme:submit code="member.strategy.button.unassign" action="/fundraiser/strategy/unassign?strategyId=${id}"/>
	</jstl:if>   
	
		
</acme:form>
