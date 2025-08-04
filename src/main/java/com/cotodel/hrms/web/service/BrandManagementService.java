package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.ExpenseBillReaderRequest;
import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface BrandManagementService {

	String activateBrandManagement(String token, EncriptResponse jsonObject);

	String addBrandDetails(String token, EncriptResponse jsonObject);

	String getBrandOutletList(String token, EncriptResponse jsonObject);

	String addGeograpgicDetails(String token, EncriptResponse jsonObject);

	String getBrupiBrandGeoList(String token, EncriptResponse jsonObject);

	String getOutletDetail(String token, EncriptResponse jsonObject);

	String saveBulkOutletDetail(String token, EncriptResponse jsonObject);

	String createBulkOutlet(String token, EncriptResponse jsonObject);

	String erupiBrandOutletById(String token, EncriptResponse jsonObject);

	String saveBrandOutletDeviceDetails(String token, EncriptResponse jsonObject);

	String getDeviceDetailList(String token, EncriptResponse jsonObject);

	String deactivateOutlet(String token, EncriptResponse jsonObject);

	String editBrandOutletDetail(String token, EncriptResponse jsonObject);

	String activateDeactivateLinkedDevice(String token, EncriptResponse jsonObject);

	String getLinkedDeviceDetail(String token, EncriptResponse jsonObject);

	String readDataFromImage(String token, ExpenseBillReaderRequest expenseBillReaderRequest);

}
