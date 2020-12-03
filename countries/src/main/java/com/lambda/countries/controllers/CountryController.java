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

import java.util.ArrayList;
import java.util.List;

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

		return new ResponseEntity<>(countries, HttpStatus.OK);
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
	 * http://localhost:2019/start/u
	 */
	@GetMapping(value = "/names/start/{letter}",
	            produces = {"application/json"})
	public ResponseEntity<?> listNamesByFirstLetter(
			@PathVariable
					char letter
	                                               )
	{
		//		List<Country> countries = new ArrayList<>();
		//		countryRepo.findAll().iterator().forEachRemaining(countries::add);
		List<Country> countries = getStarterList();
		List<Country> filteredList = findCountries(
				countries,
				c -> Character.toLowerCase(c.getName()
				                            .charAt(0)) == Character.toLowerCase(letter)
		                                          );
		//		return filteredList;
		return new ResponseEntity<>(filteredList, HttpStatus.OK);
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
	                                   )
	{
		List<Country> tempList = new ArrayList<>();
		for (Country c : countries) {
			if (tester.test(c)) {
				tempList.add(c);
			}
		}
		return tempList;
	}

	/*      http://localhost:2019/population      */

	/**
	 * http://localhost:2019/population/total
	 */

	/**
	 * http://localhost:2019/population/min
	 */

	/**
	 * http://localhost:2019/population/max
	 */

	/**
	 * http://localhost:2019/population/median
	 */
}
