package com.sssseclipse.web.core.mongo.config;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
@EnableMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
public class MongoDbConfig {

	@Bean
	public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new DateToZonedDateTimeConverter(), new ZonedDateTimeToDateConverter()));
	}
	
	@ReadingConverter
	class DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {
		@Override
		public ZonedDateTime convert(Date source) {
			return source == null ? null : ZonedDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
		}
	}
	
	@WritingConverter
	class ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, Date> {
		@Override
		public Date convert(ZonedDateTime source) {
			return source == null ? null : Date.from(source.toInstant());
		}
	}
}
