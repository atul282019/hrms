package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.BulkInviteRequest;
import com.cotodel.hrms.web.util.EncriptResponse;


@Repository
public interface BulkInviteService {

	String bulkInvite(String token, EncriptResponse jsonObject);

}
