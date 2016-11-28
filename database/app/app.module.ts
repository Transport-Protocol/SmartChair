import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router'
import { ChairComponent } from './chair/chair.component';
import { PressureComponent }   from './pressure/pressure.component';
import { TemperatureComponent }   from './temperature.component';
import { LineChartComponent } from './charts/linechart.component';
import {AppComponent} from "./app.component";
import {Header} from "./header/header";
import {Footer} from "./footer/footer";
import {NavbarComponent} from "./navbar/navbar.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {AppRoutingModule} from "./app.routing";
import {ChairService} from "./shared/chair.service";

@NgModule({
    imports: [
        BrowserModule,
        AppRoutingModule
    ],
    declarations: [ AppComponent, DashboardComponent, Header, Footer, NavbarComponent, ChairComponent, PressureComponent, TemperatureComponent, LineChartComponent ],
    providers: [ ChairService ],
    bootstrap:    [ AppComponent ]
})
export class AppModule { }