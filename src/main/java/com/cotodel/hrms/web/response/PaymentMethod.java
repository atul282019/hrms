package com.cotodel.hrms.web.response;

import java.util.Date;

public class PaymentMethod {
	public long cf_payment_id;
    public String payment_status;
    public int payment_amount;
    public String payment_currency;
    public String payment_message;
    public Date payment_time;
    public String bank_reference;
    public Object auth_id;
    public PaymentMethod payment_method;
    public String payment_group;
}
