package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;


import com.cotodel.hrms.web.response.DepartmentMaster;

@Repository
public interface DepartmentMasterService {
	
	String getDepartmentMaster(String token,DepartmentMaster departmentMaster);

}
