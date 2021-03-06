// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */

function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

/** Returns a string containgin a fun fact about Efrain */
function randomFunFact(){
    const facts = 
        ["Efraín is the funniest person Efraín knows.", "Efraín has two pet dogs, and one cat.", 
        "Efraín has had a driver's license for four years, but has never owned a car. (He's also super into car culture.)",
        "[1] public class hotTake(){ \n[2] \"Plaid is a fashion statement.\" \n[3] if (you disagree) {\n[4] hotTake() \n[5]}"]

        const funFact = facts[Math.floor(Math.random() * facts.length)];

        const funFactContainer = document.getElementById("funFact-container");
        funFactContainer.innerText = funFact;
    }

/** Returns a string containing on of my favorite quotes. This function gets the quotes from /jsonServlet. */
async function randomQuote(){
    const response = await fetch("/jsonServlet");
    const jsonResponse = await response.json();

    const randomQuoteFromResponse = jsonResponse[Math.floor(Math.random() * jsonResponse.length)];

    const randomQuoteContainer = document.getElementById("quote-container");
    randomQuoteContainer.innerText = randomQuoteFromResponse;    
}

/** Returns a string showing the server response from /fetch */
async function showServerResponse(){
    const response = await fetch("/fetchServlet");
    const responseText = await response.text();

    const responseContainer = document.getElementById("serverResponse-container");
    responseContainer.innerText = responseText;
}