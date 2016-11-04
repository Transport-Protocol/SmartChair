import {Component, ViewChild, ElementRef} from '@angular/core';
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

/**
 * Ringbuffer is constructed with a fixed size of elements, which are pre-initialized with 0
 */
export class RingBuffer {
    size = 0;
    head = 0;
    data = [];

    constructor(size: Number) {
        for(var i = 0; i < this.size; i++) {
            this.data.push(0);
        }
    }

    /**
     * returns size of RingBuffer
     * @returns {number}: size of RingBuffer
     */
    getLength() {
        return this.size;
    }

    /**
     * pushes value into the buffer, overriding the item at head+1 and setting head to this position;
     * @param value: the value, which shall be stored
     */
    push(value) {
        this.head = (this.head + 1) % this.size;
        this.data[this.head] = value;
    };

    /**
     * gets the item at head - index postion (
     * @param index: 0 is the newest item, the higher the number, the longer the item is stored already
     * @returns the value at the given position
     */
    get(index) {
        var i = (this.head - index);
        i = (i < 0 ? i+this.size: i);
        return this.data[i];
    };
}