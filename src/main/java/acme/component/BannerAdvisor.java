
package acme.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.entities.banner.Banner;

@ControllerAdvice
public class BannerAdvisor {

	@Autowired
	protected BannerRepository repository;


	@ModelAttribute("banner")
	public Banner getAdvertisement() {
		Banner result;

		try {
			result = this.repository.findRandomBanner();
		} catch (final Exception oops) {
			result = null;
		}

		return result;
	}
}
