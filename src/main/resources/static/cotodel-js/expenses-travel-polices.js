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
                    return '<td align="right"><button type="button" class="btn p-0" data-toggle="modal" data-target="#ModalExpenseCategoryEdit"  onclick="viewData(this)"><img src="img/edit.svg" alt=""> </button> <button type="button" onclick="deleteData(this)" class="btn p-0" ><img src="img/delete.svg" ></button> </td>';
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
	
	var expenseCategory = document.getElementById("expenseCategoryEdit").value;
	var expenseCategory = document.getElementById("expenseCategoryEdit").value;
	var expanceCode = document.getElementById("expanceCodeEdit").value;
	var expanceLimit = document.getElementById("expanceLimitEdit").value;
	var distingushEmployeeBand = document.getElementById("distingushEmployeeBandEdit").value;
	var timeperiod = document.getElementById("timeperiodEdit").value;
	
	var employerid = document.getElementById("employerId").value;
	var id = document.getElementById("categoryId").value;
	
	if(expenseCategory ==null || expenseCategory==""){
		document.getElementById("expenseCategoryEditError").innerHTML="Please Enter Expense Category";
		document.getElementbyId("expenseCategoryEdit").focus();
		return false;
	}
	else{
		document.getElementById("expenseCategoryEditError").innerHTML="";
	}
	if(expanceCode ==null || expanceCode==""){
		document.getElementById("expanceCodeEditError").innerHTML="Please Enter Expense Code";
		document.getElementbyId("expanceCodeEdit").focus();
		return false;
	}
	else{
		document.getElementById("expanceCodeEditError").innerHTML="";
	}
	if(expanceLimit ==null || expanceLimit==""){
		document.getElementById("expanceLimitEditError").innerHTML="Please Enter Expense Limit";
		document.getElementbyId("expanceLimitEdit").focus();
		return false;
	}
	else{
		document.getElementById("expanceLimitEditError").innerHTML="";
	}
	
	
	/////////////////////////////////////////////////////
	// Get all input elements with the class 'validate'
    const inputs = document.querySelectorAll('.bandedit1');
    const inputs2 = document.querySelectorAll('.bandedit2');
    const inputs3 = document.querySelectorAll('.bandedit3');
    const inputs4 = document.querySelectorAll('.bandedit4');
    const inputs5 = document.querySelectorAll('.bandedit5');
    const inputs6 = document.querySelectorAll('.bandedit6');
    let isValid = true;
    let isValid2 = true;
    let isValid3 = true;
    let isValid4 = true;
    let isValid5 = true;
    let isValid6 = true; // Flag to track overall form validity

    // Clear previous error messages and styles
    inputs.forEach(input => {
        input.classList.remove('errormulti');
        const errorSpan = input.nextElementSibling;
        if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
            errorSpan.textContent = '';
        }
    });

    // Validate each input field
    inputs.forEach((input, index) => {
        if (!input.value.trim()) {
            // If the input field is empty, add error class and show error message
            input.classList.add('errormulti');
            const errorSpan = input.nextElementSibling;
            if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
                errorSpan.textContent = `Input ${index + 1} is required.`;
            }
            isValid = false; // Set form validity to false
        }
    });

    if (!isValid) {
        // If the form is not valid, return false
        return false;
    } else {
        // If the form is valid, you can proceed with form submission or other actions
       // alert('Form is valid!');
        // document.getElementById('myForm').submit(); // Uncomment this line to submit the form
    }
    
    // Clear previous error messages and styles
    inputs2.forEach(input => {
        input.classList.remove('errormulti');
        const errorSpan = input.nextElementSibling;
        if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
            errorSpan.textContent = '';
        }
    });

    // Validate each input field
    inputs2.forEach((input, index) => {
        if (!input.value.trim()) {
            // If the input field is empty, add error class and show error message
            input.classList.add('errormulti');
            const errorSpan = input.nextElementSibling;
            if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
                errorSpan.textContent = `Input ${index + 1} is required.`;
            }
            isValid2 = false; // Set form validity to false
        }
    });

    if (!isValid2) {
        // If the form is not valid, return false
        return false;
    } else {
        // If the form is valid, you can proceed with form submission or other actions
        //alert('Form is valid!');
        // document.getElementById('myForm').submit(); // Uncomment this line to submit the form
    }
    
     // Clear previous error messages and styles
    inputs3.forEach(input => {
        input.classList.remove('errormulti');
        const errorSpan = input.nextElementSibling;
        if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
            errorSpan.textContent = '';
        }
    });

    // Validate each input field
    inputs3.forEach((input, index) => {
        if (!input.value.trim()) {
            // If the input field is empty, add error class and show error message
            input.classList.add('errormulti');
            const errorSpan = input.nextElementSibling;
            if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
                errorSpan.textContent = `Input ${index + 1} is required.`;
            }
            isValid3 = false; // Set form validity to false
        }
    });

    if (!isValid3) {
        // If the form is not valid, return false
        return false;
    } else {
        // If the form is valid, you can proceed with form submission or other actions
        //alert('Form is valid!');
        // document.getElementById('myForm').submit(); // Uncomment this line to submit the form
    }
    
     // Clear previous error messages and styles
    inputs4.forEach(input => {
        input.classList.remove('errormulti');
        const errorSpan = input.nextElementSibling;
        if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
            errorSpan.textContent = '';
        }
    });

    // Validate each input field
    inputs4.forEach((input, index) => {
        if (!input.value.trim()) {
            // If the input field is empty, add error class and show error message
            input.classList.add('errormulti');
            const errorSpan = input.nextElementSibling;
            if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
                errorSpan.textContent = `Input ${index + 1} is required.`;
            }
            isValid4 = false; // Set form validity to false
        }
    });

    if (!isValid4) {
        // If the form is not valid, return false
        return false;
    } else {
        // If the form is valid, you can proceed with form submission or other actions
        //alert('Form is valid!');
        // document.getElementById('myForm').submit(); // Uncomment this line to submit the form
    }
    
     // Clear previous error messages and styles
    inputs5.forEach(input => {
        input.classList.remove('errormulti');
        const errorSpan = input.nextElementSibling;
        if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
            errorSpan.textContent = '';
        }
    });

    // Validate each input field
    inputs5.forEach((input, index) => {
        if (!input.value.trim()) {
            // If the input field is empty, add error class and show error message
            input.classList.add('errormulti');
            const errorSpan = input.nextElementSibling;
            if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
                errorSpan.textContent = `Input ${index + 1} is required.`;
            }
            isValid5 = false; // Set form validity to false
        }
    });

    if (!isValid5) {
        // If the form is not valid, return false
        return false;
    } else {
        // If the form is valid, you can proceed with form submission or other actions
        // alert('Form is valid!');
        // document.getElementById('myForm').submit(); // Uncomment this line to submit the form
    }
    
     // Clear previous error messages and styles
    inputs6.forEach(input => {
        input.classList.remove('errormulti');
        const errorSpan = input.nextElementSibling;
        if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
            errorSpan.textContent = '';
        }
    });

    // Validate each input field
    inputs6.forEach((input, index) => {
        if (!input.value.trim()) {
            // If the input field is empty, add error class and show error message
            input.classList.add('errormulti');
            const errorSpan = input.nextElementSibling;
            if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
                errorSpan.textContent = `Input ${index + 1} is required.`;
            }
            isValid6 = false; // Set form validity to false
        }
    });

    if (!isValid6) {
        // If the form is not valid, return false
        return false;
    } else {
        // If the form is valid, you can proceed with form submission or other actions
        //alert('Form is valid!');
        // document.getElementById('myForm').submit(); // Uncomment this line to submit the form
    }
	//////////////////////////////
	
	
	if(timeperiod ==null || timeperiod==""){
		document.getElementById("timeperiodEditError").innerHTML="Please Enter Days";
		document.getElementById("timeperiodEdit").focus();
		return false;
	}
	else{
		document.getElementById("timeperiodEditError").innerHTML="";
	}
	
    var allInputValues = [];
	
	 $('.newTableEdit tbody tr').each(function () {
	        var period = $(this).find('select.bandedit').val();
	        var band1 = $(this).find('input.bandedit1').val();
	        var band2 = $(this).find('input.bandedit2').val();
	        var band3 = $(this).find('input.bandedit3').val();
	        var band4 = $(this).find('input.bandedit4').val();
	        var band5 = $(this).find('input.bandedit5').val();
	        var band6 = $(this).find('input.bandedit6').val();
	        var rowvalue=period+"@"+band1+"@"+band2+"@"+band3+"@"+band4+"@"+band5+"@"+band6;
	        allInputValues.push(rowvalue);
	    });  
	
     //get dynamic table data end
    
     var formData = new FormData(addExpensesEdit);
     formData.append("id", id);
     formData.append("employerId", employerid);
     formData.append("expenseCategory", expenseCategory);
     formData.append("expenseCode", expanceCode);
     formData.append("expenseLimit", expanceLimit);
     formData.append("distingushEmployeeBand", distingushEmployeeBand);
     formData.append("dayToExpiry", timeperiod);
     formData.append("listArray", allInputValues);
	 document.getElementById("editCategory").disabled = true;
	 
	 	$.ajax({
		 type: "POST",
	     url:"/updateExpensesCategory",
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
	
	if(expenseCategory ==null || expenseCategory==""){
		document.getElementById("expenseCategoryError").innerHTML="Please Enter Expense Category";
		document.getElementbyId("expenseCategory").focus();
		return false;
	}
	else{
		document.getElementById("expenseCategoryError").innerHTML="";
	}
	if(expanceCode ==null || expanceCode==""){
		document.getElementById("expanceCodeError").innerHTML="Please Enter Expense Code";
		document.getElementbyId("expanceCode").focus();
		return false;
	}
	else{
		document.getElementById("expanceCodeError").innerHTML="";
	}
	if(expanceLimit ==null || expanceLimit==""){
		document.getElementById("expanceLimitError").innerHTML="Please Enter Expense Limit";
		document.getElementbyId("expanceLimit").focus();
		return false;
	}
	else{
		document.getElementById("expanceLimitError").innerHTML="";
	}
	
	///
	// Get all input elements with the class 'validate'
	const select = document.querySelectorAll('.period');
    const inputs = document.querySelectorAll('.band1');
    const inputs2 = document.querySelectorAll('.band2');
    const inputs3 = document.querySelectorAll('.band3');
    const inputs4 = document.querySelectorAll('.band4');
    const inputs5 = document.querySelectorAll('.band5');
    const inputs6 = document.querySelectorAll('.band6');
    
    let isValid7 = true;
    let isValid = true;
    let isValid2 = true;
    let isValid3 = true;
    let isValid4 = true;
    let isValid5 = true;
    let isValid6 = true; // Flag to track overall form validity

    // Clear previous error messages and styles
    
     select.forEach(input => {
        input.classList.remove('errormulti');
        const errorSpan = input.nextElementSibling;
        if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
            errorSpan.textContent = '';
        }
    });

    // Validate each input field
    select.forEach((input, index) => {
        if (!input.value.trim()) {
            // If the input field is empty, add error class and show error message
            input.classList.add('errormulti');
            const errorSpan = input.nextElementSibling;
            if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
                errorSpan.textContent = `Input ${index + 1} is required.`;
            }
            isValid7 = false; // Set form validity to false
        }
    });
    
    if (!isValid7) {
        // If the form is not valid, return false
        return false;
    } else {
        // If the form is valid, you can proceed with form submission or other actions
       // alert('Form is valid!');
        // document.getElementById('myForm').submit(); // Uncomment this line to submit the form
    }
    
    
    inputs.forEach(input => {
        input.classList.remove('errormulti');
        const errorSpan = input.nextElementSibling;
        if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
            errorSpan.textContent = '';
        }
    });

    // Validate each input field
    inputs.forEach((input, index) => {
        if (!input.value.trim()) {
            // If the input field is empty, add error class and show error message
            input.classList.add('errormulti');
            const errorSpan = input.nextElementSibling;
            if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
                errorSpan.textContent = `Input ${index + 1} is required.`;
            }
            isValid = false; // Set form validity to false
        }
    });

    if (!isValid) {
        // If the form is not valid, return false
        return false;
    } else {
        // If the form is valid, you can proceed with form submission or other actions
       // alert('Form is valid!');
        // document.getElementById('myForm').submit(); // Uncomment this line to submit the form
    }
    
    // Clear previous error messages and styles
    inputs2.forEach(input => {
        input.classList.remove('errormulti');
        const errorSpan = input.nextElementSibling;
        if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
            errorSpan.textContent = '';
        }
    });

    // Validate each input field
    inputs2.forEach((input, index) => {
        if (!input.value.trim()) {
            // If the input field is empty, add error class and show error message
            input.classList.add('errormulti');
            const errorSpan = input.nextElementSibling;
            if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
                errorSpan.textContent = `Input ${index + 1} is required.`;
            }
            isValid2 = false; // Set form validity to false
        }
    });

    if (!isValid2) {
        // If the form is not valid, return false
        return false;
    } else {
        // If the form is valid, you can proceed with form submission or other actions
        //alert('Form is valid!');
        // document.getElementById('myForm').submit(); // Uncomment this line to submit the form
    }
    
     // Clear previous error messages and styles
    inputs3.forEach(input => {
        input.classList.remove('errormulti');
        const errorSpan = input.nextElementSibling;
        if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
            errorSpan.textContent = '';
        }
    });

    // Validate each input field
    inputs3.forEach((input, index) => {
        if (!input.value.trim()) {
            // If the input field is empty, add error class and show error message
            input.classList.add('errormulti');
            const errorSpan = input.nextElementSibling;
            if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
                errorSpan.textContent = `Input ${index + 1} is required.`;
            }
            isValid3 = false; // Set form validity to false
        }
    });

    if (!isValid3) {
        // If the form is not valid, return false
        return false;
    } else {
        // If the form is valid, you can proceed with form submission or other actions
        //alert('Form is valid!');
        // document.getElementById('myForm').submit(); // Uncomment this line to submit the form
    }
    
     // Clear previous error messages and styles
    inputs4.forEach(input => {
        input.classList.remove('errormulti');
        const errorSpan = input.nextElementSibling;
        if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
            errorSpan.textContent = '';
        }
    });

    // Validate each input field
    inputs4.forEach((input, index) => {
        if (!input.value.trim()) {
            // If the input field is empty, add error class and show error message
            input.classList.add('errormulti');
            const errorSpan = input.nextElementSibling;
            if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
                errorSpan.textContent = `Input ${index + 1} is required.`;
            }
            isValid4 = false; // Set form validity to false
        }
    });

    if (!isValid4) {
        // If the form is not valid, return false
        return false;
    } else {
        // If the form is valid, you can proceed with form submission or other actions
        //alert('Form is valid!');
        // document.getElementById('myForm').submit(); // Uncomment this line to submit the form
    }
    
     // Clear previous error messages and styles
    inputs5.forEach(input => {
        input.classList.remove('errormulti');
        const errorSpan = input.nextElementSibling;
        if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
            errorSpan.textContent = '';
        }
    });

    // Validate each input field
    inputs5.forEach((input, index) => {
        if (!input.value.trim()) {
            // If the input field is empty, add error class and show error message
            input.classList.add('errormulti');
            const errorSpan = input.nextElementSibling;
            if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
                errorSpan.textContent = `Input ${index + 1} is required.`;
            }
            isValid5 = false; // Set form validity to false
        }
    });

    if (!isValid5) {
        // If the form is not valid, return false
        return false;
    } else {
        // If the form is valid, you can proceed with form submission or other actions
        // alert('Form is valid!');
        // document.getElementById('myForm').submit(); // Uncomment this line to submit the form
    }
    
     // Clear previous error messages and styles
    inputs6.forEach(input => {
        input.classList.remove('errormulti');
        const errorSpan = input.nextElementSibling;
        if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
            errorSpan.textContent = '';
        }
    });

    // Validate each input field
    inputs6.forEach((input, index) => {
        if (!input.value.trim()) {
            // If the input field is empty, add error class and show error message
            input.classList.add('errormulti');
            const errorSpan = input.nextElementSibling;
            if (errorSpan && errorSpan.classList.contains('errormulti-message')) {
                errorSpan.textContent = `Input ${index + 1} is required.`;
            }
            isValid6 = false; // Set form validity to false
        }
    });

    if (!isValid6) {
        // If the form is not valid, return false
        return false;
    } else {
        // If the form is valid, you can proceed with form submission or other actions
        //alert('Form is valid!');
        // document.getElementById('myForm').submit(); // Uncomment this line to submit the form
    }
	///
	
	if(timeperiod ==null || timeperiod==""){
		document.getElementById("timeperiodError").innerHTML="Please Enter Days";
		document.getElementById("timeperiod").focus();
		return false;
	}
	else{
		document.getElementById("timeperiodError").innerHTML="";
	}
	
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
     document.getElementById("addcat").disabled = true;
     document.getElementById("signinLoader").style.display="flex";
	 	$.ajax({
		 type: "POST",
	     url:"/saveExpensesCategory",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
	
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			document.getElementById("addcat").disabled = false;
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
//input field validation using class anme

 function viewData(value){
	
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
  			document.getElementById("expenseCategoryEdit").value = data2.expenseCategory ;
			document.getElementById("expanceCodeEdit").value =  data2.expenseCode;
			document.getElementById("timeperiodEdit").value =data2.dayToExpiry ;
			document.getElementById("expanceLimitEdit").value =data2.expenseLimit ;
			document.getElementById("categoryId").value = data2.id;
			viewEmployeeBandEdit();
			const tableBody = document.getElementById('newTableEdit').querySelector('tbody');
  		    tableBody.innerHTML = '';

		     data2.list.forEach(item => {
	
			 const row = tableBody.insertRow();
                
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
			    
			    var perquarter = document.createElement("option");
			    perquarter.text = "Per Quarter";
			    perquarter.value = "Per Quarter";
			    
			    var peryear = document.createElement("option");
			    peryear.text = "Per Year";
			    peryear.value = "Per Year";
			   
			    day.setAttribute('class',"form-select bandedit");
				
			    day.add(perday);
			    day.add(perweak);
			    day.add(permonth);
			    day.add(perquarter);
			    day.add(peryear);
			    
			     if (item.bandType=== "Per Day") {
	                perday.selected = true; // Set the option with id 4 as selected
	            }
	             if (item.bandType=== "Per Weak") {
	                perweak.selected = true; // Set the option with id 4 as selected
	            }
	             if (item.bandType=== "Per Month") {
	                permonth.selected = true; // Set the option with id 4 as selected
	            }
	    		
	    		 if (item.bandType=== "Per Year") {
	                peryear.selected = true; // Set the option with id 4 as selected
	            }
	    		
	    		 if (item.bandType=== "Per Quarter") {
	                perquarter.selected = true; // Set the option with id 4 as selected
	            }
	    		
	   		    cell1.appendChild(day);

				if(item.bandOneInr !==null){
                const cell2 = row.insertCell(1);
                const input5 = document.createElement('input');
                input5.type = 'text';
                input5.value = item.bandOneInr;
                
                input5.setAttribute('class',"form-control bandedit1");
                input5.setAttribute('placeholder',"INR Expense Limit 1");
                // var iDiv = document.createElement('span');
				///iDiv.innerHTML = "INR";
				////iDiv.setAttribute('class', "perquarter-in");
			
                //cell2.appendChild(iDiv);
                cell2.appendChild(input5);
				}
				if(item.bandTwoInr !==null){
                const cell3 = row.insertCell(2);
                const input6 = document.createElement('input');
                input6.type = 'text';
                input6.value = item.bandTwoInr ;
                input6.setAttribute('class',"form-control bandedit2");
                input6.setAttribute('placeholder',"INR Expense Limit 2");
                cell3.appendChild(input6);
                }
				if(item.bandThreeInr !==null){
                const cell4 = row.insertCell(3);
                const input7 = document.createElement('input');
                input7.type = 'text';
                input7.value = item.bandThreeInr ;
                input7.setAttribute('class',"form-control bandedit3");
                input7.setAttribute('placeholder',"INR Expense Limit 3");
                cell4.appendChild(input7);
                }
				if(item.bandFourInr !==null){
                const cell5 = row.insertCell(4);
                const input8 = document.createElement('input');
                input8.type = 'text';
                input8.value = item. bandFourInr;
                input8.setAttribute('class',"form-control bandedit4");
                input8.setAttribute('placeholder',"INR Expense Limit 4");
                cell5.appendChild(input8);
                  }
				if(item.bandFiveInr !==null && item.bandFiveInr !=="" && item.bandFiveInr !==0){

                const cell6 = row.insertCell(5);
                const input9 = document.createElement('input');
                input9.type = 'text';
                input9.value = item.bandFiveInr;
                input9.setAttribute('class',"form-control bandedit5");
                input9.setAttribute('placeholder',"INR Expense Limit 5");
                cell6.appendChild(input9);
                }
                if(item.bandSixInr !==null && item.bandSixInr !=="" && item.bandSixInr !==0){

                const cell7 = row.insertCell(6);
                const input10 = document.createElement('input');
                input10.type = 'text';
                input10.value = item.bandSixInr;
                input10.setAttribute('class',"form-control bandedit6");
                input10.setAttribute('placeholder',"INR Expense Limit 6");
                cell7.appendChild(input10);
                }
            });
		    
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


     function viewEmployeeBandEdit() {

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
						loadTableDataEdit(data1);
						
					 }			  
		    	 },
				error: function(e) {
					alert('Failed to fetch JSON data' + e);
			}
	});
}


function loadTableData(jsonData) {
     const tableBody = document.getElementById('newTable').querySelector('thead');
     //tableBody.innerHTML = '';
     jsonData.data.forEach(item => {
	
			const row = tableBody.insertRow();
                const cell1 = row.insertCell(0);
                var dateSpan = document.createElement('span')
				dateSpan.innerHTML = "";
				
                cell1.appendChild(dateSpan);

				if(item.bandNameOne !==null){
                const cell2 = row.insertCell(1);
                const input5 = document.createElement('span');
                
                //input5.type = 'text';
                input5.setAttribute('class',"band");
                //input5.setAttribute('disabled',"disabled");
                input5.innerHTML = item.bandNameOne ? item.bandNameOne : 'N/A';
                //input5.value = item.bandNameOne ? item.bandNameOne : 'N/A';;
               
                cell2.appendChild(input5);
				}
				if(item.bandNameTwo !==null){
                const cell3 = row.insertCell(2);
                const input6 = document.createElement('span');
                //input6.type = 'text';
                input6.setAttribute('class',"band");
                // input6.setAttribute('disabled',"disabled");
                input6.innerHTML = item.bandNameTwo ? item.bandNameTwo : 'N/A';
                cell3.appendChild(input6);
                }
				if(item.bandNameThree !==null){
                const cell4 = row.insertCell(3);
                const input7 = document.createElement('span');
                //input7.type = 'text';
                input7.setAttribute('class',"band");
                //input7.setAttribute('disabled',"disabled");
                input7.innerHTML = item.bandNameThree ? item.bandNameThree : 'N/A';
                cell4.appendChild(input7);
                }
				if(item.bandNameFour !==null){
                const cell5 = row.insertCell(4);
                const input8 = document.createElement('span');
                //input8.type = 'text';
                input8.setAttribute('class',"band");
                //input8.setAttribute('disabled',"disabled");
                input8.innerHTML = item.bandNameFour ? item.bandNameFour : 'N/A';
                cell5.appendChild(input8);
                  }
				if(item.bandNameFive !==null){

                const cell6 = row.insertCell(5);
                const input9 = document.createElement('span');
               // input9.type = 'text';
               input9.setAttribute('class',"band");
               // input9.setAttribute('disabled',"disabled");
                input9.innerHTML = item.bandNameFive ?  item.bandNameFive : 'N/A';
                cell6.appendChild(input9);
                }
                if(item.bandNameSix !==null){

                const cell7 = row.insertCell(6);
                const input10 = document.createElement('span');
                //input10.type = 'text';
                input10.setAttribute('class',"band");
               // input10.setAttribute('disabled',"disabled");
                input10.innerHTML = item.bandNameSix ?  item.bandNameSix : 'N/A';
                cell7.appendChild(input10);
                }
            });
     
  }
  
  function loadTableDataEdit(jsonData) {
     const tableBody = document.getElementById('newTableEdit').querySelector('thead');
     tableBody.innerHTML = '';
     jsonData.data.forEach(item => {
	
			const row = tableBody.insertRow();
                const cell1 = row.insertCell(0);
                var dateSpan = document.createElement('span')
				dateSpan.innerHTML = "";
				
                cell1.appendChild(dateSpan);

				if(item.bandNameOne !==null){
                const cell2 = row.insertCell(1);
                const input5 = document.createElement('span');
                
                //input5.type = 'text';
                input5.setAttribute('class',"band");
                //input5.setAttribute('disabled',"disabled");
                input5.innerHTML = item.bandNameOne ? item.bandNameOne : 'N/A';
                //input5.value = item.bandNameOne ? item.bandNameOne : 'N/A';;
               
                cell2.appendChild(input5);
				}
				if(item.bandNameTwo !==null){
                const cell3 = row.insertCell(2);
                const input6 = document.createElement('span');
                //input6.type = 'text';
                input6.setAttribute('class',"band");
                // input6.setAttribute('disabled',"disabled");
                input6.innerHTML = item.bandNameTwo ? item.bandNameTwo : 'N/A';
                cell3.appendChild(input6);
                }
				if(item.bandNameThree !==null){
                const cell4 = row.insertCell(3);
                const input7 = document.createElement('span');
                //input7.type = 'text';
                input7.setAttribute('class',"band");
                //input7.setAttribute('disabled',"disabled");
                input7.innerHTML = item.bandNameThree ? item.bandNameThree : 'N/A';
                cell4.appendChild(input7);
                }
				if(item.bandNameFour !==null){
                const cell5 = row.insertCell(4);
                const input8 = document.createElement('span');
                //input8.type = 'text';
                input8.setAttribute('class',"band");
                //input8.setAttribute('disabled',"disabled");
                input8.innerHTML = item.bandNameFour ? item.bandNameFour : 'N/A';
                cell5.appendChild(input8);
                  }
				if(item.bandNameFive !==null){

                const cell6 = row.insertCell(5);
                const input9 = document.createElement('span');
               // input9.type = 'text';
               input9.setAttribute('class',"band");
               // input9.setAttribute('disabled',"disabled");
                input9.innerHTML = item.bandNameFive ?  item.bandNameFive : 'N/A';
                cell6.appendChild(input9);
                }
                if(item.bandNameSix !==null){

                const cell7 = row.insertCell(6);
                const input10 = document.createElement('span');
                //input10.type = 'text';
                input10.setAttribute('class',"band");
               // input10.setAttribute('disabled',"disabled");
                input10.innerHTML = item.bandNameSix ?  item.bandNameSix : 'N/A';
                cell7.appendChild(input10);
                }
            });
 
 
  }
  
  function loadTableData2(jsonData) {
        const tableBody = document.getElementById('newTable').querySelector('tbody');
   
    jsonData.data.forEach(item => {
	
			const row = tableBody.insertRow();
                
                var cell1 = row.insertCell(0);
			    var day = document.createElement("select");
			    day.name = "Select Day";
			    
			    var select = document.createElement("option");
			    select.text = "Select Day";
			    select.value = "";
			    
			    var perday = document.createElement("option");
			    perday.text = "Per Day";
			    perday.value = "Per Day";
			    
			    var perweak = document.createElement("option");
			    perweak.text = "Per Weak";
			    perweak.value = "Per Weak";
			    
			    var permonth = document.createElement("option");
			    permonth.text = "Per Month";
			    permonth.value = "Per Month";
			    
			    var perquater = document.createElement("option");
			    perquater.text = "Per Quarter";
			    perquater.value = "Per Quarter";
			    
			    var peryear = document.createElement("option");
			    peryear.text = "Per Year";
			    peryear.value = "Per Year";
			    
			  
			    day.setAttribute('class',"form-select period");
				
				day.add(select);
			    day.add(perday);
	    		day.add(perweak);
	    		day.add(permonth);
	    		day.add(perquater);
	    		day.add(peryear);
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