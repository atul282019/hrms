package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReputeUserRequest {
	
	private Long id;
	private String first_name;
    private String last_name;
    private String dateofbirth ;
    private String  gender;
    private String contact_number;
    private String email ;
    private String address ;
    private String org_type;
    private String  org_name;
    private String  mobile ;
    private int  email_verify_status;
    private int  mobile_verify_status;
    private String username;
    private String pwd ;
    private int employerid ;
    private int role_id ;
    private String orderId;
    private String otp;
    private String txnId;
    private int status ;
    private boolean updateStatus ;
    private boolean emailStatus ;
    private boolean erupistatus ;
    private String response;
    private String captcha;
    private String role;
    private String companyId;
    private String hrmsId;
    private String hrmsName;
    private String employeeName;
}
