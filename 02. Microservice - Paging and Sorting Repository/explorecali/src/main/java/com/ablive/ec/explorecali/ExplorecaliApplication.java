package com.ablive.ec.explorecali;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ablive.ec.explorecali.domain.Difficulty;
import com.ablive.ec.explorecali.domain.Region;
import com.ablive.ec.explorecali.service.TourPackageService;
import com.ablive.ec.explorecali.service.TourService;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class ExplorecaliApplication implements CommandLineRunner {

	@Value("${ec.importfile}")
    private String importFile;
	
	@Autowired
	private TourPackageService tourPackageService;
	
	@Autowired
	private TourService tourService;
	
	public static void main(String[] args) {
		SpringApplication.run(ExplorecaliApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		createToursPackage();
		
		long numberOfPackages = tourPackageService.total();
		
		createTours(importFile);
		
		long numberOfTours = tourService.total();		
	}
	
	private void createToursPackage() {
		
		tourPackageService.createTourPackage("BC", "Backpack Cal");
		tourPackageService.createTourPackage("CC", "California Calm");
		tourPackageService.createTourPackage("CH", "California Hot springs");
		tourPackageService.createTourPackage("CY", "Cycle California");
		tourPackageService.createTourPackage("DS", "From Desert to Sea");
		tourPackageService.createTourPackage("KC", "Kids California");
		tourPackageService.createTourPackage("NW", "Nature Watch");
		tourPackageService.createTourPackage("SC", "Snowboard Cali");
		tourPackageService.createTourPackage("TC", "Taste of California");
	}

	private void createTours(String fileToImport) throws IOException {
		
		TourFromFile.read(fileToImport)
				.forEach(importedTour -> tourService.createTour(importedTour.getTitle(), importedTour.getDescription(),
						importedTour.getBlurb(), importedTour.getPrice(), importedTour.getLength(),
						importedTour.getBullets(), importedTour.getKeywords(), importedTour.getPackageType(),
						importedTour.getDifficulty(), importedTour.getRegion()));
	}
	
	private static class TourFromFile {

		private String packageType, title, description, blurb, price, length, bullets, keywords, difficulty, region;

		static List<TourFromFile> read(String fileToImport) throws IOException {
			return new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
					.readValue(new FileInputStream(fileToImport), new TypeReference<List<TourFromFile>>() {});
		}

		protected TourFromFile() {}

		String getPackageType() {
			return packageType;
		}

		String getTitle() {
			return title;
		}

		String getDescription() {
			return description;
		}

		String getBlurb() {
			return blurb;
		}

		Integer getPrice() {
			return Integer.parseInt(price);
		}

		String getLength() {
			return length;
		}

		String getBullets() {
			return bullets;
		}

		String getKeywords() {
			return keywords;
		}

		Difficulty getDifficulty() {
			return Difficulty.valueOf(difficulty);
		}

		Region getRegion() {
			return Region.findByLabel(region);
		}
	}
    
}