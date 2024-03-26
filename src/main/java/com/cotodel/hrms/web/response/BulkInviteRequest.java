package com.cotodel.hrms.web.response;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkInviteRequest  implements Serializable{
	
	private static final long serialVersionUID = -5145118965670277166L;
	
	private Long employerId;
	private String inviteEmployee;
	private String inviteContractor;		
	private String response;
}
