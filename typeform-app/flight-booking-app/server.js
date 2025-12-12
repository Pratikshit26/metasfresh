// 🛫 Flight Booking Server with QR Payment
const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const QRCode = require('qrcode');
const { v4: uuidv4 } = require('uuid');

const app = express();
const PORT = process.env.PORT || 5000;

// Middleware
app.use(cors());
app.use(bodyParser.json());
app.use(express.static('public'));

// In-memory storage (use MongoDB in production)
const flights = [];
const bookings = [];
const payments = [];

// Sample flight data
const sampleFlights = [
  {
    id: 'FL001',
    airline: 'Air India',
    flightNumber: 'AI 101',
    from: 'Mumbai (BOM)',
    to: 'Delhi (DEL)',
    departure: '2025-12-15T06:00:00',
    arrival: '2025-12-15T08:15:00',
    duration: '2h 15m',
    price: 4500,
    currency: 'INR',
    availableSeats: 45,
    class: 'Economy'
  },
  {
    id: 'FL002',
    airline: 'IndiGo',
    flightNumber: '6E 342',
    from: 'Delhi (DEL)',
    to: 'Bangalore (BLR)',
    departure: '2025-12-15T10:30:00',
    arrival: '2025-12-15T13:15:00',
    duration: '2h 45m',
    price: 5200,
    currency: 'INR',
    availableSeats: 32,
    class: 'Economy'
  },
  {
    id: 'FL003',
    airline: 'Vistara',
    flightNumber: 'UK 955',
    from: 'Bangalore (BLR)',
    to: 'Mumbai (BOM)',
    departure: '2025-12-15T15:00:00',
    arrival: '2025-12-15T17:00:00',
    duration: '2h 0m',
    price: 6800,
    currency: 'INR',
    availableSeats: 28,
    class: 'Business'
  },
  {
    id: 'FL004',
    airline: 'SpiceJet',
    flightNumber: 'SG 8156',
    from: 'Chennai (MAA)',
    to: 'Kolkata (CCU)',
    departure: '2025-12-15T08:45:00',
    arrival: '2025-12-15T11:15:00',
    duration: '2h 30m',
    price: 4200,
    currency: 'INR',
    availableSeats: 50,
    class: 'Economy'
  },
  {
    id: 'FL005',
    airline: 'Air India',
    flightNumber: 'AI 680',
    from: 'Mumbai (BOM)',
    to: 'Dubai (DXB)',
    departure: '2025-12-15T03:00:00',
    arrival: '2025-12-15T05:30:00',
    duration: '3h 30m',
    price: 18500,
    currency: 'INR',
    availableSeats: 15,
    class: 'Business'
  }
];

flights.push(...sampleFlights);

// 🔍 Search Flights
app.post('/api/flights/search', (req, res) => {
  const { from, to, date, passengers } = req.body;
  
  let results = flights.filter(flight => {
    const matchFrom = !from || flight.from.toLowerCase().includes(from.toLowerCase());
    const matchTo = !to || flight.to.toLowerCase().includes(to.toLowerCase());
    const hasSeats = !passengers || flight.availableSeats >= passengers;
    
    return matchFrom && matchTo && hasSeats;
  });

  res.json({
    success: true,
    count: results.length,
    flights: results
  });
});

// 📋 Get all flights
app.get('/api/flights', (req, res) => {
  res.json({
    success: true,
    count: flights.length,
    flights: flights
  });
});

// 🎫 Create Booking
app.post('/api/bookings', (req, res) => {
  const { flightId, passenger, contactInfo } = req.body;
  
  const flight = flights.find(f => f.id === flightId);
  
  if (!flight) {
    return res.status(404).json({ success: false, message: 'Flight not found' });
  }
  
  if (flight.availableSeats < 1) {
    return res.status(400).json({ success: false, message: 'No seats available' });
  }
  
  const booking = {
    id: uuidv4(),
    bookingReference: `BK${Date.now().toString().slice(-8)}`,
    flightId: flight.id,
    flight: flight,
    passenger: passenger,
    contactInfo: contactInfo,
    status: 'pending',
    totalAmount: flight.price,
    currency: flight.currency,
    createdAt: new Date().toISOString(),
    paymentStatus: 'pending'
  };
  
  bookings.push(booking);
  
  res.json({
    success: true,
    booking: booking,
    message: 'Booking created successfully'
  });
});

// 💳 Generate QR Code for Payment
app.post('/api/payment/generate-qr', async (req, res) => {
  const { bookingId, amount, currency } = req.body;
  
  const booking = bookings.find(b => b.id === bookingId);
  
  if (!booking) {
    return res.status(404).json({ success: false, message: 'Booking not found' });
  }
  
  // Create payment object
  const payment = {
    id: uuidv4(),
    bookingId: bookingId,
    amount: amount,
    currency: currency,
    status: 'pending',
    paymentMethod: 'QR',
    createdAt: new Date().toISOString(),
    expiresAt: new Date(Date.now() + 15 * 60 * 1000).toISOString() // 15 min expiry
  };
  
  payments.push(payment);
  
  // Generate UPI payment string (Indian standard)
  const upiString = `upi://pay?pa=flightbooking@paytm&pn=FlightBooking&am=${amount}&cu=${currency}&tn=Booking${booking.bookingReference}`;
  
  try {
    // Generate QR code
    const qrCodeDataURL = await QRCode.toDataURL(upiString, {
      errorCorrectionLevel: 'M',
      type: 'image/png',
      width: 300,
      margin: 2
    });
    
    res.json({
      success: true,
      payment: {
        id: payment.id,
        qrCode: qrCodeDataURL,
        upiString: upiString,
        amount: amount,
        currency: currency,
        bookingReference: booking.bookingReference,
        expiresAt: payment.expiresAt
      }
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Failed to generate QR code',
      error: error.message
    });
  }
});

// ✅ Verify Payment (Mock - in production use payment gateway webhook)
app.post('/api/payment/verify', (req, res) => {
  const { paymentId, transactionId } = req.body;
  
  const payment = payments.find(p => p.id === paymentId);
  
  if (!payment) {
    return res.status(404).json({ success: false, message: 'Payment not found' });
  }
  
  // Mock verification (in production, verify with payment gateway)
  payment.status = 'completed';
  payment.transactionId = transactionId || `TXN${Date.now()}`;
  payment.completedAt = new Date().toISOString();
  
  // Update booking
  const booking = bookings.find(b => b.id === payment.bookingId);
  if (booking) {
    booking.status = 'confirmed';
    booking.paymentStatus = 'paid';
    
    // Reduce available seats
    const flight = flights.find(f => f.id === booking.flightId);
    if (flight) {
      flight.availableSeats -= 1;
    }
  }
  
  res.json({
    success: true,
    payment: payment,
    booking: booking,
    message: 'Payment verified successfully'
  });
});

// 📄 Get Booking Details
app.get('/api/bookings/:bookingId', (req, res) => {
  const booking = bookings.find(b => b.id === req.params.bookingId);
  
  if (!booking) {
    return res.status(404).json({ success: false, message: 'Booking not found' });
  }
  
  res.json({
    success: true,
    booking: booking
  });
});

// 🎟️ Generate Boarding Pass (QR Code)
app.get('/api/bookings/:bookingId/boarding-pass', async (req, res) => {
  const booking = bookings.find(b => b.id === req.params.bookingId);
  
  if (!booking) {
    return res.status(404).json({ success: false, message: 'Booking not found' });
  }
  
  if (booking.status !== 'confirmed') {
    return res.status(400).json({ success: false, message: 'Booking not confirmed' });
  }
  
  // Boarding pass data
  const boardingPassData = {
    bookingReference: booking.bookingReference,
    passengerName: booking.passenger.name,
    flightNumber: booking.flight.flightNumber,
    from: booking.flight.from,
    to: booking.flight.to,
    departure: booking.flight.departure,
    seat: `${Math.floor(Math.random() * 30) + 1}${['A', 'B', 'C', 'D', 'E', 'F'][Math.floor(Math.random() * 6)]}`,
    gate: `${Math.floor(Math.random() * 20) + 1}`,
    boardingTime: new Date(new Date(booking.flight.departure).getTime() - 45 * 60000).toISOString()
  };
  
  try {
    const qrCodeDataURL = await QRCode.toDataURL(JSON.stringify(boardingPassData), {
      errorCorrectionLevel: 'H',
      width: 400
    });
    
    res.json({
      success: true,
      boardingPass: {
        ...boardingPassData,
        qrCode: qrCodeDataURL
      }
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Failed to generate boarding pass'
    });
  }
});

// Start server
app.listen(PORT, () => {
  console.log(`🛫 Flight Booking Server running on port ${PORT}`);
  console.log(`📡 API available at http://localhost:${PORT}/api`);
});
