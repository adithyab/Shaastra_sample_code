# Shaastra Launchfest Hackathon 2017 

* Team name: everyTHING
* Members
   * Adithya Balaji
   * Anirudh Swaminathan
   * Sudharsan ST   

## Sample code:

* One single framework called <b>everyTHING</b>
* Arduino compatible
* Uses ESP8266 module

## What's going to be done:

1. Raspberry Pi - Main client for Hasura 
2. ESP is like a server.
3. All devices are connected to the Pi
4. Pi is connected to internet.
5. Phone is also connected to the internet.
6. Phone and the Pi make API calls.
7. If new data, Pi sends data to Hasura.
8. The phone, since it constantly pings Hasura the data gets updated 
for the phone.

ESP <-> Raspi    : Socket Commnication<br>
Raspi <-> Hasura : HTTP<br>
Hasura <-> Phone : HTTP
