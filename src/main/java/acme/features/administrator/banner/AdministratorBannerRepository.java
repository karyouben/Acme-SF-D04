/*
 * AuthenticatedConsumerRepository.java
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

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.banner.Banner;

@Repository
public interface AdministratorBannerRepository extends AbstractRepository {

	@Query("SELECT b FROM Banner b")
	List<Banner> findAllBanners();

	@Query("SELECT b FROM Banner b WHERE b.id = :id")
	Banner findBannerById(int id);
}
