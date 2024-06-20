
function change() {
	
  var startingEmployeeAlpha = document.getElementById('startingEmployeeAlpha').value;
  var startingEmployeeNumeric = document.getElementById('startingEmployeeNumeric').value;
  
  if(startingEmployeeAlpha !==null || startingEmployeeAlpha !==""){
	  document.getElementById("reviewDropdown").value = startingEmployeeAlpha;
	  document.getElementById("reviewDropdown").text = startingEmployeeAlpha;
  }
   if(startingEmployeeNumeric !==null || startingEmployeeNumeric !==""){
	  document.getElementById("reviewDropdown").value = startingEmployeeNumeric;
	  document.getElementById("reviewDropdown").text = startingEmployeeNumeric;
  }

};

function validateTab1(){
			
			var employeeBandEnabled = document.getElementById("employeeBandEnabled").value;
			var employerid = document.getElementById("employerId").value;
			var dropdown = document.getElementById("dropdown").value;
			//var numericFigure = document.getElementById("numericFigure").value;
			
			var startingEmployeeAlpha = document.getElementById("startingEmployeeAlpha");
			var startingEmployeeNumeric = document.getElementById("startingEmployeeNumeric");
			
			if(employeeBandEnabled.checked==false){
					document.getElementById("employeeBandEnabledError").innerHTML="Please Check Employee Band";
					document.getElementbyId("employeeBandEnabled").focus();
					return false;
				}else{
					document.getElementById("employeeBandEnabledError").innerHTML="";
					
			}
			if(dropdown==""){
					document.getElementById("dropdownError").innerHTML="Please select number of Employee Bands ";
					document.getElementbyId("dropdown").focus();
					return false;
				}else{
					document.getElementById("dropdownError").innerHTML="";
			}
		
			if(document.getElementById('numericFigure').checked) {
				  var selectedValue = document.getElementById('numericFigure').value;  
				
				  document.getElementById("numericFigureError").innerHTML="";
			}   
			else if(document.getElementById('AlphabeticalFigure').checked){
			    var selectedValue = document.getElementById('AlphabeticalFigure').value;  
			    document.getElementById("numericFigureError").innerHTML="";	
			   
			}
			else{
				document.getElementById("numericFigureError").innerHTML="Please select Bands representation";
				return false;
			}
}

	 
	 function handleDropdownNumeric() {
		 document.getElementById("continewTab1").style.display="none";
		  document.getElementById("continewTab2").style.display="block";
		
         clearTable();
         addRows();
         document.getElementById("startingEmployeeNumeric").style.display="block";
         document.getElementById("startingEmployeeAlpha").style.display="none";
     }
	 
	 function handleDropdownAlpha() {
		
		 document.getElementById("continewTab1").style.display="none";
		 document.getElementById("continewTab2").style.display="block";
         clearTable();
         addRows2();
         document.getElementById("startingEmployeeNumeric").style.display="none";
         document.getElementById("startingEmployeeAlpha").style.display="block";
     }

     function clearTable() {
         const table = document.getElementById('bandTable').getElementsByTagName('tbody')[0];
         while (table.rows.length > 0) {
             table.deleteRow(0);
         }
     }

     function addRows() {
         const dropdown = document.getElementById('dropdown');
         const table = document.getElementById('bandTable').getElementsByTagName('tbody')[0];
         const numberOfRows = parseInt(dropdown.value, 10);

         if (!isNaN(numberOfRows)) {
             for (let i = 0; i < numberOfRows; i++) {
                 // Create a new row and cells
                 const newRow = table.insertRow();
                 const newCell1 = newRow.insertCell(0);
                 const newCell2 = newRow.insertCell(1);

                 // Add a textbox to the first cell
                 const textbox = document.createElement('input');
                 const newDropdown = document.createElement('select');
                 
                 textbox.placeholder = `Employee Band ${i + 1}`;
                 if(i === 0){
                 textbox.value = `Band 1`;
                 }
                 else if(i=== 1){
                	 textbox.value = `Band 2`;
                 }
                 else if(i=== 2){
                	 textbox.value = `Band 3`;
                 }
                 else if(i=== 3){
                	 textbox.value = `Band 4`;
                 }
                 else if(i=== 4){
                	 textbox.value = `Band 5`;
                 }
                 else if(i=== 5){
                	 textbox.value = `Band 6`;
                 }
               
                 textbox.type = 'text';
                 textbox.setAttribute('class',"empolyeeinput01");
                  textbox.setAttribute('disabled',"disabled");
                 newCell1.appendChild(textbox);

                 // Add any other content to the second cell (e.g., a button, text, etc.)                
                 
                 for (let j = 1; j <= 5; j++) {
				 
                 const option = document.createElement('option');
                 option.value = `${j}`;
                 // option.text = `Option ${j}`;
                 option.text = `${j}`; 
                 newDropdown.appendChild(option);
 				 newDropdown.setAttribute('class',"did-floating-select dropdownvalue");
                 
                 newCell2.appendChild(newDropdown);
             	}
         
             }
             // Reset dropdown to default value
             
         }
     }

     function addRows2() {
         const dropdown = document.getElementById('dropdown');
         const table = document.getElementById('bandTable').getElementsByTagName('tbody')[0];
         const numberOfRows = parseInt(dropdown.value, 10);

         if (!isNaN(numberOfRows)) {
             for (let i = 0; i < numberOfRows; i++) {
                 // Create a new row and cells
                 const newRow = table.insertRow();
                 const newCell1 = newRow.insertCell(0);
                 const newCell2 = newRow.insertCell(1);

                 // Add a textbox to the first cell
                 const textbox = document.createElement('input');
                 const newDropdown = document.createElement('select');
                 
                 textbox.placeholder = `Employee Band ${i + 1}`;
                 if(i === 0){
                 textbox.value = `Band A`;
                 }
                 else if(i=== 1){
                	 textbox.value = `Band B`;
                 }
                 else if(i=== 2){
                	 textbox.value = `Band C`;
                 }
                 else if(i=== 3){
                	 textbox.value = `Band D`;
                 }
                 else if(i=== 4){
                	 textbox.value = `Band E`;
                 }
                 else if(i=== 5){
                	 textbox.value = `Band F`;
                 }
               
                 textbox.type = 'text';
                 textbox.setAttribute('class',"empolyeeinput01");
                  textbox.setAttribute('disabled',"disabled");
                 newCell1.appendChild(textbox);

                 // Add any other content to the second cell (e.g., a button, text, etc.)                
                 
                 for (let j = 1; j <= 5; j++) {
                 const option = document.createElement('option');
                 option.value = `${j}`;
                 // option.text = `Option ${j}`;
                 option.text = `${j}`; 
                 newDropdown.appendChild(option);
 				 newDropdown.setAttribute('class',"did-floating-select dropdownvalue");   
                 newCell2.appendChild(newDropdown);
          	   }
         
             }
             // Reset dropdown to default value
             
         }
     }
     
 function updateTextboxesNumeric() {
		 
		var element = document.getElementById('alphaSeniority');
		element.classList.add("abc");
		  
		var element2  = document.getElementById('numericSeniority');
		element2.classList.remove("abc");
	  
        const dropdownValue = document.getElementById('dropdown').value;
        const textboxContainer = document.getElementById('textboxContainer');	
		const dropdownContainer = document.getElementById('dropdownContainer');

         // Clear previous dropdowns
         dropdownContainer.innerHTML = '';

         // Clear previous textboxes
         textboxContainer.innerHTML = '';

         // Add new textboxes based on dropdownValue
         for (let i = 0; i < dropdownValue; i++) {
             const textbox = document.createElement('input');
			 const newDropdown = document.createElement('select');
				
             textbox.type = 'text';
             textbox.setAttribute('class',"empolyeeinput01");
             textbox.setAttribute('disabled',"disabled");
             
             textbox.placeholder = `Employee Band ${i + 1}`;
             if(i === 0){
             textbox.value = `Band 1`;
             }
             else if(i=== 1){
            	 textbox.value = `Band 2`;
             }
             else if(i=== 2){
            	 textbox.value = `Band 3`;
             }
             else if(i=== 3){
            	 textbox.value = `Band 4`;
             }
             else if(i=== 4){
            	 textbox.value = `Band 5`;
             }
             else if(i=== 5){
            	 textbox.value = `Band 6`;
             }
             textboxContainer.appendChild(textbox);
             textboxContainer.appendChild(document.createElement('br'));
				
			 for (let j = 0; j <= 5; j++) {
				 
                 const option = document.createElement('option');
                 option.value = `${j}`;
                 // option.text = `Option ${j}`;
                 option.text = `${j}`;
                 newDropdown.appendChild(option);
                 newDropdown.setAttribute('class',"did-floating-select dropdownvalue");
                
             }

             dropdownContainer.appendChild(newDropdown);
             dropdownContainer.appendChild(document.createElement('br'));
         }
     }
 	 
	 function saveEmployeeBandTab2(){
		 
			var employeeBandEnabled = document.getElementById("employeeBandEnabled").checked;
			var employerid = document.getElementById("employerId").value;
			var dropdown = document.getElementById("dropdown").value;
			var numericFigure=null;
			var startingEmployeeAlpha = document.getElementById("startingEmployeeAlpha").value;
			var startingEmployeeNumeric = document.getElementById("startingEmployeeNumeric").value;
			var addtionalTiersCheckBox = document.getElementById("addtionalTiersCheckBox").value;
			
		//	var status = document.getElementById('AlphabeticalFigure').checked;
			//var status2 = document.getElementById('numericFigure').checked;
			var startingBand=null;
			
			var introAddTierFlag=null;
			var bandEnabled=null;
			
			if(employeeBandEnabled == true){
					bandEnabled="YES";
				}else{
					bandEnabled="NO";
				}
			if(dropdown==""){
					document.getElementById("dropdownError").innerHTML="Please select number of Employee Bands ";
					document.getElementbyId("dropdown").focus();
					return false;
				}else{
					document.getElementById("dropdownError").innerHTML="";
					
			}
			
			/*if(document.getElementById('numericFigure').checked) {
				  var selectedValue = document.getElementById('numericFigure').value;  
				  //alert(selectedValue);
				  numericFigure=selectedValue;
				  startingBand=startingEmployeeNumeric;
				  document.getElementById("numericFigureError").innerHTML="";
				  
			}   
			else if(document.getElementById('AlphabeticalFigure').checked){
			    var selectedValue = document.getElementById('AlphabeticalFigure').value;  
			    document.getElementById("numericFigureError").innerHTML="";	
			   // alert(selectedValue);
			    numericFigure=selectedValue;
			    startingBand=startingEmployeeAlpha;
			}
			else{
				document.getElementById("numericFigureError").innerHTML="Please select Bands representation";
				return false;
			}
			*/
				if(addtionalTiersCheckBox.checked===false){
					//document.getElementById("employeeBandEnabledError").innerHTML="Please Check Employee Band";
					//document.getElementbyId("employeeBandEnabled").focus();
					introAddTierFlag = "NO";
					return false;
				}else{
					introAddTierFlag="YES";
					//document.getElementById("employeeBandEnabledError").innerHTML="";
					
			}
			
		    // get dynamic table data start
			var formData = new FormData(formtab2);
			
			 formData.append("employerId", employerid);
			 formData.append("bandEnabled", bandEnabled);
			 formData.append("employeeBandNo", dropdown);
			 formData.append("employeeBandNoAlpha", numericFigure);
			 formData.append("employeeBandOrder", startingBand);
			 //formData.append("employeeBandOrder", "A");
			 formData.append("introAddTierFlag", introAddTierFlag);
			 formData.append("Status", 1)
			 var allInputValues = [];
	
		 $('.newTable tbody tr').each(function () {
		        var employeeBandTier = $(this).find('select.dropdownvalue').val();
		        var employeeBand = $(this).find('input.empolyeeinput01').val();       
			    var rowvalue=employeeBand+"@"+employeeBandTier;
			    allInputValues.push(rowvalue);
		   });  
		    
	    
		    formData.append("listArray", allInputValues);
		   document.getElementById("signinLoader").style.display="flex";
		    	$.ajax({
			type: "POST",
		     url:"/saveEmployeeBandTab2",
	         data: formData,
	         processData: false,
	         contentType: false,       		 
	            success: function(data){
	            newData = data;
				var data1 = jQuery.parseJSON(newData);
				
				document.getElementById("continewNext").style.display="block";
				document.getElementById("continew2").style.display="none";
				document.getElementById("signinLoader").style.display="none";
				getEmployeeBandWithTiers();
				//console.log(data1);
				//console.log(data.status);
				//document.getElementById("signinLoader").style.display="none";
				if(data1.status==true){
					
					/*  document.getElementById("payrollsuccessmsg").innerHTML=data1.message;
					 document.getElementById("payrollsuccessmsgdiv").style.display="block"; */
				}else if(data1.status==false){
					 /* document.getElementById("payrollfailmsg").innerHTML=data1.message;
					 document.getElementById("payrollfailmsgDiv").style.display="block"; */
					
				}else{
					/*  document.getElementById("payrollfailmsgDiv").style.display="none";
					 document.getElementById("payrollsuccessmsgdiv").style.display="none"; */					
				}
	         },
	         error: function(e){
	             alert('Error: ' + e);
	         }
	    });		 
	 }
	 
	 

function getEmployeeBandWithTiers() {

	var employerId=document.getElementById("employerId").value;
	document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "POST",
		url: "/saveEmployeeBandTierTab3",
		data: {
				"employerId": employerId,
			},
       		  beforeSend : function(xhr) {
				//xhr.setRequestHeader(header, token);
				},
            success: function(data){
	 		newData = data;
			var data1 = jQuery.parseJSON( newData );
			bindJsonToTable(data1);
      	    document.getElementById("signinLoader").style.display="none";		  
    		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}

function bindJsonToTable(jsonData) {
             const table = document.getElementById('dynamicTable').getElementsByTagName('tbody')[0];
             const list = jsonData.data.list;
             
			 const newDropdown = document.getElementById('reviewDropdown');
             const option = document.createElement('option');
             option.value = jsonData.data.employeeBandOrder;
             option.text = jsonData.data.employeeBandOrder;
             newDropdown.appendChild(option);
                 
             table.innerHTML = '';
             // Loop through the JSON data and create rows
             list.forEach(item => {
               const row = table.insertRow();
                

                const cell1 = row.insertCell(0);
                const input3 = document.createElement('input');
                input3.type = 'text';
                input3.setAttribute('class',"empolyeeinput01");
                input3.setAttribute('disabled',"disabled");
                input3.value = item.employeeBand;
                cell1.appendChild(input3);

				if(item.additionalTiersOne!==null){
                const cell2 = row.insertCell(1);
                const input5 = document.createElement('input');
                input5.type = 'text';
                input5.setAttribute('class',"empolyeeinput01");
                input5.setAttribute('disabled',"disabled");
                input5.value = item.additionalTiersOne ? item.additionalTiersOne : 'N/A';;
                cell2.appendChild(input5);
				}
				if(item.additionalTiersTwo!==null){
                const cell3 = row.insertCell(2);
                const input6 = document.createElement('input');
                input6.type = 'text';
                 input6.setAttribute('class',"empolyeeinput01");
                 input6.setAttribute('disabled',"disabled");
                input6.value = item.additionalTiersTwo ? item.additionalTiersTwo : 'N/A';
                cell3.appendChild(input6);
                }
				if(item.additionalTiersThree!==null){
                const cell4 = row.insertCell(3);
                const input7 = document.createElement('input');
                input7.type = 'text';
                input7.setAttribute('class',"empolyeeinput01");
                input7.setAttribute('disabled',"disabled");
                input7.value = item.additionalTiersThree ? item.additionalTiersThree : 'N/A';
                cell4.appendChild(input7);
                }
				if(item.additionalTiersFour!==null){
                const cell5 = row.insertCell(4);
                const input8 = document.createElement('input');
                input8.type = 'text';
                input8.setAttribute('class',"empolyeeinput01");
                input8.setAttribute('disabled',"disabled");
                input8.value = item.additionalTiersFour ? item.additionalTiersFour : 'N/A';
                cell5.appendChild(input8);
                  }
				if(item.additionalTiersFive!==null){

                const cell6 = row.insertCell(5);
                const input9 = document.createElement('input');
                input9.type = 'text';
                input9.setAttribute('class',"empolyeeinput01");
                input9.setAttribute('disabled',"disabled");
                input9.value = item.additionalTiersFive ? item.additionalTiersFive : 'N/A';
                cell6.appendChild(input9);
                }
            });
        }

     function viewEmployeeBand() {

			var employerId=document.getElementById("employerId").value;
			document.getElementById("signinLoader2").style.display="flex";
			
			$.ajax({
				type: "POST",
				url: "/saveEmployeeBandTierTab3",
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
						document.getElementById("viewBandDiv").style.display="block";
						document.getElementById("defaultDiv").style.display="none";
						bindJsonToTableEmployee(data1);
					}
		      	    document.getElementById("signinLoader2").style.display="none";		  
		    		},
				error: function(e) {
					alert('Failed to fetch JSON data' + e);
				}
		});
}

function bindJsonToTableEmployee(jsonData) {
             const table = document.getElementById('viewEmployeeBandTable').getElementsByTagName('tbody')[0];
             const list = jsonData.data.list;
             
			 const newDropdown = document.getElementById('reviewDropdown');
             const option = document.createElement('option');
             option.value = jsonData.data.employeeBandOrder;
             option.text = jsonData.data.employeeBandOrder;
             
             var bandRepresentation = jsonData.data.employeeBandNoAlpha;
             var employeeBandNo = jsonData.data.employeeBandNo;
             if(bandRepresentation==="Alphabetical")
             {
						document.getElementById('AlphabeticalFigureView').checked = true;
				}
				else{
					document.getElementById('numericFigure').checked = true;
				}
			document.getElementById('Noofband').value  = employeeBandNo;
			
             newDropdown.appendChild(option);
                 
             table.innerHTML = '';
             // Loop through the JSON data and create rows
             list.forEach(item => {
               const row = table.insertRow();
                

                const cell1 = row.insertCell(0);
                const input3 = document.createElement('input');
                input3.type = 'text';
               // input3.setAttribute('class',"empolyeeinput01");
                input3.setAttribute('disabled',"disabled");
                input3.value = item.employeeBand;
                cell1.appendChild(input3);

				if(item.additionalTiersOne!==null){
                const cell2 = row.insertCell(1);
                const input5 = document.createElement('input');
                input5.type = 'text';
                //input5.setAttribute('class',"empolyeeinput01");
                input5.setAttribute('disabled',"disabled");
                input5.value = item.additionalTiersOne ? item.additionalTiersOne : 'N/A';;
                cell2.appendChild(input5);
				}
				if(item.additionalTiersTwo!==null){
                const cell3 = row.insertCell(2);
                const input6 = document.createElement('input');
                input6.type = 'text';
                 //input6.setAttribute('class',"empolyeeinput01");
                 input6.setAttribute('disabled',"disabled");
                input6.value = item.additionalTiersTwo ? item.additionalTiersTwo : 'N/A';
                cell3.appendChild(input6);
                }
				if(item.additionalTiersThree!==null){
                const cell4 = row.insertCell(3);
                const input7 = document.createElement('input');
                input7.type = 'text';
                //input7.setAttribute('class',"empolyeeinput01");
                input7.setAttribute('disabled',"disabled");
                input7.value = item.additionalTiersThree ? item.additionalTiersThree : 'N/A';
                cell4.appendChild(input7);
                }
				if(item.additionalTiersFour!==null){
                const cell5 = row.insertCell(4);
                const input8 = document.createElement('input');
                input8.type = 'text';
                //input8.setAttribute('class',"empolyeeinput01");
                input8.setAttribute('disabled',"disabled");
                input8.value = item.additionalTiersFour ? item.additionalTiersFour : 'N/A';
                cell5.appendChild(input8);
                  }
				if(item.additionalTiersFive!==null){

                const cell6 = row.insertCell(5);
                const input9 = document.createElement('input');
                input9.type = 'text';
                //input9.setAttribute('class',"empolyeeinput01");
                input9.setAttribute('disabled',"disabled");
                input9.value = item.additionalTiersFive ? item.additionalTiersFive : 'N/A';
                cell6.appendChild(input9);
                }
            });
        }