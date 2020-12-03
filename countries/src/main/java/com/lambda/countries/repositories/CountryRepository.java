package com.lambda.countries.repositories;

import com.lambda.countries.models.Country;
import org.springframework.data.repository.CrudRepository;
public interface CountryRepository extends CrudRepository<Country, Long> {}
