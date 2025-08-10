// Continue → validate each source row and paint target table
 function singleVoucherValidation() {
   const banklist = document.getElementById('banklist')?.value || '';
   if (!banklist) { alert('Please select bank'); return false; }

   const sourceRows = document.querySelectorAll('#sourceTable tbody tr');
   if (sourceRows.length === 0) { alert('No voucher rows to process.'); return false; }

   const targetBody = document.querySelector('#targetTable tbody');
   targetBody.innerHTML = '';

   let hasError = false;
   sourceRows.forEach(row => {
     const nameEl = row.querySelector('td:nth-child(1) input');
     const mobEl = row.querySelector('td:nth-child(2) input');
     const voucherEl = row.querySelector('td:nth-child(3) input');
     const redemptionEl = row.querySelector('td:nth-child(4) select');
     const reqAmtEl = row.querySelector('td:nth-child(5) input');
     const issAmtEl = row.querySelector('td:nth-child(6) input');
     const startEl = row.querySelector('td:nth-child(7) input[type="date"]');
     const validityEl = row.querySelector('td:nth-child(8) select');

     const name = (nameEl?.value || '').trim();
     const mobile = (mobEl?.value || '').trim();
     const voucherType = (voucherEl?.value || '').trim();
     const redemption = redemptionEl ? redemptionEl.options[redemptionEl.selectedIndex].text : '';
     const reqAmt = parseFloat(reqAmtEl?.value || '');
     const issAmt = parseFloat(issAmtEl?.value || '');
     const startDate = (startEl?.value || '').trim();
     const validity = validityEl ? validityEl.options[validityEl.selectedIndex].text : '';

     // minimal validations (why: prevent empty/invalid entries reaching backend)
     setInvalid(nameEl, !name);
     setInvalid(mobEl, !(mobile && mobile.length === 10));
     setInvalid(voucherEl, !voucherType);
     setInvalid(redemptionEl, !redemption);
     setInvalid(issAmtEl, !(Number.isFinite(issAmt) && issAmt > 0));
     setInvalid(startEl, !startDate);
     setInvalid(validityEl, !validity);

     if (!name || mobile.length !== 10 || !voucherType || !redemption || !Number.isFinite(issAmt) || issAmt <= 0 || !startDate || !validity) {
       hasError = true;
       return; // skip appending row
     }

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
   });

   if (hasError) { alert('Please fix the highlighted fields.'); return false; }

   // Go to verify screen
   $('#selectvouchers-wrap04').show();
   $('#selectvouchers-wrap03').hide();
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
 function setInvalid(el, on, msg) { // why: minimal inline validation
    if (!el) return;
    if (on) el.classList.add('is-invalid'); else el.classList.remove('is-invalid');
    if (msg) {
      const id = el.getAttribute('aria-describedby');
      if (id) {
        const holder = document.getElementById(id);
        if (holder) holder.textContent = msg;
      }
    }
  }