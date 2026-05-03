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
	<acme:list-column code="inventor.project.list.label.title" path="title" width="35%"/>
	<acme:list-column code="inventor.project.list.label.keyWords" path="keyWords" width="45%"/>	
	<acme:list-column code="inventor.project.list.label.draftMode" path="draftMode" width="20%"/>
	<acme:list-hidden path="description"/>
	<acme:list-hidden path="kickOff"/>
	<acme:list-hidden path="closeOut"/>

</acme:list>


