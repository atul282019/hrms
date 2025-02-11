$(document).on('change','.up', function(){
	   	var names = [];
	   	var length = $(this).get(0).files.length;
		    for (var i = 0; i < $(this).get(0).files.length; ++i) {
		        names.push($(this).get(0).files[i].name);
		    }
		    // $("input[name=file]").val(names);
			if(length>2){
			  var fileName = names.join(', ');
			  $(this).closest('.form-group').find('.form-control').attr("value",length+" files selected");
			}
			else{
				$(this).closest('.form-group').find('.form-control').attr("value",names);
			}
			
   });

   $(document).ready(function () {
             $("#uploadBulkVoucherTable").click(function () {
                 $("#selectvouchers-wrap-issue").show();
                 $("#BulkVoucherIssuance-wrap").hide();

             });
         });
   	
   	function nextTab(){
   		$("#selectvouchers-wrap-issue").show();
   		 $("#BulkVoucherIssuanceTable").hide();
   		 var element = document.getElementById("tab2");
   		 element.classList.add("active");
   		 var element2 = document.getElementById("tab3");
   		 element2.classList.add("active");
   	}
   	
   	function returnTab2(){
   			$("#selectvouchers-wrap-issue").hide();
   			$("#BulkVoucherIssuanceTable").show();
   			
   			var element = document.getElementById("tab3");
   			 element.classList.remove("active");
   			
   		}
   	
   		function bulkReject(){
   			     window.location.href = "/bulkVoucherIssuance";
				 ///createUpiVoucherIssuance
   		         
   	}
	function toggleSubmitButton() {
	       const checkbox = document.getElementById('customCheck45');
	       const submitButton = document.getElementById('submitButton');
	       
	       // Enable the button only if the checkbox is checked
	       submitButton.disabled = !checkbox.checked;
	   }
   
	$(document).ready(function() {
		    $('#submitButton').click(function() {
			  const checkbox = document.getElementById('customCheck45');
		  	  const errorMessage = document.getElementById('errorMessage');
			  const banklist = document.getElementById('banklist').value;
			  if(banklist =="" || banklist == null){
			  				 alert("Please Select Bank");
			  				 return false;
			  	  }
		  		 if (checkbox.checked) {
		  		    errorMessage.textContent = "";
		  		  } else {
		  		    errorMessage.textContent = "Please check consent"; 
		  			return false;
		  		  }
				  document.getElementById('submitButton').disabled=true;
			      var employerMobile = document.getElementById("employerMobile").value;
				  document.getElementById("authenticate").disabled = false;
			      $.ajax({
			        url:"/smsOtpSender",
			        type: 'POST',
					data: {
								"mobile": employerMobile,
						  },
					dataType: 'json',
					success: function(data) {
					var obj = data;
			        if (obj.status === true) {
			            // If successful, open the OTP modal
						var timeleft = "60";
						var resendCodeElement = document.getElementById("resendCode");
						resendCodeElement.style.display = "none";
							var downloadTimer = setInterval(function() {
								document.getElementById("countdown").innerHTML = "00:"+timeleft;
								timeleft -= 1;
								//document.getElementById("optBtn").style.display = "none";
								document.getElementById("orderId").value= obj['orderId'];
								//document.getElementById("verifyotpdiv").style.display = "block";
								if (timeleft < 0) {
									clearInterval(downloadTimer);
									resendCodeElement.style.display = "block";
									document.getElementById("authenticate").disabled = true;
									
									 
									///document.getElementById("optBtn").disabled = false;
									///document.getElementById("countdown").innerHTML = " ";
									//document.getElementById("optBtn").style.display = "none";
									//document.getElementById("verifyotpdiv").style.display = "none";
									///$('#loginIdDiv').hide('slow');
								}
								//document.getElementById('password').focus();
							}, 1000);
			            $('#otpModal').fadeIn();
			          } else {
			            alert("Error: " + response.message);
			          }
			        },
			        error: function() {
					  //$('#otpModal').fadeIn();
			         // alert("An error occurred. Please try again.");
			        }
			      });
			    });

			    // Close the modal when clicking outside the modal content
			    $(window).on('click', function(event) {
			      if (event.target.id === 'otpModal') {
			        $('#otpModal').fadeOut();
			      }
			    });
			  });
			  
			  
		
		  let deleteValue = null;

			function openRevokeDialog(button) {
			    deleteValue = button.value; // Store the button value
			    document.getElementById("confirmationDialog").style.display = "flex";
			}
			function closeDialog() {
			    document.getElementById("confirmationDialog").style.display = "none";
			}

			function confirmRevokeDialog() {
			    console.log("Revoke confirmed for value:", deleteValue);
			    deleteBeneficiay(deleteValue);
			    closeDialog();
			}
			  						  
	
function convertImageToBase64() {
           const fileInput = document.getElementById('up');
           const output = document.getElementById('base64Output');
		   const outputFileName = document.getElementById('fileName');
		  
           if (!fileInput.files || fileInput.files.length === 0) {
			 document.getElementById("fileInputError").innerHTML="Please select file";
			 return;
           }
           var filePath = fileInput.value;
              // var allowedExtensions =  /(\.jpg|\.jpeg|\.png|\.gif)$/i;
			   var allowedExtensions =  /(\.xlsx)$/i;
               if (!allowedExtensions.exec(filePath)) {
				  document.getElementById("fileInputError").innerHTML="Invalid file type";
                   fileInput.value = '';
                   return false;
               } 
			   else{
				document.getElementById("fileInputError").innerHTML="";
			   }
			  		
           const file = fileInput.files[0];	   
           const reader = new FileReader();
           reader.onload = function(event) {
               const base64String = event.target.result.split(',')[1]; 
               output.value = base64String; 
			   console.log("FileName:", fileInput.files[0].name);
			   outputFileName.value =fileInput.files[0].name;
           };
           reader.readAsDataURL(file);
		   document.getElementById("bulksubmit").disabled=false;
    }

	function saveBulkVoucherclearmessage(){
		
	//	var banklist = document.getElementById("banklist").value;
		var fileInput = document.getElementById("up").value;
		if(fileInput =="" || fileInput == null){
			 document.getElementById("fileInputError").innerHTML="Please Select File";
			 return false;
		}
		else{
			document.getElementById("fileInputError").innerHTML="";
		}
		
	}
	
function saveBulkVoucherUpload(){
	
	var fileInput = document.getElementById("up").value;
	var fileName = document.getElementById("fileName").value;
	var base64file = document.getElementById("base64Output").value;
	var createdby =  document.getElementById("employerName").value;
	var orgId = document.getElementById("employerId").value;
	var element = document.getElementById("tab2");
	 element.classList.add("active");
	 
	var voucherCode = localStorage.getItem("voucherCode");
	var voucherName = localStorage.getItem("voucherName");
	var purposeCode = localStorage.getItem("purposeCode");
	var purposeName = localStorage.getItem("purposeName");
		
	if(fileInput =="" || fileInput == null){
		 document.getElementById("fileInputError").innerHTML="Please Select File";
		 return false;
	}
	else{
		document.getElementById("fileInputError").innerHTML="";
	}
	document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "POST",
	     url:"/saveBulkVoucher",
		 dataType: 'json',   
	      data: {
					"orgId":orgId,
					"fileName":fileName,
					"file":base64file,
					"purposeCode":"",
					"accountId":1,
					"mcc": purposeCode,
					"mccDesc": purposeName,
					"beneficiaryID":"",
					"payerVA":"",
					"payeeVPA":"",
					"mandateType":"01",
					"type":"",
					"bankcode": "",
					"voucherId": "",
				    "voucherCode": voucherCode,
					"voucherDesc": voucherName,
					"voucherType": "Bulk",
					"activeStatus": "",			
					"merchanttxnid": "",
					"merchantId": "",
					"subMerchantId": "",
					"createdby":createdby,
				    "otpValidationStatus": "",
					"consent": "",
					"creationDate": "",
					"redemtionType": "",	
							 
			 },  		 
	        success:function(data){
	        var data1 = data;
	       console.log(data);
			//var data1 = jQuery.parseJSON(data);
		
			document.getElementById("signinLoader").style.display="none";
			if(data1.status==true){
				 var success = data1.data.success;
				 var fail = data1.data.fail;
				 document.getElementById("BulkVoucherIssuanceTable").style.display="block";
				 document.getElementById("BulkVoucherIssuance-wrap").style.display="none";
				 
				 document.getElementById("failed").innerHTML=data1.data.failCount;
				 document.getElementById("success").innerHTML=data1.data.successCount;
				 document.getElementById("successTotal").innerHTML=data1.data.totalCount;
				 document.getElementById("failedTotal").innerHTML=data1.data.totalCount;
				 
				 
				 document.getElementById("failedUploadDownload").innerHTML=data1.data.failCount;
 				 document.getElementById("successUploadDownload").innerHTML=data1.data.successCount;
 				 document.getElementById("successUploadTotal").innerHTML=data1.data.totalCount;
 				 document.getElementById("failedUploadTotal").innerHTML=data1.data.totalCount;
				
				 var table = $('#successUpload').DataTable( {
			      destroy: true,	
				 // "dom": 'rtip',
				 //dom: 'Bfrtip',
			     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
			      "buttons": ["csv", "excel"],
			      "language": {"emptyTable": "No Data available"  },
			     "aaData": success,
				  "aoColumns": [ 
					/*{ "mData": "voucherType"},*/
			        { "mData": "beneficiaryName"},   
			        { "mData": "mobile"},   
				    { "mData": "amount"},
					{ "mData": "startDate"},  
					{ "mData": "expDate"}
					
				 	],
					
				});
				
				var table = $('#issueVoucherTable').DataTable( {
			          destroy: true,	
					 // "dom": 'rtip',
					 //dom: 'Bfrtip',
				     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
			          "buttons": ["csv", "excel"],
			          "language": {"emptyTable": "No Data available"  },
			         "aaData": success,
			   		  "aoColumns": [ 
						{ "mData": "id", "render": function (data2, type, row) {
								 return '<td> <input type="hidden" name="issueId" id="issueId" value="'+data2+'" /> </td>';
							}}, 								
						{ "mData": "id"},	
					/*	{ "mData": "voucherType"},*/
			            { "mData": "beneficiaryName"},   
			            { "mData": "mobile"},   
					    { "mData": "amount"},
						{ "mData": "startDate"},  
						{ "mData": "expDate"},
						
						{ "mData": "id", "render": function (data2, type, row) {
						   return '<td> <button value="'+data2+'" id="btnDelete" onclick="openRevokeDialog(this)" > <a href="#"><img src="img/delete.svg" alt=""></a> </button> </td>';
						}}, 
							
			 		 	],
					});
					
				var table = $('#failedUpload').DataTable( {
					
		          destroy: true,	
				 // "dom": 'rtip',
				 //dom: 'Bfrtip',
			     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
	             "buttons": ["csv", "excel"],
	             "language": {"emptyTable": "No Data available"  },
		         "aaData": fail,
	      		  "aoColumns": [ 
					/*{ "mData": "redemtionType"},*/
					{ "mData": "beneficiaryName"},   
					{ "mData": "mobile"},   
					 { "mData": "amount"},
					{ "mData": "startDate"},  
					{ "mData": "expDate"},
					{ "mData": "message"}
	    		 	],
					createdRow: function (row, data2, dataIndex) 
	                 {
		                     console.log("row : "+JSON.stringify(data2));
		                     var regMobile = /^[6-9]\d{9}$/gi;
		                 	 var regEmail = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/;
		                 	//var email = data2.mobile;
		                	var mobile = data2.mobile;
		                     if(!mobile.match(regMobile))
		                     {
		                       $(row).find('td:eq(1)').addClass("tdactive");
		                     }
							else if(mobile.length !==10)
		                     {
		                       $(row).find('td:eq(1)').addClass("tdactive");
		                     }		 
		                  
			           }
					   
				});
			}else if(data1.status==false){
				document.getElementById("signinLoader").style.display="none";
				
			}
	     },
	     error: function(e){
	         alert('Error: ' + e);
	     }
	});	
	
}
 function showClearButton() {
            var fileInput = document.getElementById("up");
            document.getElementById("clearButton").style.display="block";
             var clearButton = document.getElementById("clearButton");
            
             if (fileInput.value !== "") {
                 clearButton.style.display = "inline-block";
             } else {
                clearButton.style.display = "none";
        }
   }

function resendVoucherOTP() {
	document.getElementById("authenticate").disabled = false;
	var userName = document.getElementById("banklinkedMobile").value;
	var orderId = document.getElementById("orderId").value;
	
	
	//document.getElementById("optBtn").disabled = true;
	//document.getElementById("mobError").innerHTML="";
	
	//document.getElementById("loginLoader").style.display = "flex";
	$.ajax({
		type: "POST",
		url:"/smsOtpResender",
		dataType: 'json',
		data: {
			"mob": userName,
			"orderId":orderId
		},
		success: function(data) {
			var obj = data;
			//document.getElementById("loginLoader").style.display = "none";
			if (obj['status'] == "SUCCESS") {
				//$('#errorOtp').hide('slow');
				//$('#loginIdDiv').hide('slow');
				var timeleft = "60";
				var resendCodeElement = document.getElementById("resendCode");
	               // Hide the "Resend OTP" link initially
	               resendCodeElement.style.display = "none";
				var downloadTimer = setInterval(function() {
					document.getElementById("countdown").innerHTML = "00."+timeleft;
					timeleft -= 1;
					//document.getElementById("optBtn").style.display = "none";
					document.getElementById("orderId").value= obj['orderId'];
					//document.getElementById("verifyotpdiv").style.display = "block";
					if (timeleft < 0) {
						clearInterval(downloadTimer);
						//document.getElementById("optBtn").disabled = false;
						document.getElementById("countdown").innerHTML = " ";
						resendCodeElement.style.display = "block";
						
						 
						//document.getElementById("optBtn").style.display = "none";
						//document.getElementById("verifyotpdiv").style.display = "none";
						
						//$('#loginIdDiv').hide('slow');
					}
				//	document.getElementById('password1').focus();
				}, 1000);
				//$('#loginIdDiv').show('slow');
			}else if (obj['status'] == "FAILURE") {

			
			} else {
				
			}
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
}

/*
function verfyIssueVoucherOTP() {
	
  	var password1 = document.getElementById("password1").value;
  	var password2 = document.getElementById("password2").value;
  	var password3 = document.getElementById("password3").value;
  	var password4 = document.getElementById("password4").value;
  	var password5 = document.getElementById("password5").value;
  	var password6 = document.getElementById("password6").value;
  	var orderId = document.getElementById("orderId").value;
  	var employerMobile = document.getElementById("employerMobile").value;
  	
  	if (document.getElementById("banklinkedMobile").value == "") {
  		document.getElementById("mobError").innerHTML="Please Enter mobile..";
  		
  		x = false;
  	} else if (password1 == "" && password1.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password1.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	 if (password2 == "" && password2.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password2.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	 if (password3 == "" && passwor3.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password3.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	 if (password4 == "" && password4.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password4.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	 if (password5 == "" && password5.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password5.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	 if (password6 == "" && password6.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password6.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
	document.getElementById("authenticate").disabled = true;
  	$.ajax({
  			type: "POST",
  			url:"/verifyOTP",
  			dataType: 'json',
  			data: {
  				"password1": password1,
  				"password2": password2,
  				"password3": password3,
  				"password4": password4,	
  				"password5": password5,
  				"password6": password6,
  				"mobile": employerMobile,
  				"orderId": orderId,
  				"userName":employerMobile
  			},
  			success: function(data) {
  				var obj = data;

  				if (obj['status']== true) {
					
					$('#otpModal').fadeOut();
					issueVoucher();
  					$('#errorOtp').hide('slow');
  				}else if (obj['status'] == false) {
					document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
					document.getElementById("authenticate").disabled = false;
				} else {
  				
  				}
  			},
  			error: function(e) {
  				alert('Error: ' + e);
  			}
  		});
  }
*/
  
    function focusNext(currentInput) {
        // Move focus to the next input box
        var maxLength = parseInt(currentInput.getAttribute("maxlength"));
        var currentLength = currentInput.value.length;

        if (currentLength >= maxLength) {
            var nextIndex = Array.from(currentInput.parentElement.children).indexOf(currentInput) + 1;
            var nextInput = currentInput.parentElement.children[nextIndex];

            if (nextInput) {
                nextInput.focus();
            }
        }
    }

function focusBack(){
	  var elts = document.getElementsByClassName('test')
	  Array.from(elts).forEach(function(elt) {
	  elt.addEventListener("keydown", function(event) {
	    // Number 13 is the "Enter" key on the keyboard
	    if (event.keyCode === 13 ||
	        event.keyCode !== 8 && elt.value.length === Number(elt.maxLength)
	    ) {
	      // Focus on the next sibling
	      elt.nextElementSibling.focus()
	    }
	    if (event.keyCode == 8) {
	      elt.value = '';
	      if (elt.previousElementSibling != null) {
	        elt.previousElementSibling.focus();
	        event.preventDefault();
	      }
	    }
	  });
	})
}



function  getLinkedBankDetail(){
	
    //document.getElementById("signinLoader").style.display="flex";
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
//            console.log(newData);
			$("#banklist option").remove();
			$("#banklist2 option").remove();
            var obj = jQuery.parseJSON( data );
             obj = obj.data;
        	 var count=0;
			 var count1=0;
         	for (var key in obj) {

             var values =  obj[key];
             var x = document.getElementById("banklist");
             if(count==0){
             var option = document.createElement("option");
             option.text ="Select Bank";
             option.value = "";
             x.add(option);
             }
             var option = document.createElement("option");
             option.value = values.acNumber;
             option.text = values.bankName+" | "+values.acNumber;
             x.add(option);

             count++;
             }
			 for (var key in obj) {

	             var values =  obj[key];
	             var x = document.getElementById("banklist2");
	             if(count1==0){
	             var option = document.createElement("option");
	             option.text ="Select Bank";
	             option.value = "";
	             x.add(option);
	             }
	             var option = document.createElement("option");
	             option.value = values.acNumber;
	             option.text = values.bankName+" | "+values.acNumber;
	             x.add(option);

	             count1++;
	             }
          },
        error: function(e){
            alert('Error: ' + e);
        }
   }); 
			
}




function  getVoucherDetailByBoucherCode(){
	
    //document.getElementById("signinLoader").style.display="flex";
 	var voucherCode = localStorage.getItem('voucherCode');
 	$.ajax({
	type: "POST",
	//url:"/getVoucherDetailByBoucherCode",
	url:"/getmccMasterListByPurposeCode",
       data: {
			"purposeCode": voucherCode
      		 },
      		  beforeSend : function(xhr) {
			//xhr.setRequestHeader(header, token);
			},
          /* success: function(data){
           newData = data;
           //console.log(newData);
           var data1 = jQuery.parseJSON( newData );
		   //var data2 = data1.data;
			
		    document.getElementById("voucherId").value=data1.data.id;
			document.getElementById("voucherCode").value=data1.data.voucherCode;
			document.getElementById("voucherType").value=data1.data.voucherType;
			document.getElementById("voucherSubType").value=data1.data.voucherSubType;
			document.getElementById("voucherDesc").value=data1.data.voucherDesc;
			document.getElementById("purposeCode").value=data1.data.purposeCode;
			document.getElementById("activeStatus").value=data1.data.activeStatus;
			document.getElementById("createdby").value=data1.data.createdby;
          },
        error: function(e){
            alert('Error: ' + e);
        }*/
		
		success: function(data){
	           newData = data;
	           console.log(newData);
			$("#voucherTypeMCC option").remove();
	           var obj = jQuery.parseJSON( data );
	            obj = obj.data;
	       	 var count=0;
	        	for (var key in obj) {

	            var values =  obj[key];
	            var x = document.getElementById("voucherTypeMCC");
	            if(count==0){
	            var option = document.createElement("option");
	            option.text ="Select Voucher Type MCC";
	            option.value = "";
	            x.add(option);
	            }
	            var option = document.createElement("option");
	            option.text = values.mccDesc;
	            option.value = values.mcc;
	            x.add(option);

	            count++;
	            }   
	        },
	        error: function(e){
	            alert('Error: ' + e);
	        }
   }); 
			
}


function  getmccMasterDetailsByPurposeCodeAndMcc(){
	saveBulkVoucherclearmessage();
 	var voucherCode = localStorage.getItem('voucherCode');
	var mcc = document.getElementById("voucherTypeMCC").value;
 	$.ajax({
	type: "POST",
	url:"/getmccMasterDetailsByPurposeCodeAndMcc",
       data: {
			"purposeCode": voucherCode,
			"mcc": mcc
      		 },
      		  beforeSend : function(xhr) {
			//xhr.setRequestHeader(header, token);
			},
           success: function(data){
           newData = data;
           console.log(newData);
           var data1 = jQuery.parseJSON( newData );
		   var data2 = data1.data;
			
			document.getElementById("voucherId").value=data1.data.id;
			document.getElementById("voucherCode").value=data1.data.purposeCode;
			document.getElementById("voucherType").value=data1.data.purposeCode;
			document.getElementById("voucherSubType").value=data1.data.purposeCode;
			document.getElementById("voucherDesc").value=data1.data.purposeCode;
			document.getElementById("purposeCode").value=data1.data.purposeCode;
			document.getElementById("activeStatus").value=data1.data.activeStatus;
			document.getElementById("mcc").value=data1.data.mcc;
			
			document.getElementById("createdby").value=data1.data.createdby;
          },
        error: function(e){
            alert('Error: ' + e);
        }
		

   }); 
			
}

function  getBankDetailByBankAccountNumber(){
	//saveBulkVoucherclearmessage();
    //document.getElementById("signinLoader").style.display="flex";
 	var accountNumber = document.getElementById("banklist").value;
 	$.ajax({
	type: "POST",
	url:"/getBankDetailByAccountNo",
       data: {
			"acNumber": accountNumber
      		 },
      		  beforeSend : function(xhr) {
			//xhr.setRequestHeader(header, token);
			},
           success: function(data){
           newData = data;
           //console.log(newData);
           var data1 = jQuery.parseJSON( newData );
		   //var data2 = data1.data;
		 
			document.getElementById("bankName").value=data1.data.bankName; 
			document.getElementById("bankCode").value=data1.data.bankCode; 
			document.getElementById("accountHolderName").value=data1.data.accountHolderName; 
			
			document.getElementById("acNumber").value=data1.data.acNumber; 
			document.getElementById("accountType").value=data1.data.accountType; 
			document.getElementById("ifsc").value=data1.data.ifsc; 
			
			document.getElementById("erupiFlag").value=data1.data.erupiFlag; 
			document.getElementById("branchCode").value=data1.data.branchCode; 
			document.getElementById("authStatus").value=data1.data.authStatus; 
			
			document.getElementById("authResponse").value=data1.data.authResponse; 
			document.getElementById("banklinkedMobile").value=data1.data.mobile; 
			document.getElementById("accstatus").value=data1.data.accstatus; 

			document.getElementById("orgId").value=data1.data.orgId; 
			document.getElementById("orgCode").value=data1.data.orgCode; 
			document.getElementById("tid").value=data1.data.tid;
			
			document.getElementById("merchentIid").value=data1.data.merchentIid; 
			document.getElementById("mcc").value=data1.data.mcc; 
			document.getElementById("submurchentid").value=data1.data.submurchentid;  
			document.getElementById("merchentid").value=data1.data.merchentIid;  
			document.getElementById("payerva").value=data1.data.payerva;  
												 
          },
        error: function(e){
            alert('Error: ' + e);
        }
   }); 
			
}

function getTotalVoucherCount(){
			var employerid = document.getElementById("employerId").value;
			$.ajax({
				type: "POST",
				url: "/getTotalVoucherCount",
				data: {
					"orgId": employerid,
					"workflowid":100003
				},
				success: function(data) {
					newData = data;
					var data1 = jQuery.parseJSON(newData);
					document.getElementById("totalNumber").innerText=data1.totalCount;
					document.getElementById("totalValue").innerText="₹"+data1.totalAmount;
					document.getElementById("totalNumber2").innerText=data1.totalCount;
					document.getElementById("totalValue2").innerText="₹"+data1.totalAmount;
					},
					error: function(e) {
						alert('Failed to fetch JSON data' + e);
				}
			});
		}


function getVoucherSummaryList(){
			var employerid = document.getElementById("employerId").value;
			$.ajax({
				type: "POST",
				url: "/getVoucherSummaryList",
				data: {
					"orgId": employerid,
					"workflowid":100003
				},
				success: function(data) {
					newData = data;
					var data1 = jQuery.parseJSON(newData);
					var data2 = data1.data;
					 //console.log(data2);
					 
					 const container = document.getElementById('jsonData');	
					 const container2 = document.getElementById('jsonData2');	
					 
					/* const statusMessage = document.createElement('div');
					 statusMessage.classList.add('selectvouchers-carosel-cards');
					 statusMessage.innerHTML = `
 					                 <h5>Total Vouchers</h5>
 					                <div class="d-flex justify-content-between my-1 mb-3"><span >Number</span><span> ${data1.totalCount}</span></div>
 					                <div class="d-flex justify-content-between my-1"><span>Value</span> <span>${data1.totalAmount}</span></div>
 					            `;
					      
					        container.appendChild(statusMessage);*/

							data1.data.forEach(voucher => {
					            const voucherDiv = document.createElement('div');
					            voucherDiv.classList.add('selectvouchers-carosel-cards');
					            voucherDiv.innerHTML = `
								<h5><img src="data:image/png;base64,${voucher.voucherIcon}" alt="${voucher.voucherIcon}" class="logo" width="20px" height="20px">${voucher.voucherName}</h5>
										
						                <div class="d-flex justify-content-between my-1 mb-3"><span class="info"></span> ${voucher.count}</div>
						                <div class="d-flex justify-content-between my-1"><span></span> ₹${voucher.totalAmount}</div>
					            `;
					            container.appendChild(voucherDiv);
					        });
							data1.data.forEach(voucher => {
				            const voucherDiv2 = document.createElement('div');
				            voucherDiv2.classList.add('selectvouchers-carosel-cards');
				            voucherDiv2.innerHTML = `
							<h5><img src="data:image/png;base64,${voucher.voucherIcon}" alt="${voucher.voucherIcon}" class="logo" width="20px" height="20px">${voucher.voucherName}</h5>
									
					                <div class="d-flex justify-content-between my-1 mb-3"><span class="info"></span> ${voucher.count}</div>
					                <div class="d-flex justify-content-between my-1"><span></span> ₹${voucher.totalAmount}</div>
				            `;
				            container2.appendChild(voucherDiv2);
							
				        });
							
					},
					error: function(e) {
						alert('Failed to fetch JSON data' + e);
				}
			});
		}

function getPrimaryBankDetail(){
			var employerid = document.getElementById("employerId").value;
			$.ajax({
				type: "POST",
				url: "/getPrimaryBankDetailsByOrgId",
				data: {
					"orgId": employerid,
					"workflowid":100003
				},
				success: function(data) {
					newData = data;
					var data1 = jQuery.parseJSON(newData);
					var data2 = data1.data;
					 //console.log(data2);
					 const container = document.getElementById('jsonDataBank');
					 const account = data1.data;
					     
					      const dataSection = document.createElement('div');
					      dataSection.classList.add('bnkdetils');
						  
						  /*const accountSection = document.createElement('div');
							  let logoHTML = '';

						          if (account.bankLogo) {
						              logoHTML = `<img src="data:image/png;base64,${account.bankLogo}" alt="${account.bankName}" class="logo" width="30">`;
						          }
								  accountSection.innerHTML = `
								  ${logoHTML}  
								    `;
							 container.appendChild(accountSection);
					      
							 dataSection.innerHTML = `
							 
					          <p><strong>${account.bankName}</strong></p>
					          <p>
							  <strong>${account.accountHolderName}</strong></p>
					          <p><span class="mb-0">A/C No: </span><strong>${account.acNumber}</strong> </p>
					          
							  
					 
					      `;
					      container.appendChild(dataSection);*/
					},
					error: function(e) {
						alert('Failed to fetch JSON data' + e);
				}
			});
		}




function verfyIssueVoucherOTP() {
	
  	var password1 = document.getElementById("password1").value;
  	var password2 = document.getElementById("password2").value;
  	var password3 = document.getElementById("password3").value;
  	var password4 = document.getElementById("password4").value;
  	var password5 = document.getElementById("password5").value;
  	var password6 = document.getElementById("password6").value;
  	var orderId = document.getElementById("orderId").value;
  	var employerMobile = document.getElementById("employerMobile").value;
  	
  	if (document.getElementById("employerMobile").value == "") {
  		document.getElementById("mobError").innerHTML="Please Enter mobile..";
  		
  		x = false;
  	} else if (password1 == "" && password1.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password1.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	 if (password2 == "" && password2.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password2.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	 if (password3 == "" && passwor3.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password3.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	 if (password4 == "" && password4.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password4.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	 if (password5 == "" && password5.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password5.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	 if (password6 == "" && password6.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password6.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
	document.getElementById("authenticate").disabled = true;
  	$.ajax({
  			type: "POST",
  			url:"/verifyOTP",
  			dataType: 'json',
  			data: {
  				"password1": password1,
  				"password2": password2,
  				"password3": password3,
  				"password4": password4,	
  				"password5": password5,
  				"password6": password6,
  				"mobile": employerMobile,
  				"orderId": orderId,
  				"userName":employerMobile
  			},
  			success: function(data) {
  				var obj = data;

  				if (obj['status']== true) {
					
					$('#otpModal').fadeOut();
					issueBulkVoucher();
  					$('#errorOtp').hide('slow');
  				}else if (obj['status'] == false) {
					document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";

					document.getElementById("authenticate").disabled = false;
				} else {
  				
  				}
  			},
  			error: function(e) {
  				alert('Error: ' + e);
  			}
  		});
  }


  function  issueBulkVoucher(){
  	
  	document.getElementById("password1").value="";
  	document.getElementById("password2").value="";
    document.getElementById("password3").value="";
  	document.getElementById("password4").value="";
  	document.getElementById("password5").value="";
    document.getElementById("password6").value="";
  	
    
  	var banklist = document.getElementById("banklist").value;
  	
  	var createdby = document.getElementById("employerName").value;
	
	var voucherCode = localStorage.getItem("voucherCode");
    var voucherName = localStorage.getItem("voucherName");
	var purposeCode = localStorage.getItem("purposeCode");
	var purposeName = localStorage.getItem("purposeName");
	
	const table = document.getElementById('issueVoucherTable');
	      const firstColumnData = [];
	      
	      for (let row of table.tBodies[0].rows) {
	        firstColumnData.push(row.cells[1].textContent);
	      }
	      console.log(firstColumnData);
	    
		  
  	var bankCode = document.getElementById("bankCode").value;
  	var mcc = document.getElementById("mcc").value;
  	var payerva = document.getElementById("payerva").value;
  	
  	var merchentid = document.getElementById("merchentid").value;
  	var submurchentid = document.getElementById("submurchentid").value;
	var orgId = document.getElementById("orgId").value;
	var acNumber = document.getElementById("acNumber").value;
	document.getElementById("signinLoader").style.display="flex";
	
   	$.ajax({
  	type: "POST",
  	url:"/issueBulkVoucher",
	data: {
					"orgId":orgId,
					"fileName":"",
					"file":"",
					"purposeCode":voucherCode,
					"accountId":1,
					"mcc":purposeCode,
					"beneficiaryID":"",
					"payerVA":payerva,
					"payeeVPA":"",
					"mandateType":"01",
					"type":"CREATE",
					"bankcode":bankCode,
					"accountNumber":acNumber,
					"voucherCode":voucherCode,
					"voucherDesc":voucherName,
					"merchantId":merchentid,
					"subMerchantId":submurchentid,
					"createdby":createdby,
					"arrayofid":firstColumnData
			 },  	
        		  beforeSend : function(xhr) {
  			//xhr.setRequestHeader(header, token);
  			},
             success: function(data){
             newData = data;
             //console.log(newData);
             var data1 = jQuery.parseJSON( newData );
  		   //var data2 = data1.data;
  		   document.getElementById("signinLoader").style.display="none";
		
		if(data1.status==true){
			
			     document.getElementById("banklist").value="";
				
				 document.getElementById("voucherCode").value="";
				 document.getElementById("voucherType").value="";
				 document.getElementById("voucherSubType").value="";
				 document.getElementById("voucherDesc").value="";
				 document.getElementById("purposeCode").value="";
				 document.getElementById("activeStatus").value="";
				 document.getElementById("employerName").value="";
				 document.getElementById("bankCode").value="";
				 document.getElementById("mcc").value="";
				 document.getElementById("payerva").value="";
				
				 document.getElementById("issuesuccmsg").innerHTML="Voucher Created Successfully.";
				 document.getElementById("issuemsgdiv").style.display="block";
				 //document.getElementById("getInTouchUser").reset();
				 $('#otmsgdiv').delay(10000).fadeOut(800);
				 //window.location.href = "/upiVoucherIssuanceNew";
				 document.getElementById('submitButton').disabled=false;
				 document.getElementById('authenticate').disabled=false;
				 document.getElementById("selectvouchers-wrap-issue").style.display="none";
				 document.getElementById("upi-voucher-wrapThree").style.display="block";
				
				 const tableBody = document.getElementById("successFailVoucherTable").getElementsByTagName("tbody")[0];

				 data1.data.forEach((item) => {
				     const row = tableBody.insertRow();

				     row.insertCell().textContent = item.name;
				     row.insertCell().textContent = item.mobile;
				     row.insertCell().textContent = item.voucherDesc;
				     row.insertCell().textContent = item.redemtionType;
				     row.insertCell().textContent = item.amount;
				     //row.insertCell().textContent = item.voucherCode;
					 row.insertCell().textContent = item.startDate;
					 row.insertCell().textContent = item.expDate;
					// row.insertCell().textContent = item.response;
					 

				     // Add the response cell with an image
				     const responseCell = row.insertCell();
				     const img = document.createElement("img");
				     if (item.response === "SUCCESS") {
				       img.src = "img/status-check.svg"; 
				       img.alt = "Success";
				     } else {
				       img.src = "img/status-cross.svg"; 
				       img.alt = "Failure";
				     }
				     img.width = 20;
				     img.height = 20;
				     responseCell.appendChild(img);

				    // row.insertCell().textContent = item.voucherDesc;
				   });
				 
				
			}else if(data1.status==false){
				 document.getElementById('submitButton').disabled=false;
				 document.getElementById('authenticate').disabled=false;									
				 document.getElementById("issuesfailmsg").innerHTML=data1.message;
				 document.getElementById("issuesfailmsgDiv").style.display="block";
				 $('#otfailmsgDiv').delay(5000).fadeOut(400);
			}else{
				 document.getElementById('submitButton').disabled=false;
				 document.getElementById('authenticate').disabled=false;						
				 document.getElementById("issuesfailmsg").innerHTML="API Gateway not respond. Please try again.";
				 document.getElementById("issuesfailmsgDiv").style.display="block";
				 $('#otfailmsgDiv').delay(5000).fadeOut(400);
			}
            },
          error: function(e){
              alert('Error: ' + e);
          }
     }); 
  		
  }
  
  document.getElementById('downloadBtn').addEventListener('click', function () {
      // Get table data
      const table = document.getElementById('failedUpload');
      
      // Convert table to a workbook
      const workbook = XLSX.utils.table_to_book(table, { sheet: "Sheet1" });

      // Export the workbook to an Excel file
      XLSX.writeFile(workbook, 'Bulk_Voucher_Templates.xlsx');
  });

  
  function deleteBeneficiay(value){
  		/* var row = jQuery(value).closest('tr');
  		 var  id = $(row).find("input[name='issueId']").val();*/
  		 document.getElementById("signinLoader").style.display="flex";
  		  	$.ajax({
  		 	type: "POST",
  		 	url:"/beneficiaryDeleteFromVoucherList",
  		     data: {
  		 			"id": value
  		    		 },
  		    		  beforeSend : function(xhr) {
  		 			//xhr.setRequestHeader(header, token);
  		 			},
  		         success: function(data){
  		         newData = data;
  		         //console.log(newData);
  				 document.getElementById("signinLoader").style.display="none";
  		         var data1 = jQuery.parseJSON( newData );
  		 		 var data2 = data1.data;
				 var table = $('#issueVoucherTable').DataTable();

 			    // Handle row click to hide
 			    $('#issueVoucherTable tbody').on('click', 'tr', function () {
 			        $(this).hide(); // Hides the row in the DOM
 			       
 			    });
				 				
  		        },
  		      error: function(e){
  		          alert('Error: ' + e);
  		      }
  		 }); 
  		
  }