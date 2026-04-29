
package acme.entities.projects;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
import acme.realms.Member;
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
	@Column(unique = true)
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
		double totalMonths = this.repository.sumCampaigns(this.title) + this.repository.sumInventions(this.title) + this.repository.sumStrategies(this.title);

		return totalMonths / this.members.size();
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToMany
	private Set<Member>		members;

	@Mandatory
	@Valid
	@ManyToMany
	private Set<Strategy>	strategies;

	@Mandatory
	@Valid
	@ManyToMany
	private Set<Invention>	inventions;

	@Mandatory
	@Valid
	@ManyToMany
	private Set<Campaign>	campaigns;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Manager			manager;

}
