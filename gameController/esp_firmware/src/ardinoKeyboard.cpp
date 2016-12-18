#include "include/ArduinoKeyboard.hpp"



ArduinoKeyboard::ArduinoKeyboard(){
  Serial1.begin(ARDUINO_BAUDRATE);
}

void ArduinoKeyboard::update(uint16_t timePassed){
  int i;
  bool doTheFlop = false;

  for(i = 0;i!=activeCount;i++){
    if(this->timer[this->active[i]+1] < (uint16_t)(this->timer[this->active[i]+1]-timePassed)){
      this->timer[this->active[i]+1]=0;
      LOGLN("DEACTIVATION");
      Serial1.write(0x00);
      Serial1.write(this->active[i]);
      this->active[i]=0;
      doTheFlop = true;
    }else{
      this->timer[this->active[i]+1] -= timePassed;
      LOG("UPDATE(")LOG(i)LOG(") NV = : ")LOGLN(this->timer[this->active[i]+1])
    }
  }
  if(doTheFlop){
    int j = 0;
    int r = 0;
    for(i = 0;i!=activeCount;i++){
      this->active[j] = this->active[i];
      if(0==this->active[j]){
        r++;
      }else{
        j++;
      }
    }
    this->activeCount-=r;
  }
}


void ArduinoKeyboard::setState(uint8_t ascii,uint16_t time){

  if(0 == this->timer[ascii+1]){
    if(0!=time){
      LOGLN("ACTIVATION");
      Serial1.write(0x01);
      Serial1.write(ascii);
      this->active[activeCount]=ascii;
      activeCount++;
    }
  }
  this->timer[ascii+1] = time;
}
