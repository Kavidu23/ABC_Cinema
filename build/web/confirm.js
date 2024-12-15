function calculateTotal() {
    // Retrieve ticket type selections
    const ticketTypes = document.querySelectorAll('select[name^="ticketType_"]');
    let date=document.getElementById("currentMonth").value;

    // Accumulate total price
    let totalPrice = 0;
    for (const ticketType of ticketTypes) {
        const selectedValue = ticketType.value;
        let price = 0;

        if (selectedValue === "adult") {
            price = 3000;
        } else if (selectedValue === "child") {
            price = 2000;
        }

        totalPrice += price;
    }

    alert("Please Check Your Details: " + '\n' + "Total Price: " + totalPrice + " LKR" +'\n'+ "The Selected Date is: " + date);
    const totalPriceInput = document.createElement('input');
    totalPriceInput.type = 'hidden';
    totalPriceInput.name = 'totalPrice';
    totalPriceInput.value = totalPrice;
    document.getElementById('ticketForm').appendChild(totalPriceInput);
}