
$(document).ready(function () {
           $("#linkedanotheraccount").click(function () {
               $("#linkaccbankform").show();
               $("#linkedbnkacntsctn").hide();

           });
       });
	   
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
            //console.log(newData);
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
	url:"/getErupiLinkDlinkAccountDetail",
       data: {
			"orgId": employerid
      		 },
      		  beforeSend : function(xhr) {
			//xhr.setRequestHeader(header, token);
			},
           success: function(data){
           newData = data;
           //console.log("Linked bank account data : ",newData);
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
             //  editButton.className = "btn btn-primary";
              // editButton.innerText = "Set As Primary";

               // Disable "Edit" button if accstatus is 0 (Link Bank A/C shown)
               if (item.accstatus === 0) {
                   editButton.disabled = true;
                   editButton.classList.add("disabled");
               }
			   if(item.psFlag=="Secondary"){ 				
			   editButton.className = 'btn btn-primary'; 				
			   editButton.innerText = 'Set As Primary'; 			  
			    }  else{ 	
			    editButton.className = 'btn btn-green'; 		       
			    editButton.innerText = item.psFlag; 	
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
					    			 window.location.href = "/roleAccess";
									 $('a[href="#menu22"]').tab('show');
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
    //console.log("Inside link bank account function");
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
                window.location.href = "/roleAccess";
				$('a[href="#menu22"]').tab('show');
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
{
	//console.log("Inside  de-link function");
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
	    			 window.location.href = "/roleAccess";
					 $('a[href="#menu22"]').tab('show');
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
	var onlyNumeric=/^\d+$/;
	
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
		else if(!tid.match(onlyNumeric))
		{
			document.getElementById("tidError").innerHTML="Only numbers are allowed in Tid";
					document.getElementById("tid").focus();
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
	    			 window.location.href = "/roleAccess";
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
{ 
	//console.log("Inside validateBankname");
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

