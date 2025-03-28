
function submitOrgId() {
    var orgIdInput = document.getElementById('orgIdInput').value;
    //var orgId = orgIdInput.value.trim();
	//orgId=70;
	

    // Validate that the orgId is a two-digit number
   // if (/^\d{2}$/.test(orgIdInput)) {
        // Send AJAX request to the server with orgId
        $.ajax({
            type: "POST",
            url: "/getjobMasterWithId",
            data: {
                "orgId": orgIdInput 
            },
            dataType: "json",
            success: function(data) {
                var tableBody = document.getElementById('JobMasterTableBody');
                const tableContainer = document.querySelector('.tabs-details-tbl');

                tableBody.innerHTML = ''; // Clear existing rows

				if (data && data.status === true && Array.isArray(data.data)) {
				    const results = data.data;

				    if (results.length > 0) {
				        tableContainer.style.display = ''; // Show table
				        results.forEach((item, index) => {
				            const row = document.createElement('tr');
				            row.innerHTML = `
				                <td>${index + 1}</td>
								<td>${item.managerLblId || 'N/A'}</td>
								<td>${item.jobDisc || 'N/A'}</td>
				                
				                <td>${item.createdBy || 'N/A'}</td>
				                <td>${item.orgId || 'N/A'}</td>
				                <td>${item.remarks || 'N/A'}</td>
				                <td>${item.status === 1 ? 'Active' : 'Inactive'}</td>
				                <td>
				                    <button class="btn btn-sm btn-${item.status === 1 ? 'danger' : 'success'}"
				                            onclick="toggleBankStatus(${item.id}, ${item.status})">
				                        ${item.status === 1 ? 'Deactivate' : 'Activate'}
				                    </button>
				                </td>
				                <td>
				                    <form action="/editjobTitlemaster" method="POST" style="display:inline;">
				                        <input type="hidden" name="id" value="${item.id}">
										<input type="hidden" name="orgId" value="${item.orgId}">
										<input type="hidden" name="jobDisc" value="${item.jobDisc}">
										<input type="hidden" name="managerLblId" value="${item.managerLblId}">
										<input type="hidden" name="createdBy" value="${item.createdBy}">
										<input type="hidden" name="remarks" value="${item.remarks}">
				                        <button type="submit" class="btn btn-sm btn-primary">Edit</button>
				                    </form>
				                </td>
				            `;
				            tableBody.appendChild(row);
				        });
				    } else {
				        tableContainer.style.display = 'block'; // Hide table if no results
				    }
				} else {
				    tableContainer.style.display = 'block'; // Hide table on server error or empty response
				}
            },
            error: function(e) {
                console.error('Error fetching data:', e);
                document.querySelector('.tabs-details-tbl').style.display = 'none'; // Hide table on error
            }
        });
   // } else {
       // alert('Please enter a valid two-digit Org ID.');
       // document.querySelector('.tabs-details-tbl').style.display = 'none'; // Hide table for invalid input
    //}
}








function toggleBankStatus(id, currentStatus) {
        const newStatus = currentStatus ;
        
        const bankData = { id: id, status: newStatus };
		console.log("inside toggle status");
        $.ajax({
            type: "POST",
            url: "/togglebankmasterStatus",
            data: bankData,
            success: function(data) {
				var response = jQuery.parseJSON(data);
                if (response.status ===true) {
                    loadBankMasterTable(); // Refresh the table data after updating
                } else {
                    console.log("Failed to update status");
                }
            },
            error: function(error) {
                console.error("Error updating voucher status:", error);
                console.log("Error updating voucher status");
            }
        });
        
    }

function sortTable(columnIndex) {
    const table = document.getElementById("managerMasterTable");
    const tbody = table.querySelector("tbody");
    const rows = Array.from(tbody.rows);

    if (!rows.length) {
        console.warn("No rows to sort.");
        return;
    }

    // Toggle sorting order
    let isAscending = table.getAttribute(`data-sort-column-${columnIndex}`) === "asc";
    isAscending = !isAscending;
    table.setAttribute(`data-sort-column-${columnIndex}`, isAscending ? "asc" : "desc");

    // Sort rows
    rows.sort((a, b) => {
        const cellA = a.cells[columnIndex]?.innerText?.trim() || "";
        const cellB = b.cells[columnIndex]?.innerText?.trim() || "";

        // Compare numeric or string values
        if (!isNaN(cellA) && !isNaN(cellB)) {
            return isAscending ? Number(cellA) - Number(cellB) : Number(cellB) - Number(cellA);
        } else {
            return isAscending
                ? cellA.localeCompare(cellB)
                : cellB.localeCompare(cellA);
        }
    });

    // Debugging: Log sorted rows
    console.log("Sorted rows:", rows.map(row => row.cells[columnIndex]?.innerText));

    // Re-append sorted rows
    tbody.innerHTML = "";
    rows.forEach(row => tbody.appendChild(row));
}


