package com.cotodel.hrms.web.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWaitList implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;

	private String name;
	private String email;
	private String mobile;
}