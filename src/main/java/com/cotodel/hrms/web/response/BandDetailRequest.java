package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BandDetailRequest {
	
	private String bandId;
	private String bandName;	
    private String bandPerDay;    
    private String bandPerMonth;    
    private String bandPerWeek;    
    private String bandPerYear;
    private boolean status;

}
