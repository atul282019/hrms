
function getOTP() {
	var regMobile = /^[6-9]\d{9}$/gi;
	var userName = document.getElementById("mob").value;
	if (userName == "") {
		document.getElementById("mob").focus();
		document.getElementById("mobError").innerHTML="Please Enter Mobile Number";
		return false;
	}
	else if(userName.length < 10){
			document.getElementById("mobError").innerHTML="Please Enter Valid Mobile Number";
			document.getElementById("mob").focus();
			return false;
	}
	
	else if(!userName.match(regMobile)){
			document.getElementById("mobError").innerHTML="Please Enter Valid Mobile Number";
			document.getElementById("mob").focus();
			return false;
	}
	document.getElementById("optBtn").disabled = true;
	document.getElementById("mobError").innerHTML="";
	
	document.getElementById("loginLoader").style.display = "flex";
	$.ajax({
		type: "POST",
		url: "" + $('#ctx').attr('content') + "/smsOtpSender",
		dataType: 'json',
		data: {
			"mob": userName,
		},
		success: function(data) {
			var obj = data;
			document.getElementById("loginLoader").style.display = "none";
			if (obj['status'] == "SUCCESS") {
				$('#errorOtp').hide('slow');
				$('#loginIdDiv').hide('slow');
				var timeleft = "60";
				var downloadTimer = setInterval(function() {
					document.getElementById("countdown").innerHTML = "00."+timeleft;
					timeleft -= 1;
					document.getElementById("optBtn").style.display = "none";
					document.getElementById("orderId").value= obj['orderId'];
					document.getElementById("verifyotpdiv").style.display = "block";
					if (timeleft <= 0) {
						clearInterval(downloadTimer);
						document.getElementById("optBtn").disabled = false;
						document.getElementById("countdown").innerHTML = " ";
						document.getElementById("optBtn").style.display = "none";
						document.getElementById("verifyotpdiv").style.display = "none";
						$('#loginIdDiv').hide('slow');
					}
					document.getElementById('password').focus();
				}, 1000);
				$('#loginIdDiv').show('slow');
			}else if (obj['status'] == "FAILURE") {

				$('#errorOtp').html(obj['msg']);
				$('#successmessage').hide('slow');
				document.getElementById("optBtn").style.display = "block";
				document.getElementById("optBtn").disabled = false;
				$('#successerror').hide('slow');
			} else {
				$('#errorOtp').html("OTP Failed");
				document.getElementById("optBtn").disabled = false;
				$('#successmessage').hide('slow');
				document.getElementById("optBtn").style.display = "block";
				$('#successerror').hide('slow');
			}
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
}

function resendOTP() {
	var regMobile = /^[6-9]\d{9}$/gi;
	var userName = document.getElementById("mob").value;
	var orderId = document.getElementById("orderId").value;
	
	if (userName == "") {
		document.getElementById("mob").focus();
		document.getElementById("mobError").innerHTML="Please Enter Mobile Number";
		return false;
	}
	else if(userName.length < 10){
			document.getElementById("mobError").innerHTML="Please Enter Valid Mobile Number";
			document.getElementById("mob").focus();
			return false;
	}
	
	else if(!userName.match(regMobile)){
			document.getElementById("mobError").innerHTML="Please Enter Valid Mobile Number";
			document.getElementById("mob").focus();
			return false;
	}
	document.getElementById("optBtn").disabled = true;
	document.getElementById("mobError").innerHTML="";
	
	document.getElementById("loginLoader").style.display = "flex";
	$.ajax({
		type: "POST",
		url: "" + $('#ctx').attr('content') + "/smsOtpResender",
		dataType: 'json',
		data: {
			"mob": userName,
			"orderId":orderId
		},
		success: function(data) {
			var obj = data;
			document.getElementById("loginLoader").style.display = "none";
			if (obj['status'] == "SUCCESS") {
				$('#errorOtp').hide('slow');
				$('#loginIdDiv').hide('slow');
				var timeleft = "60";
				var downloadTimer = setInterval(function() {
					document.getElementById("countdown").innerHTML = "00."+timeleft;
					timeleft -= 1;
					document.getElementById("optBtn").style.display = "none";
					document.getElementById("orderId").value= obj['orderId'];
					document.getElementById("verifyotpdiv").style.display = "block";
					if (timeleft <= 0) {
						clearInterval(downloadTimer);
						document.getElementById("optBtn").disabled = false;
						document.getElementById("countdown").innerHTML = " ";
						document.getElementById("optBtn").style.display = "none";
						document.getElementById("verifyotpdiv").style.display = "none";
						
						$('#loginIdDiv').hide('slow');
					}
					document.getElementById('password').focus();
				}, 1000);
				$('#loginIdDiv').show('slow');
			}else if (obj['status'] == "FAILURE") {

				$('#errorOtp').html(obj['msg']);
				$('#successmessage').hide('slow');
				document.getElementById("optBtn").style.display = "block";
				document.getElementById("optBtn").disabled = false;
				$('#successerror').hide('slow');
			} else {
				$('#errorOtp').html("OTP Failed");
				document.getElementById("optBtn").disabled = false;
				$('#successmessage').hide('slow');
				document.getElementById("optBtn").style.display = "block";
				$('#successerror').hide('slow');
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
            validatePan();
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


  function focusNextOtp(currentInput) {
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

function focusBackOtp(){
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


function validatePan(){
	var pan1 = document.getElementById("pan1").value;
	var pan2 = document.getElementById("pan2").value;
	var pan3 = document.getElementById("pan3").value;
	var pan4 = document.getElementById("pan4").value;
	var pan5 = document.getElementById("pan5").value;
	var pan6 = document.getElementById("pan6").value;
	var pan7 = document.getElementById("pan7").value;
	var pan8 = document.getElementById("pan8").value;
	var pan9 = document.getElementById("pan9").value;
	var pan10 = document.getElementById("pan10").value;
	var fulpancard  =(pan1+pan2+pan3+pan4+pan5+pan6+pan7+pan8+pan9+pan10).toUpperCase();
	
	var panNoFormat = new RegExp('[A-Z]{5}[0-9]{4}[A-Z]{1}');
	
	 if (panNoFormat.test(fulpancard)) {    
         
          document.getElementById("panError").innerHTML="";
               
    } else {    
        document.getElementById("panError").innerHTML="Please Enter Valid Pan Number";
		//document.getElementById("pan").focus();	
		return false;     
    }  
}