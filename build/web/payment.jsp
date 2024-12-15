<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PAYMENT DETAILS</title>
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Instrument+Sans:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="payment.css">
</head>
<body>
    <div class="payment-container">
        <!-- Contact Details Section -->
        <div class="contact-details">
            <h3>YOUR CONTACT DETAILS</h3>
            <div class="details-grid">
                <div class="detail-item">
                    <label for="name">NAME</label>
                    <input type="text" id="name">
                </div>
                <div class="detail-item">
                    <label for="email">E-MAIL</label>
                    <input type="email" id="email">
                </div>
                <div class="detail-item">
                    <label for="phone">PHONE</label>
                    <input type="tel" id="phone">
                </div>
            </div>
        </div>

        <!-- Payment Options Section -->
        <div class="payment-options">
            <details open>
                <summary>AVAIL OFFERS</summary>
                <p>NO OFFERS AVAILABLE AT THE MOMENT</p>
            </details>
            <details>
                <summary>PAYMENT OPTIONS</summary>
                <div class="payment-methods">
                    <button class="payment-btn">CREDIT CARD</button>
                    <button class="payment-btn">PAY PAL</button>
                </div>
            </details>
        </div>

        <!-- Seat Info Section -->
        <div class="seat-info">
            <h4>GlADIATOR II (ENGLISH)(2D)</h4>
            <p>AUDI 02</p>
            <p>ABC CINEMA - BBC COMPLEX</p>
            <p>On 01-Dec-10:00 PM</p>
            <p class="seat-label">Seat Info: <span class="seat-number">C4</span></p>
            <hr>
            <div class="price-details">
                <p>1 SEAT: <span>LKR 3250.00</span></p>
                <p>SSCL: <span>LKR 2.38</span></p>
                <p>VAT: <span>LKR 17.53</span></p>
                <p>Conv. Fees: <span>LKR 95.00</span></p>
            </div>
            <div class="total">
                <p>TOTAL (LKR): <span>3,364.91</span></p>
            </div>
        </div>
    </div>
</body>
</html>
