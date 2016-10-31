import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponentPressure, AppComponentTemperature }   from './app.component';

@NgModule({
    imports:      [ BrowserModule ],
    declarations: [ AppComponentPressure, AppComponentTemperature ],
    bootstrap:    [ AppComponentPressure, AppComponentTemperature ]
})
export class AppModule { }