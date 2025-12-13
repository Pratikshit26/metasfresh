const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');

const app = express();
const PORT = 3002;

app.use(cors());
app.use(bodyParser.json());
app.use(express.static('public'));

// Symptom database with severity levels and recommendations
const symptomDatabase = {
    emergency: [
        {
            symptoms: ['chest pain', 'crushing chest pain', 'heart pain'],
            condition: 'Possible Heart Attack',
            urgency: 'EMERGENCY',
            action: 'Call 911 immediately',
            additionalSymptoms: ['shortness of breath', 'pain radiating to arm', 'sweating', 'nausea']
        },
        {
            symptoms: ['severe headache', 'worst headache ever', 'sudden severe headache'],
            condition: 'Possible Stroke or Aneurysm',
            urgency: 'EMERGENCY',
            action: 'Call 911 immediately',
            additionalSymptoms: ['confusion', 'vision problems', 'difficulty speaking', 'weakness on one side']
        },
        {
            symptoms: ['difficulty breathing', 'cannot breathe', 'severe shortness of breath'],
            condition: 'Respiratory Emergency',
            urgency: 'EMERGENCY',
            action: 'Call 911 immediately',
            additionalSymptoms: ['blue lips', 'chest tightness', 'wheezing']
        },
        {
            symptoms: ['severe bleeding', 'uncontrolled bleeding', 'heavy bleeding'],
            condition: 'Hemorrhage',
            urgency: 'EMERGENCY',
            action: 'Call 911 or go to ER immediately',
            additionalSymptoms: ['dizziness', 'weakness', 'pale skin']
        },
        {
            symptoms: ['unconscious', 'loss of consciousness', 'fainting', 'passed out'],
            condition: 'Loss of Consciousness',
            urgency: 'EMERGENCY',
            action: 'Call 911 immediately',
            additionalSymptoms: ['confusion', 'seizure', 'not responding']
        },
        {
            symptoms: ['severe abdominal pain', 'sudden sharp stomach pain'],
            condition: 'Possible Appendicitis or Internal Emergency',
            urgency: 'EMERGENCY',
            action: 'Go to ER immediately',
            additionalSymptoms: ['vomiting', 'fever', 'rigid abdomen', 'pain in lower right side']
        }
    ],
    urgent: [
        {
            symptoms: ['high fever', 'fever over 103', 'persistent fever'],
            condition: 'High Fever - Possible Infection',
            urgency: 'URGENT',
            action: 'See doctor within 24 hours',
            additionalSymptoms: ['chills', 'body aches', 'sweating', 'confusion']
        },
        {
            symptoms: ['severe vomiting', 'persistent vomiting', 'cannot keep fluids down'],
            condition: 'Severe Dehydration Risk',
            urgency: 'URGENT',
            action: 'See doctor within 24 hours',
            additionalSymptoms: ['dizziness', 'dark urine', 'dry mouth', 'diarrhea']
        },
        {
            symptoms: ['severe pain', 'unbearable pain', 'pain level 8-10'],
            condition: 'Acute Pain Syndrome',
            urgency: 'URGENT',
            action: 'See doctor within 24 hours or go to urgent care',
            additionalSymptoms: ['inability to move', 'swelling', 'numbness']
        },
        {
            symptoms: ['vision loss', 'sudden vision changes', 'blurred vision', 'double vision'],
            condition: 'Vision Problems',
            urgency: 'URGENT',
            action: 'See eye doctor within 24 hours',
            additionalSymptoms: ['eye pain', 'seeing spots', 'flashes of light']
        },
        {
            symptoms: ['severe allergic reaction', 'facial swelling', 'tongue swelling'],
            condition: 'Allergic Reaction',
            urgency: 'URGENT',
            action: 'Use EpiPen if available, go to ER',
            additionalSymptoms: ['difficulty breathing', 'hives', 'throat tightness']
        }
    ],
    moderate: [
        {
            symptoms: ['persistent cough', 'cough for more than 2 weeks', 'chronic cough'],
            condition: 'Respiratory Condition',
            urgency: 'MODERATE',
            action: 'Schedule appointment within 3-5 days',
            additionalSymptoms: ['chest congestion', 'wheezing', 'shortness of breath', 'fatigue']
        },
        {
            symptoms: ['fever', 'temperature over 100.4', 'persistent fever'],
            condition: 'Infection or Illness',
            urgency: 'MODERATE',
            action: 'Monitor and see doctor if persists beyond 3 days',
            additionalSymptoms: ['body aches', 'chills', 'fatigue', 'headache']
        },
        {
            symptoms: ['joint pain', 'swollen joints', 'joint stiffness'],
            condition: 'Joint Disorder',
            urgency: 'MODERATE',
            action: 'Schedule appointment within a week',
            additionalSymptoms: ['redness', 'warmth', 'reduced range of motion']
        },
        {
            symptoms: ['skin rash', 'persistent rash', 'spreading rash'],
            condition: 'Dermatological Condition',
            urgency: 'MODERATE',
            action: 'See doctor within a week',
            additionalSymptoms: ['itching', 'burning', 'blisters', 'fever']
        },
        {
            symptoms: ['persistent headache', 'frequent headaches', 'daily headaches'],
            condition: 'Chronic Headache Disorder',
            urgency: 'MODERATE',
            action: 'Schedule appointment within a week',
            additionalSymptoms: ['sensitivity to light', 'nausea', 'neck pain']
        },
        {
            symptoms: ['urinary problems', 'painful urination', 'frequent urination'],
            condition: 'Urinary Tract Issue',
            urgency: 'MODERATE',
            action: 'See doctor within 2-3 days',
            additionalSymptoms: ['blood in urine', 'cloudy urine', 'pelvic pain']
        }
    ],
    routine: [
        {
            symptoms: ['mild headache', 'occasional headache'],
            condition: 'Tension Headache',
            urgency: 'ROUTINE',
            action: 'Monitor symptoms, use OTC pain relievers',
            selfCare: ['Rest', 'Hydration', 'Stress management']
        },
        {
            symptoms: ['runny nose', 'sneezing', 'nasal congestion'],
            condition: 'Common Cold or Allergies',
            urgency: 'ROUTINE',
            action: 'Self-care usually sufficient',
            selfCare: ['Rest', 'Fluids', 'OTC cold medicine', 'Humidifier']
        },
        {
            symptoms: ['mild fatigue', 'tiredness', 'low energy'],
            condition: 'Fatigue',
            urgency: 'ROUTINE',
            action: 'Monitor and improve sleep habits',
            selfCare: ['Better sleep schedule', 'Exercise', 'Balanced diet', 'Stress management']
        },
        {
            symptoms: ['minor cuts', 'scrapes', 'minor bruises'],
            condition: 'Minor Injury',
            urgency: 'ROUTINE',
            action: 'Self-care and monitor for infection',
            selfCare: ['Clean wound', 'Apply bandage', 'Monitor for redness/swelling']
        },
        {
            symptoms: ['muscle soreness', 'mild muscle pain'],
            condition: 'Muscle Strain',
            urgency: 'ROUTINE',
            action: 'Rest and self-care',
            selfCare: ['Rest', 'Ice/Heat', 'Gentle stretching', 'OTC pain relievers']
        }
    ]
};

// Chronic condition flags that increase urgency
const chronicConditions = [
    'diabetes', 'heart disease', 'asthma', 'copd', 'cancer', 
    'kidney disease', 'liver disease', 'immunocompromised', 
    'pregnancy', 'elderly over 65', 'infant under 1 year'
];

// Risk factors
const riskFactors = {
    age: {
        infant: { under: 1, riskMultiplier: 1.5 },
        child: { under: 12, riskMultiplier: 1.2 },
        elderly: { over: 65, riskMultiplier: 1.3 }
    },
    chronic: {
        hasCondition: true,
        riskMultiplier: 1.4
    }
};

// Analyze symptoms
function analyzeSymptoms(patientData) {
    const { symptoms, age, chronicConditions: patientChronicConditions, duration, severity } = patientData;
    
    let results = {
        matches: [],
        urgencyLevel: 'ROUTINE',
        recommendation: '',
        riskFactors: [],
        selfCareAdvice: [],
        warnings: []
    };

    // Normalize symptoms for matching
    const normalizedSymptoms = symptoms.map(s => s.toLowerCase().trim());

    // Check emergency symptoms first
    for (const category of ['emergency', 'urgent', 'moderate', 'routine']) {
        for (const condition of symptomDatabase[category]) {
            const matchedSymptoms = condition.symptoms.filter(symptom => 
                normalizedSymptoms.some(userSymptom => 
                    userSymptom.includes(symptom) || symptom.includes(userSymptom)
                )
            );

            if (matchedSymptoms.length > 0) {
                results.matches.push({
                    condition: condition.condition,
                    urgency: condition.urgency,
                    matchedSymptoms: matchedSymptoms,
                    action: condition.action,
                    additionalSymptoms: condition.additionalSymptoms,
                    selfCare: condition.selfCare
                });

                // Set highest urgency level
                if (results.urgencyLevel === 'ROUTINE' || 
                    (category === 'moderate' && results.urgencyLevel !== 'URGENT' && results.urgencyLevel !== 'EMERGENCY') ||
                    (category === 'urgent' && results.urgencyLevel !== 'EMERGENCY') ||
                    category === 'emergency') {
                    results.urgencyLevel = condition.urgency;
                    results.recommendation = condition.action;
                }
            }
        }
    }

    // Apply risk factors
    if (age < 1) {
        results.riskFactors.push('Infant - Higher risk category');
        if (results.urgencyLevel === 'ROUTINE') results.urgencyLevel = 'MODERATE';
    } else if (age > 65) {
        results.riskFactors.push('Elderly - Higher risk category');
        if (results.urgencyLevel === 'ROUTINE') results.urgencyLevel = 'MODERATE';
    }

    if (patientChronicConditions && patientChronicConditions.length > 0) {
        results.riskFactors.push(`Chronic conditions: ${patientChronicConditions.join(', ')}`);
        if (results.urgencyLevel === 'ROUTINE') results.urgencyLevel = 'MODERATE';
        if (results.urgencyLevel === 'MODERATE') results.urgencyLevel = 'URGENT';
    }

    // Duration factor
    if (duration && duration.includes('week') && results.urgencyLevel === 'ROUTINE') {
        results.warnings.push('Symptoms lasting over a week should be evaluated by a doctor');
        results.urgencyLevel = 'MODERATE';
    }

    // Severity factor
    if (severity >= 7 && results.urgencyLevel !== 'EMERGENCY') {
        results.urgencyLevel = 'URGENT';
        results.recommendation = 'Severe pain requires urgent medical attention';
    }

    // General self-care advice
    results.selfCareAdvice = [
        'Stay hydrated',
        'Get adequate rest',
        'Monitor your symptoms',
        'Keep a symptom diary',
        'Avoid self-medication without consulting a healthcare provider'
    ];

    return results;
}

// API Endpoints
app.post('/api/analyze-symptoms', (req, res) => {
    try {
        const patientData = req.body;

        // Validation
        if (!patientData.symptoms || patientData.symptoms.length === 0) {
            return res.status(400).json({
                success: false,
                error: 'Please provide at least one symptom'
            });
        }

        const analysis = analyzeSymptoms(patientData);

        res.json({
            success: true,
            data: {
                patient: {
                    age: patientData.age,
                    symptoms: patientData.symptoms
                },
                analysis: analysis,
                timestamp: new Date().toISOString()
            }
        });
    } catch (error) {
        console.error('Error analyzing symptoms:', error);
        res.status(500).json({
            success: false,
            error: 'Failed to analyze symptoms'
        });
    }
});

// Get symptom suggestions
app.get('/api/symptom-suggestions', (req, res) => {
    const allSymptoms = [];
    
    Object.values(symptomDatabase).forEach(category => {
        category.forEach(condition => {
            allSymptoms.push(...condition.symptoms);
            if (condition.additionalSymptoms) {
                allSymptoms.push(...condition.additionalSymptoms);
            }
        });
    });

    const uniqueSymptoms = [...new Set(allSymptoms)].sort();

    res.json({
        success: true,
        symptoms: uniqueSymptoms
    });
});

// Get emergency information
app.get('/api/emergency-info', (req, res) => {
    res.json({
        success: true,
        emergencyNumbers: {
            us: '911',
            uk: '999',
            eu: '112',
            australia: '000',
            india: '112'
        },
        emergencySigns: symptomDatabase.emergency.map(e => ({
            condition: e.condition,
            symptoms: e.symptoms,
            action: e.action
        }))
    });
});

// Save patient history (in-memory for demo)
const patientHistory = [];

app.post('/api/save-assessment', (req, res) => {
    const assessment = {
        id: Date.now(),
        ...req.body,
        timestamp: new Date().toISOString()
    };

    patientHistory.push(assessment);

    res.json({
        success: true,
        assessmentId: assessment.id,
        message: 'Assessment saved successfully'
    });
});

app.get('/api/history/:patientId', (req, res) => {
    const { patientId } = req.params;
    const history = patientHistory.filter(h => h.patientId === patientId);

    res.json({
        success: true,
        history: history
    });
});

// Health check
app.get('/api/health', (req, res) => {
    res.json({
        success: true,
        status: 'Medical Symptom Checker API is running',
        version: '1.0.0',
        endpoints: {
            analyzeSymptoms: 'POST /api/analyze-symptoms',
            symptomSuggestions: 'GET /api/symptom-suggestions',
            emergencyInfo: 'GET /api/emergency-info',
            saveAssessment: 'POST /api/save-assessment',
            history: 'GET /api/history/:patientId'
        }
    });
});

app.listen(PORT, () => {
    console.log(`🏥 Medical Symptom Checker API running on http://localhost:${PORT}`);
    console.log(`📊 API Documentation: http://localhost:${PORT}/api/health`);
});
