package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhatsAppRequest {
	private String campaignName;
    private String mobile;
    private String userName;
    private String firstName;
    private String organizationName;
    private String type;
    private String amount;
    private String category;
    private String validity;
    private String source;

}
