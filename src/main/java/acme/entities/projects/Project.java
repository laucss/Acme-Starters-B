
package acme.entities.projects;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.entities.campaigns.Campaign;
import acme.entities.inventions.Invention;
import acme.entities.strategies.Strategy;
import acme.realms.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidHeader
	@Column
	private String				title;

	@Mandatory
	@ValidHeader
	@Column
	private String				keyWords;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				kickOff;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				closeOut;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	// Derived attributes -----------------------------------------------------

	@Mandatory
	@Valid
	@Transient
	@Autowired
	private ProjectRepository	repository;


	@Mandatory
	@Valid
	@Transient
	public Double personMonths() {
		double campaignsMonths = this.repository.findCampaignsByProjectId(this.getId()).stream().mapToDouble(Campaign::getMonthsActive).sum();
		double inventionsMonths = this.repository.findInventionsByProjectId(this.getId()).stream().mapToDouble(Invention::getMonthsActive).sum();
		double strategiesMonths = this.repository.findStrategiesByProjectId(this.getId()).stream().mapToDouble(Strategy::getMonthsActive).sum();
		double totalMonths = campaignsMonths + inventionsMonths + strategiesMonths;
		Integer totalMembers = this.repository.countByProjectId(this.getId());
		return totalMembers == 0 ? 0. : totalMonths / totalMembers;

	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Manager			manager;

	@Valid
	@OneToMany(mappedBy = "project")
	private Set<Strategy>	strategies;

	@Valid
	@OneToMany(mappedBy = "project")
	private Set<Invention>	inventions;

	@Valid
	@OneToMany(mappedBy = "project")
	private Set<Campaign>	campaigns;


}
