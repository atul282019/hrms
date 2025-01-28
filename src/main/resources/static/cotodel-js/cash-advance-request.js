function addTravelRow() {
    event.preventDefault(); // Prevent the default anchor behavior

    // Get the table body where new rows will be added
    const tableBody = document.getElementById('reimbursementsBody');

    // Get the first row as a template to clone
    const templateRow = document.querySelector('.template-row');

    if (templateRow) {
        // Clone the row
        const newRow = templateRow.cloneNode(true);

        // Reset the values of the inputs in the cloned row
        newRow.querySelectorAll('input, select, textarea').forEach(input => {
            if (input.type === 'checkbox' || input.tagName === 'SELECT') {
                input.selectedIndex = 0;
                input.checked = false;
            } else {
                input.value = '';
            }
        });

        // Add the cloned row to the table body
        tableBody.appendChild(newRow);

        // Optional: Add event listener to the new delete button
        const deleteButton = newRow.querySelector('.remove-row');
        if (deleteButton) {
            deleteButton.addEventListener('click', function () {
                newRow.remove();
            });
        }
    }
}

// Add event listeners for existing delete buttons
document.querySelectorAll('.remove-row').forEach(button => {
    button.addEventListener('click', function () {
        // Get the row to be deleted
        const row = button.closest('tr');

        // Prevent deleting the first row
        const allRows = row.parentElement.children;
        if (row !== allRows[0]) {
            row.remove();
        } else {
            alert("You cannot delete the first row.");
        }
    });
});