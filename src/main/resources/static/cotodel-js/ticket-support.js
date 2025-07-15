function convertImageToBase64() {
           const fileInput = document.getElementById('up');
           const output = document.getElementById('base64Output');
		   const outputFileName = document.getElementById('fileName');
		  
		  
           if (!fileInput.files || fileInput.files.length === 0) {
			 document.getElementById("fileInputError").innerHTML="Please select file";
			 return;
           }
           var filePath = fileInput.value;
               var allowedExtensions =  /(\.jpg|\.jpeg|\.png|)$/i;
			  // var allowedExtensions =  /(\.xlsx)$/i;
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
			
		//	   document.querySelector('.form-group.choose-file .form-control').value = fileInput.files[0].name;
           };
           reader.readAsDataURL(file);
		  // document.getElementById("bulksubmit").disabled=false;
    }

async function saveTicket(){
	
	var fileInput = document.getElementById("up").value;
	var base64file = document.getElementById("base64Output").value;
	var createdby =  document.getElementById("employerMobile").value;
	var orgId = document.getElementById("employerId").value;
	
	var subject = document.getElementById("subject").value;
	var issueDesc = document.getElementById("message").value;

	const clientKey = "client-secret-key"; // Extra security measure
	const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

    // Concatenate data (must match backend)
	const dataString = orgId+subject+issueDesc+createdby+base64file+clientKey+secretKey;

	const encoder = new TextEncoder();
	const data = encoder.encode(dataString);
	const hashBuffer = await crypto.subtle.digest("SHA-256", data);
	const hashArray = Array.from(new Uint8Array(hashBuffer));
	const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
	
	if(subject =="" || subject == null){
		 document.getElementById("subjectError").innerHTML="Please Enter Subject";
		 return false;
	}
	else{
		document.getElementById("subjectError").innerHTML="";
	}
	if(issueDesc =="" || issueDesc == null){
			 document.getElementById("messageError").innerHTML="Please Enter Error Description";
			 return false;
		}
		else{
			document.getElementById("messageError").innerHTML="";
		}
	if(fileInput =="" || fileInput == null){
		 document.getElementById("fileInputError").innerHTML="Please Select File";
		 return false;
	}
	else{
		document.getElementById("fileInputError").innerHTML="";
	}
	$('#Vehicleloading').modal('show');
	$.ajax({
		type: "POST",
	     url:"/submitTicket",
		 dataType: 'json',   
	      data: {
					"orgId":orgId,
					"subject":subject,
					"issueDesc":issueDesc,
					"ticketImg":base64file,
					"createdby":createdby,
					"clientKey":clientKey,
				    "hash":hashHex
			 },  		 
	        success:function(data){
	        var data1 = data.data;
	        console.log("data",data);
		    console.log("data1",data1);
		    var data2 = jQuery.parseJSON(data1);
		    console.log("data2",data2);
			if(data2.status==true){
					     
		         $('#AddVehicleModal').modal('hide');
				 getSupportTicketListList();
				 document.getElementById("subjectError").innerHTML="";
				 document.getElementById("messageError").innerHTML="";
				 document.getElementById("fileInputError").innerHTML="";
		   
			}else if(data.status==false){
				$('#AddVehicleModal').modal('show');
			}
	     },
	     error: function(e){
	         alert('Error: ' + e);
	     }
	});	
	
}


async function getSupportTicketListList() {
  var orgId = document.getElementById("employerId").value;
  document.getElementById("signinLoader").style.display="flex";
  $.ajax({
    type: "GET",
    url: "/getAllTicket",
    data: {
      orgId: orgId
    },
    beforeSend: function (xhr) {},
    success: function (data) {
	  document.getElementById("signinLoader").style.display="none";
      const data1 = jQuery.parseJSON(data);
   		var data2 = data1.data;
      $('#supportTicketList').DataTable({
        destroy: true,
        responsive: true,
        searching: false,
        bInfo: false,
        paging: false,
        lengthChange: true,
        autoWidth: false,
        pagingType: "full_numbers",
        pageLength: 50,
        buttons: ["copy", "csv", "excel", "pdf", "print", "colvis"],
        language: { emptyTable: "No Tickets Found" },
        aaData: data2,
		aoColumns: [
		  { mData: "ticketnumber" },
		  { mData: "subject" },
	
		  { mData: "creationdate",
			render: function (data, type, row) {
			   const dateObj = new Date(data);
			   const day = String(dateObj.getDate()).padStart(2, '0');
			   const month = String(dateObj.getMonth() + 1).padStart(2, '0');
			   const year = dateObj.getFullYear();

			   let hours = dateObj.getHours();
			   const minutes = String(dateObj.getMinutes()).padStart(2, '0');
			   const ampm = hours >= 12 ? 'Pm' : 'Am';
			   hours = hours % 12 || 12;

			   const formattedDate = `${day}-${month}-${year}`;
			   const formattedTime = `${hours}:${minutes} ${ampm}`;

			   return `${formattedDate}<br>${formattedTime}`;
			 }
		  },
		  { mData: "subject" },
		  {
		    mData: "status",
		    render: function (data) {
		      return data === 0 ? "Submitted" : "Closed";
		    }
		  },
		  {
		    mData: "status",
		    render: function (data, type, row) {
		      if (data !== 1) {
		        return '<a href="#" class="view-voucher-details"> <img src="img/history-icon.png" alt="History Icon" style="height:24px; width:24px; margin-right:4px;"> Chat with Customer Success Team </a>';
		      } else {
		        return '<a href="#" class="view-voucher-details"><img src="img/history-icon.png" alt="History Icon" style="height:24px; width:24px; margin-right:4px;">Chat with Customer Success Team </a>';
		      }
		    }
		  }
		]
      });
    },
    error: function (e) {
      alert('Failed to fetch JSON data' + e);
    }
  });
}
