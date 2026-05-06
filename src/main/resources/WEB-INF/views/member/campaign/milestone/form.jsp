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
	<acme:form-textbox code="member.milestone.form.label.title" path="title"/>
	<acme:form-textarea code="member.milestone.form.label.achievements" path="achievements"/>
	<acme:form-money code="member.milestone.form.label.effort" path="effort"/>
	<acme:form-select code="member.milestone.form.label.kind" path="kind" choices="${kinds}"/>
</acme:form>
