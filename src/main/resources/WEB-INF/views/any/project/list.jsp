
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.project.list.label.title" path="title" width="20%"/>
	<acme:list-column code="any.project.list.label.keyWords" path="keyWords" width="30%"/>
	<acme:list-column code="any.project.list.label.personMonths" path="personMonths" width="30%"/>
	<acme:list-hidden path="description"/>
	<acme:list-hidden path="kickOff"/>
	<acme:list-hidden path="closeOut"/>

</acme:list>
