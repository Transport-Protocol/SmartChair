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
var chair_service_1 = require("../shared/chair.service");
var chair_1 = require("../shared/chair");
var DashboardComponent = (function () {
    function DashboardComponent(chairService) {
        this.chairService = chairService;
        this.chairs = [];
    }
    DashboardComponent.prototype.getChairs = function () {
        var _this = this;
        this.connection = this.chairService.getChairs().subscribe(function (chairs) {
            var chairJSON = JSON.parse('' + chairs);
            console.log(new Date().getTime());
            for (var i in chairJSON.cid) {
                _this.chairs[i] = new chair_1.Chair(chairJSON.cid[i]);
                if (new Date().getTime() - new Date(chairJSON.time[i]).getTime() < 300000) {
                    _this.chairs[i].active = true;
                }
                else {
                    _this.chairs[i].active = false;
                }
            }
        });
    };
    DashboardComponent.prototype.ngOnInit = function () {
        this.getChairs();
    };
    DashboardComponent = __decorate([
        core_1.Component({
            selector: 'dashboard',
            templateUrl: './app/dashboard/dashboard.component.html',
            providers: [chair_service_1.ChairService]
        }), 
        __metadata('design:paramtypes', [chair_service_1.ChairService])
    ], DashboardComponent);
    return DashboardComponent;
}());
exports.DashboardComponent = DashboardComponent;
//# sourceMappingURL=dashboard.component.js.map