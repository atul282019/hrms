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

function submitExpenseSingleDraft(){
	
	var employerId= document.getElementById("employerId").value; 
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
	$.ajax({
		type: "GET",
		url: "/getExpanseReimbursement",
		data: {
			"employeeId": employerid,
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

////



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


function getBankMaster() {
	document.getElementById("signinLoader").style.display="flex";
	//var employerid = document.getElementById("employerId").value;
	$.ajax({
		type: "POST",
		url: "/getBankMaster",
		data: {
			//"employerId": employerid
		},
		
		 success: function(data){
            newData = data;
            console.log(newData);
			$("#bankName option").remove();
            var obj = jQuery.parseJSON( data );
             obj = obj.data;
        	 var count=0;
         	for (var key in obj) {

             var values =  obj[key];
             var x = document.getElementById("bankName");
             if(count==0){
             var option = document.createElement("option");
             option.text ="Select Bank";
             option.value = "";
             x.add(option);
             }
             var option = document.createElement("option");
             option.text = values.bankName;
             option.value = values.bankCode;
             x.add(option);

             count++;
             }   
         },
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}


function  getLinkedBankDetail(){
	
    document.getElementById("signinLoader").style.display="flex";
 	var employerid = document.getElementById("employerId").value;
 	$.ajax({
	type: "POST",
	url:"/getErupiLinkBankAccountDetail",
       data: {
			"orgId": employerid
      		 },
      		  beforeSend : function(xhr) {
			//xhr.setRequestHeader(header, token);
			},
           success: function(data){
           newData = data;
           console.log("Linked bank account data : ",newData);
        var data1 = jQuery.parseJSON( newData );
		var data2 = data1.data;
		if(data2.length ===0 && data2.length <=0){
			$("#linkaccbankform").show();
			$("#linkedbnkacntsctn").hide();
		}
		else{
			$("#linkaccbankform").hide();
			$("#linkedbnkacntsctn").show();

		}
		  const wrapper = document.getElementById('data-wrapper');

		   // Render only specific fields with Edit button
		   data1.data.forEach(item => {
		       const container = document.createElement('div');
		       container.className = 'data-container';

		       const fieldsToDisplay = ["bankName", "accountHolderName", "acNumber","accountType","ifsc","mobile","merchentIid","submurchentid",//"mcc"
				,"payerva"];
			   
			   const fieldLabels = {
			          
			           bankName: "Bank Name",
			           accountHolderName: "Account Holder Name",
			           acNumber: "Account Number",
					   accountType: "Account Type",
					   ifsc: "ifsc",
					   mobile: "Mobile",
					   merchentIid: "Merchant Id",
					   submurchentid:"Sub Merchant Id",
   					   //mcc: "MCC",
   					   payerva: "Payerva",
			       };
				   
		       fieldsToDisplay.forEach(key => {
		           const fieldDiv = document.createElement('div');
		           fieldDiv.className = 'field';
		           fieldDiv.innerHTML = `<span class="label">${fieldLabels[key]}:</span> ${item[key] ?? 'N/A'}`;
		           container.appendChild(fieldDiv);
		       });

			   const editButton = document.createElement('button');
               editButton.className = "btn btn-primary";
               editButton.innerText = "Set As Primary";

               // Disable "Edit" button if accstatus is 0 (Link Bank A/C shown)
               if (item.accstatus === 0) {
                   editButton.disabled = true;
                   editButton.classList.add("disabled");
               }

               editButton.onclick = () => {
                   if (!editButton.disabled) {
                       editItem(item.acNumber);
                   }
               };
						  
			   const editButton1 = document.createElement('a');
			   editButton1.className = '';
			   editButton1.style.cursor = 'pointer';
			   editButton1.innerText = item.accstatus === 0 ? 'Link Bank A/C' : 'De-Linked Bank A/C';
			   //editButton1.href = '#';  // Use this if you don't have a specific link
			   editButton1.onclick = () => {
			                       if (item.accstatus === 0) {
			                           relinkBankAccount(item.acNumber); // Call link function
			                       } else {
			                           dlinkAccount(item.acNumber); // Call de-link function
			                       }
			                   }; 
			  
			   container.appendChild(editButton);
		       container.appendChild(editButton1);

		       wrapper.appendChild(container);
		   });

		   function editItem(acNumber) {
			   //alert(`Are You Sure you want to de link this Account`);
		        //alert(`Are You Sure  de link this Account: ${acNumber}`);
				var accountid= acNumber;
				document.getElementById("linkBankBtn").disabled = true;
					document.getElementById("signinLoader").style.display="flex";
					

					 	$.ajax({
						type: "POST",
					     url:"/updateErupiLinkBankAccountStaus",
						 data: {
								"orgId": employerid,
								"acNumber":accountid,
								
						 		},    		 
				            success: function(data){
				            newData = data;
							var data1 = jQuery.parseJSON(newData);

							document.getElementById("signinLoader").style.display="none";
							
							if(data1.status==true){
									 document.getElementById("otsuccmsg").innerHTML="Bank Account Linked Successfully.";
									 document.getElementById("otmsgdiv").style.display="block";
									 //document.getElementById("getInTouchUser").reset();
									 $('#otmsgdiv').delay(5000).fadeOut(400);
					    			 window.location.href = "/linkBankDetail";
								}else if(data1.status==false){
									 document.getElementById("otfailmsg").innerHTML=data1.message;
									 document.getElementById("otfailmsgDiv").style.display="block";
									 $('#otfailmsgDiv').delay(5000).fadeOut(400);
								}else{
									 document.getElementById("otfailmsg").innerHTML="API Gateway not respond. Please try again.";
									 document.getElementById("otfailmsgDiv").style.display="block";
									 $('#otfailmsgDiv').delay(5000).fadeOut(400);
								}
							
				         },
				         error: function(e){
				             alert('Error: ' + e);
				         }
				    });	
					
		       // Implement actual edit functionality here
		   }
          },
        error: function(e){
            alert('Error: ' + e);
        }
   }); 
			
}
function relinkBankAccount(acNumber) {
    console.log("Inside link bank account function");
    var employerid = document.getElementById("employerId").value;

    document.getElementById("signinLoader").style.display = "flex";

    $.ajax({
        type: "POST",
        url: "/re-linkErupiaccount", // Adjust URL for linking action
        data: {
            "orgId": employerid,
            "acNumber": acNumber
        },
        success: function (data) {
            newData = data;
            var data1 = jQuery.parseJSON(newData);

            document.getElementById("signinLoader").style.display = "none";

            if (data1.status == true) {
                document.getElementById("otsuccmsg").innerHTML = "Bank Account re-Linked Successfully.";
                document.getElementById("otmsgdiv").style.display = "block";
                $('#otmsgdiv').delay(5000).fadeOut(400);
                window.location.href = "/linkBankDetail";
            } else if (data1.status == false) {
                document.getElementById("otfailmsg").innerHTML = data1.message;
                document.getElementById("otfailmsgDiv").style.display = "block";
                $('#otfailmsgDiv').delay(5000).fadeOut(400);
            } else {
                document.getElementById("otfailmsg").innerHTML = "API Gateway not responding. Please try again.";
                document.getElementById("otfailmsgDiv").style.display = "block";
                $('#otfailmsgDiv').delay(5000).fadeOut(400);
            }
        },
        error: function (e) {
            alert("Error: " + e);
        }
    });
}

function dlinkAccount(acNumber)
{console.log("Inside  de-link function");
	var employerid = document.getElementById("employerId").value;
	 	$.ajax({
		type: "POST",
	     url:"/de-linkErupiaccount",
		 data: {
		 			  "orgId": employerid ,
					  "acNumber":acNumber ,					  
					  
		 		}, 
						 
	        success: function(data){
	        newData = data;
			var data1 = jQuery.parseJSON(newData);

			document.getElementById("signinLoader").style.display="none";
			
			if(data1.status==true){
					 document.getElementById("otsuccmsg").innerHTML="Bank Account De-Linked Successfully.";
					 document.getElementById("otmsgdiv").style.display="block";
					 //document.getElementById("getInTouchUser").reset();
					 $('#otmsgdiv').delay(5000).fadeOut(400);
					 document.getElementById("linkBankBtn").disabled = false;
	    			 window.location.href = "/linkBankDetail";
					 //getLinkedBankDetail();
				}else if(data1.status==false){
					 document.getElementById("otfailmsg").innerHTML=data1.message;
					 document.getElementById("otfailmsgDiv").style.display="block";
					 $('#otfailmsgDiv').delay(5000).fadeOut(400);
					 document.getElementById("linkBankBtn").disabled = false;
				}else{
					 document.getElementById("otfailmsg").innerHTML="API Gateway not respond. Please try again.";
					 document.getElementById("otfailmsgDiv").style.display="block";
					 $('#otfailmsgDiv').delay(5000).fadeOut(400);
					 document.getElementById("linkBankBtn").disabled = false;
				}
			
	     },
	     error: function(e){
	         alert('Error: ' + e);
	     }
	});	
}


function submitLinkBankAccount(){
	
	var employerId= document.getElementById("employerId").value; 
	var bankCode=  $("#bankName option:selected").val();
	var bankName=  $("#bankName option:selected").text();
	var bankingName = document.getElementById("bankingName").value;
	var bankAccNumber = document.getElementById("bankAccNumber").value;
	var bankAccNumberConfirm= document.getElementById("bankAccNumberConfirm").value;
	var accountType= document.getElementById("bankAccType").value;
	var bankIfsc = document.getElementById("bankIfsc").value;
	
	var tid = document.getElementById("tid").value;
	var merchantId = document.getElementById("merchantId").value;
	//var mcc = document.getElementById("mcc").value;
	var submerchantid = document.getElementById("submerchantid").value;
	var payerva = document.getElementById("payerva").value;
	var moblieLink = document.getElementById("moblieLink").value;
	var banknameregex=/^[A-Za-z]+( [A-Za-z]+)?$/;
	var merchantidregex=/^[A-Za-z0-9]+$/;
	var submerchantidregex=/^[A-Za-z0-9]+$/;
	
	if(bankCode=="" || bankCode==null){
			document.getElementById("bankNameError").innerHTML="Please Select Bank";
			return false;
		}
		else{
			document.getElementById("bankNameError").innerHTML="";
		}
		if(accountType=="" || accountType==null){
			document.getElementById("bankAccTypeError").innerHTML="Please Select Account Type";
			return false;
		}
		else{
			document.getElementById("bankAccTypeError").innerHTML="";
		}
		if(bankingName=="" || bankingName==null){
			document.getElementById("bankingNameError").innerHTML="Please Enter Account Holder Name";
			return false;
		}		else if(!bankingName.match(banknameregex)){
						document.getElementById("bankingNameError").innerHTML="Special Characters Not Allowed in Account Holder Name";
							document.getElementById("bankingName").focus();
							return false;
						
					}
		else{
			document.getElementById("bankingNameError").innerHTML="";
		}
				
		if(bankAccNumber=="" || bankAccNumber==null){
			document.getElementById("bankAccNumberError").innerHTML="Please Enter Account Number";
			return false;
		}
		else{
			document.getElementById("bankAccNumberError").innerHTML="";
		}
		
		if(bankAccNumberConfirm=="" || bankAccNumberConfirm==null){
			document.getElementById("bankAccNumberConfirmError").innerHTML="Please Enter Confirm Account Number";
			return false;
		}
		else{
			document.getElementById("bankAccNumberConfirmError").innerHTML="";
		}
		if(bankIfsc=="" || bankIfsc==null){
			document.getElementById("bankIfscError").innerHTML="Please Enter IFSC Code";
			return false;
		}
		else{
			document.getElementById("bankIfscError").innerHTML="";
		}
		
		if(moblieLink=="" || moblieLink==null){
			document.getElementById("moblieLinkError").innerHTML="Please Enter Mobile Number";
			return false;
		}
		else{
			document.getElementById("moblieLinkError").innerHTML="";
		}
		
		if(tid=="" || tid==null){
			document.getElementById("tidError").innerHTML="Please Enter Tid";
			return false;
		}
		else{
			document.getElementById("tidError").innerHTML="";
		}
		
		if(merchantId=="" || merchantId==null){
			document.getElementById("merchantIdError").innerHTML="Please Enter Merchant Id";
			return false;
		}		else if(!merchantId.match(merchantidregex))
								{
								document.getElementById("merchantIdError").innerHTML="Special Characters are not allowed in merchant id";
										document.getElementById("merchantId").focus();
										return false;
								}
		else{
			document.getElementById("merchantIdError").innerHTML="";
		}
		/*if(mcc=="" || mcc==null){
					document.getElementById("mccError").innerHTML="Please Enter MCC";
			return false;
		}
		else{
			document.getElementById("mccError").innerHTML="";
		}*/
		if(submerchantid=="" || submerchantid==null){
		document.getElementById("submerchantidError").innerHTML="Please Enter Sub Merchant Id";
				return false;
		}		
		else if(!submerchantid.match(submerchantidregex))
						{
						document.getElementById("submerchantidError").innerHTML="Special Characters are not allowed in submerchant id";
								document.getElementById("submerchantid").focus();
								return false;
						}
		
			else{
				document.getElementById("submerchantidError").innerHTML="";
			}

			if(payerva=="" || payerva==null){
				document.getElementById("payervaError").innerHTML="Please Enter Payer va";
				return false;
			}
			else{
				document.getElementById("payervaError").innerHTML="";
			}
			document.getElementById("signinLoader").style.display="flex";
			document.getElementById("linkBankBtn").disabled = true;
			
	 	$.ajax({
		type: "POST",
	     url:"/addErupiLinkBankAccount",
		 data: {
		 			  "employeeId": employerId,
					  "bank": {
						"id": employerId
					  },
					  "bankCode": bankCode,
					  "bankName": bankName,
					  "accountHolderName": bankingName,
					  "acNumber": bankAccNumber,
					  "conirmAccNumber": bankAccNumberConfirm,
					  "accountType":accountType,
					  "ifsc": bankIfsc,
					  "erupiFlag": "Y",
					  "createdby": "",
					  "updateDate": "",
					  "updatedby": "",
					  "branchCode": "",
					  "orgId": employerId,
					  "orgCode": "",
					  "employeCode": "",
					  "authStatus":"Y",
					  "authResponse": "",
					  "mobile": moblieLink,
					  "accstatus":1,
					  "tid": tid,
					  "merchentIid": merchantId,
					 // "mcc": mcc,
					  "submurchentid": submerchantid,
					  "payerva": payerva
					
		 		}, 
						 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);

			document.getElementById("signinLoader").style.display="none";
			
			if(data1.status==true){
					 document.getElementById("otsuccmsg").innerHTML="Bank Account Linked Successfully.";
					 document.getElementById("otmsgdiv").style.display="block";
					 //document.getElementById("getInTouchUser").reset();
					 $('#otmsgdiv').delay(5000).fadeOut(400);
					 document.getElementById("linkBankBtn").disabled = false;
	    			 window.location.href = "/linkBankDetail";
				}else if(data1.status==false){
					 document.getElementById("otfailmsg").innerHTML=data1.message;
					 document.getElementById("otfailmsgDiv").style.display="block";
					 $('#otfailmsgDiv').delay(5000).fadeOut(400);
					 document.getElementById("linkBankBtn").disabled = false;
				}else{
					 document.getElementById("otfailmsg").innerHTML="API Gateway not respond. Please try again.";
					 document.getElementById("otfailmsgDiv").style.display="block";
					 $('#otfailmsgDiv').delay(5000).fadeOut(400);
					 document.getElementById("linkBankBtn").disabled = false;
				}
			
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });	
	
}

function validateBankname()
{ console.log("Inside validateBankname");
	var banknameregex=/^[A-Za-z]+( [A-Za-z]+)?$/;
	var bankingName = document.getElementById("bankingName").value;
	
	if(bankingName=="" || bankingName==null){
				document.getElementById("bankingNameError").innerHTML="Please Enter Account Holder Name";
				document.getElementById("bankingName").focus();
				return false;
				
			}else if(!bankingName.match(banknameregex)){
				document.getElementById("bankingNameError").innerHTML="Special Characters Not Allowed in Account Holder Name";
					document.getElementById("bankingName").focus();
					return false;
				
			}
			else{
				document.getElementById("bankingNameError").innerHTML="";
			}
}
function updateLinkBankAccount(){
	
	
}


function validate() {
	var x= document.getElementById("bankAccNumber").value;
    var y= document.getElementById("bankAccNumberConfirm").value;
    var message = document.getElementById('bankAccNumberConfirmError');
	var accRegex = /^\d+$/;

	    
	    if (!x.match(accRegex)) {
	        message.innerHTML = "Please note Account number should not be in decimal";
	        //message.style.color = 'red';
	        return false;
	    }
    if (x === y) {
        message.innerHTML = '';
        message.style.color = 'green';
    } else {
        message.innerHTML = "Account No. do not match.. Please try again.";
        message.style.color = 'red';
    }
}

function validateIFSC(){
	var x= document.getElementById("bankIfsc").value.trim();
   var ifscRegex = /^[A-Za-z]{4}\d{7}$/ ;
	var ifscRegex1 = /^[A-Za-z]{4}/ ;
    if(x==""|| x==null){
    	document.getElementById("bankIfscError").innerHTML="Please Enter Valid IFSC Code";
		document.getElementById("bankIfsc").focus();
		return false;
    }
	
    else if(!x.match(ifscRegex1)){
		document.getElementById("bankIfscError").innerHTML="First four alphabets are expected in IFSC";
		document.getElementById("bankIfsc").focus();
		return false;
	}
	/*else if(!x.match(ifscRegex)){
			document.getElementById("bankIfscError").innerHTML="Special symbol not allowed in IFSC";
			document.getElementById("bankIfsc").focus();
			return false;
		}*/
    else{
    	document.getElementById("bankIfscError").innerHTML="";
    }
  
}

function validateMerchantId()
{
	var merchantId = document.getElementById("merchantId").value;
	var merchantidregex=/^[A-Za-z0-9]+$/;
	if(merchantId==""){
				document.getElementById("merchantIdError").innerHTML="Please Enter Merchant Id";
				document.getElementById("merchantId").focus();
				return false;
			}
			else if(!merchantId.match(merchantidregex))
				{
				document.getElementById("merchantIdError").innerHTML="Special Characters are not allowed in merchant id";
						document.getElementById("merchantId").focus();
						return false;
				}
			else{
				document.getElementById("merchantIdError").innerHTML="";
			}
}
function validateSubMerchantId()
{
	var submerchantId = document.getElementById("submerchantid").value;
	var submerchantidregex=/^[A-Za-z0-9]+$/;
	if(submerchantId==""){
				document.getElementById("submerchantidError").innerHTML="Please Enter Merchant Id";
				document.getElementById("submerchantid").focus();
				return false;
			}
			else if(!submerchantId.match(submerchantidregex))
				{
				document.getElementById("submerchantidError").innerHTML="Special Characters are not allowed in submerchant id";
						document.getElementById("submerchantid").focus();
						return false;
				}
			else{
				document.getElementById("submerchantidError").innerHTML="";
			}
}

function validateMobile(){
	
	var regMobile = /^[6-9]\d{9}$/gi;
		var moblieLink = document.getElementById("moblieLink").value;
		if (moblieLink == "") {
			document.getElementById("moblieLink").focus();
			document.getElementById("moblieLinkError").innerHTML="Please Enter Mobile Number";
			return false;
		}
		else if(moblieLink.length < 10){
				document.getElementById("moblieLinkError").innerHTML="Please Enter Valid Mobile Number";
				document.getElementById("moblieLink").focus();
				return false;
		}
		
		else if(!moblieLink.match(regMobile)){
				document.getElementById("moblieLinkError").innerHTML="Please Enter Valid Mobile Number";
				document.getElementById("moblieLink").focus();
				return false;
		}
		else{
			document.getElementById("moblieLinkError").innerHTML="";
		}
}


function validatePayerva(){
	
	var regEmail = /^[a-zA-Z0-9]+@[a-zA-Z0-9]+$/;
	
		var payerva = document.getElementById("payerva").value;
		if(payerva==""){
				document.getElementById("payervaError").innerHTML="Please Enter payerva";
				document.getElementById("payerva").focus();
				return false;
			}else{
				document.getElementById("payervaError").innerHTML="";
			}
			
			if (!payerva.match(regEmail)) {    
		        document.getElementById("payervaError").innerHTML="Please Enter Valid payerva";
		        document.getElementById("payerva").focus();
		        return false;  
		        
		    } else {    
		       document.getElementById("payervaError").innerHTML="";
				   
		    }
			
}

function resetErrorMessages() {
    const errorFields = [
        "bankNameError",
        "bankAccTypeError",
        "bankingNameError",
        "bankAccNumberError",
        "bankAccNumberConfirmError",
        "bankIfscError",
        "moblieLinkError",
        "tidError",
        "merchantIdError",
        "submerchantidError",
        "payervaError"
    ];

    errorFields.forEach((field) => {
        document.getElementById(field).innerHTML = "";
    });
}
