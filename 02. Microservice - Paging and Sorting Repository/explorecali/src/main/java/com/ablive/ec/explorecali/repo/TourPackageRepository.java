package com.ablive.ec.explorecali.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ablive.ec.explorecali.domain.TourPackage;

public interface TourPackageRepository extends CrudRepository<TourPackage, String> {

	Optional<TourPackage> findByName(String name);
	
}