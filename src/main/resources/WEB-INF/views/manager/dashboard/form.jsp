<%--
- form.jsp
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

<h2>
	<acme:print code="manager.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.totalProjects"/>
		</th>
		<td>
			<acme:print value="${totalProjects}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.projectDeviation"/>
		</th>
		<td>
			<acme:print value="${projectDeviation}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.minEffort"/>
		</th>
		<td>
			<acme:print value="${minEffort}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.maxEffort"/>
		</th>
		<td>
			<acme:print value="${maxEffort}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.avgEffort"/>
		</th>
		<td>
			<acme:print value="${avgEffort}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.effortDeviation"/>
		</th>
		<td>
			<acme:print value="${effortDeviation}"/>
		</td>
	</tr>		
</table>

<acme:return/>

