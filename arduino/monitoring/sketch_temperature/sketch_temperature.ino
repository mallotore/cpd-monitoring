const int sensorPin = A0;
const float baselineTemp = 20.0;
const long DEFAULT_PROBE_INTERVAL_IN_MILISECONDS = 10000L;
const long DEFAULT_DELAY_IN_MILISECONDS = 500L;
long probeIntervalInMiliseconds = 0;
long waitingTimeToNotify = 0;

void setup() {
  Serial.begin(9600);
  for(int pinNumber=2; pinNumber<5; pinNumber++){
    pinMode(pinNumber, OUTPUT);
    digitalWrite(pinNumber, LOW);
  }
  if(probeIntervalInMiliseconds == 0){
    probeIntervalInMiliseconds = DEFAULT_PROBE_INTERVAL_IN_MILISECONDS;
  }
}

void loop() {
  int sensorVal = analogRead(sensorPin);
  float voltage = (sensorVal/1024.0) * 5.0;
  float temperature = (voltage - .5) * 100;

  if(temperature < baselineTemp){
    digitalWrite(2, LOW);
    digitalWrite(3, LOW);
    digitalWrite(4, LOW);
  }else if(temperature >= baselineTemp+2 && temperature < baselineTemp+6){
    digitalWrite(2, HIGH);
    digitalWrite(3, HIGH);
    digitalWrite(4, HIGH);
  }else if(temperature >= baselineTemp+6){
    digitalWrite(2, HIGH);
    digitalWrite(3, HIGH);
    digitalWrite(4, HIGH);
  }

  if(waitingTimeToNotify >= probeIntervalInMiliseconds){
    Serial.println(temperature);
    waitingTimeToNotify = DEFAULT_DELAY_IN_MILISECONDS;
  }else{
    waitingTimeToNotify += DEFAULT_DELAY_IN_MILISECONDS;
  }
  delay(DEFAULT_DELAY_IN_MILISECONDS);
}

void serialEvent() {
  while(Serial.available() > 0) {
    String newInterval = Serial.readString();
    char newIntervalBuf[32];
    newInterval.toCharArray(newIntervalBuf, sizeof(newIntervalBuf));
    probeIntervalInMiliseconds = atol(newIntervalBuf);
  }
}
