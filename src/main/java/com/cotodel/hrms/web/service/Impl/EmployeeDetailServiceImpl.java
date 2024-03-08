package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeeCertificateRequest;
import com.cotodel.hrms.web.response.EmployeeDetailsRequest;
import com.cotodel.hrms.web.response.EmployeeExperienceRequest;
import com.cotodel.hrms.web.response.EmployeeFamilyDetailRequest;
import com.cotodel.hrms.web.response.EmployeeProjectRequest;
import com.cotodel.hrms.web.response.EmployeeQualificationRequest;
import com.cotodel.hrms.web.service.EmployeeDetailService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class EmployeeDetailServiceImpl implements EmployeeDetailService {

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String saveEmployeeDetail(String token, EmployeeDetailsRequest employeeDetailRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeDetailRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.empDetails);
		//return CommonUtility.userRequestforMultipartfile(token,employeeDetailRequest, applicationConstantConfig.employerServiceBaseUrl+CommonUtils.empDetails);
	}

	@Override
	public String saveFamilyDetail(String token, EmployeeFamilyDetailRequest employeeFamilyDetailRequest) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeFamilyDetailRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.empFamilyDetails);
	}

	@Override
	public String getEmployeeDetail(String token, EmployeeDetailsRequest employeeFamilyDetailRequest) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeFamilyDetailRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmployeeDetails);
	}

	@Override
	public String getEmployeeFamilyDetail(String token, EmployeeFamilyDetailRequest employeeFamilyDetailRequest) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeFamilyDetailRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmployeeFamilyDetails);
	}

	@Override
	public String saveFamilyQualification(String token, EmployeeQualificationRequest employeeQualificationRequest) {
		// TODO Auto-generated method stub
		//return CommonUtility.userRequestforMultipartfile(token,employeeQualificationRequest, applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveEmployeeQualification);
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeQualificationRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveEmployeeQualification);
	}

	@Override
	public String getEmployeeQualificationDetail(String token,
			EmployeeQualificationRequest employeeQualificationRequest) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeQualificationRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmployeeQualification);
	}

	@Override
	public String saveEmpCertificateDetail(String token, EmployeeExperienceRequest	 employeeExperienceRequest) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeExperienceRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveEmpExperience);
	}

	@Override
	public String getEmployeeExperience(String token, EmployeeExperienceRequest employeeExperienceRequest) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeExperienceRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmpExperience);
	}

	@Override
	public String saveEmployeeCertificate(String token, EmployeeCertificateRequest employeeCertificateRequest) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeCertificateRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveEmpCertificate);
	}

	@Override
	public String getEmployeeCertificateDetail(String token, EmployeeCertificateRequest employeeCertificateRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeCertificateRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmpCertificate);
	}

	@Override
	public String saveEmployeeProject(String token, EmployeeProjectRequest employeeProjectRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeProjectRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveEmpProject);
	}

	@Override
	public String getEmployeeProjectDetail(String token, EmployeeProjectRequest employeeProjectRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeProjectRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmpProject);
	}
	
}
