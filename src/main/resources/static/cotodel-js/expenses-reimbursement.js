function getReimbursementList() {
	document.getElementById("signinLoader").style.display="flex";
	var employerid = document.getElementById("employerId").value;
	$.ajax({
		type: "GET",
		url: "/getExpensesCategory",
		data: {
			"employerId": employerid
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			document.getElementById("signinLoader").style.display="none";
			
			 var table = $('#expensesTable').DataTable( {
	          destroy: true,	
		     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],
             "language": {"emptyTable": "No Data available"  },
	        
	         "aaData": data2,
      		  "aoColumns": [ 
				
                  { "mData": "id", "render": function (data2, type, row) {
                    return '<input type="hidden" value="'+data2+'" id="expensesid"  Name="expensesid" >';
                 }}, 
                { "mData": "expenseCategory"},          
      		    { "mData": "expenseCode"},
      		    { "mData": "expenseLimit"},
      		    { "mData": "dayToExpiry"},
      		  	{ "mData": "id", "render": function (data1, type, row) {
                    return '<td align="right"><button type="button" class="btn p-0" data-toggle="modal" data-target="#ModalExpenseCategory"  onclick="viewData(this)"><img src="img/edit.svg" alt=""> </button> <button type="button" onclick="deleteData(this)" class="btn p-0" ><img src="img/delete.svg" ></button> </td>';
                 }}, 
    		 	],
    		 	
      		});		
			
		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}
