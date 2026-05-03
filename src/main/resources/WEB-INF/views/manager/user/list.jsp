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
	<acme:list-column code="manager.user.list.label.username" path="username" width="25%"/>
	<acme:list-column code="manager.user.list.label.name" path="identity.name" width="20%"/>	
	<acme:list-column code="manager.user.list.label.surname" path="identity.surname" width="20%"/>
	<acme:list-column code="manager.user.list.label.email" path="identity.email" width="35%"/>


</acme:list>

