/*function getVoucherData() {
	// this function prints dynamic table
            fetch('/getvoucherTypeMaster')
                .then(response => response.json())
                .then(data => {
                    const tableBody = document.getElementById('voucherMasterTableBody');
                    tableBody.innerHTML = ''; // Clear existing rows

                    if (data && data.data && Array.isArray(data.data)) {
                        data.data.forEach(voucher => {
                            const row = document.createElement('tr');

                            row.innerHTML = `
                                <td>${voucher.id || ''}</td>
                                <td>${voucher.voucherDesc || ''}</td>
                                <td>${voucher.voucherType || ''}</td>
                                <td>${voucher.voucherSubType || ''}</td>
                                <td>${voucher.purposeCode || ''}</td>
                                <td>${voucher.status === 1 ? 'Active' : 'Inactive'}</td>
                            `;
                            tableBody.appendChild(row);
                        });
                    }
                })
                .catch(error => console.error('Error fetching voucher data:', error));
        }*/
        
      function getVoucherData() {
    fetch('/getvoucherTypeMaster')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('voucherMasterTableBody');
            tableBody.innerHTML = ''; // Clear existing rows

            if (data && data.data && Array.isArray(data.data)) {
                data.data.forEach(voucher => {
                    const row = document.createElement('tr');

                    row.innerHTML = `
                        
                        <td>${voucher.voucherDesc || ''}</td>
                        <td>${voucher.voucherType || ''}</td>
                        <td>${voucher.voucherSubType || ''}</td>
                        <td>${voucher.purposeCode || ''}</td>
                        <td>${voucher.status === 1 ? 'Active' : 'Inactive'}</td>
                        <td>
                            <button class="btn btn-${voucher.status === 1 ? 'danger' : 'success'}" 
                                    onclick="toggleStatus(${voucher.id},${voucher.status})">
                                ${voucher.status === 1 ? 'Deactivate' : 'Activate'}
                            </button>
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

    /*2nd attempt
function toggleVoucherStatus(id) {
    fetch('/toggleVoucherStatus', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ id: id })
    })
    .then(response => response.json())
    .then(responseData => {
        if (responseData.status === 'success') {
            //getVoucherData(); // Refresh data after successful update
        } else {
            console.log('Failed to update voucher status');
        }
    })
    .catch(error => console.error('Error sending voucher ID:', error));
}*/



   /* function getVoucherData() {
        $.ajax({
            type: "GET",
            url: "/getvoucherTypeMaster",
            success: function(response) {
                const tableBody = document.getElementById("voucherMasterTableBody");
                tableBody.innerHTML = ""; 

                response.data.forEach(voucher => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${voucher.voucherDesc}</td>
                        <td>${voucher.voucherType}</td>
                        <td>${voucher.voucherSubType}</td>
                        <td>${voucher.purposeCode}</td>
                        <td>${voucher.status === 1 ? 'Active' : 'Inactive'}</td>
                        <td>
                            <button class="btn ${voucher.status === 1 ? 'btn-danger' : 'btn-success'}"
                                    onclick="toggleStatus(${voucher.id}, ${voucher.status})">
                                ${voucher.status === 1 ? 'Deactivate' : 'Activate'}
                            </button>
                        </td>
                    `;
                    tableBody.appendChild(row);
                });
            },
            error: function(error) {
                console.error("Error fetching voucher data:", error);
            }
        });
    }*/

    


