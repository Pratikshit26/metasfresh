// 🤙 Casual Speaking Agent - Your Chill AI Buddy
// No corporate speak, just real talk!

class CasualAgent {
  constructor(name = "Buddy") {
    this.name = name;
    this.mood = "chill";
    this.conversations = [];
    this.personality = {
      greetings: [
        "Yo! What's up?",
        "Hey there! How's it going?",
        "Sup! What can I do for ya?",
        "Hey! Good to see you!",
        "What's good?"
      ],
      affirmations: [
        "Yeah, totally!",
        "For sure!",
        "You got it!",
        "Absolutely!",
        "100%!",
        "No doubt!",
        "Hell yeah!"
      ],
      thinking: [
        "Hmm, let me think...",
        "Okay so...",
        "Alright, here's the thing...",
        "Good question...",
        "Let's see...",
        "Interesting..."
      ],
      errors: [
        "Oops, my bad!",
        "Ah shoot, something went wrong",
        "Yikes, that didn't work",
        "Uh oh, got an error here",
        "Dang, hit a snag"
      ],
      goodbyes: [
        "Later! ✌️",
        "Catch you later!",
        "See ya!",
        "Peace out!",
        "Take it easy!",
        "Stay cool!"
      ]
    };
  }

  // Random picker for responses
  random(array) {
    return array[Math.floor(Math.random() * array.length)];
  }

  // Greet the user
  greet() {
    const greeting = this.random(this.personality.greetings);
    console.log(`${this.name}: ${greeting}`);
    return greeting;
  }

  // Respond to user input
  chat(userMessage) {
    this.conversations.push({ role: "user", message: userMessage });
    
    const lowerMsg = userMessage.toLowerCase();
    let response = "";

    // Pattern matching for casual responses
    if (lowerMsg.includes("hello") || lowerMsg.includes("hi") || lowerMsg.includes("hey")) {
      response = this.random(this.personality.greetings);
    }
    else if (lowerMsg.inclxudes("how are you") || lowerMsg.includes("what's up")) {
      response = "I'm doing great, thanks for asking! Just here to help out. What about you?";
    }
    else if (lowerMsg.includes("thanks") || lowerMsg.includes("thank you")) {
      response = "No problem at all! Happy to help! 😊";
    }
    else if (lowerMsg.includes("help")) {
      response = "Sure thing! I'm here to chat and help however I can. Just let me know what you need!";
    }
    else if (lowerMsg.includes("joke")) {
      response = this.tellJoke();
    }
    else if (lowerMsg.includes("weather")) {
      response = "I wish I could check the weather for ya, but I'm not hooked up to that yet. Maybe crack open a window? 🌤️";
    }
    else if (lowerMsg.includes("bye") || lowerMsg.includes("goodbye")) {
      response = this.random(this.personality.goodbyes);
    }
    else if (lowerMsg.includes("?")) {
      response = `${this.random(this.personality.thinking)} That's a good question! Let me break it down for you...`;
    }
    else {
      response = this.generateCasualResponse(userMessage);
    }

    this.conversations.push({ role: "agent", message: response });
    console.log(`${this.name}: ${response}`);
    return response;
  }

  // Generate a casual response
  generateCasualResponse(message) {
    const responses = [
      `Got it! So about "${message}" - that's pretty cool!`,
      `Interesting! Tell me more about that.`,
      `Nice! I hear you on that.`,
      `Yeah, I feel you. What else?`,
      `For sure! That makes sense.`,
      `Totally get what you mean!`
    ];
    return this.random(responses);
  }

  // Tell a random joke
  tellJoke() {
    const jokes = [
      "Why don't programmers like nature? It has too many bugs! 🐛",
      "How many programmers does it take to change a lightbulb? None, that's a hardware problem! 💡",
      "Why do Java developers wear glasses? Because they can't C# 😎",
      "What's a programmer's favorite hangout spot? The Foo Bar! 🍺",
      "Why did the developer go broke? Because they used up all their cache! 💸"
    ];
    return this.random(jokes);
  }

  // Set the mood
  setMood(mood) {
    this.mood = mood;
    console.log(`${this.name}: Cool, switching to ${mood} mode!`);
  }

  // Show conversation history
  showHistory() {
    console.log("\n--- Chat History ---");
    this.conversations.forEach((conv, idx) => {
      const speaker = conv.role === "user" ? "You" : this.name;
      console.log(`${idx + 1}. ${speaker}: ${conv.message}`);
    });
    console.log("-------------------\n");
  }

  // Get advice (casual style)
  giveAdvice(topic) {
    const advice = {
      coding: "Dude, just take it one step at a time. Break it down into smaller chunks, test as you go, and don't be afraid to Google stuff. Everyone does it! 💻",
      life: "Here's the thing - don't stress too much about the small stuff. Focus on what makes you happy and everything else will fall into place. You got this! 🌟",
      career: "Follow what you're passionate about, keep learning new stuff, and network with cool people. Opportunities will come! 🚀",
      health: "Stay hydrated, get some exercise (even just walks), and sleep properly. Your body and mind will thank you! 💪",
      default: "My advice? Just do your thing and don't overthink it. You'll figure it out! ✨"
    };

    const response = advice[topic.toLowerCase()] || advice.default;
    console.log(`${this.name}: ${response}`);
    return response;
  }

  // Motivate the user
  motivate() {
    const motivations = [
      "You're doing awesome! Keep crushing it! 💪",
      "Believe in yourself - you've got this! 🌟",
      "Every expert was once a beginner. You're on the right path! 🚀",
      "Don't give up! You're closer than you think! 🎯",
      "You're capable of amazing things! Let's go! 🔥",
      "Progress over perfection! You're doing great! ⭐"
    ];
    
    const msg = this.random(motivations);
    console.log(`${this.name}: ${msg}`);
    return msg;
  }

  // React to emotions
  reactToEmotion(emotion) {
    const reactions = {
      happy: "That's awesome! Love the positive vibes! 😄",
      sad: "Aw man, sorry to hear that. Wanna talk about it? I'm here for you. 💙",
      angry: "I get it, that's frustrating! Take a deep breath, we'll figure this out. 😤",
      excited: "Hell yeah! That energy is contagious! Let's gooo! 🎉",
      tired: "I feel you. Maybe take a quick break? Coffee helps too! ☕",
      confused: "No worries! Let's break it down together, step by step. 🤔",
      stressed: "Hey, it's gonna be okay. Let's tackle this one thing at a time. You're not alone! 🫂"
    };

    const response = reactions[emotion.toLowerCase()] || "I hear you! Thanks for sharing that with me.";
    console.log(`${this.name}: ${response}`);
    return response;
  }
}

// 🎮 Interactive Demo
function runDemo() {
  console.log("🤙 Starting Casual Agent Demo...\n");
  
  const agent = new CasualAgent("Alex");
  
  // Demo conversation
  agent.greet();
  
  console.log("\n--- Demo Conversation ---\n");
  
  agent.chat("Hey! How's it going?");
  agent.chat("Can you help me with something?");
  agent.chat("Tell me a joke!");
  agent.chat("I'm feeling stressed about coding");
  
  console.log("\n--- Getting Advice ---\n");
  agent.giveAdvice("coding");
  
  console.log("\n--- Motivation Time ---\n");
  agent.motivate();
  
  console.log("\n--- Emotional Support ---\n");
  agent.reactToEmotion("tired");
  
  console.log("\n--- Conversation History ---");
  agent.showHistory();
  
  console.log("\n--- Goodbye ---\n");
  agent.chat("Thanks! Bye!");
}

// 🚀 Export for use in other files
if (typeof module !== 'undefined' && module.exports) {
  module.exports = CasualAgent;
}

// 🎯 Run demo if this file is executed directly
if (require.main === module) {
  runDemo();
}

// 💡 Usage Examples:
/*

// Basic usage
const agent = new CasualAgent("Buddy");
agent.greet();
agent.chat("What's up?");
agent.chat("Tell me a joke");

// Get advice
agent.giveAdvice("coding");
agent.giveAdvice("life");

// Motivation
agent.motivate();

// Emotional support
agent.reactToEmotion("sad");
agent.reactToEmotion("excited");

// View history
agent.showHistory();

*/

console.log("\n✨ Casual Agent loaded! Create an instance and start chatting! ✨\n");
console.log("Example: const agent = new CasualAgent('Buddy');");
console.log("         agent.greet();\n");
