/**
 * Created by Kounex on 21.11.16.
 */

import {Component, OnInit} from '@angular/core';
import {ChairService} from '../shared/chair.service';
import {Chair} from "../shared/chair";

@Component({
    selector: 'navbar',
    templateUrl: './app/navbar/navbar.component.html',
    providers: [ChairService]
})

export class NavbarComponent implements OnInit {
    title = 'Smart Chair';
    connection;
    chairs = [];
    result;

    constructor(private chairService: ChairService) {  }

    getChairs() {
        this.connection = this.chairService.getChairs().subscribe(chairs => {
            let chairJSON = JSON.parse('' + chairs);
            for(var i in chairJSON.cid) {
                this.chairs[i] = new Chair(chairJSON.cid[i]);
            }
        });
    }

    ngOnInit() {
        this.getChairs();
    }
}