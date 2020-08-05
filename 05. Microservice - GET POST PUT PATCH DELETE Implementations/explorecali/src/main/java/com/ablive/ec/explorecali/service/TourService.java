package com.ablive.ec.explorecali.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ablive.ec.explorecali.domain.Difficulty;
import com.ablive.ec.explorecali.domain.Region;
import com.ablive.ec.explorecali.domain.Tour;
import com.ablive.ec.explorecali.domain.TourPackage;
import com.ablive.ec.explorecali.repo.TourPackageRepository;
import com.ablive.ec.explorecali.repo.TourRepository;

@Service
public class TourService {

	private TourRepository tourRepository;
	private TourPackageRepository tourPackageRepository;
	
	@Autowired
	public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
		this.tourRepository = tourRepository;
		this.tourPackageRepository = tourPackageRepository;
	}
	
	public Tour createTour(String title, String description, String blurb, Integer price, String duration, String bullets, String keywords, String tourPackageName, Difficulty difficulty, Region region) {
		
		TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
				.orElseThrow(() -> new RuntimeException("Tour package does not exists" + tourPackageName));
		
		return tourRepository.save(new Tour(title, description, blurb, price, duration, bullets, keywords, tourPackage, difficulty, region));
	}
	
	public long total() {
		return tourRepository.count();
	}
	
}