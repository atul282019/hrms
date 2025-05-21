package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface VehicleManagementService {
	public String getVehicleManagementList(String token,EncriptResponse VehicleManagementRequest);
	public String addVehicleDetails(String token,EncriptResponse VehicleManagementSaveRequest);

}
