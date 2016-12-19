import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { PressureComponent }   from './pressure.component';
import { TemperatureComponent }   from './temperature.component';

@NgModule({
    imports:      [ BrowserModule ],
    declarations: [ PressureComponent, TemperatureComponent ],
    bootstrap:    [ PressureComponent, TemperatureComponent ]
})
export class AppModule { }