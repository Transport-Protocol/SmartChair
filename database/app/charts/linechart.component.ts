///<reference path="../../typings/AmCharts.d.ts" />

import {Component, AfterViewInit, OnInit} from '@angular/core';

import {Chair} from "../shared/chair";
import {Params, ActivatedRoute} from "@angular/router";
import {ChairService} from "../shared/chair.service";

@Component({
    selector: 'linechart',
    templateUrl: 'app/charts/linechart.component.html'
})
export class LineChartComponent implements OnInit {

	static readonly MAX_MEASURE_POINTS = 20;

	chart: AmCharts.AmChart;
	chartDiv = "chartdiv";
	drawReady = false;
	connection;
	chair: Chair = new Chair('null');

	constructor(private chairService: ChairService, private route: ActivatedRoute) {
	}

	ngOnInit() {
		this.getChairByID();

		this.getTemperature();
	}

	getChairByID() {
		this.route.params.forEach((params: Params) => {
			this.chair.uuid = params['uuid'];
		});
	}

	ngAfterViewInit(): void {
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
				"ignoreAxisWidth":true,
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
				"balloon":{
					"drop":true,
					"adjustBorderColor":false,
					"color":"#ffffff"
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
				"cursorAlpha":1,
				"cursorColor":"#258cbb",
				"limitToGraph":"g1",
				"valueLineAlpha":0.2,
				"categoryBalloonDateFormat": "YYYY-MM-DD, JJ:NN:SS"
			},
			"categoryField": "date",
			"categoryAxis": {
				"parseDates": true,
				"minPeriod": "ss",
				"dashLength": 1,
				"minorGridEnabled": true
			},
			"dataProvider": []//geTemperature - start werte
		});
		this.drawReady = true;
	}

	getTemperature() {
		this.connection = this.chairService.getTemperature(this.chair.uuid).subscribe(temperature => {
			let temperatureJSON = JSON.parse('' + temperature);
			console.log('getTemperature() in temperature.component; pressure before for-loop: ' + temperature);
			if(this.drawReady) this.updateTemperature(temperatureJSON);
		});
	}

    updateTemperature(temperatureJSON) {
		if(this.chart.dataProvider.length > LineChartComponent.MAX_MEASURE_POINTS)
			this.chart.dataProvider.shift();
		console.log(temperatureJSON.time.toLocaleString());
		console.log(JSON.stringify(temperatureJSON.t));
		let dataset = {
			date: temperatureJSON.time,
			value: temperatureJSON.t[0]
		}
		console.log("test");
		if(this.chart.dataProvider.length > 0 && this.chart.dataProvider[this.chart.dataProvider.length-1].date == dataset.date)
			return;
		this.chart.dataProvider.push(dataset);
		this.chart.validateData();
	}

}