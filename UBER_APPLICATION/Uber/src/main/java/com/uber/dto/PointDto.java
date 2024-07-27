package com.uber.dto;

import lombok.Data;

@Data
public class PointDto {

	private double[] coordinates;

	private String type = "Point";

}
