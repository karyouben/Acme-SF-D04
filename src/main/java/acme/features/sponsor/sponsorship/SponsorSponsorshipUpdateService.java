/*
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipUpdateService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int sponsorshipId;
		Sponsorship sponsorship;

		sponsorshipId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorshipById(sponsorshipId);

		status = !sponsorship.isPublished() && sponsorship != null && super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSponsorshipById(id);

		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		super.bind(object, "code", "startDate", "endDate", "type", "amount", "email", "link", "project");
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;

		String dateString = "2201/01/01 00:00";
		Date futureMostDate = MomentHelper.parse(dateString, "yyyy/MM/dd HH:mm");
		dateString = "2200/12/1 00:00";
		Date latestStartDate = MomentHelper.parse(dateString, "yyyy/MM/dd HH:mm");

		String acceptedCurrencies = this.repository.findSystemConfiguration().getAcceptedCurrencies();
		List<String> acceptedCurrencyList = Arrays.asList(acceptedCurrencies.split("\\s*,\\s*"));

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship sponsorshipSameCode;
			sponsorshipSameCode = this.repository.findSponsorshipByCode(object.getCode());
			if (sponsorshipSameCode != null) {
				int id = sponsorshipSameCode.getId();
				super.state(id == object.getId(), "code", "sponsor.sponsorship.form.error.duplicate");
			}
		}

		if (object.getStartDate() != null) {

			if (!super.getBuffer().getErrors().hasErrors("startDate"))
				super.state(MomentHelper.isAfter(object.getStartDate(), object.getMoment()), "startDate", "sponsor.sponsorship.form.error.startDate");

			if (!super.getBuffer().getErrors().hasErrors("startDate"))
				super.state(MomentHelper.isBefore(object.getStartDate(), latestStartDate), "startDate", "sponsor.sponsorship.form.error.startDateOutOfBounds");

			if (object.getEndDate() != null) {

				if (!super.getBuffer().getErrors().hasErrors("endDate"))
					super.state(MomentHelper.isAfter(object.getEndDate(), object.getMoment()), "endDate", "sponsor.sponsorship.form.error.endDate");

				if (!super.getBuffer().getErrors().hasErrors("startDate"))
					super.state(MomentHelper.isBefore(object.getStartDate(), object.getEndDate()), "startDate", "sponsor.sponsorship.form.error.startDateBeforeEndDate");

				if (!super.getBuffer().getErrors().hasErrors("endDate"))
					super.state(MomentHelper.isBefore(object.getEndDate(), futureMostDate), "endDate", "sponsor.sponsorship.form.error.dateOutOfBounds");

				if (!super.getBuffer().getErrors().hasErrors("endDate"))
					super.state(MomentHelper.isLongEnough(object.getStartDate(), object.getEndDate(), 1, ChronoUnit.MONTHS), "endDate", "sponsor.sponsorship.form.error.period");

			}
		}

		if (object.getAmount() != null) {
			if (!super.getBuffer().getErrors().hasErrors("amount"))
				super.state(object.getAmount().getAmount() <= 1000000.00 && object.getAmount().getAmount() >= 0.00, "amount", "sponsor.sponsorship.form.error.amountOutOfBounds");

			if (!super.getBuffer().getErrors().hasErrors("amount"))
				super.state(this.repository.countPublishedInvoicesBySponsorshipId(object.getId()) == 0 || object.getAmount().getCurrency().equals(this.repository.findOneSponsorshipById(object.getId()).getAmount().getCurrency()), "amount",
					"sponsor.sponsorship.form.error.currencyChange");

			if (!super.getBuffer().getErrors().hasErrors("amount"))
				super.state(acceptedCurrencyList.contains(object.getAmount().getCurrency()), "amount", "sponsor.sponsorship.form.error.currencyNotSupported");
		}

	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;
		Dataset dataset;
		SelectChoices choices;
		SelectChoices projects;

		Collection<Project> unpublishedProjects = this.repository.findAllUnpublishedProjects();
		projects = SelectChoices.from(unpublishedProjects, "code", object.getProject());

		choices = SelectChoices.from(SponsorshipType.class, object.getType());

		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "type", "amount", "email", "link", "published");
		dataset.put("types", choices);

		dataset.put("project", projects.getSelected().getKey());
		dataset.put("projects", projects);

		super.getResponse().addData(dataset);
	}

}
