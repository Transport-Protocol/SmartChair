/// <reference path="../typings/socket-io.d.ts" />

import {Component, ViewChild, ElementRef} from '@angular/core';
import { RingBuffer } from "./shared/ringbuffer";
import * as io from "socket.io-client";

@Component({
    selector: 'temperature',
    template: `
        <canvas class="canvas" #temperatureCanvas width="300" height="150"></canvas>
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
    `]
})

export class TemperatureComponent {

    temperature = new RingBuffer(20);
    socket:any = null;
    drawReady = false;

    @ViewChild("temperatureCanvas") temperatureCanvas: ElementRef;

    constructor() {
        this.socket = io('http://localhost:8000');
        this.socket.emit('connect', 'hi');

        this.socket.on('message', function(message){
            this.socket.emit('message', 'Hello Server, Im fine');
            console.log(message);
        }.bind(this));

        this.socket.on('temperature', function (message) {
            this.updateTemperature(JSON.parse(message));
        }.bind(this));
    }

    updateTemperature(temperatureJSON) {
        this.temperature.push(temperatureJSON.t[1])
        if (this.drawReady) {
            this.drawCanvasTemperature();
        }
    }

    //draw code
    ngAfterViewInit() {
        this.drawReady = true;
    }

    //temperature code
    drawCanvasTemperature(): void {
        let ctx: CanvasRenderingContext2D = this.temperatureCanvas.nativeElement.getContext("2d");
        //some variables to scale context correctly to canvas
        ctx.canvas.height = 2 * ctx.canvas.width; //keep ratio to 2:3
        var disY = ctx.canvas.height;
        var disX = ctx.canvas.width;
        var radius = (disY > disX ? disX : disY) / 3;
        ctx.lineWidth = 1;
        ctx.strokeStyle = '#000000';

        drawRoundRect(disX / 2, disY / 2, 2 * disX, 3 * disY, radius);
        drawRoundRect(disX / 2, 3 * disY + disY / 2, 2 * disX, 2 * disY, radius);
        drawRoundRect(0, 3 * disY + disY / 2, disX / 2, 1.8 * disY, radius);
        drawRoundRect(ctx.canvas.width - disX / 2, 3 * disY + disY / 2, disX / 2, 1.8 * disY, radius);

        function drawRoundRect(x, y, w, h, r) {
            if (w < 2 * r)
                r = w / 2;
            if (h < 2 * r)
                r = h / 2;
            ctx.beginPath();
            ctx.moveTo(x + r, y);
            ctx.arcTo(x + w, y, x + w, y + h, r);
            ctx.arcTo(x + w, y + h, x, y + h, r);
            ctx.arcTo(x, y + h, x, y, r);
            ctx.arcTo(x, y, x + w, y, r);
            ctx.closePath();
            ctx.stroke();
        }
    }
}