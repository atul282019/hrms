package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface ReputeService {

	String getReputeEmployee(String token, EncriptResponse jsonObject);

}
