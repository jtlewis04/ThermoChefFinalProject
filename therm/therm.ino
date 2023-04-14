#include <SPI.h>
#include "Adafruit_MAX31855.h"
#include <stdio.h>
#include <LiquidCrystal.h>          //the liquid crystal library contains commands for printing to the display


#define MAXDO 5
#define MAXCS 4
#define MAXCLK 3
float degreesC = 0;  //the temperature in Celsius, calculated from the voltage
long longtime;
int time = 0;
int bufferTime = 0;

LiquidCrystal lcd(13, 12, 11, 10, 9, 8);   // tell the RedBoard what pins are connected to the display
Adafruit_MAX31855 thermocouple(MAXCLK, MAXCS, MAXDO);

void setup() {
  Serial.begin(9600);
  lcd.begin(16,2);
  lcd.clear();
  while (!Serial) delay(1);
  delay(100);
}

void loop() {
  degreesC = thermocouple.readCelsius();
  time = (millis() / 1000.0);
lcd.setCursor(0,0);
  lcd.print("Temp: ");
  lcd.print(degreesC);
  lcd.print(" C");
  lcd.setCursor(0,1);
  lcd.print("Time: ");
  lcd.print(time);
  lcd.print(" s");
  
  if(time>256)
  {
    Serial.write(byte(degreesC));
  delay(1);
  // First 8 bits
  Serial.write(byte(time/256));
  delay(1);
  // Last 8 bits
  Serial.write(byte(time));
  Serial.flush();
  }
  else
  {
    Serial.write(byte(degreesC));
    delay(1);
  Serial.write(byte(time));
  Serial.flush();
  }

  delay(1000);  //delay for 1 second between each reading (this makes the display less noisy)
}