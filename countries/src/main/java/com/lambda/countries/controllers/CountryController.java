package com.lambda.countries.controllers;


import com.lambda.countries.models.Country;
import com.lambda.countries.repositories.CountryRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;



@RestController
public class CountryController {
	CountryRepository countryRepo;


	/**
	 * Constructor autowired
	 *
	 * @param countryRepo CountryRepository interface
	 */
	@Autowired
	public CountryController(CountryRepository countryRepo) {
		this.countryRepo = countryRepo;
	}


	/**
	 * http://localhost:2019/names/all
	 *
	 * @return the name of each each country
	 */
	@GetMapping(value = "/names/all",
	            produces = {"application/json"})
	public ResponseEntity<?> findAllCountries() {
		//		List<Country> countries = new ArrayList<>();
		//		countryRepo.findAll()
		//		           .iterator()
		//		           .forEachRemaining(countries::add);
		List<Country> countries = getStarterList();
		countries.sort((c1, c2) -> c1.getName()
		                             .compareToIgnoreCase(c2.getName()));

		return new ResponseEntity<>(
				countries,
				HttpStatus.OK
		);
	}


	/**
	 * Helper function to get the full list of countries currently residing in countryRepo
	 *
	 * @return List of all countries
	 */
	@NotNull
	private List<Country> getStarterList() {
		List<Country> countries = new ArrayList<>();
		countryRepo.findAll()
		           .iterator()
		           .forEachRemaining(countries::add);
		return countries;
	}


	/**
	 * http://localhost:2019/names/start/u
	 */
	@GetMapping(value = "/names/start/{letter}",
	            produces = {"application/json"})
	public ResponseEntity<?> listNamesByFirstLetter(
			@PathVariable
					char letter
	) {
		//		List<Country> countries = new ArrayList<>();
		//		countryRepo.findAll().iterator().forEachRemaining(countries::add);
		//		List<Country> countries = getStarterList();
		List<Country> filteredList = findCountries(
				getStarterList(),
				c -> Character.toLowerCase(c.getName()
				                            .charAt(0)) == Character.toLowerCase(letter)
		);
		//		return filteredList;
		return new ResponseEntity<>(
				filteredList,
				HttpStatus.OK
		);
	}


	/**
	 * Helper function to filter through countries
	 *
	 * @param countries the list of countries
	 * @param tester    a lambda expression which returns a boolean (effectively implementing CheckCountry)
	 *
	 * @return the filtered list of countries
	 */
	private List<Country> findCountries(
			@NotNull List<Country> countries,
			CheckCountry tester
	) {
		List<Country> tempList = new ArrayList<>();
		for (Country c : countries) {
			if (tester.test(c)) {
				tempList.add(c);
			}
		}
		return tempList;
	}

	/**
	 * http://localhost:2019/population/total
	 */
	@GetMapping(value = "/population/total",
	            produces = {"application/json"})
	public ResponseEntity<?> findTotalPopulation() {
		List<Country>         countries = getStarterList();
		ListIterator<Country> it        = countries.listIterator();
		long                  popCount  = 0;
		while (it.hasNext()) {
			popCount += it.next()
			              .getPopulation();
		}
		return new ResponseEntity<>(
				"The total population is "+popCount,
				HttpStatus.OK
		);
	}

	/**
	 * http://localhost:2019/population/min
	 */
	@GetMapping(value = "/population/min",
	            produces = {"application/json"})
	public ResponseEntity<?> findMinPopulation() {
		List<Country> countries = getStarterList();

		Country minCountry  = countries.get(0);
		long    minPop      = minCountry.getPopulation();
		boolean tie         = false;
		Country tiedCountry = null;

		for (Country country : countries) {
			long pop = country.getPopulation();
			if (pop < minPop) {
				minCountry = country;
				minPop     = pop;
			} else if (pop == minPop && (country.getCountryid() != minCountry.getCountryid())) {
				tie         = true;
				tiedCountry = country;
			}
		}

		if (tie) {
			return new ResponseEntity<>(
					new ArrayList<>(Arrays.asList(
							minCountry,
							tiedCountry
					)),
					HttpStatus.OK
			);
		}
		return new ResponseEntity<>(
				minCountry,
				HttpStatus.OK
		);
	}


	/**
	 * http://localhost:2019/population/max
	 */
	@GetMapping(value = "/population/max",
	            produces = {"application/json"})
	public ResponseEntity<?> findMaxPopulation() {
		List<Country> countries   = getStarterList();
		Country       maxCountry  = countries.get(0);
		long          maxPop      = maxCountry.getPopulation() - 1;
		boolean       tie         = false;
		Country       tiedCountry = null;

		for (Country country : countries) {
			long pop = country.getPopulation();
			if (pop > maxPop) {
				maxPop     = pop;
				maxCountry = country;
			} else if (pop == maxPop && (country.getCountryid() != maxCountry.getCountryid())) {
				tie         = true;
				tiedCountry = country;
			}
		}
		if (tie) {
			return new ResponseEntity<>(
					new ArrayList<>(Arrays.asList(
							maxCountry,
							tiedCountry
					)),
					HttpStatus.OK
			);
		}
		return new ResponseEntity<>(
				maxCountry,
				HttpStatus.OK
		);

	}

	/*
	 * http://localhost:2019/population/median
	 */
//	@GetMapping(value="/population/median", produces = {"application/json"})
//	public ResponseEntity<?> findMedianPopulation() {
//		List<Country> countries = getStarterList();
//		List<Long> populations = new ArrayList<>();
//
//		for (Country c : countries) {
//			populations.add(c.getPopulation());
//		}
//
//
//		boolean isEven = populations.size() % 2 == 0;
//
//		Country medianCountry = countries.get(0);
//		Country secondMedianCountry = null;
//
//		Collections.sort(populations);
//		double middle = populations.size()/2;
//		if (populations.size()%2 == 1) {
//			middle = (populations[populations.size()/2] + populations[populations])
//		}
//
//	}
}
