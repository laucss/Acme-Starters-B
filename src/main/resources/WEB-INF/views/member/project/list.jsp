<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="member.project.list.label.title" path="title"/>
	<acme:list-column code="member.project.list.label.keyWords" path="keyWords"/>
	<acme:list-column code="member.project.list.label.kickOffMoment" path="kickOffMoment"/>
	<acme:list-column code="member.project.list.label.closeOutMoment" path="closeOutMoment"/>
	<acme:list-hidden path="description"/>
</acme:list>