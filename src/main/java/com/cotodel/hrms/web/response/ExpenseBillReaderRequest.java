package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseBillReaderRequest {
	private String file;
	private String fileName;	
	private String fileType;	
	private String response;
}
