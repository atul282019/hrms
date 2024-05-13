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
		url:"/smsOtpSender",
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
					document.getElementById("countdown").innerHTML = "00:"+timeleft;
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
		url:"/smsOtpResender",
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

function submitAction() {
	var x = true;
	var password1 = document.getElementById("password1").value;
	var password2 = document.getElementById("password1").value;
	var password3 = document.getElementById("password1").value;
	var password4 = document.getElementById("password1").value;
	var password5 = document.getElementById("password1").value;
	var password6 = document.getElementById("password1").value;
	var orderId = document.getElementById("orderId").value;
	
	if (document.getElementById("mob").value == "") {
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
	
		//var formData = new FormData(loginForm);
		//formData.append("loginId", loginId);
	if (x) {
		document.forms[0].action = "" + $('#ctx').attr('content') + "/userLogin";
		document.forms[0].method = "post";
		document.forms[0].submit();
	}
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