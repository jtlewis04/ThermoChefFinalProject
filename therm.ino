float thermPin = 0;
float thing = 0;

void setup() {
  // put your setup code here, to run once:
Serial.begin(9600);
pinMode(A0, INPUT);
pinMode(A1, INPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
thermPin = analogRead(A0);
thing = analogRead(A1);
float Vo = thermPin*(5)/1023;
float resistance = -Vo*300/(Vo-5);
Serial.println(resistance);
delay(10);
}
