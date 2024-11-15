
function getIssueVoucherList(){
	//document.getElementById("signinLoader").style.display="flex";
	var employerid = document.getElementById("employerId").value;
	$.ajax({
		type: "POST",
		url: "/getIssueVoucherList",
		data: {
			//"employeeId": employerid,
			"orgId": employerid
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			 //console.log(data2);
			//document.getElementById("signinLoader").style.display="none";
			
			var table = $('#issueVoucherTable').DataTable( {
	          destroy: true,	
		     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],
             "language": {"emptyTable": "No Data available"  },
	        
	         "aaData": data2,
      		  "aoColumns": [ 
				{ "mData": "id"}, 
                { "mData": "name"},   
                { "mData": "mobile"},   
			    { "mData": "amount"},
			    { "mData": "merchanttxnId"},   
				{ "mData": "purposeCode"},  
				{ "mData": "mcc"},
				   
				{ "mData": "creationDate"},
				{ "mData": "creationDate"},
				{ "mData": "type"},      
				{ "mData": "redemtionType"},
				
    		 	],
    		 	
      		});		
			
		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}

function sortTable(columnIndex) {
           const rows = Array.from(tableBody.rows);
           const sortedRows = rows.sort((a, b) => {
               const cellA = a.cells[columnIndex].innerText;
               const cellB = b.cells[columnIndex].innerText;
               if (!isNaN(cellA) && !isNaN(cellB)) {
                   return Number(cellA) - Number(cellB);
               }
               return cellA.localeCompare(cellB);
           });

           tableBody.innerHTML = "";
           sortedRows.forEach(row => tableBody.appendChild(row));
       }