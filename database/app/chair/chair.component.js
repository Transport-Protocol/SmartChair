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
var ChairComponent = (function () {
    function ChairComponent(route, zone) {
        this.route = route;
        this.zone = zone;
        this.chair = new chair_1.Chair('null');
    }
    ChairComponent.prototype.getChairByID = function () {
        var _this = this;
        this.route.params.forEach(function (params) {
            _this.chair.uuid = params['uuid'];
        });
    };
    ChairComponent.prototype.ngOnInit = function () {
        this.getChairByID();
    };
    ChairComponent = __decorate([
        core_1.Component({
            selector: 'chair',
            templateUrl: './app/chair/chair.component.html'
        }), 
        __metadata('design:paramtypes', [router_1.ActivatedRoute, core_1.NgZone])
    ], ChairComponent);
    return ChairComponent;
}());
exports.ChairComponent = ChairComponent;
//# sourceMappingURL=chair.component.js.map