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
		 const table = document.getElementById("successUpload");
		 const rows = table.querySelectorAll("tbody tr");
		 let totalAmount = 0;
		 let validRowCount = 0;
		 rows.forEach(row => {
		     const amountCell = row.cells[2]; // Assuming amount is in the 2nd column (index 1)
		     const amount = parseFloat(amountCell.textContent.replace(/[₹,]/g, '').trim());
		     if (!isNaN(amount)) {
		         totalAmount += amount;
		         validRowCount++;
		     }
		 });
		 document.getElementById("totalNumberCount").textContent = `${validRowCount}`;
		 document.getElementById("totalValueCount").textContent = `₹${totalAmount.toFixed(2)}`;
		 document.getElementById("totalNumber2Count").innerText=`${validRowCount}`;
		 document.getElementById("totalValue2Count").innerText=`₹${totalAmount.toFixed(2)}`;
		
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
			  const totalValueCountAmtText = document.getElementById('totalValueCount').innerText;
			  const amountInAccText = document.querySelector('.text-wrapper-3').innerText;

			  // Remove ₹, commas, and spaces
			  const totalValueCountAmt = Number(totalValueCountAmtText.replace(/[^0-9.-]/g, ""));
			  const amountInAcc = Number(amountInAccText.replace(/[^0-9.-]/g, ""));

			  if (totalValueCountAmt > amountInAcc) {
			      errorMessage.textContent = "Amount in the account is not sufficient to issue voucher";
			      return false;
			  }
			   
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
				  var totalNumberCount = document.getElementById("totalNumber2Count").innerHTML;
			      $.ajax({
			        /*url:"/smsOtpSender",
			        type: 'POST',
					data: {
								"mobile": employerMobile,
						  },
					  url:"/smsOtpSenderWithTemplate",
					  type: 'POST',
					  data: {
					  			"mobile": employerMobile,
					  			"template": "Cotodel Voucher Activity",
					  },*/
					  url:"/smsOtpWithTemplateMobileAndAmount",
		  			type: 'POST',
		  			data: {
				  			"mobile": employerMobile,
				  			"template": "OTP Number Vouchers Issuance",
				  			"value" : totalNumberCount,
			  			},
					dataType: 'json',
					success: function(data) {
					var obj = data;
			        if (obj.status === true) {
						
						// Mask the mobile number (show only last 4 digits)
						var maskedMobile = "XXXXXX" + employerMobile.toString().slice(-4);
						document.getElementById("maskedMobileDisplay").innerText = `OTP code has been sent to your phone ${maskedMobile}. Enter OTP to validate issuance.`;				
										
			            // If successful, open the OTP modal
						var timeleft = "60";
						var resendCodeElement = document.getElementById("resendCode");
						resendCodeElement.style.display = "none";
							var downloadTimer = setInterval(function() {
								//document.getElementById("countdown").innerHTML = "00:"+timeleft;
								let seconds = timeleft < 10 ? "0" + timeleft : timeleft;
								       document.getElementById("countdown").innerHTML = "00:" + seconds;
								timeleft -= 1;
								//document.getElementById("optBtn").style.display = "none";
								document.getElementById("orderId").value= obj['orderId'];
								//document.getElementById("verifyotpdiv").style.display = "block";
								if (timeleft < 0) {
									clearInterval(downloadTimer);
									resendCodeElement.style.display = "block";
									document.getElementById("authenticate").disabled = true;
									
								}
							}, 1000);
			            $('#otpModal1').fadeIn();
			          } else {
						document.getElementById('submitButton').disabled=false;
			            alert("Error: " + obj.message);
			          }
			        },
			        error: function() {
					alert("Error: " + obj.message);			        }
			      });
			    });

			    // Close the modal when clicking outside the modal content
			    $(window).on('click', function(event) {
			      if (event.target.id === 'otpModal') {
			        $('#otpModal1').fadeOut();
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
	// Reusable date formatter
	function formatDateToDDMMYY(dateStr) {
	    if (!dateStr) return "";
	    let dateObj = new Date(dateStr);

	    if (isNaN(dateObj)) return dateStr; // if invalid, return as-is

	    let dd = String(dateObj.getDate()).padStart(2, '0');
	    let mm = String(dateObj.getMonth() + 1).padStart(2, '0'); 
	    let yy = String(dateObj.getFullYear()).slice(-2);

	    return dd + "-" + mm + "-" + yy;
	}	
async function saveBulkVoucherUpload(){
	
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
	
	const clientKey = "client-secret-key"; // Extra security measure
	const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

    // Concatenate data (must match backend)
	const dataString = orgId+fileName+base64file+voucherCode+purposeCode
	+voucherName+createdby+clientKey+secretKey;

	const encoder = new TextEncoder();
	const data = encoder.encode(dataString);
	const hashBuffer = await crypto.subtle.digest("SHA-256", data);
	const hashArray = Array.from(new Uint8Array(hashBuffer));
	const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
	
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
					//"accountId":1,
					"mcc": voucherCode,
					"mccDescription": purposeName,
					//"mandateType":"01",
					"voucherCode":purposeCode,
					"voucherDesc": voucherName,
					//"voucherType": "Bulk",
					"createdby":createdby,
					//"purposeCode":"",
					//"beneficiaryID":"",
					//"payerVA":"",
					//"payeeVPA":"",
					//"type":"",
					//"bankcode": "",
					//"voucherId": "",
					//"activeStatus": "",			
					//"merchanttxnid": "",
					//"merchantId": "",
					//"subMerchantId": "",
				    //"otpValidationStatus": "",
					//"consent": "",
					//"creationDate": "",
					//"redemtionType": "",
					"clientKey":clientKey,
				    "hash":hashHex
	
			 },  		 
	        success:function(data){
		  
	        var data1 = data.data;
	       console.log("data"+data);
		   console.log("data1"+data1);
		   var data2;
		   if(data.status !==false){
		    data2 = jQuery.parseJSON(data1);
		   console.log("data2"+data2);
		   }
		   document.getElementById("signinLoader").style.display="none";
			
			if(data.status==true){
				//document.getElementById("continueButton").disabled = false;
				 var success = data2.data.success;
				 var fail = data2.data.fail;
				 if (success.length === 0 ) {
				         document.getElementById("continueButton").disabled = true;
				     } else {
				         document.getElementById("continueButton").disabled = false;
				     }

				 document.getElementById("BulkVoucherIssuanceTable").style.display="block";
				 document.getElementById("BulkVoucherIssuance-wrap").style.display="none";
				 
				 document.getElementById("failed").innerHTML=data2.data.failCount;
				 document.getElementById("success").innerHTML=data2.data.successCount;
				 document.getElementById("successTotal").innerHTML=data2.data.totalCount;
				 document.getElementById("failedTotal").innerHTML=data2.data.totalCount;
				 document.getElementById("failedUploadDownload").innerHTML=data2.data.failCount;
 				 document.getElementById("successUploadDownload").innerHTML=data2.data.successCount;
 				 document.getElementById("successUploadTotal").innerHTML=data2.data.totalCount;
 				 document.getElementById("failedUploadTotal").innerHTML=data2.data.totalCount;
				
				 var table = $('#successUpload').DataTable( {
			      destroy: true,	
				 // "dom": 'rtip',
				 //dom: 'Bfrtip',
			     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
			      "buttons": ["csv", "excel"],
			      "language": {"emptyTable": "No Data available"  },
			     "aaData": success,
				  "aoColumns": [ 
			        { "mData": "beneficiaryName"},   
			        { "mData": "mobile"},   
					{ 
					        "mData": "amount",
					        "mRender": function(data, type, row) {
					            if (!isNaN(data)) {
					                return '₹' + Number(data).toLocaleString('en-IN', {
					                    minimumFractionDigits: 2,
					                    maximumFractionDigits: 2
					                });
					            }
					            return data;
					        }
					    },
					{ "mData": "startDate",
						"mRender": function (data) {
						            return formatDateToDDMMYY(data);
						        }
					},  
					{ "mData": "expDate",
						"mRender": function (data) {
						            return formatDateToDDMMYY(data);
						        }
					}
					
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
			            { "mData": "beneficiaryName"},   
			            { "mData": "mobile"},   
						{ 
						        "mData": "amount",
						        "mRender": function(data, type, row) {
						            if (!isNaN(data)) {
						                return '₹' + Number(data).toLocaleString('en-IN', {
						                    minimumFractionDigits: 2,
						                    maximumFractionDigits: 2
						                });
						            }
						            return data;
						        }
						    },
						{ "mData": "startDate",
							"mRender": function (data) {
							            return formatDateToDDMMYY(data);
							        }
						},  
						{ "mData": "expDate",
							"mRender": function (data) {
							            return formatDateToDDMMYY(data);
							        }
						},
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
					{ "mData": "beneficiaryName"},   
					{ "mData": "mobile"},   
					 { "mData": "amount"},
					{ "mData": "startDate",
						"mRender": function (data) {
						            return formatDateToDDMMYY(data);
						        }
					},  
					{ "mData": "expDate",
						"mRender": function (data) {
						            return formatDateToDDMMYY(data);
						        }
					},
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
			}else if(data.status==false){
				document.getElementById("fileInputError").innerHTML=data.message;
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
	document.getElementById("password1").value="";
  	 document.getElementById("password2").value="";
  	 document.getElementById("password3").value="";
  	 document.getElementById("password4").value="";
  	 document.getElementById("password5").value="";
  	 document.getElementById("password6").value="";
	 
	 
	document.getElementById("authenticate").disabled = false;
	var userName = document.getElementById("banklinkedMobile").value;
	var orderId = document.getElementById("orderId").value;
	var employerMobile = document.getElementById("employerMobile").value;

	var totalNumberCount = document.getElementById("totalNumber2Count").innerHTML;
				     
						  
	
	$.ajax({
		/*type: "POST",
		url:"/smsOtpResender",
		dataType: 'json',
		data: {
			"mobile": userName,
			"orderId":orderId
		},
		url:"/smsOtpSenderWithTemplate",
		type: 'POST',
		dataType: 'json',
		data: {
			"mobile": userName,
			"template": "Cotodel Voucher Activity",
		},*/
		url:"/smsOtpWithTemplateMobileAndAmount",
		type: 'POST',
		dataType: 'json',
		data: {
	  			"mobile": employerMobile,
	  			"template": "OTP Number Vouchers Issuance",
	  			"value" : totalNumberCount,
  			},
			
		success: function(data) {
			var obj = data;
			//document.getElementById("loginLoader").style.display = "none";
			if (obj['status'] == true ) {
				// Mask the mobile number (show only last 4 digits)
				var maskedMobile = "XXXXXX" + employerMobile.toString().slice(-4);
				document.getElementById("maskedMobileDisplay").innerText = `OTP code has been sent to your phone ${maskedMobile}. Enter OTP to validate issuance.`;				
								
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
						
					}
				//	document.getElementById('password1').focus();
				}, 1000);
				//$('#loginIdDiv').show('slow');
			}else if (obj['status'] ==false) {

			
			} else {
				
			}
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
}

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



/*function  getLinkedBankDetail(){
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
			
}*/
// Duplicate function with suffix "2"
function getLinkedBankDetail2() {
  const employerid = document.getElementById("employerId").value;

  $.ajax({
    type: "POST",
    url: "/getErupiLinkBankAccountDetail",
    data: { orgId: employerid },
    success: function (data) {
      const select = document.getElementById("banklist2");
      select.innerHTML = "";

      const parsed = JSON.parse(data).data;
      console.log("getLinkedBankDetail2()=", parsed);

      const dropdownList = document.getElementById("dropdownList2");
      dropdownList.innerHTML = "";

      const bankKeys = Object.keys(parsed);

      if (bankKeys.length === 1) {
        const onlyBank = parsed[bankKeys[0]];
        const masked = "XXXX" + onlyBank.acNumber.slice(-4);

        const option = document.createElement("option");
        option.value = onlyBank.acNumber;
        option.text =
          onlyBank.accountSeltWallet === "Wallet"
            ? "cotoBalance"
            : `${onlyBank.bankName} | ${masked}`;
        select.appendChild(option);

        const selectedHTML =
          onlyBank.accountSeltWallet === "Wallet"
            ? `<div class="dropdown-cotowallet" style="font-family: 'Instrument Sans', sans-serif;">
                <span style="font-weight: 500; color: #4A4E69;">coto</span>
                <span style="font-weight: 700; color: #2F945A;">Balance</span>
             </div>
             <span class="dropdown-mask">${masked}</span>
             <i class="bi bi-caret-down-fill dropdown-arrow"></i>`
            : `<div class="dropdown-item">
                <div class="dropdown-bank-info">
                  <img src="data:image/png;base64,${onlyBank.bankIcon}" alt="logo" style="width: 24px; height: 24px;">
                  <span>${onlyBank.bankName}</span>
                </div>
                <span class="dropdown-mask">${masked}</span>
                <i class="bi bi-caret-down-fill dropdown-arrow"></i>
             </div>`;

        document.getElementById("selectedBank2").innerHTML = selectedHTML;
        select.value = onlyBank.acNumber;
        getBankDetailByBankAccountNumber2();
        showLinkedAccAmount2(onlyBank.acNumber, onlyBank.accountSeltWallet);
      } else {
        let walletBank = null;

        bankKeys.forEach((key) => {
          const bank = parsed[key];
          const masked = "XXXX" + bank.acNumber.slice(-4);

          const option = document.createElement("option");
          option.value = bank.acNumber;
          option.text =
            bank.accountSeltWallet === "Wallet"
              ? "cotoBalance"
              : `${bank.bankName} | ${masked}`;
          select.appendChild(option);

          const div = document.createElement("div");
          div.className = "dropdown-item";

          if (bank.accountSeltWallet === "Wallet") {
            div.innerHTML = `
              <div class="dropdown-cotowallet" style="font-family: 'Instrument Sans', sans-serif; font-size: 21px;">
                <span style="font-weight: 500; color: #4A4E69;">coto</span>
                <span style="font-weight: 700; color: #2F945A;">Balance</span>
              </div>
              <span class="dropdown-mask">${masked}</span>
            `;
          } else {
            div.innerHTML = `
              <div class="dropdown-bank-info">
                <img src="data:image/png;base64,${bank.bankIcon}" alt="logo" style="width: 24px; height: 24px;">
                <span>${bank.bankName}</span>
              </div>
              <span class="dropdown-mask">${masked}</span>
            `;
          }

          div.onclick = function () {
            const selectedHTML =
              div.innerHTML +
              '<i class="bi bi-caret-down-fill dropdown-arrow"></i>';
            document.getElementById("selectedBank2").innerHTML = selectedHTML;
            select.value = bank.acNumber;
            document.getElementById("dropdownList2").style.display = "none";
            getBankDetailByBankAccountNumber2();
            showLinkedAccAmount2(bank.acNumber, bank.accountSeltWallet);
          };

          dropdownList.appendChild(div);

          if (bank.accountSeltWallet === "Wallet" && walletBank === null) {
            walletBank = { bank, masked, div };
          }
        });

        if (walletBank) {
          const selectedHTML =
            walletBank.div.innerHTML +
            '<i class="bi bi-caret-down-fill dropdown-arrow"></i>';
          document.getElementById("selectedBank2").innerHTML = selectedHTML;
          select.value = walletBank.bank.acNumber;
          getBankDetailByBankAccountNumber2();
          showLinkedAccAmount2(
            walletBank.bank.acNumber,
            walletBank.bank.accountSeltWallet
          );
        }
      }
    },
    error: function (e) {
      alert("Error: " + e);
    },
  });
}

// Dropdown toggle for second section
document.getElementById("selectedBank2").addEventListener("click", function () {
  const list = document.getElementById("dropdownList2");
  list.style.display = list.style.display === "block" ? "none" : "block";
});

document.addEventListener("click", function (e) {
  const dropdown = document.getElementById("customDropdown2");
  if (dropdown && !dropdown.contains(e.target)) {
    document.getElementById("dropdownList2").style.display = "none";
  }
});

function getLinkedBankDetail() {
  const employerid = document.getElementById("employerId").value;

  $.ajax({
    type: "POST",
    url: "/getErupiLinkBankAccountDetail",
    data: { orgId: employerid },
    success: function (data) {
      const select = document.getElementById("banklist");
      select.innerHTML = "";

      const parsed = JSON.parse(data).data;
      console.log("getLinkedBankDetail()=", parsed);

      const dropdownList = document.getElementById("dropdownList");
      dropdownList.innerHTML = "";

      const bankKeys = Object.keys(parsed);

      if (bankKeys.length === 1) {
        const onlyBank = parsed[bankKeys[0]];
        const masked = "XXXX" + onlyBank.acNumber.slice(-4);

        const option = document.createElement("option");
        option.value = onlyBank.acNumber;
        option.text = onlyBank.accountSeltWallet === "Wallet"
          ? "cotoBalance"
          : `${onlyBank.bankName} | ${masked}`;
        select.appendChild(option);

        const selectedHTML = onlyBank.accountSeltWallet === "Wallet"
          ? `<div class="dropdown-cotowallet" style="font-family: 'Instrument Sans', sans-serif;">
                <span style="font-weight: 500; color: #4A4E69;">coto</span>
                <span style="font-weight: 700; color: #2F945A;">Balance</span>
             </div>
             <span class="dropdown-mask">${masked}</span>
             <i class="bi bi-caret-down-fill dropdown-arrow"></i>`
          : `<div class="dropdown-item">
                <div class="dropdown-bank-info">
                  <img src="data:image/png;base64,${onlyBank.bankIcon}" alt="logo" style="width: 24px; height: 24px;">
                  <span>${onlyBank.bankName}</span>
                </div>
                <span class="dropdown-mask">${masked}</span>
                <i class="bi bi-caret-down-fill dropdown-arrow"></i>
             </div>`;

        document.getElementById("selectedBank").innerHTML = selectedHTML;
        select.value = onlyBank.acNumber;
        getBankDetailByBankAccountNumber();
        showLinkedAccAmount(onlyBank.acNumber, onlyBank.accountSeltWallet);
      } else {
        let walletBank = null;

        bankKeys.forEach((key) => {
          const bank = parsed[key];
          const masked = "XXXX" + bank.acNumber.slice(-4);

          const option = document.createElement("option");
          option.value = bank.acNumber;
          option.text = bank.accountSeltWallet === "Wallet"
            ? "cotoBalance"
            : `${bank.bankName} | ${masked}`;
          select.appendChild(option);

          const div = document.createElement("div");
          div.className = "dropdown-item";

          if (bank.accountSeltWallet === "Wallet") {
            div.innerHTML = `
              <div class="dropdown-cotowallet" style="font-family: 'Instrument Sans', sans-serif; font-size: 21px;">
                <span style="font-weight: 500; color: #4A4E69;">coto</span>
                <span style="font-weight: 700; color: #2F945A;">Balance</span>
              </div>
              <span class="dropdown-mask">${masked}</span>
            `;
          } else {
            div.innerHTML = `
              <div class="dropdown-bank-info">
                <img src="data:image/png;base64,${bank.bankIcon}" alt="logo" style="width: 24px; height: 24px;">
                <span>${bank.bankName}</span>
              </div>
              <span class="dropdown-mask">${masked}</span>
            `;
          }

          div.onclick = function () {
            const selectedHTML = div.innerHTML + '<i class="bi bi-caret-down-fill dropdown-arrow"></i>';
            document.getElementById("selectedBank").innerHTML = selectedHTML;
            select.value = bank.acNumber;
            document.getElementById("dropdownList").style.display = "none";
            getBankDetailByBankAccountNumber();
            showLinkedAccAmount(bank.acNumber, bank.accountSeltWallet);
          };

          dropdownList.appendChild(div);

          if (bank.accountSeltWallet === "Wallet" && walletBank === null) {
            walletBank = { bank, masked, div };
          }
        });

        if (walletBank) {
          const selectedHTML = walletBank.div.innerHTML + '<i class="bi bi-caret-down-fill dropdown-arrow"></i>';
          document.getElementById("selectedBank").innerHTML = selectedHTML;
          select.value = walletBank.bank.acNumber;
          getBankDetailByBankAccountNumber();
          showLinkedAccAmount(walletBank.bank.acNumber, walletBank.bank.accountSeltWallet);
        }
      }
    },
    error: function (e) {
      alert("Error: " + e);
    },
  });
}

document.getElementById("selectedBank").addEventListener("click", function () {
  const list = document.getElementById("dropdownList");
  list.style.display = list.style.display === "block" ? "none" : "block";
});

document.addEventListener("click", function (e) {
  const dropdown = document.getElementById("customDropdown");
  if (!dropdown.contains(e.target)) {
    document.getElementById("dropdownList").style.display = "none";
  }
});

function  getVoucherDetailByBoucherCode(){
 	var voucherCode = localStorage.getItem('voucherCode');
 	$.ajax({
	type: "POST",
	url:"/getmccMasterListByPurposeCode",
       data: {
			"purposeCode": voucherCode
      		 },
      		  beforeSend : function(xhr) {
			//xhr.setRequestHeader(header, token);
			},
         
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

			//document.getElementById("orgId").value=data1.data.orgId; 
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
function  getBankDetailByBankAccountNumber2(){
 	var accountNumber = document.getElementById("banklist2").value;
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


/*function getVoucherSummaryList(){
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
*/
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
  			//url:"/verifyOTP",
			url:"/otpVerifyWithTemplate",
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
					
					$('#otpModal1').fadeOut();
					issueBulkVoucher();
  					$('#errorOtp').hide('slow');
  				}else if (obj['status'] == false) {
					document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";

					document.getElementById("authenticate").disabled = false;
					
					document.getElementById("password1").value="";
				  	 document.getElementById("password2").value="";
				  	 document.getElementById("password3").value="";
				  	 document.getElementById("password4").value="";
				  	 document.getElementById("password5").value="";
				  	 document.getElementById("password6").value="";
				} else {
  				
  				}
  			},
  			error: function(e) {
  				alert('Error: ' + e);
  			}
  		});
  }


 async function  issueBulkVoucher(){
  	
  	document.getElementById("password1").value="";
  	document.getElementById("password2").value="";
    document.getElementById("password3").value="";
  	document.getElementById("password4").value="";
  	document.getElementById("password5").value="";
    document.getElementById("password6").value="";    
  	//var banklist = document.getElementById("banklist").value;
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
	var employerId = document.getElementById("employerId").value;
	var acNumber = document.getElementById("acNumber").value;
	document.getElementById("signinLoader").style.display="flex";
	
	const clientKey = "client-secret-key"; // Extra security measure
    const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

    // Concatenate data (must match backend)

	const dataString = employerId+voucherCode+purposeCode+payerva+"01"+""+
	bankCode+acNumber+voucherCode+voucherName+merchentid+submurchentid+createdby+clientKey+secretKey;

    // Generate SHA-256 hash
    const encoder = new TextEncoder();
    const data = encoder.encode(dataString);
    const hashBuffer = await crypto.subtle.digest("SHA-256", data);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
   	$.ajax({
  	type: "POST",
  	url:"/issueBulkVoucher",
	data: {
			"orgId":employerId,
			"purposeCode":voucherCode,
			"mcc":purposeCode,
			"payerVA":payerva,
			"mandateType":"01",
			"type":"",
			"bankcode":bankCode,
			"accountNumber":acNumber,
			"voucherCode":voucherCode,
			"voucherDesc":voucherName,
			"merchantId":merchentid,
			"subMerchantId":submurchentid,
			"createdby":createdby,
			"mccDescription":purposeName,
			"arrayofid":firstColumnData,
			"hash":hashHex,
			"clientKey":clientKey
			 },  	
        		  beforeSend : function(xhr) {
  			//xhr.setRequestHeader(header, token);
  			},
             success: function(data){
             newData = data;
             var data1 = jQuery.parseJSON( newData );
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
				 $('#otmsgdiv').delay(10000).fadeOut(800);
				 document.getElementById('submitButton').disabled=false;
				 document.getElementById('authenticate').disabled=false;
				 document.getElementById("selectvouchers-wrap-issue").style.display="none";
				 document.getElementById("upi-voucher-wrapThree").style.display="block";
				 const tableBody = document.getElementById("successFailVoucherTable").getElementsByTagName("tbody")[0];
				 data1.data.forEach((item) => {
					const row = tableBody.insertRow();
					   row.insertCell().textContent = item.name;
					   row.insertCell().textContent = item.mobile;
					   row.insertCell().textContent = item.mccDescription;
					   row.insertCell().textContent = item.redemtionType;
					   // Format amount with ₹, commas, and 2 decimal places
					   const formattedAmount = "₹" + Number(item.amount).toLocaleString("en-IN", {
					       minimumFractionDigits: 2,
					       maximumFractionDigits: 2
					   });
					   row.insertCell().textContent = formattedAmount;
					   row.insertCell().textContent = formatDateToDDMMYY(item.startDate);
					   row.insertCell().textContent = formatDateToDDMMYY(item.expDate);
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

  
  /*function deleteBeneficiay(value){
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
  */
 // this function updates the voucher value and voucher amount on deleting a specific row
  function deleteBeneficiay(value) {
      document.getElementById("signinLoader").style.display = "flex";

      $.ajax({
          type: "POST",
          url: "/beneficiaryDeleteFromVoucherList",
          data: { "id": value },
          success: function(data) {
              document.getElementById("signinLoader").style.display = "none";

              var data1 = jQuery.parseJSON(data);
              var table = $('#issueVoucherTable').DataTable();

              // Find the row with matching id
              var row = table.rows().nodes().to$().filter(function () {
                  return $(this).find('button#btnDelete').val() == value;
              });

              if (row.length > 0) {
                  // Get the amount from the row (5th column index = 4)
                  var amountText = row.find("td:eq(4)").text().trim();
                  var amount = Number(amountText.replace(/[^0-9.-]/g, ""));

                  // Subtract from total amount
                  var totalEl = document.getElementById("totalValueCount");
                  var currentTotal = Number(totalEl.innerText.replace(/[^0-9.-]/g, ""));
                  var newTotal = currentTotal - amount;
                  totalEl.innerText = newTotal.toLocaleString('en-IN', {
                      minimumFractionDigits: 2,
                      maximumFractionDigits: 2
                  });

                  // Update total number count
                  var countEl = document.getElementById("totalNumberCount");
                  var currentCount = Number(countEl.innerText.replace(/[^0-9]/g, ""));
                  var newCount = currentCount > 0 ? currentCount - 1 : 0;
                  countEl.innerText = newCount;

                  // ✅ Copy values into secondary IDs
                  document.getElementById("totalValue2Count").innerText = totalEl.innerText;
                  document.getElementById("totalNumber2Count").innerText = countEl.innerText;

                  // Remove row from DataTable
                  table.row(row).remove().draw();
              }
          },
          error: function(e) {
              alert('Error: ' + e);
          }
      });
  }



  
  
  
  async function getEmployeeOnboarding() {
      //const employeeId = document.getElementById("employeeId").value;
  	const employeeId="";
      const employerId = document.getElementById("employerId").value;

      const clientKey = "client-secret-key";
      const secretKey = "0123456789012345";
      const dataString = employerId + employeeId + clientKey + secretKey;

      const encoder = new TextEncoder();
      const data = encoder.encode(dataString);
      const hashBuffer = await crypto.subtle.digest("SHA-256", data);
      const hashArray = Array.from(new Uint8Array(hashBuffer));
      const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');

      const requestData = {
        employerId,
        employeeId,
        key: clientKey,
        hash: hashHex
      };

      try {
        const response = await fetch("/getEmployeeOnboarding", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(requestData)
        });

        const jsonText = await response.text();
        const data1 = JSON.parse(jsonText);
        const filteredData = (data1.data || []).filter(emp => emp.status === 1);

        const nameMobileOnly = filteredData.map(emp => ({
          name: emp.name,
          mobile: emp.mobile
        }));

        // ✅ Set to hidden input
        document.getElementById("nameMobileOnlyHidden").value = JSON.stringify(nameMobileOnly);
        return true;

      } catch (error) {
        alert("Error fetching employee data");
        return false;
      }
    }

    		  

    async function downloadModifiedExcel() {
      const isSuccess = await getEmployeeOnboarding();

      if (!isSuccess) return;

      const hiddenValue = document.getElementById("nameMobileOnlyHidden").value;
      const payload = hiddenValue ? JSON.parse(hiddenValue) : [];

      if (!payload.length) {
        alert("No data to export.");
        return;
      }

      fetch('/generateVoucherExcelTemp', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      })
        .then(res => res.blob())
        .then(blob => {
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = "ModifiedVoucherExcelTemp.xlsx";
          document.body.appendChild(a);
          a.click();
          a.remove();
        })
        .catch(() => alert("Excel download failed."));
    }