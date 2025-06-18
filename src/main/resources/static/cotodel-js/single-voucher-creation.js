function singleVoucherValidation(){
		
		var banklistElem = document.getElementById("banklist");
		var banklist = banklistElem.value;
	
		if (banklist === "" || banklist == null) {
		    alert("Please select bank");
		    banklistElem.style.borderColor = '#F24822'; // ✅ Now targeting the element
		    return false;
		}
		else{
			banklistElem.style.borderColor = ''; 
		}
	    const tableBody = document.getElementById('voucherTableBody');
	    const rows = tableBody.querySelectorAll('tr');
	    const tableData = [];
	    const errorMessages = [];
	    let overallIsValid = true; // Track global validation state

	    rows.forEach((row, index) => {
	        let isValid = true; // Track individual row validation state

			const nameInput = row.querySelector('input[placeholder="Enter Name"]');
	        const mobileInput = row.querySelector('input[placeholder="Enter Mobile Number"]');
	        const voucherInput = row.querySelector('.table-input-voucher');
			const selectMCC = row.querySelector('.selectMCC');
			const selectPurpose = row.querySelector('.selectPurpose');
			const selectMCCDescription = row.querySelector('.selectMCCDescription');
			const selectPurposeDescription = row.querySelector('.selectPurposeDescription');
	        const redemptionSelect = row.querySelector('.redemptionType');
	        const amountInput = row.querySelector('input[placeholder="Enter Amount"]');
	        const dateInput = row.querySelector('input[type="date"]');
	        const validitySelect = row.querySelector('.validity');

	        // Reset field highlights
	        if (nameInput) nameInput.style.borderColor = '';
	        if (mobileInput) mobileInput.style.borderColor = '';
	        if (voucherInput) voucherInput.style.borderColor = '';
	        if (redemptionSelect) redemptionSelect.style.borderColor = '';
	        if (amountInput) amountInput.style.borderColor = '';
	        if (dateInput) dateInput.style.borderColor = '';
	        if (validitySelect) validitySelect.style.borderColor = '';

	        // Validate Name
	        if (!nameInput || !nameInput.value) {
	            overallIsValid = false;
	            isValid = false;
	            errorMessages.push(`Row ${index + 1}: Name is required.`);
	            if (nameInput) nameInput.style.borderColor = '#F24822';
	        }

	        // Validate Mobile
	        if (!mobileInput || mobileInput.value.length !== 10 || isNaN(mobileInput.value)) {
	            overallIsValid = false;
	            isValid = false;
	            errorMessages.push(`Row ${index + 1}: Mobile must be 10 digits.`);
	            if (mobileInput) mobileInput.style.borderColor = '#F24822';
	        }

	        // Validate Voucher
	        if (!voucherInput || !voucherInput.value) {
	            overallIsValid = false;
	            isValid = false;
	            errorMessages.push(`Row ${index + 1}: Voucher type is required.`);
	            if (voucherInput) voucherInput.style.borderColor = '#F24822';
	        }

	        // Validate Redemption Type
	        if (!redemptionSelect || !redemptionSelect.value) {
	            overallIsValid = false;
	            isValid = false;
	            errorMessages.push(`Row ${index + 1}: Redemption type is required.`);
	            if (redemptionSelect) redemptionSelect.style.borderColor = '#F24822';
	        }

	        // Validate Amount
	        if (!amountInput || isNaN(amountInput.value) || amountInput.value <= 0) {
	            overallIsValid = false;
	            isValid = false;
	            errorMessages.push(`Row ${index + 1}: Amount must be a positive number.`);
	            if (amountInput) amountInput.style.borderColor = '#F24822';
	        }
			const today = new Date().toISOString().split('T')[0];
			if (dateInput) {
			    dateInput.setAttribute('min', today);
			}
	        // Validate Start Date
	        if (!dateInput || !dateInput.value) {
	            overallIsValid = false;
	            isValid = false;
	            errorMessages.push(`Row ${index + 1}: Start date is required.`);
	            if (dateInput) dateInput.style.borderColor = '#F24822';
	        }

	        // Validate Validity
	        if (!validitySelect || !validitySelect.value) {
	            overallIsValid = false;
	            isValid = false;
	            errorMessages.push(`Row ${index + 1}: Validity is required.`);
	            if (validitySelect) validitySelect.style.borderColor = '#F24822';
	        }

	        if (isValid) {
			const rowData = {
						name: nameInput ? nameInput.value : '',
			            mobile: mobileInput ? mobileInput.value : '',
			            voucher: voucherInput ? voucherInput.value : '',
						mcc: selectMCC ? selectMCC.value : '',
						purposeCode: selectPurpose ? selectPurpose.value : '',
						mccDescription: selectMCCDescription ? selectMCCDescription.value : '',
						purposeDescription: selectPurposeDescription ? selectPurposeDescription.value : '',
			            redemptionType: redemptionSelect ? redemptionSelect.value : '',
						amount: amountInput ? amountInput.value : '',
			            startDate: dateInput ? dateInput.value : '',
			            validity: validitySelect ? validitySelect.value : ''
			           };
	            tableData.push(rowData);
			
	        }
			//console.log(tableData);
		
	    });
		
		let totalAmount = 0;
		let validRowCount = tableData.length;

		tableData.forEach(row => {
		    const amount = parseFloat(row.amount);
		    if (!isNaN(amount)) {
		        totalAmount += amount;
		    }
		});
		document.getElementById("totalRowCount").textContent = `${validRowCount}`;
		document.getElementById("totalAmount").textContent = `₹${totalAmount.toFixed(2)}`;
		console.log("tableData2");
		console.log(tableData);
		const commonErrorMsg = document.getElementById('common-error-msg');
		if (!overallIsValid) {
		    commonErrorMsg.textContent = errorMessages.join('\n');
		    commonErrorMsg.style.display = 'block';
		    return false; // 🛑 Prevent proceeding if validation failed
		} else {
		    commonErrorMsg.style.display = 'none';
		    console.log("tableData2");
		    console.log(tableData);

	const sourceRows = document.querySelectorAll("#sourceTable tbody tr");
				 const targetBody = document.querySelector("#targetTable tbody");

				 targetBody.innerHTML = ""; // Clear previous content

				 // Loop through all rows except the last two (error row + add-new row)
				 for (let i = 0; i < sourceRows.length - 1; i++) {
				   const row = sourceRows[i];
				   const cells = row.querySelectorAll("td");
				   const newRow = document.createElement("tr");

				   // Skip last <td> (delete icon)
				   for (let j = 0; j < cells.length - 1; j++) {
				     const cell = cells[j];
				     const newCell = document.createElement("td");
				     let value = "";

				     // Check for input, select, or plain text
				     const input = cell.querySelector("input");
				     const select = cell.querySelector("select");

				     if (input) {
				       value = input.value.trim();
				     } else if (select) {
				       value = select.options[select.selectedIndex].text;
				     } else {
				       value = cell.textContent.trim();
				     }

				     newCell.textContent = value;
				     newRow.appendChild(newCell);
				   }

				   targetBody.appendChild(newRow);
				 }
			   var element = document.getElementById("lable2");
			   							   	element.classList.add("active");
			   var element = document.getElementById("lable3");
			   	   	element.classList.add("active");
			   	   	
				$("#selectvouchers-wrap04").show();
				$("#selectvouchers-wrap03").hide();

		    // ✅ Move to next tab only if all is valid
		    document.getElementById("lable2").classList.add("active");
		    document.getElementById("lable3").classList.add("active");

		    $("#selectvouchers-wrap04").show();
		    $("#selectvouchers-wrap03").hide();
		}
	
		const sourceRows = document.querySelectorAll("#sourceTable tbody tr");
		const targetBody = document.querySelector("#targetTable tbody");
		targetBody.innerHTML = ""; // Clear previous content
		 // Loop through all rows except the last two (error row + add-new row)
		 for (let i = 0; i < sourceRows.length - 1; i++) {
		   const row = sourceRows[i];
		   const cells = row.querySelectorAll("td");
		   const newRow = document.createElement("tr");

		   // Skip last <td> (delete icon)
		   for (let j = 0; j < cells.length - 1; j++) {
		     const cell = cells[j];
		     const newCell = document.createElement("td");
		     let value = "";

		     // Check for input, select, or plain text
		     const input = cell.querySelector("input");
		     const select = cell.querySelector("select");

		     if (input) {
		       value = input.value.trim();
		     } else if (select) {
		       value = select.options[select.selectedIndex].text;
		     } else {
		       value = cell.textContent.trim();
		     }

		     newCell.textContent = value;
		     newRow.appendChild(newCell);
		   }

		   targetBody.appendChild(newRow);
		 }
			 
	}
		