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
			$("#expenseCategoryMulti option").remove();
            var obj = jQuery.parseJSON( data );
             obj = obj.data;
        	 var count=0;
         	for (var key in obj) {

             var values =  obj[key];
             var x = document.getElementById("expenseCategoryMulti");
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

function submitExpense(){
	
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
			document.getElementById("ModalAddmanualexp").style.display = "none";;
			
		   document.getElementById("ModalAddExpenseReimbursement").style.display = "none";;
	
  			// Get the <span> element that closes the modal
			getExpanceCategoryList();
			//document.getElementById("signinLoader").style.display="none";
			//console.log(data1)
		/*	if(data1.status==true){
				 document.getElementById("certificatesuccmsg").innerHTML="Data Saved Successfully";
				 document.getElementById("certificatemsgdiv").style.display="block";
				 //document.getElementById("saveorg").reset();
				 $('#certificatemsgdiv').delay(5000).fadeOut(400);
				 getEmployeeProjectDetail();
			}else if(data1.status==false){
				 document.getElementById("certificatefailmsg").innerHTML=data1.message;
				 document.getElementById("certificatefailmsgDiv").style.display="block";
				 $('#certificatefailmsgDiv').delay(5000).fadeOut(400);
				 getEmployeeProjectDetail();
			}else{
				 document.getElementById("certificatemsgdiv").style.display="none";
				 document.getElementById("certificatefailmsgDiv").style.display="none";
				 //document.getElementById("FailedError").innerHTML="API Gateway not respond. Please try again.";
			}*/
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
                { "mData": "created_date"},   
			    { "mData": "expenseCategory"},
				
			    { "mData": "expenseTitle"},   
			 	{ "mData": "amount"},    
				{ "mData": "modeOfPayment"},
				{ "mData": "modeOfPayment"},        
      		  	{ "mData": "id", "render": function (data1, type, row) {
                    return '<td> <div class="d-flex align-items-center"> <button class="btn-attach" id="btnView" onclick="viewExpance(this)"> View <img src="img/attached.svg" alt=""> </button> <div class="dropdown no-arrow ml-2"> <a class="dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="fas fa-ellipsis-v fa-sm"></i></a><br> <div class="dropdown-menu dropdown-menu-right shadow"  aria-labelledby="userDropdown"><button class="dropdown-item py-2" onclick="deleteExpance(this)" > Delete  </button><a class="dropdown-item py-2" href="#"> Download </a> </div> </div> </div> </td>';
                 }}, 
    		 	],
    		 	createdRow: function (row, data2, dataIndex) 
                    {
                     //console.log("row : "+JSON.stringify(data2));
                   
                 	var expenseCategory = data2.expenseCategory;
                 	//var expenseLimit = data2.expenseLimit;
                	
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