
 function saveEmployeeBandTab1(){
	
	    alert("saveEmployeeBand");
	   
		var formData = new FormData(formtab1);
		
	
	   
	    
 }
 
	 function saveEmployeeBandTab2(){
		 
			var employeeBandEnabled = document.getElementById("employeeBandEnabled").value;
			
			var dropdown = document.getElementById("dropdown").value;
			var numericFigure = document.getElementById("numericFigure").value;
			var startingEmployeeBand = document.getElementById("startingEmployeeBand").value;
			
			var addtionalTiersCheckBox = document.getElementById("addtionalTiersCheckBox").value;
			
		    alert("saveEmployeeBandTab 2");
		    // get dynamic table data start
			var formData = new FormData(formtab2);
			
			 formData.append("employeeBandEnabled", employeeBandEnabled);
			 formData.append("dropdown", dropdown);
			 formData.append("numericFigure", numericFigure);
			 formData.append("startingEmployeeBand", startingEmployeeBand);
			 formData.append("addtionalTiersCheckBox", addtionalTiersCheckBox);
			 
			 
		    var allbandValue = [];
			var allBandTiersValue =[];
			var allList =[];	 
	        const employeeBand2 = document.querySelectorAll('.empolyeeinput01');
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
	        });
	        
	        allList.push(allbandValue,allBandTiersValue)
		    console.log("allbandValue-----"+allbandValue);
		    console.log("allBandTiersValue-----"+allBandTiersValue);
		    console.log("allList-----"+allList);
		     
		   // get dynamic table data start
		    
		    formData.append("listBand", allbandValue);
		    formData.append("listBandTier", allBandTiersValue);
		    
		    	$.ajax({
			type: "POST",
		     url:"/saveEmployeeBandTab2",
	         data: formData,
	         processData: false,
	         contentType: false,       		 
	            success: function(data){
	            newData = data;
				var data1 = jQuery.parseJSON(newData);
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