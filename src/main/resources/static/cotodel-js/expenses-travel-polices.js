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
	        var band4 = $(this).find('input.band4').val();
	        var band5 = $(this).find('input.band5').val();
	        var band6 = $(this).find('input.band6').val();
	        var rowvalue=period+"@"+band1+"@"+band2+"@"+band3+"@"+band4+"@"+band5+"@"+band6;
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
	var result = confirm("Are you sure you want to delete?");
	if (result) {
		 document.getElementById("signinLoader").style.display="flex";
	}
	else{
		return false;
	}
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
            document.getElementById("signinLoader").style.display="none";
            var data1 = jQuery.parseJSON( newData );
			//var data2 = data1.data;
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
			    employeesAllow="Specific";
			    nameEmployeesCash = speficEmployees;
			    
			    if(speficEmployees==null || speficEmployees =="") {
					document.getElementById("speficEmployeesError").innerHTML="Please Enter Employee Name";
					return false;
				}
				else{
					document.getElementById("speficEmployeesError").innerHTML="";
				}
			}
		if(disbursmentDate =="" || disbursmentDate ==null){
			document.getElementById("disbursmentDateError").innerHTML="Please Enter Days of disbursal";
			return false;
		}
		else{
			document.getElementById("disbursmentDateError").innerHTML="";
		}
			
        formData.append("employerId", employerid);
	    formData.append("allowEmployeesTravel", travel);
	    formData.append("allowEmployeesCash", cash);
	    formData.append("employeesAllow", employeesAllow);
	    formData.append("nameEmployeesCash",nameEmployeesCash)
	    formData.append("daysDisbursalCash", disbursmentDate);
	    document.getElementById("signinLoader").style.display="flex";
	    document.getElementById("adavanceButton").disabled=true;
	 	$.ajax({
		 type: "POST",
	     url:"/saveExpanceTravelAdvance",
         data: formData,
         processData: false,
         contentType: false,       	
         	 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			window.location.reload();
		  
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

     function viewEmployeeBand() {

			var employerId=document.getElementById("employerId").value;
			//document.getElementById("signinLoader2").style.display="flex";
			
			$.ajax({
				type: "GET",
				url: "/getExpenseBandList",
				data: {
						"employerId": employerId,
					},
		       		  beforeSend : function(xhr) {
						//xhr.setRequestHeader(header, token);
					},
		            success: function(data){
			 		newData = data;
					var data1 = jQuery.parseJSON( newData );
					if(data1.data.list !== null && data1.data.list !== ""){
						loadTableData(data1);
						loadTableData2(data1);
					}
					
						  
		    		},
				error: function(e) {
					alert('Failed to fetch JSON data' + e);
				}
		});
}

function loadTableData(jsonData) {
     const tableBody = document.getElementById('newTable').querySelector('tbody');
     tableBody.innerHTML = '';
     jsonData.data.forEach(item => {
	
			const row = tableBody.insertRow();
                const cell1 = row.insertCell(0);
                var dateSpan = document.createElement('span')
				dateSpan.innerHTML = "";
				
                //const input3 = document.createElement('input');
                //input3.type = 'text';
               // input3.setAttribute('class',"empolyeeinput01");
               // input3.setAttribute('disabled',"disabled");
                //input3.value = item.bandNameOne;
                cell1.appendChild(dateSpan);

				if(item.bandNameOne !==null){
                const cell2 = row.insertCell(1);
                const input5 = document.createElement('span');
                
                //input5.type = 'text';
                input5.setAttribute('class',"band band1");
                //input5.setAttribute('disabled',"disabled");
                input5.innerHTML = item.bandNameOne ? item.bandNameOne : 'N/A';
                //input5.value = item.bandNameOne ? item.bandNameOne : 'N/A';;
               
                cell2.appendChild(input5);
				}
				if(item.bandNameTwo !==null){
                const cell3 = row.insertCell(2);
                const input6 = document.createElement('span');
                //input6.type = 'text';
                input6.setAttribute('class',"band band2");
                // input6.setAttribute('disabled',"disabled");
                input6.innerHTML = item.bandNameTwo ? item.bandNameTwo : 'N/A';
                cell3.appendChild(input6);
                }
				if(item.bandNameThree !==null){
                const cell4 = row.insertCell(3);
                const input7 = document.createElement('span');
                //input7.type = 'text';
                input7.setAttribute('class',"band band3");
                //input7.setAttribute('disabled',"disabled");
                input7.innerHTML = item.bandNameThree ? item.bandNameThree : 'N/A';
                cell4.appendChild(input7);
                }
				if(item.bandNameFour !==null){
                const cell5 = row.insertCell(4);
                const input8 = document.createElement('span');
                //input8.type = 'text';
                input8.setAttribute('class',"band band4");
                //input8.setAttribute('disabled',"disabled");
                input8.innerHTML = item.bandNameFour ? item.bandNameFour : 'N/A';
                cell5.appendChild(input8);
                  }
				if(item.bandNameFive !==null){

                const cell6 = row.insertCell(5);
                const input9 = document.createElement('span');
               // input9.type = 'text';
               input9.setAttribute('class',"band band5");
               // input9.setAttribute('disabled',"disabled");
                input9.innerHTML = item.bandNameFive ?  item.bandNameFive : 'N/A';
                cell6.appendChild(input9);
                }
                if(item.bandNameSix !==null){

                const cell7 = row.insertCell(6);
                const input10 = document.createElement('span');
                //input10.type = 'text';
                input10.setAttribute('class',"band band6");
               // input10.setAttribute('disabled',"disabled");
                input10.innerHTML = item.bandNameSix ?  item.bandNameSix : 'N/A';
                cell7.appendChild(input10);
                }
            });
     
  }
  
  function loadTableData2(jsonData) {
        const tableBody = document.getElementById('newTable').querySelector('tbody');
   
      /* jsonData.data.forEach(item => {
		const row2 = document.createElement('tr');
        
        row2.innerHTML = `
            <td>  <select class="form-select period">
                   <option value="">Select Time Period</option>
                   <option value="perday">Per Day</option>
                   <option value="perweak">Per Weak</option>
                   <option value="permonth">Per Month</option>
             </select> </td>
            <td> <div class="perquarter-in"><span>INR</span> <input type="text" placeholder="Expense Limit 1" class="form-control band2" name="${item.bandNameOne}"></td>
            <td> <div class="perquarter-in"><span>INR</span> <input type="text" placeholder="Expense Limit 2" class="form-control band2"  name="${item.bandNameTwo}"></td>
            <td> <div class="perquarter-in"><span>INR</span> <input type="text" placeholder="Expense Limit 3" class="form-control band2" name="${item.bandNameThree}"></td>
            <td> <div class="perquarter-in"><span>INR</span> <input type="text" placeholder="Expense Limit 4" class="form-control band2" name="${item.bandNameFour}"></td>
            <td> <div class="perquarter-in"><span>INR</span> <input type="text" placeholder="Expense Limit 5" class="form-control band2" name="${item.bandNameFive}"></td>
            <td> <div class="perquarter-in"><span>INR</span> <input type="text" placeholder="Expense Limit 6" class="form-control band2" name="${item.bandNameSix}"></td>
            <td></td>       
        `;
       
       tableBody.appendChild(row2);     
    });*/
    
    jsonData.data.forEach(item => {
	
			const row = tableBody.insertRow();
                
                //const cell1 = row.insertCell(0);
               /// const input3 = document.createElement('input');
               // input3.type = 'text';
               // input3.setAttribute('class',"empolyeeinput01");
                //input3.setAttribute('disabled',"disabled");
               // input3.value = item.bandNameOne;
                //cell1.appendChild(input3);
                
                var cell1 = row.insertCell(0);
			    var day = document.createElement("select");
			    day.name = "Select Day";
			    var perday = document.createElement("option");
			    perday.text = "Per Day";
			    perday.value = "Per Day";
			    var perweak = document.createElement("option");
			    perweak.text = "Per Weak";
			    perweak.value = "Per Weak";
			    var permonth = document.createElement("option");
			    permonth.text = "Per Month";
			    permonth.value = "Per Month";
			    day.setAttribute('class',"form-select period");
				
			    day.add(perday);
	    		day.add(perweak);
	    		day.add(permonth)
	   		    cell1.appendChild(day);

				if(item.bandNameOne !==null){
                const cell2 = row.insertCell(1);
                const input5 = document.createElement('input');
                input5.type = 'text';
                input5.setAttribute('class',"form-control band1");
                input5.setAttribute('placeholder',"INR Expense Limit 1");
                // var iDiv = document.createElement('span');
				///iDiv.innerHTML = "INR";
				////iDiv.setAttribute('class', "perquarter-in");
			
                //cell2.appendChild(iDiv);
                cell2.appendChild(input5);
				}
				if(item.bandNameTwo !==null){
                const cell3 = row.insertCell(2);
                const input6 = document.createElement('input');
                input6.type = 'text';
                input6.setAttribute('class',"form-control band2");
                input6.setAttribute('placeholder',"INR Expense Limit 2");
                cell3.appendChild(input6);
                }
				if(item.bandNameThree !==null){
                const cell4 = row.insertCell(3);
                const input7 = document.createElement('input');
                input7.type = 'text';
                input7.setAttribute('class',"form-control band3");
                input7.setAttribute('placeholder',"INR Expense Limit 3");
                cell4.appendChild(input7);
                }
				if(item.bandNameFour !==null){
                const cell5 = row.insertCell(4);
                const input8 = document.createElement('input');
                input8.type = 'text';
                input8.setAttribute('class',"form-control band4");
                input8.setAttribute('placeholder',"INR Expense Limit 4");
                cell5.appendChild(input8);
                  }
				if(item.bandNameFive !==null){

                const cell6 = row.insertCell(5);
                const input9 = document.createElement('input');
                input9.type = 'text';
                input9.setAttribute('class',"form-control band5");
                input9.setAttribute('placeholder',"INR Expense Limit 5");
                cell6.appendChild(input9);
                }
                if(item.bandNameSix !==null){

                const cell7 = row.insertCell(6);
                const input10 = document.createElement('input');
                input10.type = 'text';
                input10.setAttribute('class',"form-control band6");
                input10.setAttribute('placeholder',"INR Expense Limit 6");
                cell7.appendChild(input10);
                }
            });
	     	
    }