package com.forecast.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForecastItemRepository extends MongoRepository<ForecastItem, String> {
	ForecastItem findByPlace(String place);
}