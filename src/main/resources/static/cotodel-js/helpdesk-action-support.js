function convertImageToBase64() {
           const fileInput = document.getElementById('fileInput');
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


	async function replyTicket(){
		var ticketId = document.getElementById("ticketId").value;
		var fileInput = document.getElementById("fileInput").value;
		var base64file = document.getElementById("base64Output").value;
		var name =  document.getElementById("createdBy").value;
		var createdby =  document.getElementById("employerMobile").value;
		var orgId = document.getElementById("employerId").value;
		var issueDesc = document.getElementById("replyText").value;
		
		var dropdown = document.getElementById("ticketStatus");
		var respTicketStatus = dropdown.value;
		var respTicketStatusDesc = dropdown.options[dropdown.selectedIndex].text;
		
		const clientKey = "client-secret-key"; // Extra security measure
		const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

		const dataString = orgId+issueDesc+createdby+base64file+clientKey+secretKey;

		const encoder = new TextEncoder();
		const data = encoder.encode(dataString);
		const hashBuffer = await crypto.subtle.digest("SHA-256", data);
		const hashArray = Array.from(new Uint8Array(hashBuffer));
		const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
		
	
		if(issueDesc =="" || issueDesc == null){
				 document.getElementById("fileInputError").innerHTML="Please Enter Error Description";
				 return false;
			}
			else{
				document.getElementById("fileInputError").innerHTML="";
			}
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
		     url:"/replyTicket",
			 dataType: 'json',   
		      data: {
				        "id":ticketId,
						"orgId":orgId,
						"issueDesc":issueDesc,
						"respTicketStatus":respTicketStatus,
						"respTicketStatusDesc":respTicketStatusDesc,
						"ticketImg":base64file,
						"createdby":createdby,
						"name":name,
						"clientKey":clientKey,
					    "hash":hashHex
				 },  		 
		        success:function(data){
				document.getElementById("signinLoader").style.display="none";
		        var data1 = data.data;
			    var data2 = jQuery.parseJSON(data1);
			    console.log("data2",data2);
				if(data2.status==true){
					document.getElementById("replyText").value=""; 
					document.getElementById("fileName").innerHTML=""; 
					getTicketTransactionHistoryById();
					
				}else if(data.status==false){
					$('#AddVehicleModal').modal('show');
				}
		     },
		     error: function(e){
		         alert('Error: ' + e);
		     }
		});	
		
	}

async function getSupportTicketActionList() {
  var orgId = document.getElementById("employerId").value;
  document.getElementById("signinLoader").style.display="flex";
  $.ajax({
    type: "GET",
    url: "/getTicketListForAction",
    data: {
      orgId: orgId
    },
    beforeSend: function (xhr) {},
    success: function (data) {
	  document.getElementById("signinLoader").style.display="none";
      const data1 = jQuery.parseJSON(data);
   		var data2 = data1.data;
      $('#TicketSupportActionTable').DataTable({
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
		  { mData: "name" },
		  { mData: "organizationName" },
		  { mData: "mobile" },
		  { mData: "ticketNo" },
		  { mData: "issueType" },
		 
		  { mData: "subject" },
		  {
		    mData: "status",
		    render: function (data) {
		      return data === 0 ? "Submitted" : "Closed";
		    }
		  },
		  { mData: "creationDate"},
		  {
		    mData: "status",
		    render: function (data, type, row) {
		      const ticketId = row.id; // or use row.id if that's your identifier
		      return `<a href="/helpdeskadmin?ticketId=${ticketId}"> 
		                <img src="img/edit-pencil-blue.png" alt="Edit Pencil" 
		                     style="height:24px; width:24px; margin-right:4px;"> 
		              </a>`;
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


async function getTicketDetailById() {
  var orgId = document.getElementById("employerId").value;
  var ticketId = document.getElementById("ticketId").value;
  document.getElementById("signinLoader").style.display="flex";
  $.ajax({
    type: "GET",
    url: "/getTicketDetailByTicketId",
    data: {
      orgId: orgId,
	  "id":ticketId
    },
    beforeSend: function (xhr) {},
    success: function (data) {
	  document.getElementById("signinLoader").style.display="none";
      const data1 = jQuery.parseJSON(data);
   	  var data2 = data1.data;
	  const rawDate = new Date(data2.creationDate);

	  // Format: 'Wed, 09/07'
	  const options = { weekday: 'short' };
	  const weekday = rawDate.toLocaleDateString('en-US', options);
	  const day = String(rawDate.getDate()).padStart(2, '0');
	  const month = String(rawDate.getMonth() + 1).padStart(2, '0');

	  document.getElementById("tickeNo").innerHTML=data2.ticketNo;
	  document.getElementById("senderName").innerHTML=data2.name;
	  document.getElementById("companyName").innerHTML=data2.organizationName;
	  document.getElementById("dateTime").innerHTML=data2.creationDate;
	  document.getElementById("status").innerHTML=data2.statusDesc;
	  document.getElementById("subject").innerHTML=data2.subject;
	  document.getElementById("submittedBy").innerHTML=data2.name;
	  document.getElementById("submittedDate").innerHTML=`${weekday}, ${day}/${month}`;
	  document.getElementById("submittedDate1").innerHTML=`${weekday}, ${day}/${month}`;
	  document.getElementById("ticketDetail").innerHTML=data2.subject;
	 document.getElementById("imgTicket").src = "data:image/png;base64," + data2.ticketImg;
	 document.getElementById("issueDescDetail").innerHTML=data2.issueDesc;
	 
    },
    error: function (e) {
      alert('Failed to fetch JSON data' + e);
    }
  });
}


async function getTicketTransactionHistoryById() {
  var orgId = document.getElementById("employerId").value;
  var ticketId = document.getElementById("ticketId").value;
  document.getElementById("signinLoader").style.display="flex";
  $.ajax({
    type: "GET",
    url: "/ticketReplyHistory",
    data: {
      orgId: orgId,
	  "id":ticketId
    },
    beforeSend: function (xhr) {},
	success: function (data) {
		document.getElementById("signinLoader").style.display="none";
		const data1 = jQuery.parseJSON(data);
		var data2 = data1.data;
	    if (data1.status && Array.isArray(data1.data)) {
	      const container = $('#ticketMessageContainer');
	      container.empty(); // Clear previous content
	
	      data2.forEach(item => {
	        const issueDesc = item.issueDesc || 'No description';
	        const senderName = item.name || item.createdby || 'Unknown';
	        const creationDate = new Date(item.creationdate);
	        const formattedDate = creationDate.toLocaleDateString('en-GB', {
	          weekday: 'short',
	          day: '2-digit',
	          month: '2-digit'
	        }).replace(',', '');
	
	        const messageHTML = `
	          <div class="admin-ticket-message admin-ticket-message-white">
	            <p class="mb-1">${issueDesc}</p>
	            <div class="admin-ticket-message-footer">
	              <span style="margin-right: 12px;">${senderName}</span>
	              <span>${formattedDate}</span>
	            </div>
	          
	        `;
	        container.append(messageHTML);
	      });
	    } else {
	      $('#ticketMessageContainer').html('<p>No messages found.</p>');
	    }
	  },
	  error: function () {
	    $('#ticketMessageContainer').html('<p>Error loading messages.</p>');
	  }
  });
}
