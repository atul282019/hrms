package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;


import com.cotodel.hrms.web.response.jobTitlemaster;

@Repository
public interface jobTitlemasterService {
	public String savejobTitlemaster(String token,jobTitlemaster jobtitlemaster);
	public String getjobTitlemaster(String token,jobTitlemaster jobtitlemaster);

}
