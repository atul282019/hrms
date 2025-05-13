function getOTP() {
	console.log("get otp called");
    var regMobile = /^[6-9]\d{9}$/gi;
    var userName = document.getElementById("mobile").value;

    if (userName == "") {
        document.getElementById("mobile").focus();
        document.getElementById("mobError").innerHTML = "Please Enter Mobile Number";
        return false;
    } else if (userName.length < 10) {
        document.getElementById("mobError").innerHTML = "Please Enter Valid Mobile Number";
        document.getElementById("mob").focus();
        return false;
    } else if (!userName.match(regMobile)) {
        document.getElementById("mobError").innerHTML = "Please Enter Valid Mobile Number";
        document.getElementById("mob").focus();
        return false;
    }

    document.getElementById("optBtn").disabled = true;
    document.getElementById("mobError").innerHTML = "";
    document.getElementById("loginLoader").style.display = "flex";
 
    $.ajax({
        type: "POST",
        url: "/smsOtpSender",
        dataType: 'json',
        data: { "mobile": userName },
        success: function (data) {
            var obj = data;
            document.getElementById("loginLoader").style.display = "none";

            if (obj.status === true) {
				

				// Mask the mobile number (show only last 4 digits)
				var maskedMobile = "XXXXXX" + userName.toString().slice(-4);
				document.getElementById("maskedMobileDisplay").innerHTML = `Enter the OTP send to ${maskedMobile}`;

                $('#errorOtp').hide('slow');
                $('#loginIdDiv').hide('slow');
				var timeleft = 60; // 3 minutes in seconds
				   var downloadTimer = setInterval(function () {
				       var minutes = Math.floor(timeleft / 60); // Calculate minutes
				       var seconds = timeleft % 60; // Calculate remaining seconds

				       // Update countdown display in "MM:SS" format
				       document.getElementById("countdown").innerHTML = 
				           (minutes < 10 ? "0" + minutes : minutes) + ":" + 
				           (seconds < 10 ? "0" + seconds : seconds);

				       timeleft -= 1;
					   localStorage.setItem("otpExpiryTime", Date.now() + seconds * 1000);   
					   document.getElementById("optBtn").style.display = "none";
					   document.getElementById("orderId").value = obj['orderId'];
					   document.getElementById("verifyotpdiv").style.display = "block";

				       if (timeleft < 0) {
						clearInterval(downloadTimer);
                        document.getElementById("countdown").innerHTML = " ";
						document.getElementById("resendOTPText").style.display = "block";
                     
                        document.getElementById("optBtn").disabled = true;
						document.getElementById("optBtn").style.display = "none";
                        document.getElementById("verifyotpdiv").style.display = "none";
				       }
				   }, 1000); 

                $('#loginIdDiv').show('slow');
            } else if (obj.status===false) {
                $('#errorOtp').html(obj.message);
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
        error: function (e) {
            alert('Error: ' + e);
        }
    });
}

function resendOTP() {
	
    console.log("resend otp clicked");
	document.getElementById("password1").value = "";
	document.getElementById("password2").value = "";
	document.getElementById("password3").value = "";
	document.getElementById("password4").value = "";
	document.getElementById("password5").value = "";
	document.getElementById("password6").value = "";
    var regMobile = /^[6-9]\d{9}$/gi;
    var userName = document.getElementById("mobile").value;
    var orderId = document.getElementById("orderId").value;

    if (userName == "") {
        document.getElementById("mobile").focus();
        document.getElementById("mobError").innerHTML = "Please Enter Mobile Number";
        return false;
    } else if (userName.length < 10) {
        document.getElementById("mobError").innerHTML = "Please Enter Valid Mobile Number";
        document.getElementById("mobile").focus();
        return false;
    } else if (!userName.match(regMobile)) {
        document.getElementById("mobError").innerHTML = "Please Enter Valid Mobile Number";
        document.getElementById("mob").focus();
        return false;
    }

    document.getElementById("optBtn").disabled = true;
    document.getElementById("mobError").innerHTML = "";
    document.getElementById("resendOTPText").style.display = "none"; // Hide Resend OTP link
    document.getElementById("loginLoader").style.display = "flex";
	document.getElementById("verifyotpdiv").style.display = "block";

    $.ajax({
        type: "POST",
        url: "/smsOtpResender",
        dataType: 'json',
        data: { "mobile": userName, "orderId": orderId },
        success: function (data) {
            var obj = data;
            document.getElementById("loginLoader").style.display = "none";

            if (obj['status'] == true) {
				var maskedMobile = "XXXXXX" + userName.toString().slice(-4);
				document.getElementById("maskedMobileDisplay").innerHTML = `Enter the OTP send to ${maskedMobile}`;
                $('#errorOtp').hide('slow');
               // document.getElementById("countdown").innerHTML = "00:60"; // Reset countdown
               // startCountdown(); // Restart the countdown logic
			   var timeleft = 60; // 3 minutes in seconds
			   var downloadTimer = setInterval(function () {
			       var minutes = Math.floor(timeleft / 60); // Calculate minutes
			       var seconds = timeleft % 60; // Calculate remaining seconds

			       // Update countdown display in "MM:SS" format
			       document.getElementById("countdown").innerHTML = 
			           (minutes < 10 ? "0" + minutes : minutes) + ":" + 
			           (seconds < 10 ? "0" + seconds : seconds);

			       timeleft -= 1;
				   localStorage.setItem("otpExpiryTime", Date.now() + seconds * 1000);   
				   document.getElementById("optBtn").style.display = "none";
				   document.getElementById("orderId").value = obj['orderId'];
				   document.getElementById("verifyotpdiv").style.display = "block";

			       if (timeleft < 0) {
					clearInterval(downloadTimer);
                       document.getElementById("countdown").innerHTML = " ";
					document.getElementById("resendOTPText").style.display = "block";
                    
                       document.getElementById("optBtn").disabled = true;
					document.getElementById("optBtn").style.display = "none";
                       document.getElementById("verifyotpdiv").style.display = "none";
			       }
			   }, 1000); 

            } else {
                $('#errorOtp').html(obj['msg']);
                document.getElementById("resendOTPText").style.display = "block";
                document.getElementById("optBtn").disabled = true;
				document.getElementById("verifyotpdiv").style.display = "none";
            }
        },
        error: function (e) {
            alert('Error: ' + e);
        }
    });
}

function startCountdown() {
        var timeleft =60; // 3 minutes in seconds
	    //var timeleft = Math.floor(Math.random() * 60); /// 1 min  random in seconds
        var downloadTimer = setInterval(function () {
        var minutes = Math.floor(timeleft / 60); // Calculate minutes
        var seconds = timeleft % 60; // Calculate remaining seconds

        // Update countdown display in "MM:SS" format
        document.getElementById("countdown").innerHTML = 
            (minutes < 10 ? "0" + minutes : minutes) + ":" + 
            (seconds < 10 ? "0" + seconds : seconds);

        timeleft -= 1;
		localStorage.setItem("otpExpiryTime", Date.now() + seconds * 1000); 
        if (timeleft < 0) {
            clearInterval(downloadTimer);
            document.getElementById("countdown").innerHTML = " ";
            document.getElementById("resendOTPText").style.display = "block";
            document.getElementById("optBtn").disabled = false;
            document.getElementById("verifyotpdiv").style.display = "none";
        }
    }, 1000); // Runs every second
}
function toggleOtpVisibility() {
    // Get the icon element
    const toggleIcon = document.getElementById('toggleOtpVisibility').querySelector('i');

    // Get all OTP input fields (currently only of type password)
    const otpInputs = document.querySelectorAll('.otp-box input[type="password"], .otp-box input[type="text"]');

    // Check if the current icon is "eye" (which means password is hidden)
    const isPasswordVisible = toggleIcon.classList.contains("fa-eye");

    // Loop through each OTP input and toggle between 'password' and 'text' type
    otpInputs.forEach(input => {
        if (isPasswordVisible) {
            input.type = "text";  // Show password (set input type to text)
        } else {
            input.type = "password";  // Hide password (set input type to password)
        }
    });

    // Toggle the icon between 'fa-eye' (eye) and 'fa-eye-slash' (crossed eye)
    if (isPasswordVisible) {
        toggleIcon.classList.remove("fa-eye");
        toggleIcon.classList.add("fa-eye-slash");  // Change to crossed-eye icon (showing password)
    } else {
        toggleIcon.classList.remove("fa-eye-slash");
        toggleIcon.classList.add("fa-eye");  // Change back to eye icon (hiding password)
    }
}




       



/*
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
}function resendOTP() {
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
*/
function submitAction() {
	var x = true;
	var password1 = document.getElementById("password1").value;
	var password2 = document.getElementById("password1").value;
	var password3 = document.getElementById("password1").value;
	var password4 = document.getElementById("password1").value;
	var password5 = document.getElementById("password1").value;
	var password6 = document.getElementById("password1").value;
	var orderId = document.getElementById("orderId").value;
	
	if (document.getElementById("mobile").value == "") {
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
	 if (password3 == "" && password3.length < 1) {
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
		document.forms[0].action = "/userLogin";
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



function refreshCountdown() {
    var expiryTime = parseInt(localStorage.getItem("otpExpiryTime"));
    if (!expiryTime) return;
    var downloadTimer = setInterval(function () {
        var currentTime = Date.now();
        var timeleft = Math.floor((expiryTime - currentTime) / 1000);

        if (timeleft <= 0) {
            clearInterval(downloadTimer);
            localStorage.removeItem("otpExpiryTime");
            document.getElementById("countdown").innerHTML = " ";
            document.getElementById("resendOTPText").style.display = "block";
            document.getElementById("optBtn").disabled = false;
            document.getElementById("verifyotpdiv").style.display = "none";
        } else {
            var minutes = Math.floor(timeleft / 60);
            var seconds = timeleft % 60;
            document.getElementById("countdown").innerHTML =
                (minutes < 10 ? "0" + minutes : minutes) + ":" +
                (seconds < 10 ? "0" + seconds : seconds);
        }
    }, 1000);
}
