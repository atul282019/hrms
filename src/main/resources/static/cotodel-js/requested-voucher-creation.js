
document.addEventListener('input', function (e) {
  if (e.target.tagName === 'INPUT' || e.target.tagName === 'SELECT') {
    e.target.style.border = '';
    e.target.style.backgroundColor = '';
  }
});

function setInvalid(el, isInvalid) {
  if (!el) return;
  if (isInvalid) {
    el.style.border = '2px solid red';
    el.style.backgroundColor = '#ffe6e6';
  } else {
    el.style.border = '';
    el.style.backgroundColor = '';
  }
}

function singleVoucherValidation() {
  const availableBalance = parseFloat(
    document.querySelector(".text-wrapper-3")
      .textContent.replace(/[₹,]/g, '').trim()
  ) || 0;

  const banklist = document.getElementById('banklist')?.value || '';
  if (!banklist) { alert('Please select bank'); return false; }

  const sourceRows = document.querySelectorAll('#sourceTable tbody tr');
  if (sourceRows.length === 0) { alert('No voucher rows to process.'); return false; }

  const targetBody = document.querySelector('#targetTable tbody');
  targetBody.innerHTML = '';

  let totalIssued = 0; // track cumulative issued amount

  for (let row of sourceRows) {
    const nameEl = row.querySelector('td:nth-child(1) input');
    const mobEl = row.querySelector('td:nth-child(2) input');
    const voucherEl = row.querySelector('td:nth-child(3) input');
    const redemptionEl = row.querySelector('td:nth-child(4) select');
    const reqAmtEl = row.querySelector('td:nth-child(5) input');
    const issAmtEl = row.querySelector('td:nth-child(6) input');
    const startEl = row.querySelector('td:nth-child(7) input[type="date"]');
    const validityEl = row.querySelector('td:nth-child(8) input');

    const name = (nameEl?.value || '').trim();
    const mobile = (mobEl?.value || '').trim();
    const voucherType = (voucherEl?.value || '').trim();
    const redemption = redemptionEl ? redemptionEl.options[redemptionEl.selectedIndex].text : '';
    const reqAmt = parseFloat(reqAmtEl?.value || '');
    const issAmt = parseFloat(issAmtEl?.value || '');
    const startDate = (startEl?.value || '').trim();
    const validity = parseInt((validityEl?.value || '').trim(), 10);

    // minimal validations
    setInvalid(nameEl, !name);
    setInvalid(mobEl, !(mobile && mobile.length === 10));
    setInvalid(voucherEl, !voucherType);
    setInvalid(redemptionEl, !redemption);
    setInvalid(startEl, !startDate);
    setInvalid(validityEl, !validity);

    // issued amount validation
    if (!(Number.isFinite(issAmt) && issAmt > 0)) {
      setInvalid(issAmtEl, true);
      //alert("Issued amount must be greater than 0");
	  document.getElementById('common-error-msg').innerHTML="Issued amount must be greater than 0";
	  
      return false; // stop immediately
    } else if (issAmt > availableBalance) {
      setInvalid(issAmtEl, true);
   //   alert(`Issued Amount ₹${issAmt.toFixed(2)} cannot exceed available balance ₹${availableBalance.toFixed(2)} in this row.`);
	  document.getElementById('common-error-msg').innerHTML=`Issued Amount ₹${issAmt.toFixed(2)} cannot exceed available balance ₹${availableBalance.toFixed(2)} in this row.`;
	    
	  return false; // stop immediately
    } else {
      setInvalid(issAmtEl, false);
    }

    // validity range check (2–365)
    if (!(Number.isInteger(validity) && validity >= 2 && validity <= 365)) {
      setInvalid(validityEl, true);
      //alert(`Validity must be between 2 and 365 days. Found: ${validity}`);
	  document.getElementById('common-error-msg').innerHTML=`Validity must be between 2 and 365 days.`;
	     
      return false;
    } else {
      setInvalid(validityEl, false);
    }

    if (!name || mobile.length !== 10 || !voucherType || !redemption || !startDate || !validity) {
    //  alert("Please fix the highlighted fields.");
	  document.getElementById('common-error-msg').innerHTML="Please fix the highlighted fields.";
	  	     
      return false;
    }

    // add to total issued
    totalIssued += issAmt;

    // Append row to verify table
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${name}</td>
      <td>${mobile}</td>
      <td>${voucherType}</td>
      <td>${redemption}</td>
      <td>${Number.isFinite(reqAmt) ? reqAmt.toFixed(2) : ''}</td>
      <td>${issAmt.toFixed(2)}</td>
      <td>${startDate}</td>
      <td>${validity}</td>`;
    targetBody.appendChild(tr);
  }

  // ✅ total issued validation
  if (totalIssued > availableBalance) {
   // alert(`Total issued amount ₹${totalIssued.toFixed(2)} cannot exceed available balance ₹${availableBalance.toFixed(2)}.`);
	document.getElementById('common-error-msg').innerHTML=`Total issued amount ₹${totalIssued.toFixed(2)} cannot exceed available balance ₹${availableBalance.toFixed(2)}.`;
    return false;
  }

  // Go to verify screen
  $('#selectvouchers-wrap04').show();
  $('#selectvouchers-wrap03').hide();
  document.getElementById("lable2").classList.add("active");
  return true;
}

window.singleVoucherValidation = singleVoucherValidation;

// Verify step helpers
$(document).on('change', '#customCheck45', function() {
  const checked = $(this).is(':checked');
  $('#submitButton').prop('disabled', !checked);
  $('#errorMessage').text(checked ? '' : 'Please confirm before issuing.');
});
$(document).on('click', '#verifyDetailBack', function() {
  $('#selectvouchers-wrap04').hide();
  $('#selectvouchers-wrap03').show();
});
