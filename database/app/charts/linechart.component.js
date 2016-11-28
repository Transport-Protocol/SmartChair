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
///<reference path="../../typings/AmCharts.d.ts" />
var core_1 = require('@angular/core');
var LineChartComponent = (function () {
    function LineChartComponent() {
        this.chartDiv = "chartdiv";
        this.count = 0;
        this.data = [{
                "date": "2016-10-17T12:01:03.381Z",
                "value": 13
            }, {
                "date": "2016-10-18T12:01:03.381Z",
                "value": 11
            }, {
                "date": "2016-10-19T12:01:03.381Z",
                "value": 15
            }, {
                "date": "2016-10-20T12:01:03.381Z",
                "value": 16
            }, {
                "date": "2016-10-21T12:01:03.381Z",
                "value": 18
            }, {
                "date": "2016-10-22T12:01:03.381Z",
                "value": 13
            }, {
                "date": "2016-10-23T12:01:03.381Z",
                "value": 22
            }, {
                "date": "2016-10-24T12:01:03.381Z",
                "value": 23
            }, {
                "date": "2016-10-25T12:01:03.381Z",
                "value": 20
            }, {
                "date": "2016-10-26T12:01:03.381Z",
                "value": 17
            }, {
                "date": "2016-10-27T12:01:03.381Z",
                "value": 16
            }, {
                "date": "2016-10-28T12:01:03.381Z",
                "value": 18
            }, {
                "date": "2016-10-29T12:01:03.381Z",
                "value": 21
            }, {
                "date": "2016-10-31T12:01:03.381Z",
                "value": 25
            }];
    }
    LineChartComponent.prototype.ngAfterViewInit = function () {
        this.chart = AmCharts.makeChart(this.chartDiv, {
            "type": "serial",
            "theme": "light",
            "marginRight": 40,
            "marginLeft": 40,
            "autoMarginOffset": 20,
            //"dataDateFormat": "YYYY-MM-DD",
            "valueAxes": [{
                    "id": "v1",
                    "axisAlpha": 0,
                    "position": "left",
                    "ignoreAxisWidth": true
                }],
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
                "valueLineAlpha": 0.2
            },
            "categoryField": "date",
            "categoryAxis": {
                "parseDates": true,
                "dashLength": 1,
                "minorGridEnabled": true
            },
            "dataProvider": this.data
        });
        //bind(this) is needed, because the "this" context gets lost in setInterval functions
        setInterval(this.update.bind(this), 5000);
    };
    LineChartComponent.prototype.update = function () {
        this.chart.dataProvider.shift();
        this.chart.dataProvider.push({
            date: new Date().setDate(new Date().getDate() + this.count++),
            value: Math.floor(Math.random() * 25)
        });
        this.chart.validateData();
    };
    LineChartComponent = __decorate([
        core_1.Component({
            selector: 'linechart',
            templateUrl: 'app/charts/linechart.component.html'
        }), 
        __metadata('design:paramtypes', [])
    ], LineChartComponent);
    return LineChartComponent;
}());
exports.LineChartComponent = LineChartComponent;
//# sourceMappingURL=linechart.component.js.map