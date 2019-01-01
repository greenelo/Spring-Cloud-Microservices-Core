package com.sssseclipse.web.core.mongo.config;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
@EnableMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
public class MongoDbConfig {

	@Bean
	public MongoCustomConversions customConversions() {
		List<Converter<?, ?>> converters = new ArrayList<>();
		converters.add(new DateToZonedDateTimeConverter());
		converters.add(new ZonedDateTimeToDateConverter());
		return new MongoCustomConversions(converters);
	}

	class DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {
		@Override
		public ZonedDateTime convert(Date date) {
			return date == null ? null : date.toInstant().atZone(ZoneOffset.systemDefault());
		}
	}

	class ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, Date> {
		@Override
		public Date convert(ZonedDateTime source) {
			return source == null ? null : Date.from(source.toInstant());
		}
	}
}
