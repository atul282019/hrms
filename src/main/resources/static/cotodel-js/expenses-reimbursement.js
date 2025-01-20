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

function submitExpenseDraft(){
	
	var employerId= document.getElementById("employerId").value; 
	var expenseCategory=  document.getElementById("expenseCategory").value ;
	var dateofExpense = document.getElementById("dateofExpense").value;
	var expenseTitle = document.getElementById("expenseTitle").value;
	var venderName= document.getElementById("venderName").value;
	var invoiceNumber = document.getElementById("invoiceNumber").value;
	var currency= document.getElementById("currency").value;
	var amount = document.getElementById("amount").value;
	var modeofPayment = document.getElementById("modeofPayment").value;
	var additionalRemark = document.getElementById("additionalRemark").value;
	var fileInput = document.getElementById("fileInput").value; 
	
	var imageAdd = document.getElementById("imageAdd").src;
	var imagePDF = document.getElementById("base64PDF").value; 
	
	var fileType = null;
	var fileBase64=null;
	
	if(expenseCategory=="" || expenseCategory==null){
		document.getElementById("expenseCategoryError").innerHTML="Please Select Expence Category";
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
		document.getElementById("venderNameError").innerHTML="Please Enter Vender Name";
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

function submitExpenseMultiple(){
	
	var employerId= document.getElementById("employerId").value; 
	var empId= document.getElementById("empId").value; 
	var expenseCategory=  document.getElementById("expenseCategory").value ;
	var dateofExpense = document.getElementById("dateofExpense").value;
	var expenseTitle = document.getElementById("expenseTitle").value;
	var venderName= document.getElementById("venderName").value;
	var invoiceNumber = document.getElementById("invoiceNumber").value;
	var currency= document.getElementById("currency").value;
	var amount = document.getElementById("amount").value;
	var modeofPayment = document.getElementById("modeofPayment").value;
	var additionalRemark = document.getElementById("additionalRemark").value;
	var fileInput = document.getElementById("fileInput").value; 
	
	var imageAdd = document.getElementById("imageAdd").src;
	var imagePDF = document.getElementById("base64PDF").value; 
	
	var fileType = null;
	var fileBase64=null;
	
	if(expenseCategory=="" || expenseCategory==null){
		document.getElementById("expenseCategoryError").innerHTML="Please Select Expence Category";
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
		document.getElementById("venderNameError").innerHTML="Please Enter Vender Name";
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

function submitExpenseSingleDraft(){
	
	var employerId= document.getElementById("employerId").value; 
	var empId= document.getElementById("empId").value; 
	var expenseCategory=  document.getElementById("expenseCategorySingle").value ;
	var dateofExpense = document.getElementById("dateSingle").value;
	var expenseTitle = document.getElementById("expenseTitleSingle").value;
	var venderName= document.getElementById("vendorNameSingle").value;
	var invoiceNumber = document.getElementById("invoiceNumberSingle").value;
	var currency= document.getElementById("currencySingle").value;
	var amount = document.getElementById("amountSingle").value;
	var modeofPayment = document.getElementById("modeofpaymentSingle").value;
	var additionalRemark = document.getElementById("remarkSingle").value;
	
	var imageAdd = document.getElementById("imageAddSingle").src;
	var imagePDF = document.getElementById("base64PDFSingle").value; 
	
	var fileType = null;
	var fileBase64=null;
	
	if(expenseCategory=="" || expenseCategory==null){
		document.getElementById("expenseCategorySingleError").innerHTML="Please Select Expence Category";
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
		document.getElementById("venderNameError").innerHTML="Please Enter Vender Name";
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


function submitExpenseSingle(){
	
	var employerId= document.getElementById("employerId").value;
	var empId= document.getElementById("empId").value; 
	var expenseCategory=  document.getElementById("expenseCategorySingle").value ;
	var dateofExpense = document.getElementById("dateSingle").value;
	var expenseTitle = document.getElementById("expenseTitleSingle").value;
	var venderName= document.getElementById("vendorNameSingle").value;
	var invoiceNumber = document.getElementById("invoiceNumberSingle").value;
	var currency= document.getElementById("currencySingle").value;
	var amount = document.getElementById("amountSingle").value;
	var modeofPayment = document.getElementById("modeofpaymentSingle").value;
	var additionalRemark = document.getElementById("remarkSingle").value;
	
	var imageAdd = document.getElementById("imageAddSingle").src;
	var imagePDF = document.getElementById("base64PDFSingle").value; 
	
	var fileType = null;
	var fileBase64=null;
	
	if(expenseCategory=="" || expenseCategory==null){
		document.getElementById("expenseCategorySingleError").innerHTML="Please Select Expence Category";
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
		document.getElementById("venderNameError").innerHTML="Please Enter Vender Name";
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
                    return '<td> <div class="d-flex align-items-center"> <button class="btn-attach" id="btnView" onclick="viewExpance(this)"> View <img src="img/attached.svg" alt=""> </button> <div class="dropdown no-arrow ml-2"> <a class="dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="fas fa-ellipsis-v fa-sm"></i></a><br> <div class="dropdown-menu dropdown-menu-right shadow"  aria-labelledby="userDropdown"><button class="dropdown-item py-2" onclick="deleteExpance(this)" > Delete  </button><a class="dropdown-item py-2" href="#"> Download </a> </div> </div> </div> </td>';
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
                      $(row).find('td:eq(4)').html(imgTag);
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
				   
			 	{ "mData": "amount"},    
			 	//{ "mData": function (data1, type, row) {
			   //     return data1.currency + " " + data1.amount;
			   // }},
				{ "mData": "statusMessage"},
				{ "mData": "modeOfPayment"},        
      		  	{ "mData": "id", "render": function (data1, type, row) {
                    return '<td> <div class="d-flex align-items-center"> <button class="btn-attach" id="btnView" onclick="viewExpanceApproval(this)"> View <img src="img/attached.svg" alt=""> </button> <div class="dropdown no-arrow ml-2"> <a class="dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="fas fa-ellipsis-v fa-sm"></i></a><br> <div class="dropdown-menu dropdown-menu-right shadow"  aria-labelledby="userDropdown"><button class="dropdown-item py-2" onclick="deleteExpance(this)" > Delete  </button><a class="dropdown-item py-2" href="#"> Download </a> </div> </div> </div> </td>';
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
	
	    document.getElementById("signinLoader").style.display="flex";
		var employerid = document.getElementById("employerId").value;
		var empId = document.getElementById("empId").value;
		var expenseId = document.getElementById("expenseId").value;
		var employerName = document.getElementById("employerName").value;
		$.ajax({
			type: "POST",
			url: "approveExpensesById",
			data:{
				"id":expenseId,
				"employeeId":empId,
				"employerId":employerid,
				"username":employerName,
				"approvedOrRejected":"Approved",
				"rejectedRemarks":""
			},
			success: function(data) {
				newData = data;
				var data1 = jQuery.parseJSON(newData);
				var data2 = data1.list;
				getExpanceCategoryApprovalList();
				var modalfirst = document.getElementById("modalReimbursementApproved");
				modalfirst.style.display = "block";
				document.getElementById("signinLoader").style.display="none";	
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
	getExpanceCategoryApprovalList();
}
function rejectExpenses(){
	
	    document.getElementById("signinLoader").style.display="flex";
		var employerid = document.getElementById("employerId").value;
		var empId = document.getElementById("empId").value;
		var expenseId = document.getElementById("expenseId").value;
		var employerName = document.getElementById("employerName").value;
		$.ajax({
			type: "POST",
			url: "approveExpensesById",
			data:{
				"id":expenseId,
				"employeeId":empId,
				"employerId":employerid,
				"username":employerName,
				"approvedOrRejected":"Reject",
				"rejectedRemarks":"Incomplete information"
			},
			success: function(data) {
				newData = data;
				var data1 = jQuery.parseJSON(newData);
				var data2 = data1.list;
				getExpanceCategoryApprovalList();
				var modalfirst = document.getElementById("modalReimbursementApproved");
				modalfirst.style.display = "block";
				document.getElementById("signinLoader").style.display="none";
				
				
			},
			error: function(e) {
				alert('Failed to fetch JSON data' + e);
			}
		});
}



