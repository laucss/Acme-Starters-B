
package acme.forms;

import javax.validation.constraints.NotNull;

import acme.client.components.basis.AbstractForm;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportAssignment extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@NotNull
	public Integer				auditReportId;

	@Mandatory
	@NotNull
	public Integer				projectId;
}
