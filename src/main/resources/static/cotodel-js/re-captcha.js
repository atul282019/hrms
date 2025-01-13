function loadCaptcha() {
    $.ajax({
        type: "GET",
        url: "/getrecaptcha",  // Backend endpoint that provides the image
       // xhrFields: {
        //    responseType: 'blob'  // Expect binary data (image)
        //},
        success: function(data) {
			//var newdata=data;
			//var data1 = jQuery.parseJSON(newdata);
            // Create a URL for the Blob (image data)
           // const url = URL.createObjectURL(blob);

            // Log the generated Blob URL (for debugging)
            //console.log("Generated Blob URL:", url);

            // Set the Blob URL as the source for the image element
           // document.getElementById("captcha_id").src = "data:image/png;base64," + data1.captcha;
			document.getElementById("captcha_id").src = "" + $('#ctx').attr('content') + "/captcha";
			 
        },
        error: function(e) {
            alert('Error loading CAPTCHA: ' + e);
        }
    });
}

function reCaptcha() {
  	document.getElementById("captcha").value = "";
   // var requestUri = $('#ctx').attr('content');
    document.getElementById("captcha_id").src = "" + "/captcha";
    document.getElementById("captcha").focus();
 
  }