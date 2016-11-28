/// <reference path="../typings/socket-io.d.ts" />
"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var ringbuffer_1 = require("./shared/ringbuffer");
var io = require("socket.io-client");
var TemperatureComponent = (function () {
    function TemperatureComponent() {
        this.temperature = new ringbuffer_1.RingBuffer(20);
        this.socket = null;
        this.drawReady = false;
        this.socket = io('http://localhost:8000');
        this.socket.emit('connect', 'hi');
        this.socket.on('message', function (message) {
            this.socket.emit('message', 'Hello Server, Im fine');
            console.log(message);
        }.bind(this));
        this.socket.on('temperature', function (message) {
            this.updateTemperature(JSON.parse(message));
        }.bind(this));
    }
    TemperatureComponent.prototype.updateTemperature = function (temperatureJSON) {
        this.temperature.push(temperatureJSON.t[1]);
        if (this.drawReady) {
            this.drawCanvasTemperature();
        }
    };
    //draw code
    TemperatureComponent.prototype.ngAfterViewInit = function () {
        this.drawReady = true;
    };
    //temperature code
    TemperatureComponent.prototype.drawCanvasTemperature = function () {
        var ctx = this.temperatureCanvas.nativeElement.getContext("2d");
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
    };
    __decorate([
        core_1.ViewChild("temperatureCanvas"), 
        __metadata('design:type', core_1.ElementRef)
    ], TemperatureComponent.prototype, "temperatureCanvas", void 0);
    TemperatureComponent = __decorate([
        core_1.Component({
            selector: 'temperature',
            template: "\n        <canvas class=\"canvas\" #temperatureCanvas width=\"300\" height=\"150\"></canvas>\n    ",
            styles: ["\n        .canvas {\n          border: 1px solid black;\n          padding-left: 15px;\n          padding-right: 15px;\n        }\n        img {\n            display: none;\n        }\n    "]
        }), 
        __metadata('design:paramtypes', [])
    ], TemperatureComponent);
    return TemperatureComponent;
}());
exports.TemperatureComponent = TemperatureComponent;
//# sourceMappingURL=temperature.component.js.map