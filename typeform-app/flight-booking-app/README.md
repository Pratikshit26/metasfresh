# ✈️ Flight Booking App with QR Code Payment

A complete flight booking application with integrated QR code payment system.

## 🚀 Features

### ✅ Flight Booking
- Search flights by origin, destination, date, and passengers
- Real-time seat availability
- Multiple airlines and routes
- Economy and Business class options

### 💳 QR Code Payment
- UPI payment integration
- Generate QR codes for payments
- Scan to pay with any UPI app (Google Pay, PhonePe, Paytm)
- 15-minute payment expiry
- Payment verification

### 🎟️ Digital Boarding Pass
- QR code boarding pass generation
- Complete flight and passenger details
- Seat assignment
- Gate and boarding time information
- Downloadable boarding pass

### 🔐 Booking Management
- Unique booking reference numbers
- Booking status tracking
- Payment status monitoring
- Email and SMS notifications (ready to integrate)

## 📋 Tech Stack

**Backend:**
- Node.js + Express
- QRCode library for QR generation
- UUID for unique IDs
- In-memory storage (easily migrate to MongoDB/PostgreSQL)

**Frontend:**
- Pure HTML/CSS/JavaScript
- Responsive design
- Modal-based workflow
- Real-time updates

**Payment:**
- UPI QR code generation
- Razorpay/Stripe ready
- Transaction verification

## 🛠️ Installation

### 1. Install Dependencies
```bash
cd flight-booking-app
npm install
```

### 2. Environment Setup
Create `.env` file:
```env
PORT=5000
MONGODB_URI=mongodb://localhost:27017/flightbooking
JWT_SECRET=your_secret_key_here
RAZORPAY_KEY_ID=your_razorpay_key
RAZORPAY_KEY_SECRET=your_razorpay_secret
```

### 3. Start the Server
```bash
# Development
npm run server

# Or with nodemon
nodemon server.js
```

### 4. Open the App
```
http://localhost:5000
```

## 📱 How to Use

### Step 1: Search Flights
1. Enter origin city (e.g., Mumbai)
2. Enter destination city (e.g., Delhi)
3. Select date and number of passengers
4. Click "Search Flights"

### Step 2: Book a Flight
1. Browse available flights
2. Click "Book Now" on your preferred flight
3. Fill in passenger details:
   - Full name (as per ID)
   - Email
   - Phone number
   - Age
4. Click "Proceed to Payment"

### Step 3: Make Payment
1. QR code will be generated
2. Scan with any UPI app:
   - Google Pay
   - PhonePe
   - Paytm
   - BHIM
   - etc.
3. Complete payment in the UPI app
4. Click "I've Paid" (or wait for auto-verification)

### Step 4: Get Boarding Pass
1. Boarding pass generated automatically
2. Contains:
   - QR code for airport scanning
   - Flight details
   - Seat number
   - Gate information
   - Boarding time
3. Download or save to mobile

## 🎨 UI Features

### Search Section
- Clean, intuitive form
- Date picker
- Passenger selector
- Instant search

### Flight Cards
- Airline and flight number
- Route visualization
- Departure/arrival times
- Duration display
- Price with currency
- Seat availability
- Class type (Economy/Business)

### Modal Workflow
- Passenger details modal
- QR payment modal
- Boarding pass modal
- Smooth transitions

### Boarding Pass Design
- Professional airline-style design
- Purple gradient theme
- Clear typography
- QR code prominent
- All essential information

## 🔧 API Endpoints

### Flights
```javascript
GET    /api/flights              // Get all flights
POST   /api/flights/search       // Search flights
```

### Bookings
```javascript
POST   /api/bookings             // Create booking
GET    /api/bookings/:id         // Get booking details
GET    /api/bookings/:id/boarding-pass  // Generate boarding pass
```

### Payments
```javascript
POST   /api/payment/generate-qr  // Generate payment QR
POST   /api/payment/verify       // Verify payment
```

## 💡 Sample Data

The app comes with 5 pre-loaded flights:

1. **Air India AI 101** - Mumbai → Delhi (₹4,500)
2. **IndiGo 6E 342** - Delhi → Bangalore (₹5,200)
3. **Vistara UK 955** - Bangalore → Mumbai (₹6,800)
4. **SpiceJet SG 8156** - Chennai → Kolkata (₹4,200)
5. **Air India AI 680** - Mumbai → Dubai (₹18,500)

## 🚀 Production Deployment

### Database Migration
Replace in-memory storage with MongoDB:

```javascript
// Install mongoose
npm install mongoose

// Connect to MongoDB
const mongoose = require('mongoose');
mongoose.connect(process.env.MONGODB_URI);

// Create models
const Flight = require('./models/Flight');
const Booking = require('./models/Booking');
const Payment = require('./models/Payment');
```

### Payment Gateway Integration

**Razorpay Integration:**
```javascript
const Razorpay = require('razorpay');

const razorpay = new Razorpay({
  key_id: process.env.RAZORPAY_KEY_ID,
  key_secret: process.env.RAZORPAY_KEY_SECRET
});

// Generate payment link
const payment = await razorpay.payments.createUpiLink({
  amount: booking.totalAmount * 100,
  currency: 'INR',
  description: `Booking ${booking.bookingReference}`
});
```

### Email Notifications
```javascript
const nodemailer = require('nodemailer');

// Send booking confirmation
await sendEmail({
  to: passenger.email,
  subject: 'Flight Booking Confirmed',
  template: 'booking-confirmation',
  data: { booking, boardingPass }
});
```

### SMS Notifications
```javascript
// Integrate Twilio or MSG91
const twilio = require('twilio');

await sendSMS({
  to: passenger.phone,
  message: `Flight booked! Ref: ${bookingRef}. Check email for details.`
});
```

## 🔐 Security Features

- Input validation
- XSS protection
- CORS enabled
- JWT authentication (ready to implement)
- API rate limiting (ready to implement)
- Payment encryption

## 📊 Future Enhancements

- [ ] User authentication & profiles
- [ ] Seat selection interface
- [ ] Multi-city flights
- [ ] Return flights
- [ ] Flight change/cancellation
- [ ] Loyalty points system
- [ ] Travel insurance
- [ ] Hotel booking integration
- [ ] Car rental
- [ ] Multiple payment methods (credit card, net banking)
- [ ] Real-time flight status
- [ ] Push notifications
- [ ] Mobile app (React Native)

## 🐛 Known Issues

- Payment verification is simulated (integrate with payment gateway webhook)
- Seat availability not real-time (integrate with airline API)
- Email/SMS not implemented (add in production)

## 📝 License

MIT License - Feel free to use for personal or commercial projects

## 🤝 Contributing

Pull requests welcome! For major changes, please open an issue first.

## 📧 Support

For issues or questions, contact: support@flightbooking.com

---

**Built with ❤️ for seamless flight booking experience**
