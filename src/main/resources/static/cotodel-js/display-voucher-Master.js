
 function getVoucherData() {
    fetch('/getvoucherTypeMaster')
        .then(response => response.json())
        .then(data => {
			console.log(data);
            const tableBody = document.getElementById('voucherMasterTableBody');
            tableBody.innerHTML = ''; // Clear existing rows

            if (data && data.data && Array.isArray(data.data)) {
                data.data.forEach(voucher => {
					
                    const row = document.createElement('tr');

                    row.innerHTML = `
					<td>${voucher.voucherName || ''}</td>
					<td>${voucher.purposeCode || ''}</td>
					<td>${voucher.purposeDesc || ''}</td>
					<td>${voucher.mcc || ''}</td>
					<td>${voucher.mccDesc || ''}</td>
					<td>
                    <img src="data:image/svg+xml;base64,${voucher.voucherIcon}" 
                         alt="" style="width: 50px;">
                	</td>
					<td>
                    <img src="data:image/svg+xml;base64,${voucher.mccIcon}" 
                         alt="" style="width: 50px;">
                	</td>
					<td>
                    <img src="data:image/svg+xml;base64,${voucher.mccMainIcon}" 
                         alt="" style="width: 50px;">
                	</td>
						
                        
                        
                        <td><img src="${voucher.status === 1 ? 'img/check-circle-green.svg' : 'img/close-circle-red.svg'}" 
	                                alt="${voucher.status === 1 ? 'Active' : 'Inactive'}"
	                                style="width: 20px; height: 20px;"
                            >
						                        </td>
                        <td>
                            <button class="btn btn-${voucher.status === 1 ? 'danger' : 'success'}" 
                                    onclick="toggleStatus(${voucher.id},${voucher.status})">
                                ${voucher.status === 1 ? 'Deactivate' : 'Activate'}
                            </button>
                        </td>
						<td>
						    <a href="/editVoucherMaster?vid=${voucher.id}" 
						       style="position: relative;" 
						       title="Edit">
						        <img src="img/edit.svg" alt="Update" 
						             style="width: 25px; cursor: pointer;">
						    </a>
						</td>             
                    `;
                    tableBody.appendChild(row);
                });
            }
        })
        .catch(error => console.error('Error fetching voucher data:', error));
}
function toggleStatus(id, currentStatus) {
        const newStatus = currentStatus ;
        
        const voucherData = { id: id, status: newStatus };
		console.log("inside toggle status");
        $.ajax({
            type: "POST",
            url: "/toggleVoucherStatus",
            data: voucherData,
            success: function(data) {
				var response = jQuery.parseJSON(data);
                if (response.status ==="SUCCESS") {
                    getVoucherData(); // Refresh the table data after updating
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
    const table = document.getElementById("voucherMasterTable");
    const tbody = table.tBodies[0];
    const rows = Array.from(tbody.rows);
    const isAscending = table.getAttribute("data-sort-order") === "asc";

    rows.sort((rowA, rowB) => {
        const cellA = rowA.cells[columnIndex].textContent.trim().toLowerCase();
        const cellB = rowB.cells[columnIndex].textContent.trim().toLowerCase();
        if (cellA < cellB) return isAscending ? -1 : 1;
        if (cellA > cellB) return isAscending ? 1 : -1;
        return 0;
    });

    // Toggle sort order
    table.setAttribute("data-sort-order", isAscending ? "desc" : "asc");

    // Append sorted rows back to the table
    rows.forEach(row => tbody.appendChild(row));
}

  



    


