// Validate the date before submitting the form
document.getElementById('filter-form').addEventListener('submit', function(event) {
    // Get the selected date from the input
    const selectedDate = document.getElementById('date').value;
    
    // Get today's date
    const today = new Date();
    const todayString = today.toISOString().split('T')[0]; // Format the date as YYYY-MM-DD
    
    // Check if the selected date is before today or next month
    if (selectedDate < todayString) {
        // Prevent form submission
        event.preventDefault();
        
        // Alert user to pick a date within this month
        alert('Please select a date within the current month.');
    }
});
