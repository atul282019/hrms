
function loadBankMasterTable() {
    fetch('/getaftersaveBankMasterDetailsList', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        const tableBody = document.getElementById('bankMasterTableBody');
        tableBody.innerHTML = ''; // Clear existing rows

        if (data && data.status === 'SUCCESS' && Array.isArray(data.data)) {
            data.data.forEach((bank, index) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                 <td>${index + 1}</td> <!-- Serial number -->
                    <td>
                        <img src="data:image/png;base64,${bank.bankLogo}" 
                             alt="Bank Logo" style="width: 50px;">
                    </td>
                    <td>${bank.bankName || 'N/A'}</td>
                    <td>${bank.bankCode || 'N/A'}</td>
                    <td>${bank.status === 1 ? 'Active' : 'Inactive'}</td>
                    <td>
                        <button class="btn btn-sm btn-${bank.status === 1 ? 'danger' : 'success'}" 
                                onclick="toggleBankStatus(${bank.id}, ${bank.status})">
                            ${bank.status === 1 ? 'Deactivate' : 'Activate'}
                        </button>
                <td>        
            <form action="/editBankMaster" method="POST" style="display:inline;">
            <input type="hidden" name="id" value="${bank.id}">
            <input type="hidden" name="bankName" value="${bank.bankName}">
            <input type="hidden" name="bankCode" value="${bank.bankCode}">
            <input type="hidden" name="status" value="${bank.status}">
            
            <button type="submit" class="btn btn-sm btn-primary">Edit</button></td>
            
                    </td>
                `;
                tableBody.appendChild(row);
            });
        } else {
            const emptyRow = document.createElement('tr');
            emptyRow.innerHTML = `
                <td colspan="5" style="text-align: center;">No bank data found.</td>
            `;
            tableBody.appendChild(emptyRow);
        }
    })
    .catch(error => console.error('Error fetching bank data:', error));
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
    const table = document.getElementById("bankMasterTable");
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


