/*
 * AuthenticatedConsumerCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Banner banner = this.repository.findBannerById(id);

		Date currentMoment = MomentHelper.getCurrentMoment();
		Date instantiation = new Date(currentMoment.getTime() - 1000); //Substracts one second to ensure the moment is in the past
		banner.setInstantiation(instantiation);

		super.getBuffer().addData(banner);
	}
	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "startDisplayPeriod", "endDisplayPeriod", "slogan", "linkPicture", "linkWebDoc");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		final String PERIOD_START = "startDisplayPeriod";
		final String PERIOD_END = "endDisplayPeriod";

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_START) && !super.getBuffer().getErrors().hasErrors(PERIOD_END)) {
			final boolean startBeforeEnd = MomentHelper.isAfter(object.getEndDisplayPeriod(), object.getStartDisplayPeriod());
			super.state(startBeforeEnd, PERIOD_END, "administrator.banner.form.error.end-before-start");

			if (startBeforeEnd) {
				final boolean startOneWeekBeforeEndMinimum = MomentHelper.isLongEnough(object.getStartDisplayPeriod(), object.getEndDisplayPeriod(), 7, ChronoUnit.DAYS);

				super.state(startOneWeekBeforeEndMinimum, PERIOD_END, "administrator.banner.form.error.small-display-period");
			}

			if (!super.getBuffer().getErrors().hasErrors("instantiation")) {
				final boolean startAfterInstantiation = MomentHelper.isAfter(object.getStartDisplayPeriod(), object.getInstantiation());
				super.state(startAfterInstantiation, PERIOD_START, "administrator.banner.form.error.start-before-instantiation");
			}
		}

		Date minDate;
		Date maxDate;

		minDate = MomentHelper.parse("2000-01-01 00:00", "yyyy-MM-dd HH:mm");
		maxDate = MomentHelper.parse("2200-12-31 23:59", "yyyy-MM-dd HH:mm");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_START))
			super.state(MomentHelper.isAfterOrEqual(object.getStartDisplayPeriod(), minDate), PERIOD_START, "administrator.banner.form.error.before-min-date");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_START))
			super.state(MomentHelper.isBeforeOrEqual(object.getStartDisplayPeriod(), maxDate), PERIOD_START, "administrator.banner.form.error.after-max-date");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_START))
			super.state(MomentHelper.isBeforeOrEqual(object.getStartDisplayPeriod(), MomentHelper.deltaFromMoment(maxDate, -7, ChronoUnit.DAYS)), PERIOD_START, "administrator.banner.form.error.no-room-for-min-period-duration");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_END))
			super.state(MomentHelper.isAfterOrEqual(object.getEndDisplayPeriod(), minDate), PERIOD_END, "administrator.banner.form.error.before-min-date");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_END))
			super.state(MomentHelper.isBeforeOrEqual(object.getEndDisplayPeriod(), maxDate), PERIOD_END, "administrator.banner.form.error.after-max-date");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_END))
			super.state(MomentHelper.isAfterOrEqual(object.getEndDisplayPeriod(), MomentHelper.deltaFromMoment(minDate, 7, ChronoUnit.DAYS)), PERIOD_END, "administrator.banner.form.error.no-room-for-min-period-duration");
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "startDisplayPeriod", "endDisplayPeriod", "slogan", "linkPicture", "linkWebDoc");
		dataset.put("instantiation", object.getInstantiation());

		super.getResponse().addData(dataset);
	}

}
