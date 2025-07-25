package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiBrandGeoRequest {
		private Long id;	
		private Long orgid;	
		private int brandid;	
		private List<GeographicLocation> geographicLocation;
		private String createdby;	
		private int status;
}
