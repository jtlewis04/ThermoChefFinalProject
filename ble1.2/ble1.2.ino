#include <SoftwareSerial.h>  

int bluetoothTx = 1;  // TX-O pin of bluetooth mate, Arduino D2
int bluetoothRx = 0;  // RX-I pin of bluetooth mate, Arduino D3

SoftwareSerial bluetooth(bluetoothRx, bluetoothTx);

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
  String inData = (String)bluetooth.read();
  Serial.print(inData);
  }
 
  // read from USB (Arduino Terminal)
  if (Serial.available()) {
    //Serial.write("usb: ");
    String str = (String)Serial.read();
    bluetooth.print(str);
    Serial.print(str);
    Serial.write('\n');
  }
  // and loop forever and ever!
}
