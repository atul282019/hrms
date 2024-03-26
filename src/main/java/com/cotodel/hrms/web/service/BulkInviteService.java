package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.BulkInviteRequest;


@Repository
public interface BulkInviteService {

	String bulkInvite(String token, BulkInviteRequest bulkInviteRequest);

}
