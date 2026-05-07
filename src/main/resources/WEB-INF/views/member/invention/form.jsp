
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="member.invention.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="member.invention.form.label.name" path="name"/>
	<acme:form-textarea code="member.invention.form.label.description" path="description"/>
	<acme:form-moment code="member.invention.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="member.invention.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="member.invention.form.label.moreInfo" path="moreInfo"/>
	<acme:form-double code="member.invention.form.label.monthsActive" path="monthsActive"/>
	<acme:form-money code="member.invention.form.label.cost" path="cost"/>
	
	<acme:button code="member.invention.form.button.parts" action="/member/part/list?inventionId=${id}"/>
	<acme:button code="member.invention.form.button.inventor" action="/member/inventor/show?id=${inventorId}"/>
	
		<jstl:if test="${projectId != null}">
		<acme:submit code="member.invention.button.unassign" action="/inventor/invention/unassign?inventionId=${id}"/>
	</jstl:if>
        
</acme:form>
