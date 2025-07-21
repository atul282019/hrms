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
		var responseIssueDesc = document.getElementById("replyText").value;
		
		var dropdown = document.getElementById("ticketStatus");
		var respTicketStatus = dropdown.value;
		var respTicketStatusDesc = dropdown.options[dropdown.selectedIndex].text;
		
		const clientKey = "client-secret-key"; // Extra security measure
		const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

		const dataString = orgId+responseIssueDesc+createdby+base64file+clientKey+secretKey;

		const encoder = new TextEncoder();
		const data = encoder.encode(dataString);
		const hashBuffer = await crypto.subtle.digest("SHA-256", data);
		const hashArray = Array.from(new Uint8Array(hashBuffer));
		const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
		
	
		if(responseIssueDesc =="" || responseIssueDesc == null){
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
						"responseIssueDesc":responseIssueDesc,
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
  document.getElementById("signinLoader").style.display = "flex";

  $.ajax({
    type: "GET",
    url: "/getTicketDetailByTicketId",
    data: {
      orgId: orgId,
      "id": ticketId
    },
    beforeSend: function (xhr) {},
    success: function (data) {
      document.getElementById("signinLoader").style.display = "none";
      const data1 = jQuery.parseJSON(data);
      var data2 = data1.data;
      console.log("/getTicketDetailByTicketId first function data", data1);

      const rawDate = new Date(data2.creationDate);

      // Format: 'Thu 18/07 | 06:08 PM'
      const weekday = rawDate.toLocaleDateString('en-GB', { weekday: 'short' });
      const day = String(rawDate.getDate()).padStart(2, '0');
      const month = String(rawDate.getMonth() + 1).padStart(2, '0');

      let hours = rawDate.getHours();
      const minutes = String(rawDate.getMinutes()).padStart(2, '0');
      const ampm = hours >= 12 ? 'PM' : 'AM';
      hours = hours % 12 || 12;
      hours = String(hours).padStart(2, '0');

      const formattedDateTime = `${weekday} ${day}/${month} | ${hours}:${minutes} ${ampm}`;

      document.getElementById("tickeNo").innerHTML = data2.ticketNo;
      document.getElementById("senderName").innerHTML = data2.name;
      document.getElementById("companyName").innerHTML = data2.organizationName;
      document.getElementById("dateTime").innerHTML = data2.creationDate; // original full datetime
      document.getElementById("status").innerHTML = data2.statusDesc;
      document.getElementById("subject").innerHTML = data2.subject;
      document.getElementById("submittedBy").innerHTML = data2.name;
      document.getElementById("submittedDate").innerHTML = formattedDateTime;
      document.getElementById("submittedDate1").innerHTML = formattedDateTime;
      document.getElementById("ticketDetail").innerHTML = data2.issueDesc;
      document.getElementById("imgTicket").src = "data:image/png;base64," + data2.ticketImg;
    },
    error: function (e) {
      alert('Failed to fetch JSON data' + e);
    }
  });
}

async function getTicketTransactionHistoryById() {
  const orgId = document.getElementById("employerId").value;
  const ticketId = document.getElementById("ticketId").value;

  $.ajax({
    type: "GET",
    url: "/ticketReplyHistory",
    data: { orgId, id: ticketId },
    success: function (data) {
      const data1 = jQuery.parseJSON(data);
      const data2 = data1.data;
      console.log("/ticketReplyHistory admin side", data2);
      const container = $('#ticketMessageContainer');
      container.empty();

      if (data1.status && Array.isArray(data2)) {
        data2.forEach(item => {
          const responseIssueDesc = item.responseIssueDesc;
          const issueDesc = item.issueDesc;
          const senderName = item.name || item.createdby || 'Unknown';

          // ✅ Use formatted date function
          const formattedDate = formatFullDateTime(item.creationdate);

          let messageHTML = '';

          if (responseIssueDesc && responseIssueDesc.trim() !== '') {
            messageHTML += `
              <br>
              <div class="admin-ticket-message admin-ticket-message-white">
                <p class="mb-1">${responseIssueDesc}</p>
                <div class="admin-ticket-message-footer">
                  <span style="margin-right: 12px;">${senderName}</span>
                  <span>${formattedDate}</span>
                </div>
              </div>
            `;
          }

          if (issueDesc && issueDesc.trim() !== '') {
            messageHTML += `
              <br>
              <div class="admin-ticket-message admin-ticket-message-green">
                <p class="mb-1">${issueDesc}</p>
                <div class="admin-ticket-message-footer">
                  <span style="margin-right: 12px;">${senderName}</span>
                  <span>${formattedDate}</span>
                </div>
              </div>
            `;
          }

          if (messageHTML !== '') {
            container.append(messageHTML);
          }
        });
      } else {
        container.html('<br><br>');
      }
    },
    error: function () {
      $('#ticketMessageContainer').html('<p>Error loading messages.</p>');
    }
  });
}

// ✅ Date formatter: Thu 18/07/2025 | 06:08 PM
function formatFullDateTime(dateStr) {
  const dateObj = new Date(dateStr);

  const weekday = dateObj.toLocaleDateString('en-GB', { weekday: 'short' });
  const day = String(dateObj.getDate()).padStart(2, '0');
  const month = String(dateObj.getMonth() + 1).padStart(2, '0');
  const year = dateObj.getFullYear();

  let hours = dateObj.getHours();
  const minutes = String(dateObj.getMinutes()).padStart(2, '0');
  const ampm = hours >= 12 ? 'PM' : 'AM';
  hours = hours % 12 || 12;
  hours = String(hours).padStart(2, '0');

  return `${weekday} ${day}/${month}/${year} | ${hours}:${minutes} ${ampm}`;
}


/*async function getTicketTransactionHistoryById() {
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
			const issueDesc = item.issueDesc;
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
