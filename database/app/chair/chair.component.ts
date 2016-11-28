import {Component, OnInit} from '@angular/core'
import {Chair} from "../shared/chair";
import {Params, ActivatedRoute} from "@angular/router";

@Component({
    selector: 'chair',
    templateUrl: './app/chair/chair.component.html'
})

export class ChairComponent implements OnInit {

    chair: Chair = new Chair('null');

    constructor(private route: ActivatedRoute) {  }

    getChairByID() {
        this.route.params.forEach((params: Params) => {
            this.chair.uuid = params['uuid'];
        });
    }

    ngOnInit() {
        this.getChairByID();
    }

}
