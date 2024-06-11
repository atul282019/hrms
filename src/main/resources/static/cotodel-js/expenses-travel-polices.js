function getExpanceCategoryList() {
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
                    return '<td align="right"><button type="button" class="btn p-0" data-toggle="modal" data-target="#ModalExpenseCategory"  onclick="viewData(this)"><img src="img/edit.svg" alt=""> </button> <a href="#"><img src="img/delete.svg" alt="" class="ml-4"></a></td>';
                 }}, 
    		 	],
    		 	createdRow: function (row, data2, dataIndex) 
                    {
                     console.log("row : "+JSON.stringify(data2));
                   
                 	var expenseCategory = data2.expenseCategory;
                 	var expenseLimit = data2.expenseLimit;
                	
                     if(expenseCategory=="Conveyance")
                     {
					 var imgTag = '<img src="img/taxi.svg" alt="" class="mr-2">'+expenseCategory;
 					 var addImage = expenseLimit+'<button class="button" onclick="toggleRow(this)">+</button>';
 					  $(row).find('td:eq(3)').html(addImage);
                      $(row).find('td:eq(1)').html(imgTag);
                     }
                     if(expenseCategory=="Miscellaneous")
                     {
					 var imgTag = ' <img src="img/Miscellaneous.svg" alt="" class="mr-2">'+expenseCategory;
 					
                      $(row).find('td:eq(1)').html(imgTag);
                     }
                     
                     if(expenseCategory=="Food")
                     {
						 var imgTag = ' <img src="img/food.svg" alt="" class="mr-2">'+expenseCategory;
	 					 $(row).find('td:eq(1)').html(imgTag);
	                      
	                     var plus   = expenseLimit+'<button class="button" onclick="toggleRow(this)">+</button>';
	                     $(row).find('td:eq(3)').html(plus);
                      
                     }
                     
                     if(expenseCategory=="Cash Advance")
                     {
					 var imgTag = '<img src="img/cash.svg" alt="" class="mr-2">'+expenseCategory;
 					
                      $(row).find('td:eq(1)').html(imgTag);
                     }
                     if(expenseCategory=="Travel")
                     {
					 var imgTag = '<img src="img/Travel.svg" alt="" class="mr-2">'+expenseCategory;
 					
                      $(row).find('td:eq(1)').html(imgTag);
                     }
                     if(expenseCategory=="Stay")
                     {
					 var imgTag = '<img src="img/hotel.svg" alt="" class="mr-2">'+expenseCategory;
					 
					 var addImage = expenseLimit+'<button class="button" onclick="toggleRow(this)">+</button>';
 					  $(row).find('td:eq(3)').html(addImage);
                      $(row).find('td:eq(1)').html(imgTag);
                     }
                  }
      		});		
			addAdditionalTR();
		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}


function addExpensesCategory(){
	
	
	var expenseCategory = document.getElementById("expenseCategory").value;
	var expanceCode = document.getElementById("expanceCode").value;
	var distingushEmployeeBand = document.getElementById("distingushEmployeeBand").value;
	var timeperiod = document.getElementById("timeperiod").value;
	
	var employerid = document.getElementById("employerId").value;
	var id = document.getElementById("categoryId").value;
	
    var allInputValues = [];
	
	 $('.newTable tbody tr').each(function () {
	        var period = $(this).find('select.period').val();
	        var band1 = $(this).find('input.band1').val();
	        var band2 = $(this).find('input.band2').val();
	        var band3 = $(this).find('input.band3').val();
	      var rowvalue=period+"@"+band1+"@"+band2+"@"+band3;
	       allInputValues.push(rowvalue);
	    });  
	 //alert(""+allInputValues);
     //console.log(allInputValues);
     //get dynamic table data end
    
     var formData = new FormData(addExpenses);
     formData.append("id", id);
     formData.append("employerId", employerid);
     formData.append("expenseCategory", expenseCategory);
     formData.append("expenseCode", expanceCode);
     formData.append("distingushEmployeeBand", distingushEmployeeBand);
     formData.append("period", timeperiod);
     formData.append("listArray", allInputValues);
    
 
	 	$.ajax({
		 type: "POST",
	     url:"/saveExpensesCategory",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
	
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			// document.getElementById("signinLoader").style.display="none";
			if(data1.status==true){
				
				 document.getElementById("payrollsuccessmsg").innerHTML=data1.message;
				 document.getElementById("payrollsuccessmsgdiv").style.display="block";
				 
			}else if(data1.status==false){
				
				 document.getElementById("payrollfailmsg").innerHTML=data1.message;
				 document.getElementById("payrollfailmsgDiv").style.display="block";
				
			}else{
				
				 document.getElementById("payrollfailmsgDiv").style.display="none";
				 document.getElementById("payrollsuccessmsgdiv").style.display="none";
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });			
               
}  


 function viewData(value){
	
	 document.getElementById("editCategoryButton").style.display="block";
	 document.getElementById("addCategoryButton").style.display="none";
	 var row = jQuery(value).closest('tr');
	 var  id = $(row).find("input[name='expensesid']").val();
	 var  id = $(row).find("input[name='expensesid']").val();
	 var expanceCode = row[0].children[2].innerHTML;
	
	 
	 	$.ajax({
		type: "GET",
		url:"/editExpensesCategory",
        data: {
				"id": id,
				"expenseCode": expanceCode,
       		 },
       		  beforeSend : function(xhr) {
				//xhr.setRequestHeader(header, token);
				},
            success: function(data){
            newData = data;
            // console.log(newData);
            var data1 = jQuery.parseJSON( newData );
			var data2 = data1.data;
			//console.log(newData);
  			document.getElementById("expenseCategory").value = data2.expenseCategory ;
			document.getElementById("expanceCode").value =  data2.expenseCode;
			document.getElementById("timeperiod").value =data2.dayToExpiry ;
			document.getElementById("categoryId").value = data2.id;
			
           },
         error: function(e){
             alert('Error: ' + e);
         }
    }); 
				
 }
		 