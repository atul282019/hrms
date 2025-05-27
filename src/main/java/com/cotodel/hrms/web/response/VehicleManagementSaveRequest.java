package com.cotodel.hrms.web.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleManagementSaveRequest {
	private Long id;	
	private String vehicleSequenceId;
	private String vehicleType;	
	private String vehicleNo;	
	private LocalDateTime creationDate;	
	private String createdBy;	
	private int status;	
	private Long orgId;	
	private String manufacturingYear;	
	private String fuelType;	
	private String creationType;////creation Type--bulk or single	
	private String applicationType;////upload Type--web  or mobile	
	private String ownerName;
	private String ownerAddress;
	private String ownership_type;//ownershipModel
	private String chassisNumber;	
	private String engineNumber;	
	private String color;	
	private String authority;	
	private String cubicCapacity;	
	private int seat;	
	private String weight;	
	private String insuranceCompany;	
	private String fitness;	
	private String tax;	
	private String financer;	
	private String permitNumber;
	private String response;
	private String ownershipDriver;
	private String assignmentDriver;
	private String assignmentType;
	private String clientName;
	private String selectedTimeperiod;// time period in days

	private String vehicleNumber;
	private String vehicleManufactor;
	private String vehicleId;
	private String vehicleFuelTypeId;
	private String vehicleFuelType;
	private String vehicleModel;
	
	private String ownership_type_id;
	private String driverName1;
	private String assignmentType_id;			
	private String driverName2;
	private String driverId;
	private String driverMobile;
	private String requestType;
	private String customTimePeriod;

}
