/*AT VERSION OF ARDUINO CODE*/
#include<SoftwareSerial.h>

SoftwareSerial esp(5,6);   //RX TX    2 4 
String port="5500";

int ledPin=13;
int rpin=9;
int gpin=10;
int bpin = 11;
int bupin=8;
int gapin = 4;
String addr = "192.168.43.89";



void setup(){ 
 esp.begin(9600);
 Serial.begin(9600);
 Serial.println("Connecting...");

//pinmode
pinMode(ledPin,OUTPUT);
pinMode(rpin,OUTPUT);
pinMode(bpin,OUTPUT);
pinMode(gpin,OUTPUT);
pinMode(bupin,OUTPUT);
pinMode(gapin,INPUT);

//pinmode

 
//ESP STATUS 
 int state=0;
 while(!state){
    esp.println("AT");
   
    delay(100);
    String a = esp.readString();
    Serial.println(a); 
    if(a.indexOf("OK")>-1){
      state=1;
      Serial.println("Ready!"); 
    }
    delay(100);
 }

// CONNECTION STATUS
state=0;
while(!state){
    esp.println("AT+CIFSR");
    
    delay(10);
    String a = esp.readString();
    delay(100);
    esp.println("AT+CWJAP?");
    delay(100);
    a+="\n"+esp.readString();
     if(a.indexOf("OK")>-1){
      state=1;
      Serial.println("Connected! "+a); 
    }
    delay(100);

}

//CIPMUX
state=0;
while(!state){
    esp.println("AT+CIPMUX=1");
    delay(100);
    String a = esp.readString(); 
     if(a.indexOf("OK")>-1 || a.indexOf("li")){
      state=1;
      Serial.println("CIPMUX SUCCESS "+a); 
    }
    else
       Serial.println("CIPMUX FAILED "+a); 
       delay(100);
}
//start server
 state=0;
 ///while(!state){
    esp.println("AT+CIPSERVER=1,"+port);
    
    delay(100);
    String a = esp.readString(); 
    if(a.indexOf("OK")>-1){
      state=1;
      Serial.println("SERVER STARTED AT "+port); 
    }
    else
       Serial.println("AT+CIPSERVER=1,"+port); 
    delay(100);
    digitalWrite(13,HIGH);
 //}
  

}

void loop(){
     
     String a="";
     
     
    (a=esp.readString());
     Serial.print(a);
  
         
         //-------------------------------------------------------------------------------  
         a.toLowerCase();  
         if(a.indexOf("rn")>-1)  {
           on(rpin);
         }
         else if(a.indexOf("rf")>-1){
            off(rpin);
         }
         else  if(a.indexOf("gn")>-1)  {
           on(gpin);
         }
         else if(a.indexOf("gf")>-1){
            off(gpin);
         }
         else  if(a.indexOf("bn")>-1)  {
           on(bpin);
         }
         else if(a.indexOf("bf")>-1){
            off(bpin);
         }
         

         else if(a.indexOf("wh")>-1){
            blink(gpin,5,500);
         }
         
         else if(a.indexOf("mm")>-1){
            blink(rpin,5,500);
         }

         else  if(a.indexOf("fa")>-1){
            blink(bpin,5,500);
         }
      
        if(digitalRead(gapin)>0){
           digitalWrite(rpin,HIGH);
       
        // ------------------------------- 
            
            esp.println("AT+CIPSTART=0,\"TCP\",\""+addr+"\",5506");
            delay(50);
            esp.println("AT+CIPSEND=3");
            
            delay(50);
             esp.println("gas"); 
            delay(50); 
            esp.println("AT+CIPCLOSE=0");
                         
          delay(100);
             
            Serial.println(esp.readString());
            
            blink(bupin,5,500);
            digitalWrite(rpin,LOW);   
          }
       a="";
//-------------------------------------------------------------------------------
     delay(10);
     
}

void  on(int lPin){
     for(int i=10;i<255;i++){
        analogWrite(lPin,i);
        delay(10);
     }
     digitalWrite(lPin,HIGH); 
}

void off(int lPin){
     
     for(int i=255;i>-1;i--){
        analogWrite(lPin,i);
        delay(1);
     }
      
}
void blink(int lPin,int n,int Delay){
  for(int i=0;i<n;i++){
      digitalWrite(lPin,HIGH);
      delay(Delay);
      digitalWrite(lPin,LOW);
      delay(Delay);
  }
}





