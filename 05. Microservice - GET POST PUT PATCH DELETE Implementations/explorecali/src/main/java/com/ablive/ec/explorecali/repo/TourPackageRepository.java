package com.ablive.ec.explorecali.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.ablive.ec.explorecali.domain.TourPackage;

@RepositoryRestResource(collectionResourceRel = "packages", path = "packages")
public interface TourPackageRepository extends CrudRepository<TourPackage, String> {
	
	Optional<TourPackage> findByName(String name);

	@Override
	@RestResource(exported = false)
	<S extends TourPackage> S save(S entity);

	@Override
	@RestResource(exported = false)
	<S extends TourPackage> Iterable<S> saveAll(Iterable<S> entities);

	@Override
	@RestResource(exported = false)
	void deleteById(String id);

	@Override
	@RestResource(exported = false)
	void delete(TourPackage entity);

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends TourPackage> entities);

	@Override
	@RestResource(exported = false)
	void deleteAll();
	
}