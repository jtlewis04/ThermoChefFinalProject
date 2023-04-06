
float voltage = 0;                          //the voltage measured from the TMP36
float degreesC = 0;                         //the temperature in Celsius, calculated from the voltage
float degreesF = 0;                         //the temperature in Fahrenheit, calculated from the voltage
byte time = 0;

void setup() {
  Serial.begin(9600);
  time = millis();
}

void loop() {   
  voltage = analogRead(A0) * 0.004882813;   //convert the analog reading, which varies from 0 to 1023, back to a voltage value from 0-5 volts
  degreesC = (voltage - 0.5) * 100.0;       //convert the voltage to a temperature in degrees Celsius
  degreesF = degreesC * (9.0 / 5.0) + 32.0;
  // time = byte(millis()/1000.0);
  // if(Serial.available()){
  // Serial.write(time);
  Serial.write((byte)degreesC);
  Serial.flush();
  // }Q

  delay(1000);                              //delay for 1 second between each reading (this makes the display less noisy)
}

