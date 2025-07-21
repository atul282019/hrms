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

				 document.getElementById("subject").value="";
				 document.getElementById("message").value="";
				 document.getElementById("subjectError").innerHTML="";
				 document.getElementById("messageError").innerHTML="";
				 document.getElementById("fileInputError").innerHTML="";
				 document.getElementById('bs-canvas-right2').style.right = '-378px';
				 document.getElementById('modal-overlay2').style.display = 'none';
			}else if(data.status==false){
				//$('#AddVehicleModal').modal('show');
				document.getElementById('bs-canvas-right2').style.right = '0';
			    document.getElementById('modal-overlay2').style.display = 'block';
			}
	     },
	     error: function(e){
	         alert('Error: ' + e);
	     }
	});	
	
}
// Updated: getSupportTicketListList
async function getSupportTicketListList() {
  const orgId = document.getElementById("employerId").value;
  document.getElementById("signinLoader").style.display = "flex";

  $.ajax({
    type: "GET",
    url: "/getAllTicket",
    data: { orgId },
    success: function (data) {
      document.getElementById("signinLoader").style.display = "none";
      const data1 = jQuery.parseJSON(data);
      const data2 = data1.data;

      $('#supportTicketList').DataTable({
        destroy: true,
        responsive: true,
        searching: false,
        bInfo: false,
        paging: false,
        lengthChange: true,
        autoWidth: false,
        language: { emptyTable: "No Tickets Found" },
        aaData: data2,
        aoColumns: [
          { mData: "ticketnumber" },
          { mData: "subject" },
		  {
		    mData: "creationdate",
		    render: function (data2) {
		      const dateObj = new Date(data2);
		      const day = String(dateObj.getDate()).padStart(2, '0');
		      const month = String(dateObj.getMonth() + 1).padStart(2, '0');
		      const year = dateObj.getFullYear();

		      let hours = dateObj.getHours();
		      const minutes = String(dateObj.getMinutes()).padStart(2, '0');
		      const ampm = hours >= 12 ? 'PM' : 'AM';
		      hours = hours % 12 || 12;
		      hours = String(hours).padStart(2, '0');

		      return `${day}-${month}-${year}<br>${hours}:${minutes} ${ampm}`;
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
            mData: null,
            render: function (data, type, row) {
              return `<a href="#" class="view-voucher-details" data-id="${row.id}">
                        <img src="img/history-icon.png" alt="History Icon" style="height:24px; width:24px; margin-right:4px;">
                        Chat with Customer Success Team
                      </a>`;
            }
          }
        ]
      });

      // Add click handler after table renders
      $('#supportTicketList tbody').on('click', '.view-voucher-details', function (e) {
        e.preventDefault();
        const ticketId = $(this).data('id');
        openTicketModal(ticketId);
      });
    },
    error: function (e) {
      alert('Failed to fetch JSON data' + e);
    }
  });
}

// New helper to open modal and load messages
function openTicketModal(ticketId) {
  document.getElementById("ticketId").value = ticketId;
  getTicketTransactionHistoryById(ticketId);
  document.getElementById("bs-canvas-right1").style.right = "0";
}

// Refactor this to accept ticketId as param
async function getTicketTransactionHistoryById(ticketId) {
  const orgId = document.getElementById("employerId").value;

  $.ajax({
    type: "GET",
    url: "/ticketReplyHistory",
    data: { orgId, id: ticketId },
    success: function (data) {
      const data1 = jQuery.parseJSON(data);
      const data2 = data1.data;
      const container = $('#chat-box');
      container.empty();

      if (data1.status && Array.isArray(data2)) {
        data2.forEach(item => {
          const responseIssueDesc = item.responseIssueDesc;
          const issueDesc = item.issueDesc;
		 
          const senderName = item.name || item.createdby || 'Unknown';

          const formattedDate = formatDateTime(item.creationdate);
		  const responseDate = formatDateTime(item.responseDate);
          let messageHTML = '';

          if (responseIssueDesc && responseIssueDesc.trim() !== '') {
            messageHTML += `
              <div class="chat-msg admin-msg">
                <p>${responseIssueDesc}</p>
                <span style="margin-right: 12px;">${senderName}</span>
                <span>${formattedDate}</span>
              </div>
              <div class="chat-gap"></div>
            `;
          }

          if (issueDesc && issueDesc.trim() !== '') {
            messageHTML += `
              <div class="chat-msg user-msg">
                <p>${issueDesc}</p>
				<p>${responseDate}</p>
              </div>
              <div class="chat-gap"></div>
            `;
          }
		 	
          if (messageHTML !== '') {
            container.append(messageHTML);
          }
        });
      } else {
        container.html('<p>No messages found.</p>');
      }
    },
    error: function () {
      $('#chat-box').html('<p>Error loading messages.</p>');
    }
  });
}

// Format: Thu 17/07 | 06:08 PM
function formatDateTime(dateStr) {
  const dateObj = new Date(dateStr);

  const weekday = dateObj.toLocaleDateString('en-GB', { weekday: 'short' });
  const day = String(dateObj.getDate()).padStart(2, '0');
  const month = String(dateObj.getMonth() + 1).padStart(2, '0');

  let hours = dateObj.getHours();
  const minutes = String(dateObj.getMinutes()).padStart(2, '0');
  const ampm = hours >= 12 ? 'PM' : 'AM';
  hours = hours % 12 || 12;
  hours = String(hours).padStart(2, '0');

  return `${weekday} ${day}/${month} | ${hours}:${minutes} ${ampm}`;
}

/*async function getTicketTransactionHistoryById1() {
	// this is backup function
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
	        const responseIssueDesc = item.responseIssueDesc || 'No description';
	        const senderName = item.name || item.createdby || 'Unknown';
	        const creationDate = new Date(item.creationdate);
	        const formattedDate = creationDate.toLocaleDateString('en-GB', {
	          weekday: 'short',
	          day: '2-digit',
	          month: '2-digit'
	        }).replace(',', '');
	
	        const messageHTML = `
	          <div class="admin-ticket-message admin-ticket-message-white">
	            <p class="mb-1">${responseIssueDesc}</p>
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
}*/

async function replyTicket(){
	var ticketId = document.getElementById("ticketId").value;
	var fileInput = document.getElementById("up").value;
	var base64file = document.getElementById("base64Output").value;
	var name =  document.getElementById("createdBy").value;
	var createdby =  document.getElementById("employerMobile").value;
	var orgId = document.getElementById("employerId").value;
	var issueDesc = document.getElementById("chatInput").value;
	
	/*var dropdown = document.getElementById("ticketStatus");
	var respTicketStatus = dropdown.value;
	var respTicketStatusDesc = dropdown.options[dropdown.selectedIndex].text;*/
	
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
	/*if(fileInput =="" || fileInput == null){
		 document.getElementById("fileInputError").innerHTML="Please Select File";
		 return false;
	}
	else{
		document.getElementById("fileInputError").innerHTML="";
	}*/
	document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "POST",
	     url:"/replyTicket",
		 dataType: 'json',   
	      data: {
			        "id":ticketId,
					"orgId":orgId,
					"issueDesc":issueDesc,
					"respTicketStatus":"0",
					"respTicketStatusDesc":"",
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
				document.getElementById("chatInput").value=""; 
				document.getElementById("fileName").innerHTML=""; 
				getTicketTransactionHistoryById(ticketId);
				
			}else if(data.status==false){
				$('#AddVehicleModal').modal('show');
			}
	     },
	     error: function(e){
	         alert('Error: ' + e);
	     }
	});	
	
}
