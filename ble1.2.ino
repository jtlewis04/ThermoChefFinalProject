#include <SoftwareSerial.h>  

int bluetoothTx = 0;  // TX-O pin of bluetooth mate, Arduino D2
int bluetoothRx = 1;  // RX-I pin of bluetooth mate, Arduino D3

SoftwareSerial bluetooth(bluetoothTx, bluetoothRx);

void setup()
{
  Serial.begin(9600);  // Begin the serial monitor at 9600bps
  bluetooth.begin(9600);  // The Bluetooth Mate defaults to 115200bps
  delay(100);  // Short delay, wait for the Mate to send back CMD
  
  
}

void loop()
{
  bluetooth.listen();  // listen the HM10 port
  // if HM10 sends something then read
  if(bluetooth.available()){
  int inData = (int)bluetooth.read();
  Serial.write(inData);
  }
 
  // read from USB (Arduino Terminal)
  if (Serial.available()) {
    //Serial.write("usb: ");
    String str = Serial.readString();
    bluetooth.print(str);
    Serial.print(str);
    Serial.write('\n');
  }
  // and loop forever and ever!
}
