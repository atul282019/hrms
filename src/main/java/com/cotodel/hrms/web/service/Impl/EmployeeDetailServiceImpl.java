package com.cotodel.hrms.web.service.Impl;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BulkConfirmationRequest;
import com.cotodel.hrms.web.response.EmployeeCertificateNewRequest;
import com.cotodel.hrms.web.response.EmployeeCertificateRequest;
import com.cotodel.hrms.web.response.EmployeeDetailsRequest;
import com.cotodel.hrms.web.response.EmployeeExperienceNewRequest;
import com.cotodel.hrms.web.response.EmployeeExperienceRequest;
import com.cotodel.hrms.web.response.EmployeeFamilyDetailRequest;
import com.cotodel.hrms.web.response.EmployeeOnboarding;
import com.cotodel.hrms.web.response.EmployeeProjectRequest;
import com.cotodel.hrms.web.response.EmployeeQualificationNewRequest;
import com.cotodel.hrms.web.response.EmployeeQualificationRequest;
import com.cotodel.hrms.web.service.EmployeeDetailService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.CopyUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class EmployeeDetailServiceImpl implements EmployeeDetailService {

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

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
		EmployeeQualificationNewRequest employeeQualificationNewRequest=new EmployeeQualificationNewRequest();
		CopyUtility.copyProperties(employeeQualificationRequest, employeeQualificationNewRequest);
		 String docfile ="";
		try {			
			byte[] byt = employeeQualificationRequest.getAttachment().getBytes();
			docfile = DatatypeConverter.printBase64Binary(byt);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String docfilename=employeeQualificationRequest.getAttachment().getOriginalFilename();
		employeeQualificationNewRequest.setDocfile(docfile);
		employeeQualificationNewRequest.setDocFileName(docfilename);
		//return CommonUtility.userRequestforMultipartfile(token,employeeQualificationRequest, applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveEmployeeQualification);
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeQualificationNewRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveEmployeeQualification);
	}

	@Override
	public String getEmployeeQualificationDetail(String token,
			EmployeeQualificationRequest employeeQualificationRequest) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeQualificationRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmployeeQualification);
	}

	@Override
	public String saveEmpCertificateDetail(String token, EmployeeExperienceRequest	 employeeExperienceRequest) {
		
		EmployeeExperienceNewRequest employeeExperienceNewRequest=new EmployeeExperienceNewRequest();
		CopyUtility.copyProperties(employeeExperienceRequest, employeeExperienceNewRequest);
		String docfile ="";
		try {			
			byte[] byt = employeeExperienceRequest.getAttachment().getBytes();
			docfile = DatatypeConverter.printBase64Binary(byt);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String docfilename=employeeExperienceRequest.getAttachment().getOriginalFilename();
		employeeExperienceNewRequest.setDocfile(docfile);
		employeeExperienceNewRequest.setDocFileName(docfilename);
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeExperienceNewRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveEmpExperience);
	}

	@Override
	public String getEmployeeExperience(String token, EmployeeExperienceRequest employeeExperienceRequest) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeExperienceRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmpExperience);
	}

	@Override
	public String saveEmployeeCertificate(String token, EmployeeCertificateRequest employeeCertificateRequest) {

		EmployeeCertificateNewRequest employeeCertificateNewRequest=new EmployeeCertificateNewRequest();
		CopyUtility.copyProperties(employeeCertificateRequest, employeeCertificateNewRequest);
		 String docfile ="";
		try {			
			byte[] byt = employeeCertificateRequest.getAttachment().getBytes();
			docfile = DatatypeConverter.printBase64Binary(byt);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String docfilename=employeeCertificateRequest.getAttachment().getOriginalFilename();
		employeeCertificateNewRequest.setDocfile(docfile);
		employeeCertificateNewRequest.setDocFileName(docfilename);
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeCertificateNewRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveEmpCertificate);
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

	@Override
	public String saveEmployeeOnboarding(String token, EncriptResponse employeeOnboarding) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeOnboarding), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveEmpOnboarding);
	}

	@Override
	public String getEmployeeOnboarding(String token, EncriptResponse employeeOnboarding) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeOnboarding), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmployeeOnboarding);
	}

	@Override
	public String getEmployeeOnboardingFailList(String token, EncriptResponse employeeOnboarding) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeOnboarding), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmployeeOnboardingFailList);
	}

	@Override
	public String getEmployeeOnboardingById(String token, EncriptResponse employeeOnboarding) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeOnboarding), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmployeeOnboardingById);
	}

	@Override
	public String confirmBulkEmplOnboarding(String token, EncriptResponse employeeOnboarding) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeOnboarding), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.confirmBulkEmplOnboarding);
	}

	@Override
	public String saveEmployeeProfile(String token, EncriptResponse employeeOnboarding) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeOnboarding), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveEmployeeProfile);
		
		
	}

	@Override
	public String getEmployeeOnboardingByManagerId(String token, EncriptResponse employeeOnboarding) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeOnboarding), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmployeeOnboardingByIdManagerId);
	}
	
	@Override
	public String saveDirectorOnboarding(String token, EncriptResponse directorOnboarding) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(directorOnboarding), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveDirectorOnboarding);
	}
	
}
