package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.EmployeeCertificateRequest;
import com.cotodel.hrms.web.response.EmployeeDetailsNewRequest;
import com.cotodel.hrms.web.response.EmployeeDetailsRequest;
import com.cotodel.hrms.web.response.EmployeeExperienceRequest;
import com.cotodel.hrms.web.response.EmployeeFamilyDetailRequest;
import com.cotodel.hrms.web.response.EmployeeOnboarding;
import com.cotodel.hrms.web.response.EmployeeProjectRequest;
import com.cotodel.hrms.web.response.EmployeeQualificationRequest;


@Repository
public interface EmployeeDetailService {

	String saveEmployeeDetail(String token, EmployeeDetailsRequest employeeDetailRequest);

	String saveFamilyDetail(String token, EmployeeFamilyDetailRequest employeeFamilyDetailRequest);

	String getEmployeeDetail(String token, EmployeeDetailsRequest employeeFamilyDetailRequest);

	String getEmployeeFamilyDetail(String token, EmployeeFamilyDetailRequest employeeFamilyDetailRequest);

	String saveFamilyQualification(String token, EmployeeQualificationRequest employeeQualificationRequest);

	String getEmployeeQualificationDetail(String token, EmployeeQualificationRequest employeeQualificationRequest);

	String saveEmpCertificateDetail(String token, EmployeeExperienceRequest employeeExperienceRequest);

	String getEmployeeExperience(String token, EmployeeExperienceRequest employeeExperienceRequest);

	String saveEmployeeCertificate(String token, EmployeeCertificateRequest employeeCertificateRequest);

	String getEmployeeCertificateDetail(String token, EmployeeCertificateRequest employeeCertificateRequest);

	String saveEmployeeProject(String token, EmployeeProjectRequest employeeProjectRequest);

	String getEmployeeProjectDetail(String token, EmployeeProjectRequest employeeProjectRequest);

	String saveEmployeeOnboarding(String token, EmployeeOnboarding employeeOnboarding);

	String getEmployeeOnboarding(String token, EmployeeOnboarding employeeOnboarding);


}
