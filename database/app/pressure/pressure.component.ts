import {Component, ViewChild, ElementRef, OnInit} from '@angular/core';
import {Chair} from "../shared/chair";
import {Params, ActivatedRoute} from "@angular/router";
import {ChairService} from "../shared/chair.service";

@Component({
    selector: 'pressure',
    template: `
        <canvas class="canvas" #pressureCanvas width="150" height="300"></canvas>
    `,
    styles: [`
        .canvas {
          border: 1px solid black;
          padding-left: 15px;
          padding-right: 15px;
        }
        img {
            display: none;
        }
    `],
    providers: [ChairService]
})

export class PressureComponent implements OnInit {

    pressure = {};
    socket:any = null;
    connection;
    drawReady = false;
    chair: Chair = new Chair('null');

    @ViewChild("pressureCanvas") pressureCanvas: ElementRef;

    constructor(private chairService: ChairService, private route: ActivatedRoute) {
    }

    ngOnInit() {

        this.getChairByID();

        for(var i = 0; i < 10; i++) {
            this.pressure[i] = ''+i;
        }

        this.getPressure();
    }

    getChairByID() {
        this.route.params.forEach((params: Params) => {
            this.chair.uuid = params['uuid'];
        });
    }

    getPressure() {
        this.connection = this.chairService.getPressure(this.chair.uuid).subscribe(pressure => {
            let pressureJSON = JSON.parse('' + pressure);
            console.log('getPressure() in pressure.component; pressure before for-loop: ' + pressure);
            for(var i in pressureJSON.p) {
                console.log('getPressure() in pressure.component; pressure in for-loop: ' + pressureJSON.p[i]);
                this.pressure[i] = pressureJSON.p[i];
            }
            if(this.drawReady) {
                this.drawCanvasPressure();
            }
        });
    }

    //draw code
    ngAfterViewInit() {
        this.drawReady = true;
    }

    drawCanvasPressure(): void {
        let ctx: CanvasRenderingContext2D = this.pressureCanvas.nativeElement.getContext("2d");
        //some variables to scale context correctly to canvas
        ctx.canvas.height = 2*ctx.canvas.width; //keep ratio to 2:3
        var disY = ctx.canvas.height / 6;
        var disX = ctx.canvas.width / 3;
        var radius = (disY > disX ? disX : disY) / 3;

        ctx.lineWidth = 1;
        ctx.strokeStyle = '#000000';

        //draw chair outlines
        drawRoundRect(disX/2,disY/2,2*disX,3*disY,radius);
        drawRoundRect(disX/2,3*disY+disY/2,2*disX,2*disY,radius);
        drawRoundRect(0,3*disY+disY/2,disX/2,1.8*disY,radius);
        drawRoundRect(ctx.canvas.width-disX/2,3*disY+disY/2,disX/2,1.8*disY,radius);

        for(var i in this.pressure) {
            console.log('drawCanvasPressure() in pressure.component; pressure '+ i + ': ' + this.pressure[i]);
        }

        drawCircle(1, 1, this.pressure[4]);
        drawCircle(2, 1, this.pressure[5]);
        drawCircle(1, 2, this.pressure[6]);
        drawCircle(2, 2, this.pressure[7]);
        drawCircle(1, 3, this.pressure[8]);
        drawCircle(2, 3, this.pressure[9]);
        drawCircle(1, 4, this.pressure[0]);
        drawCircle(2, 4, this.pressure[1]);
        drawCircle(1, 5, this.pressure[2]);
        drawCircle(2, 5, this.pressure[3]);

        function drawRoundRect(x, y, w, h, r) {
            if (w < 2 * r) r = w / 2;
            if (h < 2 * r) r = h / 2;
            ctx.beginPath();
            ctx.moveTo(x+r, y);
            ctx.arcTo(x+w, y,   x+w, y+h, r);
            ctx.arcTo(x+w, y+h, x,   y+h, r);
            ctx.arcTo(x,   y+h, x,   y,   r);
            ctx.arcTo(x,   y,   x+w, y,   r);
            ctx.closePath();
            ctx.stroke();
        }


        function drawCircle(posX, posY, color) {
            ctx.beginPath();
            ctx.arc(posX * disX, posY * disY, radius, 0, 2*Math.PI);
            ctx.fillStyle = percentageToHsl(toPercent(color), 120, 0);
            ctx.fill();
            ctx.stroke();
        }

        /**
         * converts a percentage to a HSL color
         * @param percentage: the percentage value as a float between 0 and 1
         * @param hue0: color at 0%
         * @param hue1: color at 100%
         * @returns {string}: returns the string for a HSL-color
         */
        function percentageToHsl(percentage, hue0, hue1) {
            var hue = (percentage * (hue1 - hue0)) + hue0;
            return 'hsl(' + hue + ', 100%, 50%)';
        }

        /**
         * calculates the percentual value of incoming pressure data
         * @param value: pressure value from 0 to 1024
         * @returns {number} pressure value in 0% to 100%
         */
        function toPercent(value) {
            return value / 1024
        }
    }
}