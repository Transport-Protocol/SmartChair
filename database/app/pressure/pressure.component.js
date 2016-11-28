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
var chair_1 = require("../shared/chair");
var router_1 = require("@angular/router");
var chair_service_1 = require("../shared/chair.service");
var Observable_1 = require("rxjs/Observable");
var PressureComponent = (function () {
    function PressureComponent(chairService, route, zone) {
        var _this = this;
        this.chairService = chairService;
        this.route = route;
        this.zone = zone;
        this.pressure = {};
        this.drawReady = false;
        this.chair = new chair_1.Chair('null');
        this.detectChange().subscribe((function (uuid) {
            if (uuid != _this.chair.uuid) {
                _this.zone.run(function () {
                    console.log('re-render');
                    _this.start();
                });
            }
        }));
    }
    PressureComponent.prototype.detectChange = function () {
        var _this = this;
        var observable = new Observable_1.Observable(function (observer) {
            _this.route.params.forEach(function (params) {
                observer.next(params['uuid']);
            });
        });
        return observable;
    };
    PressureComponent.prototype.ngOnInit = function () {
        this.start();
    };
    PressureComponent.prototype.start = function () {
        this.getChairByID();
        for (var i = 0; i < 10; i++) {
            this.pressure[i] = '' + i;
        }
        this.getPressure();
    };
    PressureComponent.prototype.getChairByID = function () {
        var _this = this;
        this.route.params.forEach(function (params) {
            _this.chair.uuid = params['uuid'];
        });
    };
    PressureComponent.prototype.getPressure = function () {
        var _this = this;
        this.connection = this.chairService.getPressure(this.chair.uuid).subscribe(function (pressure) {
            var pressureJSON = JSON.parse('' + pressure);
            //console.log('getPressure() in pressure.component; pressure before for-loop: ' + pressure);
            for (var i in pressureJSON.p) {
                //console.log('getPressure() in pressure.component; pressure in for-loop: ' + pressureJSON.p[i]);
                _this.pressure[i] = pressureJSON.p[i];
            }
            if (_this.drawReady) {
                _this.drawCanvasPressure();
            }
        });
    };
    //draw code
    PressureComponent.prototype.ngAfterViewInit = function () {
        this.drawReady = true;
    };
    PressureComponent.prototype.drawCanvasPressure = function () {
        var ctx = this.pressureCanvas.nativeElement.getContext("2d");
        //some variables to scale context correctly to canvas
        ctx.canvas.height = 2 * ctx.canvas.width; //keep ratio to 2:3
        var disY = ctx.canvas.height / 6;
        var disX = ctx.canvas.width / 3;
        var radius = (disY > disX ? disX : disY) / 3;
        ctx.lineWidth = 1;
        ctx.strokeStyle = '#000000';
        //draw chair outlines
        drawRoundRect(disX / 2, disY / 2, 2 * disX, 3 * disY, radius);
        drawRoundRect(disX / 2, 3 * disY + disY / 2, 2 * disX, 2 * disY, radius);
        drawRoundRect(0, 3 * disY + disY / 2, disX / 2, 1.8 * disY, radius);
        drawRoundRect(ctx.canvas.width - disX / 2, 3 * disY + disY / 2, disX / 2, 1.8 * disY, radius);
        /*
        for(var i in this.pressure) {
            console.log('drawCanvasPressure() in pressure.component; pressure '+ i + ': ' + this.pressure[i]);
        }
        */
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
        function drawCircle(posX, posY, color) {
            ctx.beginPath();
            ctx.arc(posX * disX, posY * disY, radius, 0, 2 * Math.PI);
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
            return value / 1024;
        }
    };
    __decorate([
        core_1.ViewChild("pressureCanvas"), 
        __metadata('design:type', core_1.ElementRef)
    ], PressureComponent.prototype, "pressureCanvas", void 0);
    PressureComponent = __decorate([
        core_1.Component({
            selector: 'pressure',
            template: "\n        <canvas class=\"canvas\" #pressureCanvas width=\"150\" height=\"300\"></canvas>\n    ",
            styles: ["\n        .canvas {\n          border: 1px solid black;\n          padding-left: 15px;\n          padding-right: 15px;\n        }\n        img {\n            display: none;\n        }\n    "],
            providers: [chair_service_1.ChairService]
        }), 
        __metadata('design:paramtypes', [chair_service_1.ChairService, router_1.ActivatedRoute, core_1.NgZone])
    ], PressureComponent);
    return PressureComponent;
}());
exports.PressureComponent = PressureComponent;
//# sourceMappingURL=pressure.component.js.map