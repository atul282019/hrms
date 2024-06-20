package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BandDetailRequest {
	
	private String expenseCategoryId;
	private String bandType;	
    private String bandOneInr;    
    private String bandTwoInr;    
    private String bandThreeInr;
    private String bandFourInr; 
    private String bandFiveInr; 
    private String bandSixInr; 
    private boolean status;

}
