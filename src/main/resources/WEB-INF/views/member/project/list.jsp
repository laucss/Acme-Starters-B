<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="member.project.list.label.title" path="title"/>
	<acme:list-column code="member.project.list.label.keyWords" path="keyWords"/>
	<acme:list-column code="member.project.list.label.kickOff" path="kickOff"/>
	<acme:list-column code="member.project.list.label.closeOut" path="closeOut"/>
	<acme:list-hidden path="description"/>
</acme:list>