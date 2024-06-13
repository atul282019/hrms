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
                    return '<td align="right"><button type="button" class="btn p-0" data-toggle="modal" data-target="#ModalExpenseCategory"  onclick="viewData(this)"><img src="img/edit.svg" alt=""> </button> <button type="button" onclick="deleteData(this)" class="btn p-0" ><img src="img/delete.svg" ></button> </td>';
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


function editExpensesCategory(){
	
	var expenseCategory = document.getElementById("expenseCategory").value;
	var expanceCode = document.getElementById("expanceCode").value;
	var expanceLimit = document.getElementById("expanceLimit").value;
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
     formData.append("expenseLimit", expanceLimit);
     formData.append("distingushEmployeeBand", distingushEmployeeBand);
     formData.append("dayToExpiry", timeperiod);
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
			location.reload();
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
			getExpanceCategoryList();
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });			
               
}  


function addExpensesCategory(){
	
	var expenseCategory = document.getElementById("expenseCategory").value;
	var expanceCode = document.getElementById("expanceCode").value;
	var expanceLimit = document.getElementById("expanceLimit").value;
	var distingushEmployeeBand = document.getElementById("distingushEmployeeBand").value;
	var timeperiod = document.getElementById("timeperiod").value;
	
	var employerid = document.getElementById("employerId").value;
	//var id = document.getElementById("categoryId").value;
	
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
     //formData.append("id", id);
     formData.append("employerId", employerid);
     formData.append("expenseCategory", expenseCategory);
     formData.append("expenseCode", expanceCode);
     formData.append("expanceLimit", expanceLimit);
     formData.append("distingushEmployeeBand", distingushEmployeeBand);
     formData.append("dayToExpiry", timeperiod);
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
			location.reload();
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
			getExpanceCategoryList();
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
			document.getElementById("expanceLimit").value =data2.expenseLimit ;
			document.getElementById("categoryId").value = data2.id;
			
           },
         error: function(e){
             alert('Error: ' + e);
         }
    }); 
				
 }

 function toggleDiv(show) {
            const section = document.getElementById('nameEmployeesCash');
            if (show) {
                section.classList.remove('hidden');
            } else {
                section.classList.add('hidden');
            }
        }

function toggleDivEdit(show) {
         
            const section = document.getElementById('speficEmployeesDiv');
            if (show) {
                section.style.display="block";
            } else {
                section.style.display="none";
            }
        }


function deleteData(value){
	
	 var row = jQuery(value).closest('tr');
	 var  id = $(row).find("input[name='expensesid']").val();
	 var expanceCode = row[0].children[2].innerHTML;
	 var employerid = document.getElementById("employerId").value;
	 
	 $.ajax({
		type: "GET",
		url:"/deleteExpanseTravelAdvance",
        data: {
				"id": id,
				"employerId": employerid,
       		 },
       		  beforeSend : function(xhr) {
				//xhr.setRequestHeader(header, token);
				},
            success: function(data){
            newData = data;
            // console.log(newData);
            var data1 = jQuery.parseJSON( newData );
			var data2 = data1.data;
			location.reload();
			//console.log(newData);
  			/*document.getElementById("expenseCategory").value = data2.expenseCategory ;
			document.getElementById("expanceCode").value =  data2.expenseCode;
			document.getElementById("timeperiod").value =data2.dayToExpiry ;
			document.getElementById("expanceLimit").value =data2.expenseLimit ;
			document.getElementById("categoryId").value = data2.id;*/
			
           },
         error: function(e){
             alert('Error: ' + e);
         }
    });
    		
}

 function addAdvanceRequest(){
	
	
	var employeesAllow = null;
	var nameEmployeesCash = "all";
    var travel =null;
	var cash = null;
	
	var speficEmployees = document.getElementById("speficEmployees").value;
	var employerid = document.getElementById("employerId").value;
	var disbursmentDate =  document.getElementById("disbursmentDate").value;
	
    var formData = new FormData(addAdvance);
	if(document.getElementById("travel").checked){
		travel="Yes";
	}
	if(document.getElementById("cash").checked){
		cash="Yes";
	}
	
     if(document.getElementById('all').checked) {
				   
				  employeesAllow="All";
				
				  //document.getElementById("numericFigureError").innerHTML="";
				  
			}   
			else if(document.getElementById('spefic').checked){
			   // document.getElementById("numericFigureError").innerHTML="";	
			    employeesAllow="Spefic";
			    nameEmployeesCash = speficEmployees;
			}
			
        formData.append("employerId", employerid);
	    formData.append("allowEmployeesTravel", travel);
	    formData.append("allowEmployeesCash", cash);
	    formData.append("employeesAllow", employeesAllow);
	    formData.append("nameEmployeesCash",nameEmployeesCash)
	    formData.append("daysDisbursalCash", disbursmentDate);
	    
	 	$.ajax({
		 type: "POST",
	     url:"/saveExpanceTravelAdvance",
         data: formData,
         processData: false,
         contentType: false,       	
         	 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			
			window.location.reload();
			// document.getElementById("signinLoader").style.display="none";
			if(data1.status==true){
				
			//	 document.getElementById("payrollsuccessmsg").innerHTML=data1.message;
				// document.getElementById("payrollsuccessmsgdiv").style.display="block";
				 
			}else if(data1.status==false){
				
				 //document.getElementById("payrollfailmsg").innerHTML=data1.message;
				 //document.getElementById("payrollfailmsgDiv").style.display="block";
				
			}else{
				
				 //document.getElementById("payrollfailmsgDiv").style.display="none";
				 //document.getElementById("payrollsuccessmsgdiv").style.display="none";
			}
			getExpanseTravelAdvance();
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });			
               
}  


function getExpanseTravelAdvance() {

	var employerId=document.getElementById("employerId").value;
	//document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "GET",
		url: "/getExpanseTravelAdvance",
		data: {
				"employerId": employerId,
			},
       		  beforeSend : function(xhr) {
				//xhr.setRequestHeader(header, token);
				},
            success: function(data){
	 		newData = data;
			var data1 = jQuery.parseJSON( newData );
			//bindJsonToTable(data1);
			 //var bandRepresentation = jsonData.data.employeeBandNoAlpha;
      	    //document.getElementById("signinLoader").style.display="none";		
	      	     if(data1.data.allowEmployeesTravel==="Yes")
	             {
							document.getElementById('travelBooking').checked = true;
				 }
				
				if(data1.data.allowEmployeesCash==="Yes" || data1.data.allowEmployeesCash==="YES"){
					document.getElementById('cashBooking').checked = true;
				}
				
				if(data1.data.employeesAllow==="All"){
					document.getElementById('allowAll').checked = true;
				}
				else{
					document.getElementById('allowSpefic').checked = true;
				}
				
		      const nameEmployeesCashContainer = document.getElementById('nameEmployeesCash');
	        data1.data.nameEmployeesCash.forEach(name => {
	            const inputField = document.createElement('input');
	            inputField.type = 'text';
	            inputField.value = name;
	            inputField.className = 'incur-input';
	            nameEmployeesCashContainer.appendChild(inputField);
	        });
			document.getElementById('disbursalDate').value  =data1.data.daysDisbursalCash;  
			
			
    		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}