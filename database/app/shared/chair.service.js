/**
 * Created by Kounex on 27.11.16.
 */
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
var core_1 = require("@angular/core");
var Observable_1 = require('rxjs/Observable');
var io = require("socket.io-client");
var ChairService = (function () {
    function ChairService() {
        this.socket = io('http://localhost:8000');
    }
    ChairService.prototype.getChairs = function () {
        var _this = this;
        this.socket.disconnect();
        this.socket = io('http://localhost:8000');
        var observable = new Observable_1.Observable(function (observer) {
            _this.socket.emit('getChairs', '');
            _this.socket.on('chairs', function (data) {
                observer.next(data);
            });
            return function () {
                _this.socket.disconnect();
            };
        });
        return observable;
    };
    ChairService.prototype.getPressure = function (whereUUID) {
        var _this = this;
        this.socket.disconnect();
        this.socket = io('http://localhost:8000');
        var observable = new Observable_1.Observable(function (observer) {
            _this.socket.emit('getPressure', whereUUID);
            _this.socket.on('pressure', function (data) {
                //console.log('getPressure() in chair.service; only data: ' + data);
                observer.next(data);
            });
            return function () {
                _this.socket.disconnect();
            };
        });
        return observable;
    };
    ChairService.prototype.getTemperature = function (whereUUID) {
        var _this = this;
        this.socket.disconnect();
        this.socket = io('http://localhost:8000');
        var observable = new Observable_1.Observable(function (observer) {
            _this.socket.emit('getTemperature', whereUUID);
            _this.socket.on('temperature', function (data) {
                //console.log('getTemperature() in chair.service; only data: ' + data);
                observer.next(data);
            });
            return function () {
                _this.socket.disconnect();
            };
        });
        return observable;
    };
    ChairService.prototype.getFirstXTemperatures = function (amount, whereUUID) {
        var _this = this;
        this.socket.disconnect();
        this.socket = io('http://localhost:8000');
        var observable = new Observable_1.Observable(function (observer) {
            _this.socket.emit('getFirstXTemperatures', amount, whereUUID);
            _this.socket.on('firstTemperature', function (data) {
                //console.log('getFirstXTemperatures() in chair.service; only data: ' + data);
                observer.next(data);
            });
            return function () {
                _this.socket.disconnect();
            };
        });
        return observable;
    };
    ChairService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [])
    ], ChairService);
    return ChairService;
}());
exports.ChairService = ChairService;
//# sourceMappingURL=chair.service.js.map