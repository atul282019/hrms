function getExpanceMaster() {
	document.getElementById("signinLoader").style.display="flex";
	var employerid = document.getElementById("employerId").value;
	$.ajax({
		type: "GET",
		url: "/getExpensesCategory",
		data: {
			"employerId": employerid
		},
		
		 success: function(data){
            newData = data;
            console.log(newData);
			$("#expenseCategory option").remove();
            var obj = jQuery.parseJSON( data );
             obj = obj.data;
        	 var count=0;
         	for (var key in obj) {

             var values =  obj[key];
             var x = document.getElementById("expenseCategory");
             if(count==0){
             var option = document.createElement("option");
             option.text ="Select Expenses Category";
             option.value = "";
             x.add(option);
             }
             var option = document.createElement("option");
             option.text = values.expenseCategory;
             option.value = values.id;
             x.add(option);

             count++;
             }   
         },
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}

function getExpanceMasterMulti() {
	document.getElementById("signinLoader").style.display="flex";
	var employerid = document.getElementById("employerId").value;
	$.ajax({
		type: "GET",
		url: "/getExpensesCategory",
		data: {
			"employerId": employerid
		},
		
		 success: function(data){
            newData = data;
            console.log(newData);
			$("#expenseCategorySingle option").remove();
            var obj = jQuery.parseJSON( data );
             obj = obj.data;
        	 var count=0;
         	for (var key in obj) {

             var values =  obj[key];
             var x = document.getElementById("expenseCategorySingle");
             if(count==0){
             var option = document.createElement("option");
             option.text ="Select Expenses Category";
             option.value = "";
             x.add(option);
             }
             var option = document.createElement("option");
             option.text = values.expenseCategory;
             option.value = values.expenseCategory;
             x.add(option);

             count++;
             }   
         },
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}

async function submitExpenseDraft(){
	
	var employerId= document.getElementById("employerId").value; 
	var expenseCategory=  document.getElementById("expenseCategory").value ;
	var dateofExpense = document.getElementById("dateofExpense").value;
	var expenseTitle = document.getElementById("expenseTitle").value;
	var venderName= document.getElementById("venderName").value;
	var invoiceNumber = document.getElementById("invoiceNumber").value;
	var currency= document.getElementById("currency").value;
	var amount = document.getElementById("amount").value;
	var limit = document.getElementById("limit").value;
	var modeofPayment = document.getElementById("modeofPayment").value;
	var additionalRemark = document.getElementById("additionalRemark").value;
	var fileInput = document.getElementById("fileInput").value; 
	
	var imageAdd = document.getElementById("imageAdd").src;
	var imagePDF = document.getElementById("base64PDF").value; 
	
	var fileType = null;
	var fileBase64=null;
	
	if(expenseCategory=="" || expenseCategory==null){
		document.getElementById("expenseCategoryError").innerHTML="Please Select Expense Category";
		return false;
	}
	else{
		document.getElementById("expenseCategoryError").innerHTML="";
	}
	
	if(dateofExpense=="" || dateofExpense==null){
		document.getElementById("dateofExpenseError").innerHTML="Please Select Date";
		return false;
	}
	else{
		document.getElementById("dateofExpenseError").innerHTML="";
	}
	
	if(expenseTitle=="" || expenseTitle==null){
		document.getElementById("expenseTitleError").innerHTML="Please Enter Expense Title";
		return false;
	}
	else{
		document.getElementById("expenseTitleError").innerHTML="";
	}
	
	if(venderName=="" || venderName==null){
		document.getElementById("venderNameError").innerHTML="Please Enter Vendor Name";
		return false;
	}
	else{
		document.getElementById("venderNameError").innerHTML="";
	}
	if(invoiceNumber=="" || invoiceNumber==null){
		document.getElementById("invoiceNumberError").innerHTML="Please Enter Invoice Number";
		return false;
	}
	else{
		document.getElementById("invoiceNumberError").innerHTML="";
	}
	
	if(currency=="" || currency==null){
		document.getElementById("currencyError").innerHTML="Please Select Currency";
		return false;
	}
	else{
		document.getElementById("currencyError").innerHTML="";
	}
	
	if(amount=="" || amount==null){
		document.getElementById("amountError").innerHTML="Please Enter Amount";
		return false;
	}
	else{
		document.getElementById("amountError").innerHTML="";
	}
	if (amount >= limit) {
		    document.getElementById("amountSingleError").innerHTML = "Amount should not be greater than or equal to the periodic limit.";
		    return false;
		} else {
		    document.getElementById("amountSingleError").innerHTML = "";
		}
	if(modeofPayment=="" || modeofPayment==null){
		document.getElementById("modeofPaymentError").innerHTML="Please Select Mode of Payment";
		return false;
	}
	else{
		document.getElementById("modeofPaymentError").innerHTML="";
	}

	//const base64String = imageAdd;
	const cleanedBase64String = imagePDF.replace("data:application/pdf;base64,", "");
	
	try {
		
		if(imageAdd !==null && imageAdd !==""){
			const result = extractBase64Info(imageAdd);
			fileType = result.dataType;
		    fileBase64 = result.base64Content;
		}else{
	   		 fileType = "application/pdf";
		    fileBase64 = cleanedBase64String;
	    }
	    
	   // console.log('Data Type:', result.dataType);
	    //console.log('Base64 Content:', result.base64Content);
	} catch (error) {
	    console.error(error.message);
	}
	
	const clientKey = "client-secret-key"; // Extra security measure
	const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

	    // Concatenate data (must match backend)
	const dataString = employerId+expenseCategory+dateofExpense+expenseTitle+venderName+invoiceNumber+
	currency+amount+modeofPayment+additionalRemark+employerId+clientKey+secretKey;
	console.log("data string"+dataString); 
	// Generate SHA-256 hash
	const encoder = new TextEncoder();
	const data = encoder.encode(dataString);
	const hashBuffer = await crypto.subtle.digest("SHA-256", data);
	const hashArray = Array.from(new Uint8Array(hashBuffer));
	const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
			
	//console.log(cleanedBase64String);
	var formData = new FormData(expenseReimbursement);
	formData.append("employerId",employerId);
	formData.append("employeeId",employerId);
	//formData.append("employeeId",employeeId);
	formData.append("expenseCategory",expenseCategory);
	formData.append("dateOfExpense",dateofExpense);
	formData.append("expenseTitle",expenseTitle);
	formData.append("vendorName",venderName);
	formData.append("invoiceNumber",invoiceNumber);
	formData.append("currency",currency);
	formData.append("amount",amount);
	formData.append("modeOfPayment",modeofPayment);
	formData.append("remarks",additionalRemark);
	
	formData.append("fileInput",fileBase64);
	formData.append("fileType",fileType);
	//document.getElementById("signinLoader").style.display="flex";
	
	 	$.ajax({
		type: "POST",
	     url:"/addExpenseReimbursement",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			//close popup
			document.getElementById("ModalAddmanualexp").style.display = "none";
		    document.getElementById("ModalAddExpenseReimbursement").style.display = "none";
  			// Get the <span> element that closes the modal
			getExpanceCategoryList();
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });	
	
}


async function submitExpenseMultiple(){
	
	var employerId= document.getElementById("employerId").value; 
	var empId= document.getElementById("empId").value; 
	var expenseCategorySelect = document.getElementById("expenseCategory");
	var expenseCategory = expenseCategorySelect.options[expenseCategorySelect.selectedIndex].text;
	var dateofExpense = document.getElementById("dateofExpense").value;
	var expenseTitle = document.getElementById("expenseTitle").value;
	var venderName= document.getElementById("venderName").value;
	var invoiceNumber = document.getElementById("invoiceNumber").value;
	var currency= document.getElementById("currency").value;
	var amount = document.getElementById("amount").value;
	var limit = parseFloat(document.getElementById("limit").innerHTML); 
	var modeofPayment = document.getElementById("modeofPayment").value;
	var additionalRemark = document.getElementById("additionalRemark").value;
	var fileInput = document.getElementById("fileInput").value; 
	
	var imageAdd = document.getElementById("imageAdd").src;
	var imagePDF = document.getElementById("base64PDF").value; 
	
	var fileType = null;
	var fileBase64=null;
	
	var expenseTitleRegex = /^[A-Za-z]+( [A-Za-z]+)*$/;  // Alphanumeric with spaces between words
    var vendorNameRegex = /^[A-Za-z0-9]+( [A-Za-z0-9]+)*$/; // Alphanumeric, only spaces between words
    var invoiceNumberRegex = /^[A-Za-z0-9]+$/; // Only alphanumeric
    var amountRegex = /^(?:[1-9][0-9]{0,3}|9999)$/; // Numeric, max 9999
	
	if(expenseCategory=="" || expenseCategory==null){
		document.getElementById("expenseCategoryError").innerHTML="Please Select Expense Category";
		return false;
	}
	else{
		document.getElementById("expenseCategoryError").innerHTML="";
	}
	
	if(dateofExpense=="" || dateofExpense==null){
		document.getElementById("dateofExpenseError").innerHTML="Please Select Date";
		return false;
	}
	else{
		document.getElementById("dateofExpenseError").innerHTML="";
	}
	
	if(expenseTitle=="" || expenseTitle==null){
		document.getElementById("expenseTitleError").innerHTML="Please Enter Expense Title";
		return false;
	}
	else if (!expenseTitle.match(expenseTitleRegex)) {
		        document.getElementById("expenseTitleError").innerHTML = "Invalid Expense Title";
		        document.getElementById("expenseTitle").focus();
		        return false;
		    }
	else{
		document.getElementById("expenseTitleError").innerHTML="";
	}
	
	if(venderName=="" || venderName==null){
		document.getElementById("venderNameError").innerHTML="Please Enter Vendor Name";
		return false;
	}
	else if (!venderName.match(vendorNameRegex)) {
		       document.getElementById("venderNameError").innerHTML = "Special Characters Not Allowed in Vendor Name";
		       document.getElementById("venderName").focus();
		       return false;
		   } 
	else{
		document.getElementById("venderNameError").innerHTML="";
	}
	if(invoiceNumber=="" || invoiceNumber==null){
		document.getElementById("invoiceNumberError").innerHTML="Please Enter Invoice Number";
		return false;
	}	else if (!invoiceNumber.match(invoiceNumberRegex)) {
		        document.getElementById("invoiceNumberError").innerHTML = "Special Symbols Not Allowed";
		        document.getElementById("invoiceNumber").focus();
		        return false;
		    }
	else{
		document.getElementById("invoiceNumberError").innerHTML="";
	}
	
	if(currency=="" || currency==null){
		document.getElementById("currencyError").innerHTML="Please Select Currency";
		return false;
	}
	else{
		document.getElementById("currencyError").innerHTML="";
	}
	
	if(amount=="" || amount==null){
		document.getElementById("amountError").innerHTML="Please Enter Amount";
		return false;
	}
	else if (!amount.match(amountRegex)) {
		       document.getElementById("amountError").innerHTML = "Invalid Amount(Allowed Range: 1 - 9999)";
		       document.getElementById("amount").focus();
		       return false;
		   }
	else{
		document.getElementById("amountError").innerHTML="";
	}
	if (amount >= limit) {
		    document.getElementById("amountError").innerHTML = "Amount should not be greater than or equal to the periodic limit.";
		    return false;
		} else {
		    document.getElementById("amountError").innerHTML = "";
		}
	if(modeofPayment=="" || modeofPayment==null){
		document.getElementById("modeofPaymentError").innerHTML="Please Select Mode of Payment";
		return false;
	}
	else{
		document.getElementById("modeofPaymentError").innerHTML="";
	}

	//const base64String = imageAdd;
	const cleanedBase64String = imagePDF.replace("data:application/pdf;base64,", "");
	
	try {
		
		if(imageAdd !==null && imageAdd !==""){
			const result = extractBase64Info(imageAdd);
			fileType = result.dataType;
		    fileBase64 = result.base64Content;
		}else{
	   		 fileType = "application/pdf";
		    fileBase64 = cleanedBase64String;
	    }
	    
	   // console.log('Data Type:', result.dataType);
	    //console.log('Base64 Content:', result.base64Content);
	} catch (error) {
	    console.error(error.message);
	}
	
	const clientKey = "client-secret-key"; // Extra security measure
	const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

	// Concatenate data (must match backend)
	const dataString = employerId+expenseCategory+dateofExpense+expenseTitle+venderName+invoiceNumber+
	currency+amount+modeofPayment+additionalRemark+empId+clientKey+secretKey;
	console.log("data string"+dataString); 
	// Generate SHA-256 hash
	const encoder = new TextEncoder();
	const data = encoder.encode(dataString);
	const hashBuffer = await crypto.subtle.digest("SHA-256", data);
	const hashArray = Array.from(new Uint8Array(hashBuffer));
	const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
				
	//console.log(cleanedBase64String);
	var formData = new FormData(expenseReimbursement);
	formData.append("employerId",employerId);
	formData.append("employeeId",empId);
	//formData.append("employeeId",employeeId);
	formData.append("expenseCategory",expenseCategory);
	formData.append("dateOfExpense",dateofExpense);
	formData.append("expenseTitle",expenseTitle);
	formData.append("vendorName",venderName);
	formData.append("invoiceNumber",invoiceNumber);
	formData.append("currency",currency);
	formData.append("amount",amount);
	formData.append("modeOfPayment",modeofPayment);
	formData.append("remarks",additionalRemark);
	
	formData.append("fileInput",fileBase64);
	formData.append("fileType",fileType);
	
	formData.append("clientKey",clientKey);
	formData.append("hash",hashHex);
	//document.getElementById("signinLoader").style.display="flex";
	
	 	$.ajax({
		type: "POST",
	     url:"/addExpenseReimbursement",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			if(data1.status==true){ 
			//close popup
			document.getElementById("ModalAddmanualexp").style.display = "none";
		    document.getElementById("ModalAddExpenseReimbursement").style.display = "none";
  			// Get the <span> element that closes the modal
			getExpanceCategoryList();
			// Reset all form fields

			// Reset the form fields
			document.getElementById("expenseReimbursement").reset();

			// Clear file input manually
			document.getElementById("fileInput").value = "";

			// Hide the uploaded documents section
			document.getElementById("docViewDiv").style.display = "none";
			
			
			

			// Clear the uploaded document list
			document.getElementById("show-list").innerHTML = "";

			// Optionally, clear any displayed error messages
			var errorFields = document.querySelectorAll('[id$="Error"]');
			errorFields.forEach(function(field) {
			    field.innerHTML = "";
			});
			$('#expReimbursementsubmission').modal('show');
			
			}
			
			else if(data1.status==false)
   				{								
					//close popup
					document.getElementById("ModalAddmanualexp").style.display = "none";
				    document.getElementById("ModalAddExpenseReimbursement").style.display = "none";
					document.querySelector('#ModalReject .modal-bottom span.required-star').innerText = data1.message;
				   
					$('#ModalReject').modal('show');
				}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });	
	
}

async function submitExpenseSingleDraft(){
	
	var employerId= document.getElementById("employerId").value; 
	var empId= document.getElementById("empId").value; 
	var expenseCategory=  document.getElementById("expenseCategorySingle").value ;
	var dateofExpense = document.getElementById("dateSingle").value;
	var expenseTitle = document.getElementById("expenseTitleSingle").value;
	var venderName= document.getElementById("vendorNameSingle").value;
	var invoiceNumber = document.getElementById("invoiceNumberSingle").value;
	var currency= document.getElementById("currencySingle").value;
	var amount = document.getElementById("amountSingle").value;
	var limit = parseFloat(document.getElementById("limit").innerHTML); 
			
	var modeofPayment = document.getElementById("modeofpaymentSingle").value;
	var additionalRemark = document.getElementById("remarkSingle").value;
	
	var imageAdd = document.getElementById("imageAddSingle").src;
	var imagePDF = document.getElementById("base64PDFSingle").value; 
	
	var fileType = null;
	var fileBase64=null;
	
	if(expenseCategory=="" || expenseCategory==null){
		document.getElementById("expenseCategorySingleError").innerHTML="Please Select Expense Category";
		return false;
	}
	else{
		document.getElementById("expenseCategorySingleError").innerHTML="";
	}
	
	if(dateofExpense=="" || dateofExpense==null){
		document.getElementById("dateSingleError").innerHTML="Please Select Date";
		return false;
	}
	else{
		document.getElementById("dateSingleError").innerHTML="";
	}
	
	if(expenseTitle=="" || expenseTitle==null){
		document.getElementById("expenseTitleError").innerHTML="Please Enter Expense Title";
		return false;
	}
	else{
		document.getElementById("expenseTitleError").innerHTML="";
	}
	
	if(venderName=="" || venderName==null){
		document.getElementById("venderNameError").innerHTML="Please Enter Vendor Name";
		return false;
	}
	else{
		document.getElementById("venderNameError").innerHTML="";
	}
	if(invoiceNumber=="" || invoiceNumber==null){
		document.getElementById("invoiceNumberError").innerHTML="Please Enter Invoice Number";
		return false;
	}
	else{
		document.getElementById("invoiceNumberError").innerHTML="";
	}
	
	if(currency=="" || currency==null){
		document.getElementById("currencyError").innerHTML="Please Select Currency";
		return false;
	}
	else{
		document.getElementById("currencyError").innerHTML="";
	}
	
	if(amount=="" || amount==null){
		document.getElementById("amountError").innerHTML="Please Enter Amount";
		return false;
	}
	else{
		document.getElementById("amountError").innerHTML="";
	}
	if (amount >= limit) {
		    document.getElementById("amountSingleError").innerHTML = "Amount should not be greater than or equal to the periodic limit.";
		    return false;
		} else {
		    document.getElementById("amountSingleError").innerHTML = "";
		}
	if(modeofPayment=="" || modeofPayment==null){
		document.getElementById("modeofPaymentError").innerHTML="Please Select Mode of Payment";
		return false;
	}
	else{
		document.getElementById("modeofPaymentError").innerHTML="";
	}

	//const base64String = imageAdd;
	const cleanedBase64String = imagePDF.replace("data:application/pdf;base64,", "");
	
	try {
		
		if(imageAdd !==null && imageAdd !==""){
			const result = extractBase64Info(imageAdd);
			fileType = result.dataType;
		    fileBase64 = result.base64Content;
		}else{
	   		 fileType = "application/pdf";
		    fileBase64 = cleanedBase64String;
	    }
	    
	   // console.log('Data Type:', result.dataType);
	    //console.log('Base64 Content:', result.base64Content);
	} catch (error) {
	    console.error(error.message);
	}
	
	const clientKey = "client-secret-key"; // Extra security measure
	const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

	// Concatenate data (must match backend)
		
	const dataString = employerId+expenseCategory+dateofExpense+expenseTitle+venderName+invoiceNumber+
	currency+amount+modeofPayment+additionalRemark+empId+clientKey+secretKey;
	console.log("data string"+dataString); 
	// Generate SHA-256 hash
	const encoder = new TextEncoder();
	const data = encoder.encode(dataString);
	const hashBuffer = await crypto.subtle.digest("SHA-256", data);
	const hashArray = Array.from(new Uint8Array(hashBuffer));
	const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
		
	//console.log(cleanedBase64String);
	var formData = new FormData(expenseReimbursement);
	formData.append("employerId",employerId);
	formData.append("employeeId",empId);
	//formData.append("employeeId",employeeId);
	formData.append("expenseCategory",expenseCategory);
	formData.append("dateOfExpense",dateofExpense);
	formData.append("expenseTitle",expenseTitle);
	formData.append("vendorName",venderName);
	formData.append("invoiceNumber",invoiceNumber);
	formData.append("currency",currency);
	formData.append("amount",amount);
	formData.append("modeOfPayment",modeofPayment);
	formData.append("remarks",additionalRemark);
	formData.append("fileInput",fileBase64);
	formData.append("fileType",fileType);
	
	formData.append("clientKey",clientKey);
		formData.append("hash",hashHex);
	//document.getElementById("signinLoader").style.display="flex";
	
	 	$.ajax({
		type: "POST",
	     url:"/addExpenseReimbursement",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			//close popup
			document.getElementById("ModalAddfreshexpContinew").style.display = "none";;
			
		   document.getElementById("ModalAddExpenseReimbursement").style.display = "none";;
	
  			// Get the <span> element that closes the modal
			getExpanceCategoryList();
			
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });	
	
}


async function submitExpenseSingle(){
	
	var employerId= document.getElementById("employerId").value;
	var empId= document.getElementById("empId").value; 
	var expenseCategory=  document.getElementById("expenseCategorySingle").value ;
	var dateofExpense = document.getElementById("dateSingle").value;
	var expenseTitle = document.getElementById("expenseTitleSingle").value;
	var venderName= document.getElementById("vendorNameSingle").value;
	var invoiceNumber = document.getElementById("invoiceNumberSingle").value;
	var currency= document.getElementById("currencySingle").value;
	var amount = document.getElementById("amountSingle").value;
	var limit = parseFloat(document.getElementById("limit").innerHTML); 
		
	var modeofPayment = document.getElementById("modeofpaymentSingle").value;
	var additionalRemark = document.getElementById("remarkSingle").value;
	
	var imageAdd = document.getElementById("imageAddSingle").src;
	var imagePDF = document.getElementById("base64PDFSingle").value; 

	
	var fileType = null;
	var fileBase64=null;
	
	var expenseTitleRegex = /^[A-Za-z]+( [A-Za-z]+)*$/;  // Alphanumeric with spaces between words
    var vendorNameRegex = /^[A-Za-z0-9]+( [A-Za-z0-9]+)*$/; // Alphanumeric, only spaces between words
    var invoiceNumberRegex = /^[A-Za-z0-9]+$/; // Only alphanumeric
    var amountRegex = /^(?:[1-9][0-9]{0,3}|9999)$/; // Numeric, max 9999
	
	if(expenseCategory=="" || expenseCategory==null){
		document.getElementById("expenseCategorySingleError").innerHTML="Please Select Expense Category";
		return false;
	}
	else{
		document.getElementById("expenseCategorySingleError").innerHTML="";
	}
	
	if(dateofExpense=="" || dateofExpense==null){
		document.getElementById("dateSingleError").innerHTML="Please Select Date";
		return false;
	}
	else{
		document.getElementById("dateSingleError").innerHTML="";
	}
	
	if(expenseTitle=="" || expenseTitle==null){
		document.getElementById("expenseTitleSingleError").innerHTML="Please Enter Expense Title";
		return false;
	}	
	else if (!expenseTitle.match(expenseTitleRegex)) {
	        document.getElementById("expenseTitleSingleError").innerHTML = "Invalid Expense Title";
	        document.getElementById("expenseTitleSingle").focus();
	        return false;
	    }
	else{
		document.getElementById("expenseTitleSingleError").innerHTML="";
	}
	
	if(venderName=="" || venderName==null){
		document.getElementById("vendorNameSingleError").innerHTML="Please Enter Vendor Name";
		return false;
	}
	else if (!venderName.match(vendorNameRegex)) {
	       document.getElementById("vendorNameSingleError").innerHTML = "Special Characters Not Allowed in Vendor Name";
	       document.getElementById("vendorNameSingle").focus();
	       return false;
	   } 
	else{
		document.getElementById("vendorNameSingleError").innerHTML="";
	}
	if(invoiceNumber=="" || invoiceNumber==null){
		document.getElementById("invoiceNumberSingleError").innerHTML="Please Enter Invoice Number";
		return false;
	}
	else if (!invoiceNumber.match(invoiceNumberRegex)) {
	        document.getElementById("invoiceNumberSingleError").innerHTML = "Special Symbols Not Allowed";
	        document.getElementById("invoiceNumberSingle").focus();
	        return false;
	    }
	else{
		document.getElementById("invoiceNumberSingleError").innerHTML="";
	}
	
	if(currency=="" || currency==null){
		document.getElementById("currencySingleError").innerHTML="Please Select Currency";
		return false;
	}
	else{
		document.getElementById("currencySingleError").innerHTML="";
	}
	
	if(amount=="" || amount==null){
		document.getElementById("amountSingleError").innerHTML="Please Enter Amount";
		return false;
	}
	else if (!amount.match(amountRegex)) {
	       document.getElementById("amountSingleError").innerHTML = "Invalid Amount (Allowed Range: 1 - 9999)";
	       document.getElementById("amountSingle").focus();
	       return false;
	   }
	else{
		document.getElementById("amountSingleError").innerHTML="";
	}
	if (amount >= limit) {
	    document.getElementById("amountSingleError").innerHTML = "Amount should not be greater than or equal to the periodic limit.";
	    return false;
	} else {
	    document.getElementById("amountSingleError").innerHTML = "";
	}

	if(modeofPayment=="" || modeofPayment==null){
		document.getElementById("modeofpaymentSingleError").innerHTML="Please Select Mode of Payment";
		return false;
	}
	else{
		document.getElementById("modeofpaymentSingleError").innerHTML="";
	}

	//const base64String = imageAdd;
	const cleanedBase64String = imagePDF.replace("data:application/pdf;base64,", "");
	
	try {
		
		if(imageAdd !==null && imageAdd !==""){
			const result = extractBase64Info(imageAdd);
			fileType = result.dataType;
		    fileBase64 = result.base64Content;
		}else{
	   		 fileType = "application/pdf";
		    fileBase64 = cleanedBase64String;
	    }
	    
	   // console.log('Data Type:', result.dataType);
	    //console.log('Base64 Content:', result.base64Content);
	} catch (error) {
	    console.error(error.message);
	}
	
	const clientKey = "client-secret-key"; // Extra security measure
	const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

	// Concatenate data (must match backend)
		
	const dataString = employerId+expenseCategory+dateofExpense+expenseTitle+venderName+invoiceNumber+
	currency+amount+modeofPayment+additionalRemark+empId+clientKey+secretKey;
	console.log("data string"+dataString); 
	// Generate SHA-256 hash
	const encoder = new TextEncoder();
	const data = encoder.encode(dataString);
	const hashBuffer = await crypto.subtle.digest("SHA-256", data);
	const hashArray = Array.from(new Uint8Array(hashBuffer));
	const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
	//console.log(cleanedBase64String);
	
	var formData = new FormData(expenseReimbursement);
	formData.append("employerId",employerId);
	formData.append("employeeId",empId);
	//formData.append("employeeId",employeeId);
	formData.append("expenseCategory",expenseCategory);
	formData.append("dateOfExpense",dateofExpense);
	formData.append("expenseTitle",expenseTitle);
	formData.append("vendorName",venderName);
	formData.append("invoiceNumber",invoiceNumber);
	formData.append("currency",currency);
	formData.append("amount",amount);
	formData.append("modeOfPayment",modeofPayment);
	formData.append("remarks",additionalRemark);
	
	formData.append("fileInput",fileBase64);
	formData.append("fileType",fileType);
	
	formData.append("clientKey",clientKey);
	formData.append("hash",hashHex);
	
	 	$.ajax({
		type: "POST",
	     url:"/addExpenseReimbursement",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			//close popup
			document.getElementById("ModalAddfreshexpContinew").style.display = "none";;
			
		   document.getElementById("ModalAddExpenseReimbursement").style.display = "none";;
			
		   	resetFormFields();
  			// Get the <span> element that closes the modal
			getExpanceCategoryList();
			
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });	
	
}
function resetFormFields() {
    // Clear all input fields
    document.getElementById("expenseCategorySingle").value = "";
    document.getElementById("dateSingle").value = "";
    document.getElementById("expenseTitleSingle").value = "";
    document.getElementById("vendorNameSingle").value = "";
    document.getElementById("invoiceNumberSingle").value = "";
    document.getElementById("currencySingle").value = "";
    document.getElementById("amountSingle").value = "";
    document.getElementById("modeofpaymentSingle").value = "";
    document.getElementById("remarkSingle").value = "";

    // Clear error messages
    let errorFields = document.querySelectorAll(".error-msg");
    errorFields.forEach((field) => {
        field.innerHTML = "";
    });

    // Reset image and PDF inputs if applicable
    document.getElementById("imageAddSingle").src = "";
    document.getElementById("base64PDFSingle").value = "";
}

function getExpanceCategoryList(){
	document.getElementById("signinLoader").style.display="flex";
	var employerid = document.getElementById("employerId").value;
	var empId = document.getElementById("empId").value;
	$.ajax({
		type: "GET",
		url: "/getExpanseReimbursement",
		data: {
			"employeeId": empId,
			"employerId": employerid
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.list;
			 //console.log(data2);
			document.getElementById("signinLoader").style.display="none";
			
			var table = $('#reimbursementTable').DataTable( {
	          destroy: true,	
		     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],
             "language": {"emptyTable": "No Data available"  },
	        
	         "aaData": data2,
      		  "aoColumns": [ 
				
                { "mData": "id", "render": function (data2, type, row) {
					 return ' <div class="table-check"><input type="checkbox" value="'+data2+'" id="customCheck3" name="customCheck3" ></div>';
                 }}, 
                { "mData": "sequenceId"},   
                { "mData": "created_date"},   
			    { "mData": "expenseCategory"},
			    { "mData": "expenseTitle"},   
			 	//{ "mData": "amount"},    
			 	{ "mData": function (data1, type, row) {
			        return data1.currency + " " + data1.amount;
			    }},
				{ "mData": "statusMessage"},
				{ "mData": "modeOfPayment"},        
      		  	{ "mData": "id", "render": function (data1, type, row) {
                    return '<td> <div class="d-flex align-items-center"> <div class="dropdown no-arrow ml-2"> <a class="dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="fas fa-ellipsis-v fa-sm"></i></a><br> <div class="dropdown-menu dropdown-menu-right shadow"  aria-labelledby="userDropdown"><button class="dropdown-item py-2" onclick="deleteExpance(this)" > Delete  </button>  <button class="dropdown-item py-2" id="btnView" onclick="viewExpance(this)"> View</button> </div> </div> </div> </td>';
                 }}, 
    		 	],
    		 	createdRow: function (row, data2, dataIndex) 
                    {
                     //console.log("row : "+JSON.stringify(data2));
                   
                 	var expenseCategory = data2.expenseCategory;
                 	var statusMessage = data2.statusMessage;
                	
                     if(expenseCategory=="Conveyance")
                     {
					 var imgTag = '<img src="img/taxi.svg" alt="" class="mr-2">'+expenseCategory;
                      $(row).find('td:eq(3)').html(imgTag);
                     }
                     if(expenseCategory=="Miscellaneous")
                     {
					 var imgTag = ' <img src="img/Miscellaneous.svg" alt="" class="mr-2">'+expenseCategory;
 					
                      $(row).find('td:eq(3)').html(imgTag);
                     }
                     
                     if(expenseCategory=="Food")
                     {
						 var imgTag = ' <img src="img/food.svg" alt="" class="mr-2">'+expenseCategory;
	 					 $(row).find('td:eq(3)').html(imgTag);
                     }
                     
                     if(expenseCategory=="Cash Advance")
                     {
					 var imgTag = '<img src="img/cash.svg" alt="" class="mr-2">'+expenseCategory;
                      $(row).find('td:eq(4)').html(imgTag);
                     }
                     if(expenseCategory=="Travel")
                     {
					 var imgTag = '<img src="img/Travel.svg" alt="" class="mr-2">'+expenseCategory;
 					
                      $(row).find('td:eq(3)').html(imgTag);
                     }
                     if(expenseCategory=="Stay")
                     {
					 var imgTag = '<img src="img/hotel.svg" alt="" class="mr-2">'+expenseCategory;
                      $(row).find('td:eq(3)').html(imgTag);
                     }
                     
                     if(statusMessage=="Draft")
                     {
                      $(row).find('td:eq(6)').addClass('td-btn draft');
                     }
                     if(statusMessage=="Submitted")
                     {
                      $(row).find('td:eq(6)').addClass('td-btn submitted');
                     }
                  }
      		});		
			
		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}

function getExpanceCategoryApprovalList(){
	document.getElementById("signinLoader").style.display="flex";
	var employerid = document.getElementById("employerId").value;
	var empId = document.getElementById("empId").value;
	$.ajax({
		type: "GET",
		url: "/getExpanseReimbursementApprovalList",
		data: {
			"employeeId": empId,
			"employerId": employerid
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.list;
			 //console.log(data2);
			document.getElementById("signinLoader").style.display="none";
			
			var table = $('#tableApprovalFlow').DataTable( {
	          destroy: true,	
		     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],
             "language": {"emptyTable": "No Data available"  },
	        
	         "aaData": data2,
      		  "aoColumns": [ 
				
                { "mData": "id", "render": function (data2, type, row) {
					 return ' <div class="table-check"><input type="checkbox" value="'+data2+'" id="customCheck4" name="customCheck4" ></div>';
                 }}, 
                { "mData": "sequenceId"},   
				{ "mData": "expenseCategory"},
				{ "mData": "name"}, 
				{ "mData": "depratment"}, 
               // { "mData": "createationDate"},   
			    { "mData": "expenseTitle"},   
				   
			 	//{ "mData": "amount"},    
			 	{ "mData": function (data2, type, row) {
			        return data2.currency + " " + data2.amount;
			    }},
				{ "mData": "statusMessage"},
				{ "mData": "modeOfPayment"},        
      		  	{ "mData": "id", "render": function (data2, type, row) {
                    return '<td> <div  class="d-flex align-items-center">  <div class="dropdown no-arrow ml-2"> <a class="dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="fas fa-ellipsis-v fa-sm"></i></a><br> <div class="dropdown-menu dropdown-menu-right shadow"  aria-labelledby="userDropdown"><button class="dropdown-item py-2" onclick="deleteExpance(this)" > Delete  </button><button class="dropdown-item py-2" id="btnView" onclick="viewExpanceApproval(this)"> View  </button> </div> </div> </div> </td>';
                 }}, 
    		 	],
    		 	createdRow: function (row, data2, dataIndex) 
                    {
                     //console.log("row : "+JSON.stringify(data2));
                   
                 	var expenseCategory = data2.expenseCategory;
                 	//var statusMessage = data2.statusMessage;
                	
                     if(expenseCategory=="Conveyance")
                     {
					 var imgTag = '<img src="img/taxi.svg" alt="" class="mr-2">'+expenseCategory;
                      $(row).find('td:eq(2)').html(imgTag);
                     }
                     if(expenseCategory=="Miscellaneous")
                     {
					 var imgTag = ' <img src="img/Miscellaneous.svg" alt="" class="mr-2">'+expenseCategory;
 					
                      $(row).find('td:eq(2)').html(imgTag);
                     }
                     
                     if(expenseCategory=="Food")
                     {
						 var imgTag = ' <img src="img/food.svg" alt="" class="mr-2">'+expenseCategory;
	 					 $(row).find('td:eq(2)').html(imgTag);
                     }
                     
                     if(expenseCategory=="Cash Advance")
                     {
					 var imgTag = '<img src="img/cash.svg" alt="" class="mr-2">'+expenseCategory;
                      $(row).find('td:eq(2)').html(imgTag);
                     }
                     if(expenseCategory=="Travel")
                     {
					 var imgTag = '<img src="img/Travel.svg" alt="" class="mr-2">'+expenseCategory;
 					
                      $(row).find('td:eq(2)').html(imgTag);
                     }
                     if(expenseCategory=="Stay")
                     {
					 var imgTag = '<img src="img/hotel.svg" alt="" class="mr-2">'+expenseCategory;
                      $(row).find('td:eq(2)').html(imgTag);
                     }
                     
                  //   if(statusMessage=="Draft")
                  //   {
                  //    $(row).find('td:eq(6)').addClass('td-btn draft');
                  //   }
                  //   if(statusMessage=="Submitted")
                  //   {
                  //    $(row).find('td:eq(6)').addClass('td-btn submitted');
                  //   }
                  }
      		});		
			
		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}


function getTravelExpenseApprovalList(){
	document.getElementById("signinLoader").style.display="flex";
	var employerid = document.getElementById("employerId").value;
	var empId = document.getElementById("empId").value;
	$.ajax({
		type: "GET",
		url: "/getTravelRequestApprovalList",
		data: {
			"employeeId": empId,
			"employerId": employerid
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			console.log("Travel request data"+data1.data);
			document.getElementById("signinLoader").style.display="none";
			
			var table = $('#tableCashAdvacceTravelApprovalFlow').DataTable( {
	          destroy: true,	
		     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],
             "language": {"emptyTable": "No Data available"  },
	        
	         "aaData": data2,
      		  "aoColumns": [ 
				
                { "mData": "id", "render": function (data2, type, row) {
					 return ' <div class="table-check"><input type="checkbox" value="'+data2+'" id="customCheck4" name="customCheck4" ></div>';
                 }}, 
                { "mData": "sequenceId"},   
				{ "mData": "requestType"},
				{ "mData": "name"}, 
				{ "mData": "depratment"}, 
               // { "mData": "createationDate"},   
			   // { "mData": "expenseTitle"},   
				   
			 	//{ "mData": "amount"},    
			 	{ "mData": function (data2, type, row) {
			        return 'INR' + " " + data2.amount;
			    }},
				{ "mData": "statusRemarks"},
				{ "mData": "paymentMode"}, 
				{ "mData": function (data2, type, row) {
			        return 'INR' + " " + data2.approvedAmount;
			    }},   
				{ "mData": "approvedBy"},  
      		  	{ "mData": "id", "render": function (data2, type, row) {
                    return '<td> <div  class="d-flex align-items-center">  <div class="dropdown no-arrow ml-2"> <a class="dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="fas fa-ellipsis-v fa-sm"></i></a><br> <div class="dropdown-menu dropdown-menu-right shadow"  aria-labelledby="userDropdown"><button class="dropdown-item py-2" onclick="deleteAdvanceTravel(this)" > Delete  </button><button class="dropdown-item py-2" id="btnView" onclick="viewAdvanceTravelApprove(this)"> View  </button> </div> </div> </div> </td>';
                 }}, 
    		 	],
    		 	createdRow: function (row, data2, dataIndex) 
                    {
                     //console.log("row : "+JSON.stringify(data2));
                   
                 	var expenseCategory = data2.expenseCategory;
                 	//var statusMessage = data2.statusMessage;
                	
                     if(expenseCategory=="Conveyance")
                     {
					 var imgTag = '<img src="img/taxi.svg" alt="" class="mr-2">'+expenseCategory;
                      $(row).find('td:eq(2)').html(imgTag);
                     }
                     if(expenseCategory=="Miscellaneous")
                     {
					 var imgTag = ' <img src="img/Miscellaneous.svg" alt="" class="mr-2">'+expenseCategory;
 					
                      $(row).find('td:eq(2)').html(imgTag);
                     }
                     
                     if(expenseCategory=="Food")
                     {
						 var imgTag = ' <img src="img/food.svg" alt="" class="mr-2">'+expenseCategory;
	 					 $(row).find('td:eq(2)').html(imgTag);
                     }
                     
                     if(expenseCategory=="Cash Advance")
                     {
					 var imgTag = '<img src="img/cash.svg" alt="" class="mr-2">'+expenseCategory;
                      $(row).find('td:eq(2)').html(imgTag);
                     }
                     if(expenseCategory=="Travel")
                     {
					 var imgTag = '<img src="img/Travel.svg" alt="" class="mr-2">'+expenseCategory;
 					
                      $(row).find('td:eq(2)').html(imgTag);
                     }
                     if(expenseCategory=="Stay")
                     {
					 var imgTag = '<img src="img/hotel.svg" alt="" class="mr-2">'+expenseCategory;
                      $(row).find('td:eq(2)').html(imgTag);
                     }
                     
                  //   if(statusMessage=="Draft")
                  //   {
                  //    $(row).find('td:eq(6)').addClass('td-btn draft');
                  //   }
                  //   if(statusMessage=="Submitted")
                  //   {
                  //    $(row).find('td:eq(6)').addClass('td-btn submitted');
                  //   }
                  }
      		});		
			
		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}

 function  viewExpance(value){
	 var row = jQuery(value).closest('tr');
	 var  id = $(row).find("input[name='customCheck3']").val();
	 document.getElementById("signinLoader").style.display="flex";
	 	$.ajax({
		type: "POST",
		url:"/viewExpenseReimbursement",
        data: {
				"id": id
       		 },
       		  beforeSend : function(xhr) {
				//xhr.setRequestHeader(header, token);
				},
            success: function(data){
            newData = data;
            //console.log(newData);
            var data1 = jQuery.parseJSON( newData );
			var data2 = data1.data;
			
			//console.log(newData);
  			document.getElementById("viewExpenseCategory").value= data2.expenseCategory;
			document.getElementById("viewDateofExpense").value = data2.dateOfExpense;
			document.getElementById("viewExpenseTitle").value = data2.expenseTitle;
			document.getElementById("viewVenderName").value =data2.vendorName;
			document.getElementById("viewInvoiceNumber").value = data2.invoiceNumber;
			document.getElementById("viewCurrency").value = data2.currency;
			document.getElementById("viewAmount").value = data2.amount;
		    document.getElementById("viewModeofPayment").value = data2.modeOfPayment;
			document.getElementById("viewAdditionalRemark").value =data2.remarks;
			document.getElementById("viewStatus").value =data2.expenseTitle;
			//document.getElementById("image").src="data:image/jpeg;base64,"+data2.file;
			if(data2.fileType =="application/pdf"){
			
				document.getElementById("imagePDFView").style="display: block";
				document.getElementById("imageView").style="display: none";
				document.getElementById("imagePDFView").src="data:application/pdf;base64,"+data2.file;
			}
			else{
				document.getElementById("imageView").style="display: block";
				document.getElementById("imagePDFView").style="display: none";
				document.getElementById("imageView").src="data:image/jpeg;base64,"+data2.file;
			}
			//document.getElementById("").value =data2.expenseTitle;
			document.getElementById("signinLoader").style.display="none";
			var modal = document.getElementById("ModalSubmitExpenseReimbursementView");
			  modal.style.display = "block";
           },
         error: function(e){
             alert('Error: ' + e);
         }
    }); 
				
 }

 function  viewExpanceApproval(value){
  var row = jQuery(value).closest('tr');
  var  id = $(row).find("input[name='customCheck4']").val();
  document.getElementById("signinLoader").style.display="flex";
  	$.ajax({
 	type: "POST",
 	url:"/getExpensesReimbursementDetailById",
        data: {
 			"id": id
       		 },
       		  beforeSend : function(xhr) {
 			//xhr.setRequestHeader(header, token);
 			},
            success: function(data){
            newData = data;
            //console.log(newData);
            var data1 = jQuery.parseJSON( newData );
 		var data2 = data1.data;
 		
 		//console.log(newData);
		document.getElementById("expenseId").value= data2.id;
  		document.getElementById("approvalExpense").value= data2.expenseCategory;
 		document.getElementById("approvalExpenseDate").value = data2.createationDate;
 		document.getElementById("approvalExpenseTitle").value = data2.expenseTitle;
 		document.getElementById("approvalExpenseVenderName").value =data2.vendorName;
 		document.getElementById("approvalExpenseInviceNumber").value = data2.invoiceNumber;
 		document.getElementById("approvalExpenseCurrency").value = data2.currency;
 		document.getElementById("approvalExpenseAmount").value = data2.amount;
 	    document.getElementById("approvalExpensePaymentMode").value = data2.modeOfPayment;
 		document.getElementById("approvalExpenseRemark").value =data2.remarks;
		document.getElementById("submittedBy").innerHTML =data2.name;
 		document.getElementById("viewStatus").value =data2.expenseTitle;
		
		// Reject Popup value
		
		document.getElementById("idReject").innerHTML= data2.id;
  		document.getElementById("categoryReject").innerHTML= data2.expenseCategory;
 		document.getElementById("submittedDateReject").innerHTML = data2.createationDate;
 		//document.getElementById("approvalExpenseTitle").value = data2.expenseTitle;
 		document.getElementById("nameReject").innerHTML =data2.name;
 		//document.getElementById("approvalExpenseInviceNumber").value = data2.invoiceNumber;
 		document.getElementById("currencyReject").innerHTML = data2.currency;
 		document.getElementById("amountReject").innerHTML = data2.amount;
 	    document.getElementById("bankAccount").innerHTML = data2.modeOfPayment;
 		////document.getElementById("approvalExpenseRemark").value =data2.remarks;
		//document.getElementById("submittedBy").innerHTML =data2.name;
		
		
 		//document.getElementById("image").src="data:image/jpeg;base64,"+data2.file;
 		if(data2.fileType =="application/pdf"){
 		
 			document.getElementById("imagePDFViewApproval").style="display: block";
 			document.getElementById("imageViewApproval").style="display: none";
 			document.getElementById("imagePDFViewApproval").src="data:application/pdf;base64,"+data2.file;
 		}
 		else{
 			document.getElementById("imageViewApproval").style="display: block";
 			document.getElementById("imagePDFViewApproval").style="display: none";
 			document.getElementById("imageViewApproval").src="data:image/jpeg;base64,"+data2.file;
 		}
 		//document.getElementById("").value =data2.expenseTitle;
 		document.getElementById("signinLoader").style.display="none";
 		var modal = document.getElementById("ModalViewPendingExp");
 		  modal.style.display = "block";
           },
         error: function(e){
             alert('Error: ' + e);
         }
    }); 
 			
 }
function deleteExpance(value){
	
	var result = confirm("Are you sure you want to delete?");
	if (result) {
		 document.getElementById("signinLoader").style.display="flex";
	}
	else{
		return false;
	}
	 var row = jQuery(value).closest('tr');
	 var  id = $(row).find("input[name='customCheck3']").val();
	
	 	$.ajax({
		type: "POST",
		url:"/deleteExpanseReimbursement",
        data: {
				"id": id
       		 },
       		  beforeSend : function(xhr) {
				//xhr.setRequestHeader(header, token);
				},
            success: function(data){
            newData = data;
            //console.log(newData);
            var data1 = jQuery.parseJSON( newData );
			var data2 = data1.data;
			document.getElementById("signinLoader").style.display="none";
			getExpanceCategoryList();
			//console.log(newData);
           },
         error: function(e){
             alert('Error: ' + e);
         }
    }); 
	 
	 
}

 ///open model popup of view reimbursement
  document.addEventListener('DOMContentLoaded', function () {
	
  var modal = document.getElementById("ModalSubmitExpenseReimbursementView");
  // Close the modal if the user clicks outside of it
   window.onclick = function(event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
 }
 
 
  var modalfirst = document.getElementById("ModalAddExpenseReimbursement");
  // Close the modal if the user clicks outside of it
   window.onclick = function(event) {
    if (event.target == modal) {
      modalfirst.style.display = "none";
    }
 }
 
 
 firstPopup.onclick = function() {
   modalfirst.style.display = "none";
  }
  
  btnCloseView.onclick = function() {
   var modal = document.getElementById("ModalSubmitExpenseReimbursementView");
  // Get the <span> element that closes the modal
   modal.style.display = "none";
  }

var modal2 = document.getElementById("ModalAddfreshexpContinew");

  // Close the modal if the user clicks outside of it
   window.onclick = function(event) {
    if (event.target == modal2) {
      modal2.style.display = "none";
    }
 }
   btnCloseFresh.onclick = function() {
   modal2.style.display = "none";
  
  }  
});

function openModelPopup(){
	 var modal2 = document.getElementById("ModalAddfreshexpContinew");
	 var modalfirst = document.getElementById("ModalAddExpenseReimbursement");
	 modal2.style.display = "block";
	 modalfirst.style.display = "none";
	// var element = document.getElementById("firstPopup");
      //element.classList.add("highlight");
	
	 
}

function extractBase64Info(base64String) {
    const regex = /^data:(.*);base64,(.*)$/;
    const match = base64String.match(regex);
    
    if (match) {
        const dataType = match[1];
        const base64Content = match[2];
        return {
            dataType,
            base64Content
        };
    } else {
        throw new Error("Invalid base64 string format");
    }
}

function approveExpenses(){
	
		var employerid = document.getElementById("employerId").value;
		var empId = document.getElementById("empId").value;
		var expenseId = document.getElementById("expenseId").value;
		var employerName = document.getElementById("employerName").value;
		var approveAmmount = document.getElementById("approveAmmount").value;
		var limit = parseFloat(document.getElementById("approveLimit").innerHTML); 
		
		if(approveAmmount=="" || approveAmmount==null){
				document.getElementById("approveAmmountError").innerHTML="Please Enter Approved Amount";
				return false;
			}
			else{
				document.getElementById("approveAmmountError").innerHTML="";
			}
			if (approveAmmount >= limit) {
				    document.getElementById("approveAmmountError").innerHTML = "Approved amount should not be greater than or equal to the daily limit.";
				    return false;
				} else {
				    document.getElementById("approveAmmountError").innerHTML = "";
				}

			document.getElementById("signinLoader").style.display="flex";		
		$.ajax({
			type: "POST",
			url: "/updateStatusExpensesById",
			data:{
				"id":expenseId,
				"employeeId":empId,
				"employerId":employerid,
				"username":"employerName",
				"approvedOrRejected":"Approved",
				"approvedAmount":approveAmmount,
				"rejectedRemarks":""
			},
			success: function(data) {
				newData = data;
				var data1 = jQuery.parseJSON(newData);
				var data2 = data1.list;
				//data1.status=false;
				//data1.message="test message";
				document.getElementById("signinLoader").style.display="none";
				if(data1.status==true)
				{getExpanceCategoryApprovalList();
				var modalfirst = document.getElementById("modalReimbursementApproved");
				modalfirst.style.display = "block";
				}
				else if(data1.status==false)
					{
						var modalfirst = document.getElementById("ModalViewPendingExp");
						modalfirst.style.display = "none";
						document.querySelector('#ModalReject .modal-bottom span.required-star').innerText = data1.message;
												   
						$('#ModalReject').modal('show');
					}
					
			},
			error: function(e) {
				alert('Failed to fetch JSON data' + e);
			}
		});
}
function approvedClose(){
	var modalfirst = document.getElementById("modalReimbursementApproved");
	modalfirst.style.display = "none";
	var modalfirst2 = document.getElementById("ModalViewPendingExp");
    modalfirst2.style.display = "none";
	getExpanceCategoryApprovalList();
}
function closeApproval(){
	var modalfirst = document.getElementById("ModalViewPendingExp");
	modalfirst.style.display = "none";
	//getExpanceCategoryApprovalList();
	window.location.reload();
}


function closeReject(){
	var modalfirst = document.getElementById("ModalReimbursementRejected");
	modalfirst.style.display = "none";
	
	var modalfirst1 = document.getElementById("ModalRejectRemarkReimbursement");
	modalfirst1.style.display = "none";
	
	var modalfirst2 = document.getElementById("modalReimbursementApproved");
	modalfirst2.style.display = "none";
	
	var modalfirst3 = document.getElementById("ModalViewPendingExp");
    modalfirst3.style.display = "none";
	
	//getExpanceCategoryApprovalList();
	window.location.reload();
}


function backReject(){
	var modalfirst = document.getElementById("ModalReimbursementRejected");
	modalfirst.style.display = "none";
	
	var modalfirst1 = document.getElementById("ModalRejectRemarkReimbursement");
	modalfirst1.style.display = "none";
	
}

function rejectExpenses(){
	
		var employerid = document.getElementById("employerId").value;
		var empId = document.getElementById("empId").value;
		var expenseId = document.getElementById("expenseId").value;
		var employerName = document.getElementById("employerName").value;
		var rejectedRemarks = document.getElementById("rejectRemark").value;
		if(rejectedRemarks=="" || rejectedRemarks==null){
			document.getElementById("rejectedRemarksError").innerHTML="Please Enter Reject Remark";
			return false;
		}
		else{
			document.getElementById("rejectedRemarksError").innerHTML="";
		}
		document.getElementById("signinLoader").style.display="flex";				
		$.ajax({
			type: "POST",
			url: "/updateStatusExpensesById",
			data:{
				"id":expenseId,
				"employeeId":empId,
				"employerId":employerid,
				"username":"atul yadav",
				"approvedOrRejected":"Reject",
				"approvedAmount":"",
				"rejectedRemarks":rejectedRemarks
			},
			success: function(data) {
				newData = data;
				var data1 = jQuery.parseJSON(newData);
				var data2 = data1.list;
				getExpanceCategoryApprovalList();
				
				var modalfirst = document.getElementById("ModalReimbursementRejected");
			    modalfirst.style.display = "block";
				
				document.getElementById("signinLoader").style.display="none";
				
			},
			error: function(e) {
				alert('Failed to fetch JSON data' + e);
			}
		});
}

async function saveTravelRequest(){
	
	var employerid = document.getElementById("employerId").value;
	var empId = document.getElementById("empId").value;
	var tavelByMode = document.getElementById("tavelByMode").value;
	var travelBookedBy = document.getElementById("travelBookedBy").value;
	var travelDate = document.getElementById("travelDate").value;

	var travelDepartureFrom = document.getElementById("travelDepartureFrom").value;
	var travelArrivalTo = document.getElementById("travelArrivalTo").value;
	var travelTime = document.getElementById("travelTime").value;
	
	var travelPreference = document.getElementById("travelPreference").value;
	var travelClass = document.getElementById("travelClass").value;
	var travelAmount = document.getElementById("travelAmount").value;
	var travelAmountPayment = document.getElementById("travelAmountPayment").value;
	var travelRemarks = document.getElementById("travelRemarks").value;
	var employerName = document.getElementById("employerName").value;
	document.getElementById("signinLoader").style.display="flex";			
	
	const clientKey = "client-secret-key"; // Extra security measure
	const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

	// Concatenate data (must match backend)
		
	const dataString = employerid+empId+employerName+travelBookedBy+travelDate+travelDepartureFrom+travelArrivalTo+
	travelTime+travelPreference+travelClass+travelAmountPayment+travelRemarks+clientKey+secretKey;
	console.log("data string"+dataString); 
	// Generate SHA-256 hash
	const encoder = new TextEncoder();
	const data = encoder.encode(dataString);
	const hashBuffer = await crypto.subtle.digest("SHA-256", data);
	const hashArray = Array.from(new Uint8Array(hashBuffer));
	const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
			
	$.ajax({
		type: "POST",
		url: "/cashAdvanceRequest",
		data:{
			
			"employeeId":empId,
			"employerId":employerid,
			"username":employerName,
			"toBeBookedBy":travelBookedBy,
			"travelDate":travelDate,
			"departureLocation":travelDepartureFrom,
			"arrivalLocation":travelArrivalTo,
			"preferredTimeBefore":travelTime,
			"modeOfPayment":travelPreference,
			"carrierDetails":travelClass,
			"modeOfPayment":travelAmountPayment,
			"remarks":travelRemarks,	
			"clientKey":clientKey,
			"hash":hashHex,			
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.list;
			var modalfirst = document.getElementById("ModalConfirm");
		    modalfirst.style.display = "block";
			document.getElementById("signinLoader").style.display="none";
		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
	
}

function closePopup(){
	var modalfirst = document.getElementById("ModalConfirm");
	modalfirst.style.display = "none";
	
	//var modalfirst2 = document.getElementById("travelDetailsTable");
	//modalfirst2.style.display = "none";
	
	//var modalfirst3 = document.getElementById("ModalChooseAdvanceRequest");
	//modalfirst3.style.display = "none";
	
	//window.location.reload();
	
}

function getCashAdvanceRequestList(){
	document.getElementById("signinLoader").style.display="flex";
	var employerid = document.getElementById("employerId").value;
	var empId = document.getElementById("empId").value;	
	$.ajax({
		type: "GET",
		url: "/getCashAdanceRequestData",
		data: {
			"employeeId": empId,
			"employerId": employerid
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			 //console.log(data2);
			document.getElementById("signinLoader").style.display="none";
			
			var table = $('#tblCashAdvanceTravel').DataTable( {
	          destroy: true,	
		     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],
             "language": {"emptyTable": "No Data available"  },
	        
	         "aaData": data2,
      		  "aoColumns": [ 
				
                { "mData": "id", "render": function (data2, type, row) {
					 return ' <div class="table-check"><input type="checkbox" value="'+data2+'" id="customCheck4" name="customCheck4" ></div>';
                 }}, 
				{ "mData": "sequenceId"},   
                { "mData": "createdDate"}, 
				//{ "mData": function (data2, type, row) {
			   //     return data2.date + " " + data2.time;
			   // }},  
				{ "mData": "requestType"},
				{ "mData": function (data2, type, row) {
			        return 'INR' + " " + data2.amount;
			    }},
				{ "mData": function (data2, type, row) {
				 return 'INR' + " " + data2.approvedAmount;
			    }},
			    { "mData": "statusRemarks"},
				{ "mData": "paymentMode"},        
				{ "mData": "approvedBy"},    
      		  	{ "mData": "id", "render": function (data1, type, row) {
                    return '<td> <div  class="d-flex align-items-center"><div class="dropdown no-arrow ml-2"> <a class="dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="fas fa-ellipsis-v fa-sm"></i></a><br> <div class="dropdown-menu dropdown-menu-right shadow"  aria-labelledby="userDropdown"><button class="dropdown-item py-2" onclick="deleteAdvanceTravel(this)" > Delete  </button><button class="dropdown-item py-2" id="btnView" onclick="viewAdvanceTravel(this)"> View </button> </div> </div> </div> </td>';
                 }}, 
    		 	],
				createdRow: function (row, data2, dataIndex) 
				   {
				    //console.log("row : "+JSON.stringify(data2));
				  
					var requestType = data2.requestType;
					//var statusMessage = data2.statusMessage;

				    if(requestType=="Accomodation")
				    {
					var imgTag = '<img src="img/hotel.svg" alt="" class="mr-2">'+requestType;
					 $(row).find('td:eq(3)').html(imgTag);
				    }
				    if(requestType=="In-City-Cab")
				    {
				      var imgTag = ' <img src="img/citycabs.svg" alt="" class="mr-2">'+requestType;
					  $(row).find('td:eq(3)').html(imgTag);
				    }
				    
				    if(requestType=="Miscellaneous")
				    {
					 var imgTag = ' <img src="img/Miscellaneous.svg" alt="" class="mr-2">'+requestType;
						 $(row).find('td:eq(3)').html(imgTag);
				    }
				    
				    if(requestType=="Travel")
				    {
					var imgTag = '<img src="img/Travel.svg" alt="" class="mr-2">'+requestType;
				     $(row).find('td:eq(3)').html(imgTag);
				    }
				    if(requestType=="Meal")
				    {
				     var imgTag = '<img src="img/food.svg" alt="" class="mr-2">'+requestType;
					
				     $(row).find('td:eq(3)').html(imgTag);
				    }
					if(requestType=="Cash")
				    {
				     var imgTag = '<img src="img/cash.svg" alt="" class="mr-2">'+requestType;
					
				     $(row).find('td:eq(3)').html(imgTag);
				    }
		     	 }
    		 	
      		});		
			
		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}
function validateDate() {
    let dateInput = document.getElementById("dateSingle").value;
    let errorElement = document.getElementById("dateSingleError");

    if (!dateInput) {
        errorElement.innerHTML = "Please select a date.";
        return false;
    }

    let selectedDate = new Date(dateInput);
    let today = new Date();
    today.setHours(0, 0, 0, 0); // Remove time for accurate comparison

    if (selectedDate > today) {
        errorElement.innerHTML = "Please select the date before today";
        document.getElementById("dateSingle").value = ""; // Clear invalid date instantly
        return false;
    } else {
        errorElement.innerHTML = ""; // Clear error if valid
        return true;
    }
}

function exportToExcel(tableId, headingSelector) {
        const table = document.getElementById(tableId);
        const checkboxes = table.querySelectorAll("tbody input[type='checkbox']");
        const selectedRows = Array.from(checkboxes).filter(checkbox => checkbox.checked);

        if (selectedRows.length === 0) {
            alert("Please select at least one record to download.");
            return;
        }

        let data = [];
        const headers = [];
        const thElements = table.querySelectorAll("thead th");

        // Exclude the last column ("Actions")
        for (let i = 0; i < thElements.length - 1; i++) {
            headers.push(thElements[i].textContent.trim());
        }

        selectedRows.forEach(checkbox => {
            const row = checkbox.closest("tr");
            const rowData = [];
            const tdElements = row.querySelectorAll("td");

            for (let i = 0; i < tdElements.length - 1; i++) {
                rowData.push(tdElements[i].textContent.trim());
            }

            data.push(rowData);
        });

        const worksheet = XLSX.utils.aoa_to_sheet([headers, ...data]);

        // Apply bold style to header row
        const headerRange = XLSX.utils.decode_range(worksheet["!ref"]);
        for (let col = headerRange.s.c; col <= headerRange.e.c; col++) {
            const cellRef = XLSX.utils.encode_cell({ r: 0, c: col });
            if (!worksheet[cellRef]) continue;
            worksheet[cellRef].s = { font: { bold: true } }; // Make header bold
        }

        const workbook = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(workbook, worksheet, tableId);

        // Extract filename from h4 heading text
        const headingElement = document.querySelector(headingSelector);
        const fileName = headingElement ? `${headingElement.textContent.trim()}.xlsx` : `${tableId}.xlsx`;

        XLSX.writeFile(workbook, fileName);
    }
	function sendToOcr(base64Image,filetype,fileName) {
	    console.log("sendToOcr base64Image  ",base64Image);
		console.log("sendToOcr base64Image  ",filetype);
		console.log("sendToOcr base64Image  ",fileName);

	  

	    $.ajax({
	        type: "POST",
	        url: "/readOcr",
	        data: {"file": base64Image,
					"fileType":filetype,
					"fileName":fileName 
			},
	        
	        success: function (response) {
	            console.log("OCR Result:", response);
				var parsedData=JSON.parse(response);
				var newData=parsedData;
	            // handle result display here
				if(newData.status==true)
				{document.getElementById("venderName").value=newData.data.venderName;
				document.getElementById("invoiceNumber").value=newData.data.order;
				document.getElementById("amount").value=newData.data.totalAmount;}
	        },
	        error: function (e) {
	            alert("OCR Error: " + e.responseText);
	        }
	    });
	}
	function sendToOcrSingle(base64Image,filetype,fileName) {
		    console.log("sendToOcr base64Image  ",base64Image);
			console.log("sendToOcr base64Image  ",filetype);
			console.log("sendToOcr base64Image  ",fileName);

		  

		    $.ajax({
		        type: "POST",
		        url: "/readOcr",
		        data: {"file": base64Image,
						"fileType":filetype,
						"fileName":fileName 
				},
		        
		        success: function (response) {
		            console.log("OCR Result:", response);
					var parsedData=JSON.parse(response);
					var newData=parsedData;
		            // handle result display here
					if(newData.status==true)
					{document.getElementById("vendorNameSingle").value=newData.data.venderName;
					document.getElementById("invoiceNumberSingle").value=newData.data.order;
					document.getElementById("amountSingle").value=newData.data.totalAmount;}
		        },
		        error: function (e) {
		            alert("OCR Error: " + e.responseText);
		        }
		    });
		}


