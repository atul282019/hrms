package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.managerMaster;



@Repository
public interface managerMasterService {
	public String savemanagerMaster(String token,managerMaster managermaster);
	public String getmanagerMasterWithId(String token,managerMaster managermaster);

}
