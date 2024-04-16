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


document.getElementById("bulksubmit").onclick = function() {
    //disable
    this.disabled = true;

 	 var regName = /^[a-zA-Z\s]*$/;
	 var onlySpace = /^$|.*\S+.*/;	 
	
	
	document.getElementById("employerId").value;
	var employerId = document.getElementById("employerId").value;
	var formData = new FormData(empdetailForm);
	formData.append("employerId",employerId);
	document.forms[0].action = "" + $('#ctx').attr('content') + "/saveBulkFile";
	document.forms[0].method = "post";
	document.forms[0].submit();
	
    //do some validation stuff
   /* document.getElementById("signinLoader").style.display="flex";
    $.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/saveBulkFile",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			console.log(data1);
			console.log(data1.correct);			
			console.log(data1.incorrect);
			if(data1.status=="SUCCESS"){
				// document.getElementById("employeeId").value=data1.data.id;
				 document.getElementById("otsuccmsg").innerHTML="File Process Successfully";
				 document.getElementById("otmsgdiv").style.display="block";
				 document.getElementById("up").value="";
				 document.getElementById('up').value = '';
				  document.getElementById("empdetailForm").reset();
				 $("#up").val('');
				  document.getElementById("bulksubmit").disabled = false;
				 this.disabled = false;
				 $('#otmsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status=="FAILURE"){
				//getEmployeeDetail();
				 document.getElementById("otfailmsg").innerHTML=data1.message;
				 document.getElementById("otfailmsgDiv").style.display="block";
				 $('#otfailmsgDiv').delay(5000).fadeOut(400);
				  this.disabled = false;
			}else{
				 document.getElementById("otmsgdiv").style.display="none";
				 document.getElementById("otfailmsgDiv").style.display="none";
				 //document.getElementById("FailedError").innerHTML="API Gateway not respond. Please try again.";
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });	*/
}
