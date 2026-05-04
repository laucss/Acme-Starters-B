
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.project.form.label.title" path="title"/>
	<acme:form-textbox code="any.project.form.label.keyWords" path="keyWords"/>
	<acme:form-textbox code="any.project.form.label.description" path="description"/>
	<acme:form-moment code="any.project.form.label.kickOff" path="kickOff"/>
	<acme:form-moment code="any.project.form.label.closeOut" path="closeOut"/>
	<acme:form-double code="any.project.form.label.personMonths" path="personMonths"/>
	

</acme:form>
