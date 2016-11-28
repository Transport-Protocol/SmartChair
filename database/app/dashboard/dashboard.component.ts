import {Component, OnInit} from "@angular/core";
import {ChairService} from "../shared/chair.service";

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
            for(var i in chairs) {
                this.chairs[i] = { uuid: chairs[i] };
            }
        });
    }

    ngOnInit() {
        this.getChairs();
    }
}