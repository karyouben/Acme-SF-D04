
package acme.components;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.helpers.MomentHelper;
import acme.client.repositories.AbstractRepository;
import acme.entities.banner.Banner;

@Repository
public interface BannerRepository extends AbstractRepository {

	public static Random RANDOM = new Random();


	@Query("select count(b) from Banner b WHERE b.startDisplayPeriod < :date AND b.endDisplayPeriod > :date")
	int countBanners(Date date);

	@Query("select b from Banner b WHERE b.startDisplayPeriod < :date AND b.endDisplayPeriod > :date")
	List<Banner> findManyBanners(Date date);

	default Banner findRandomBanner() {
		Banner result;
		int count;
		List<Banner> list;
		int randomNumber = 0;

		count = this.countBanners(MomentHelper.getCurrentMoment());
		if (count == 0)
			result = null;
		else {
			list = this.findManyBanners(MomentHelper.getCurrentMoment());
			if (!list.isEmpty())
				randomNumber = BannerRepository.RANDOM.nextInt(list.size());

			result = list.isEmpty() ? null : list.get(randomNumber);
		}

		return result;
	}
}
