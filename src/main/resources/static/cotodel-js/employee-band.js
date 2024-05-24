
	 function saveEmployeeBandTab2(){
		 
			var employeeBandEnabled = document.getElementById("employeeBandEnabled").value;
			  var employerid = document.getElementById("employerId").value;
			var dropdown = document.getElementById("dropdown").value;
			var numericFigure = document.getElementById("numericFigure").value;
			var startingEmployeeBand = document.getElementById("startingEmployeeBand").value;
			
			var addtionalTiersCheckBox = document.getElementById("addtionalTiersCheckBox").value;
			
		    alert("saveEmployeeBandTab 2");
		    // get dynamic table data start
			var formData = new FormData(formtab2);
			
			 formData.append("employerId", employerid);
			 formData.append("employeeBandEnabled", employeeBandEnabled);
			 formData.append("dropdown", dropdown);
			 formData.append("numericFigure", numericFigure);
			 formData.append("startingEmployeeBand", startingEmployeeBand);
			 formData.append("addtionalTiersCheckBox", addtionalTiersCheckBox);
			
			  var allInputValues = [];
	
		 $('.newTable tbody tr').each(function () {
			 
		        var employeeBandTier = $(this).find('select.dropdownvalue').val();
		        var employeeBand = $(this).find('input.empolyeeinput01').val();
		       
		      var rowvalue=employeeBand+"@"+employeeBandTier;
		       allInputValues.push(rowvalue);
		    });  
		    
	    
	       /* const employeeBand2 = document.querySelectorAll('.empolyeeinput01');
	        // Iterate over each textbox and log its value
	        employeeBand2.forEach((textbox) => {
	            console.log(textbox.value);
	             allbandValue.push(textbox.value);
	        });
	        
	         const dropdowns = document.querySelectorAll('.dropdownvalue');
	        // Iterate over each dropdown and log its value
	        dropdowns.forEach((dropdown) => {
	            console.log(dropdown.value);
	            allBandTiersValue.push(dropdown.value);
	        });*/
	        
	       /* allList.push(allbandValue,allBandTiersValue)
		    console.log("allbandValue-----"+allbandValue);
		    console.log("allBandTiersValue-----"+allBandTiersValue);
		    console.log("allList-----"+allList);
		   */  
		   // get dynamic table data start
		    
		    formData.append("listArray", allInputValues);
		   
		    	$.ajax({
			type: "POST",
		     url:"/saveEmployeeBandTab2",
	         data: formData,
	         processData: false,
	         contentType: false,       		 
	            success: function(data){
	            newData = data;
				var data1 = jQuery.parseJSON(newData);
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
	//document.getElementById("signinLoader").style.display="flex";
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
      	     		  
    		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}

function bindJsonToTable(jsonData) {
            const table = document.getElementById('dynamicTable').getElementsByTagName('tbody')[0];
            const list = jsonData.data.list;

            // Clear existing rows
            table.innerHTML = '';

            // Loop through the JSON data and create rows
            list.forEach(item => {
                const row = table.insertRow();
                
                /*const cell1 = row.insertCell(0);
                const input1 = document.createElement('input');
                input1.type = 'text';
                input1.value = item.id;
                cell1.appendChild(input1);

                const cell2 = row.insertCell(1);
                const input2 = document.createElement('input');
                input2.type = 'text';
                input2.value = item.employeeBandId;
                cell2.appendChild(input2);*/

                const cell1 = row.insertCell(0);
                const input3 = document.createElement('input');
                input3.type = 'text';
                input3.setAttribute('class',"empolyeeinput01");
                input3.value = item.employeeBand;
                cell1.appendChild(input3);

           /*     const cell4 = row.insertCell(3);
                const input4 = document.createElement('input');
                input4.type = 'text';
                input4.value = item.additionalTiers;
                cell4.appendChild(input4);*/
				if(item.additionalTiersOne!==null){
                const cell2 = row.insertCell(1);
                const input5 = document.createElement('input');
                input5.type = 'text';
                input5.setAttribute('class',"empolyeeinput01");
                input5.value = item.additionalTiersOne ? item.additionalTiersOne : 'N/A';;
                cell2.appendChild(input5);
				}
				if(item.additionalTiersTwo!==null){
                const cell3 = row.insertCell(2);
                const input6 = document.createElement('input');
                input6.type = 'text';
                 input6.setAttribute('class',"empolyeeinput01");
                input6.value = item.additionalTiersTwo ? item.additionalTiersTwo : 'N/A';
                cell3.appendChild(input6);
                }
				if(item.additionalTiersThree!==null){
                const cell4 = row.insertCell(3);
                const input7 = document.createElement('input');
                input7.type = 'text';
                input7.setAttribute('class',"empolyeeinput01");
                input7.value = item.additionalTiersThree ? item.additionalTiersThree : 'N/A';
                cell4.appendChild(input7);
                }
				if(item.additionalTiersFour!==null){
                const cell5 = row.insertCell(4);
                const input8 = document.createElement('input');
                input8.type = 'text';
                input8.setAttribute('class',"empolyeeinput01");
                input8.value = item.additionalTiersFour ? item.additionalTiersFour : 'N/A';
                cell5.appendChild(input8);
                  }
				if(item.additionalTiersFive!==null){

                const cell6 = row.insertCell(5);
                const input9 = document.createElement('input');
                input9.type = 'text';
                input9.setAttribute('class',"empolyeeinput01");
                input9.value = item.additionalTiersFive ? item.additionalTiersFive : 'N/A';
                cell6.appendChild(input9);
                }
            });
        }

     