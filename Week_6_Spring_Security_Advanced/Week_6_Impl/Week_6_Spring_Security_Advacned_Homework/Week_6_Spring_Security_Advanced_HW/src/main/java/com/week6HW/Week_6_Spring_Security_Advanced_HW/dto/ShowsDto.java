package com.week6HW.Week_6_Spring_Security_Advanced_HW.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowsDto {

	private Long id;

	@JsonProperty("movie_name")
	private String movieName;

	private Double ratings;
}
