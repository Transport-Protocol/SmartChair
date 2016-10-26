int sensorPin0 = A0;    // select the input pin for the potentiometer
int sensorPin1 = A1;    // select the input pin for the potentiometer
int addPin0   = 8;
int addPin1   = 9;
int addPin2   = 10;

void setup() {
  Serial.begin(9600);
  // put your setup code here, to run once:
  pinMode(addPin0, OUTPUT);
  pinMode(addPin1, OUTPUT);
  pinMode(addPin2, OUTPUT);

  
  digitalWrite(addPin0, LOW);
  digitalWrite(addPin1, LOW);
  digitalWrite(addPin2, LOW);
  
}


void loop() {
  int sensorValue0;
  int sensorValue1;
  int address=0;
  
  Serial.print("Start:----------------------------\n");
  while(address<8){
    digitalWrite(addPin0, (address & 0x1)?HIGH:LOW);
    digitalWrite(addPin1, (address & 0x2)?HIGH:LOW);
    digitalWrite(addPin2, (address & 0x4)?HIGH:LOW);
    sensorValue0 = analogRead(sensorPin0);
    sensorValue1 = analogRead(sensorPin1);
    Serial.print(sensorValue0);
    Serial.print("\n");
    Serial.print(sensorValue1);
    Serial.print("\n");
    address+=1;
  }
  delay(1000);
}
