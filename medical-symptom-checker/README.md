# 🏥 Medical Symptom Checker

A comprehensive web application that analyzes patient symptoms and provides guidance on when to seek medical attention.

## 🌟 Features

### Core Functionality
- **Symptom Analysis**: Analyzes multiple symptoms simultaneously
- **Urgency Classification**: 4-tier system (Emergency, Urgent, Moderate, Routine)
- **Risk Assessment**: Considers age, chronic conditions, and symptom duration
- **Condition Matching**: Matches symptoms to potential medical conditions
- **Self-Care Advice**: Provides recommendations for routine conditions

### Emergency Detection
- ❤️ Heart attack symptoms
- 🧠 Stroke indicators
- 🫁 Respiratory emergencies
- 🩸 Severe bleeding
- 😵 Loss of consciousness
- 🔪 Severe abdominal pain

### Urgency Levels

#### 🚨 EMERGENCY (Call 911 Immediately)
- Chest pain/heart attack symptoms
- Severe headache/stroke symptoms
- Difficulty breathing
- Severe bleeding
- Loss of consciousness
- Severe abdominal pain

#### ⚠️ URGENT (See Doctor Within 24 Hours)
- High fever (>103°F)
- Persistent vomiting
- Severe pain (8-10/10)
- Vision problems
- Severe allergic reactions

#### 📋 MODERATE (Schedule Within 3-7 Days)
- Persistent cough (>2 weeks)
- Moderate fever
- Joint pain/swelling
- Persistent rash
- Urinary problems

#### 💚 ROUTINE (Self-Care/Monitor)
- Mild headache
- Common cold symptoms
- Mild fatigue
- Minor cuts/bruises
- Muscle soreness

## 🚀 Quick Start

### Installation

1. **Install dependencies:**
```bash
cd medical-symptom-checker
npm install
```

2. **Start the server:**
```bash
npm start
```

3. **Open your browser:**
```
http://localhost:3002
```

### Development Mode
```bash
npm run dev
```

## 📖 Usage Guide

### Step 1: Enter Patient Information
- Name (optional)
- Age (required)
- Current symptoms
- Symptom duration
- Pain severity (0-10 scale)
- Chronic conditions

### Step 2: Symptom Input
- Type symptoms separated by commas
- Examples:
  - "chest pain, shortness of breath"
  - "high fever, headache, body aches"
  - "persistent cough, fatigue"

### Step 3: Analysis
The app analyzes:
- Symptom severity
- Patient age and risk factors
- Chronic condition impact
- Symptom duration
- Pain level

### Step 4: Review Results
- **Urgency Level**: Emergency, Urgent, Moderate, or Routine
- **Recommended Action**: Specific next steps
- **Possible Conditions**: Matched conditions
- **Risk Factors**: Age/chronic condition warnings
- **Self-Care Advice**: For routine cases

## 🏗️ Architecture

### Backend (Node.js/Express)
```
server.js (Main API)
├── Symptom Database
│   ├── Emergency conditions
│   ├── Urgent conditions
│   ├── Moderate conditions
│   └── Routine conditions
├── Analysis Engine
│   ├── Symptom matching
│   ├── Risk factor assessment
│   └── Urgency calculation
└── API Endpoints
    ├── POST /api/analyze-symptoms
    ├── GET /api/symptom-suggestions
    ├── GET /api/emergency-info
    └── POST /api/save-assessment
```

### Frontend (HTML/CSS/JavaScript)
```
public/index.html
├── Patient Information Form
├── Symptom Input Interface
├── Results Display
└── Emergency Banner
```

## 🔌 API Documentation

### Analyze Symptoms
**Endpoint:** `POST /api/analyze-symptoms`

**Request Body:**
```json
{
  "name": "John Doe",
  "age": 45,
  "symptoms": ["chest pain", "shortness of breath"],
  "duration": "1-6 hours",
  "severity": 8,
  "chronicConditions": ["diabetes", "heart disease"]
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "patient": {
      "age": 45,
      "symptoms": ["chest pain", "shortness of breath"]
    },
    "analysis": {
      "matches": [{
        "condition": "Possible Heart Attack",
        "urgency": "EMERGENCY",
        "action": "Call 911 immediately",
        "matchedSymptoms": ["chest pain"],
        "additionalSymptoms": ["sweating", "nausea"]
      }],
      "urgencyLevel": "EMERGENCY",
      "recommendation": "Call 911 immediately",
      "riskFactors": [
        "Chronic conditions: diabetes, heart disease"
      ],
      "selfCareAdvice": []
    }
  }
}
```

### Get Symptom Suggestions
**Endpoint:** `GET /api/symptom-suggestions`

**Response:**
```json
{
  "success": true,
  "symptoms": [
    "chest pain",
    "difficulty breathing",
    "fever",
    "headache",
    "..."
  ]
}
```

### Emergency Information
**Endpoint:** `GET /api/emergency-info`

**Response:**
```json
{
  "success": true,
  "emergencyNumbers": {
    "us": "911",
    "uk": "999",
    "eu": "112"
  },
  "emergencySigns": [...]
}
```

## ⚕️ Medical Disclaimer

**IMPORTANT:** This application is for educational and informational purposes only and is NOT a substitute for professional medical advice, diagnosis, or treatment.

### Limitations
- ❌ Not a diagnostic tool
- ❌ Cannot replace doctor consultation
- ❌ Does not consider complete medical history
- ❌ Cannot detect all emergency conditions
- ❌ Should not delay emergency care

### When to Call 911
**ALWAYS call emergency services if experiencing:**
- Chest pain or pressure
- Difficulty breathing
- Sudden severe headache
- Loss of consciousness
- Severe bleeding
- Signs of stroke (FAST: Face drooping, Arm weakness, Speech difficulty, Time to call 911)

## 🔒 Privacy & Security

### Data Handling
- No personal health information stored permanently
- In-memory assessment history (demo only)
- No third-party data sharing
- Client-side processing for symptom input

### Production Recommendations
- Implement HIPAA compliance
- Add user authentication
- Encrypt sensitive data
- Use secure database
- Implement audit logging
- Add privacy policy and consent

## 🎯 Use Cases

### For Patients
- Determine urgency of symptoms
- Decide whether to call 911
- Know when to schedule doctor visit
- Get self-care recommendations

### For Healthcare Education
- Symptom recognition training
- Triage education
- Emergency identification practice

### For Telehealth
- Pre-consultation screening
- Symptom documentation
- Risk assessment

## 🚧 Future Enhancements

### Phase 1 (Current)
- ✅ Basic symptom analysis
- ✅ 4-tier urgency system
- ✅ Emergency detection
- ✅ Risk factor assessment

### Phase 2
- 🔄 AI/ML symptom matching
- 🔄 Multi-language support
- 🔄 Symptom history tracking
- 🔄 Doctor appointment booking

### Phase 3
- ⏳ Integration with EHR systems
- ⏳ Telemedicine video calls
- ⏳ Prescription management
- ⏳ Lab result interpretation

### Phase 4
- ⏳ Mobile application
- ⏳ Wearable device integration
- ⏳ Voice symptom input
- ⏳ AI chatbot assistant

## 🧪 Testing

### Manual Testing
1. **Emergency Symptoms:**
   - Input: "chest pain, difficulty breathing"
   - Expected: EMERGENCY urgency, Call 911

2. **Urgent Symptoms:**
   - Input: "high fever 104, persistent vomiting"
   - Expected: URGENT urgency, See doctor within 24h

3. **Routine Symptoms:**
   - Input: "runny nose, sneezing"
   - Expected: ROUTINE urgency, Self-care

### Risk Factor Testing
- Test with age < 1 (infant)
- Test with age > 65 (elderly)
- Test with chronic conditions
- Test symptom duration impact

## 📊 Symptom Database

### Categories Covered
- Cardiovascular (heart attack, chest pain)
- Neurological (stroke, severe headache)
- Respiratory (breathing difficulties, asthma)
- Gastrointestinal (abdominal pain, vomiting)
- Infectious (fever, infections)
- Musculoskeletal (joint pain, injuries)
- Dermatological (rashes, skin conditions)
- Urinary (UTI, kidney issues)

### Total Conditions: 25+
- Emergency: 6
- Urgent: 5
- Moderate: 6
- Routine: 5+

## 🤝 Contributing

This is an educational project. For production use, consult with medical professionals and ensure compliance with healthcare regulations.

## 📄 License

MIT License - Educational purposes only

---

**Remember:** When in doubt, always seek professional medical attention. Your health and safety are the top priority! 🏥
