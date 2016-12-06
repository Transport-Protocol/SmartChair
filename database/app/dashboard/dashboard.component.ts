import {Component, OnInit} from "@angular/core";
import {ChairService} from "../shared/chair.service";
import {Chair} from "../shared/chair";

@Component({
    selector: 'dashboard',
    templateUrl: './app/dashboard/dashboard.component.html',
    providers: [ ChairService ]
})

export class DashboardComponent implements OnInit {
    connection;
    chairs = [];
    result;

    constructor(private chairService: ChairService) {  }

    getChairs() {
        this.connection = this.chairService.getChairs().subscribe(chairs => {
            let chairJSON = JSON.parse('' + chairs);
            console.log(new Date().getTime());
            for(var i in chairJSON.cid) {
                this.chairs[i] = new Chair(chairJSON.cid[i]);
                if (new Date().getTime() - new Date(chairJSON.time[i]).getTime() < 300000) {
                    this.chairs[i].active = true;
                } else {
                    this.chairs[i].active = false;
                }
            }
        });
    }

    ngOnInit() {
        this.getChairs();
    }
}