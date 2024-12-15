// Enable the "Proceed" button only if the checkbox is checked
const checkbox = document.getElementById('agree');
const proceedButton = document.getElementById('proceed-btn');

checkbox.addEventListener('change', () => {
    proceedButton.disabled = !checkbox.checked;
});
