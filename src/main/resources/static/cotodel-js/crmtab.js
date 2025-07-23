function openRightCanvas() {
  const canvas = document.getElementById('bs-canvas-right');
  canvas.classList.add('show');
  document.body.classList.add('modal-open');
}
function closeRightCanvas() {
  const canvas = document.getElementById('bs-canvas-right');
  canvas.classList.remove('show');
  document.body.classList.remove('modal-open');
}


document.addEventListener("DOMContentLoaded", function () {
  const closeBtn = document.querySelector('.bs-canvas-close');
  if (closeBtn) {
    closeBtn.addEventListener('click', function () {
      const canvas = document.getElementById('bs-canvas-right');
      canvas.classList.remove('show');
      document.body.classList.remove('modal-open');
    });
  }
});
