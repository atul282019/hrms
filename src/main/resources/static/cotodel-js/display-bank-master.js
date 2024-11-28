/*function loadBankMasterTable() {
    $.ajax({
        type: "POST",
        url: "/getaftersaveBankMasterDetailsList",
        dataType: "json",
        success: function(response) {
            console.log(response); // Check the structure of the response object

            const tableBody = document.getElementById("bankMasterTableBody");
            tableBody.innerHTML = ""; // Clear existing rows

            if (response.status === 'SUCCESS') {
                if (Array.isArray(response.data)) {  // Ensure that response.data is an array
                    response.data.forEach((bank) => {
                        const row = document.createElement("tr");

                        // Bank Logo
                        const logoCell = document.createElement("td");
                        const logoImage = document.createElement("img");

                        // Set the image source to the Base64 data URI
                        if (bank.bankLogo) {
                            logoImage.src = `data:image/png;base64,${bank.bankLogo}`;
                        } else {
                            logoImage.src = "img/default-logo.png"; // Default image if no logo is provided
                        }
                        logoImage.alt = "Bank Logo";
                        logoImage.style.width = "50px"; // Adjust size as needed
                        logoCell.appendChild(logoImage);
                        row.appendChild(logoCell);

                        // Bank Name
                        const nameCell = document.createElement("td");
                        nameCell.textContent = bank.bankName || "N/A";
                        row.appendChild(nameCell);

                        // Bank Code
                        const codeCell = document.createElement("td");
                        codeCell.textContent = bank.bankCode || "N/A";
                        row.appendChild(codeCell);

                        // Status (Convert 1 to "Active" and 0 to "Inactive")
                        const statusCell = document.createElement("td");
                        statusCell.textContent = bank.status === 1 ? "Active" : "Inactive";
                        row.appendChild(statusCell);

                        // Action (Edit or Delete buttons)
                        const actionCell = document.createElement("td");
                        const editButton = document.createElement("button");
                        editButton.className = "btn btn-sm btn-warning";
                        editButton.textContent = "Edit";
                        editButton.onclick = () => editBank(bank.bankCode); // Implement editBank function
                        actionCell.appendChild(editButton);
                        row.appendChild(actionCell);

                        tableBody.appendChild(row);
                    });
                } else {
                    console.error("response.data is not an array:", response.data);
                    // If data is not an array, handle accordingly (e.g., show a message)
                }
            } else {
                const emptyRow = document.createElement("tr");
                const emptyCell = document.createElement("td");
                emptyCell.colSpan = 5;
                emptyCell.textContent = "No bank data found.";
                emptyCell.style.textAlign = "center";
                emptyRow.appendChild(emptyCell);
                tableBody.appendChild(emptyRow);
            }
        },
        error: function(error) {
            console.error("Error fetching bank data:", error);
            // You can add additional error handling logic here
        }
    });
}
*/
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
            <input type="hidden" name="bankLogo" value="${bank.bankLogo}">
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


