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
            for(var i in chairs) {
                this.chairs[i] = { uuid: chairs[i] };
            }
        });
    }

    ngOnInit() {
        this.getChairs();
    }
}