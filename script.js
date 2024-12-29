const appliances = {
    light: document.getElementById('light'),
    fan: document.getElementById('fan'),
    tv: document.getElementById('tv')
  };
  
  function toggleAppliance(applianceName, action) {
    const appliance = appliances[applianceName];
    if (!appliance) return;
  
    if (action === 'on') {
      appliance.classList.remove('off');
      appliance.classList.add('on');
      appliance.querySelector('.status').textContent = 'Status: On';
    } else if (action === 'off') {
      appliance.classList.remove('on');
      appliance.classList.add('off');
      appliance.querySelector('.status').textContent = 'Status: Off';
    }
  }
  
  function startListening() {
    if (!('webkitSpeechRecognition' in window)) {
      alert('Speech recognition not supported in this browser. Please use Chrome.');
      return;
    }
  
    const recognition = new webkitSpeechRecognition();
    recognition.continuous = false;
    recognition.lang = 'en-US';
  
    recognition.onstart = function () {
      console.log('Voice recognition started. Speak into the microphone.');
    };
  
    recognition.onresult = function (event) {
      const transcript = event.results[0][0].transcript.toLowerCase();
      console.log('Voice Input:', transcript);
  
      if (transcript.includes('turn on')) {
        const applianceName = transcript.split('turn on ')[1];
        toggleAppliance(applianceName, 'on');
      } else if (transcript.includes('turn off')) {
        const applianceName = transcript.split('turn off ')[1];
        toggleAppliance(applianceName, 'off');
      } else {
        alert('Command not recognized. Try "Turn on [appliance]" or "Turn off [appliance]".');
      }
    };
  
    recognition.onerror = function (event) {
      console.error('Error occurred in recognition:', event.error);
    };
  
    recognition.onend = function () {
      console.log('Voice recognition ended.');
    };
  
    recognition.start();
  }
  
