//const cashfree = Cashfree({
  //  mode:"sandbox" //or production
//});

function getSessionId(){
//	document.getElementById("signinLoader").style.display = "flex";
	var employerId = document.getElementById("employerId").value;
	var orderAmount = document.getElementById("orderAmount").value;
	//var orderCurrency = document.getElementById("employerId").value;
	var customerId = document.getElementById("employerId").value;
	var customerName = document.getElementById("employerName").value;
	var customerEmail = document.getElementById("employerId").value;
	var customerPhone = document.getElementById("employerMobile").value;
	//var payment_session_id = document.getElementById("employerId").value;

	$.ajax({
		type: "POST",
		url: "/getCashfreePaymentSession",
		data: {
				"orgId":employerId,
				 "orderAmount" :orderAmount,
				"orderCurrency": "INR",
				"customerId" :customerId,
				"customerName": customerName,
				"customerEmail" :customerEmail,
				"customerPhone" : customerPhone,
				"payment_session_id" :""

		},
		beforeSend: function(xhr) {
		},
		success: function(data) {
			try {
	               let parsedData = typeof data === "string" ? JSON.parse(data) : data;

	               if (parsedData.data && parsedData.data.payment_session_id) {
	                   let payment_session_id = parsedData.data.payment_session_id;
					   let checkoutOptions = {
				       paymentSessionId: payment_session_id,
					   redirectTarget: "_self",
					  	};
						cashfree.checkout(checkoutOptions);
	                  
	               } else {
	                   console.error("Unexpected response structure:", parsedData);
	               }
	           } catch (error) {
	               console.error("Error parsing JSON:", error);
	           }
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
} 


function getOrderDetail(){
	document.getElementById("signinLoader").style.display = "flex";
	var order_id = document.getElementById("order_id").value;

	$.ajax({
		type: "POST",
		url: "/getOrderDetailByOrderId",
		data: {
				"orderId" :order_id,
			},
		beforeSend: function(xhr) {
		},
		success: function(data) {
			document.getElementById("signinLoader").style.display = "none";
			try {
	               let parsedData = typeof data === "string" ? JSON.parse(data) : data;
				   console.log(parsedData);
				   document.getElementById("name").innerHTML=parsedData.data.customerName;
				   document.getElementById("amount").innerHTML =parsedData.data.orderAmount;
				   document.getElementById("currency").innerHTML=parsedData.data.orderCurrency;
	           } catch (error) {
	               console.error("Error parsing JSON:", error);
	           }
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
} 
