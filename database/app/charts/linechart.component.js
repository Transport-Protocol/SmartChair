///<reference path="../../typings/AmCharts.d.ts" />
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
var LineChartComponent = (function () {
    function LineChartComponent(chairService, route) {
        this.chairService = chairService;
        this.route = route;
        this.chartDiv = "chartdiv";
        this.drawReady = false;
        this.chair = new chair_1.Chair('null');
    }
    LineChartComponent.prototype.ngOnInit = function () {
        this.getChairByID();
        this.getTemperature();
    };
    LineChartComponent.prototype.getChairByID = function () {
        var _this = this;
        this.route.params.forEach(function (params) {
            _this.chair.uuid = params['uuid'];
        });
    };
    LineChartComponent.prototype.ngAfterViewInit = function () {
        this.chart = AmCharts.makeChart(this.chartDiv, {
            "type": "serial",
            "theme": "light",
            "marginRight": 40,
            "marginLeft": 40,
            "autoMarginOffset": 20,
            "dataDateFormat": "YYYY-MM-DD JJ:NN:SS",
            "valueAxes": [{
                    "id": "v1",
                    "axisAlpha": 0,
                    "position": "left",
                    "ignoreAxisWidth": true,
                    "maximum": 50,
                    "minimum": -20,
                    "labelFrequency": 2
                }],
            "numberFormatter": {
                "precision": 1,
                "decimalSeparator": ",",
                "thousandsSeparator": ""
            },
            "balloon": {
                "borderThickness": 1,
                "shadowAlpha": 0
            },
            "graphs": [{
                    "id": "g1",
                    "balloon": {
                        "drop": true,
                        "adjustBorderColor": false,
                        "color": "#ffffff"
                    },
                    "bullet": "round",
                    "bulletBorderAlpha": 1,
                    "bulletColor": "#FFFFFF",
                    "bulletSize": 5,
                    "hideBulletsCount": 50,
                    "lineThickness": 2,
                    "title": "temperature",
                    "useLineColorForBulletBorder": true,
                    "valueField": "value",
                    "balloonText": "<span style='font-size:18px;'>[[value]]</span>"
                }],
            "chartCursor": {
                "pan": true,
                "valueLineEnabled": true,
                "valueLineBalloonEnabled": true,
                "cursorAlpha": 1,
                "cursorColor": "#258cbb",
                "limitToGraph": "g1",
                "valueLineAlpha": 0.2,
                "categoryBalloonDateFormat": "YYYY-MM-DD, JJ:NN:SS"
            },
            "categoryField": "date",
            "categoryAxis": {
                "parseDates": true,
                "minPeriod": "ss",
                "dashLength": 1,
                "minorGridEnabled": true
            },
            "dataProvider": [] //geTemperature - start werte
        });
        this.drawReady = true;
    };
    LineChartComponent.prototype.getTemperature = function () {
        var _this = this;
        this.connection = this.chairService.getTemperature(this.chair.uuid).subscribe(function (temperature) {
            var temperatureJSON = JSON.parse('' + temperature);
            console.log('getTemperature() in temperature.component; pressure before for-loop: ' + temperature);
            if (_this.drawReady)
                _this.updateTemperature(temperatureJSON);
        });
    };
    LineChartComponent.prototype.updateTemperature = function (temperatureJSON) {
        if (this.chart.dataProvider.length > LineChartComponent.MAX_MEASURE_POINTS)
            this.chart.dataProvider.shift();
        console.log(temperatureJSON.time.toLocaleString());
        console.log(JSON.stringify(temperatureJSON.t));
        var dataset = {
            date: temperatureJSON.time,
            value: temperatureJSON.t[0]
        };
        console.log("test");
        if (this.chart.dataProvider.length > 0 && this.chart.dataProvider[this.chart.dataProvider.length - 1].date == dataset.date)
            return;
        this.chart.dataProvider.push(dataset);
        this.chart.validateData();
    };
    LineChartComponent.MAX_MEASURE_POINTS = 20;
    LineChartComponent = __decorate([
        core_1.Component({
            selector: 'linechart',
            templateUrl: 'app/charts/linechart.component.html'
        }), 
        __metadata('design:paramtypes', [chair_service_1.ChairService, router_1.ActivatedRoute])
    ], LineChartComponent);
    return LineChartComponent;
}());
exports.LineChartComponent = LineChartComponent;
//# sourceMappingURL=linechart.component.js.map