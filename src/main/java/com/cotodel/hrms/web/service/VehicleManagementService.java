package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface VehicleManagementService {
	public String getVehicleManagementList(String token,EncriptResponse VehicleManagementRequest);
	public String addVehicleDetails(String token,EncriptResponse VehicleManagementSaveRequest);
	public String getVehicleManagementById(String token, EncriptResponse jsonObject);
	public String updateVehicleDetails(String token, EncriptResponse jsonObject);
	public String getEmployeeDriver(String token, EncriptResponse jsonObject);
	public String vehichleTripHistory(String token, EncriptResponse jsonObject);
	public String getVehicleNumberDetaiilByVehicleNumber(String token, EncriptResponse jsonObject);
	public String checkAccountNumberValidation(String token, EncriptResponse jsonObject);

}
