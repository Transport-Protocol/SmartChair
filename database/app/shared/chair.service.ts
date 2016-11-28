/**
 * Created by Kounex on 27.11.16.
 */

import {Injectable} from "@angular/core";
import {Observable} from 'rxjs/Observable';
import * as io from "socket.io-client";

@Injectable()
export class ChairService {

    private url = 'http://localhost:8000'
    private socket;

    getChairs() {
        let observable = new Observable(observer => {
            this.socket = io(this.url);
            this.socket.emit('getChairs', '');

            this.socket.on('chairs', function (data) {
                observer.next(data);
            });
            return () => {
                this.socket.disconnect();
            };
        });
        return observable;
    }

    getPressure(whereUUID) {
        let observable = new Observable(observer => {
            this.socket = io(this.url);
            this.socket.emit('getPressure', whereUUID);

            this.socket.on('pressure', function (data) {
                console.log('getPressure() in chair.service; only data: ' + data);
                observer.next(data);
            });
            return () => {
                this.socket.disconnect();
            };
        });
        return observable;
    }

    getTemperature(whereUUID) {
        let observable = new Observable(observer => {
            this.socket = io(this.url);
            this.socket.emit('getTemperature', whereUUID);

            this.socket.on('temperature', function (data) {
                console.log('getTemperature() in chair.service; only data: ' + data);
                observer.next(data);
            });
            return () => {
                this.socket.disconnect();
            };
        });
        return observable;
    }

    getFirstXTemperatures(amount, whereUUID) {
        let observable = new Observable(observer => {
            this.socket = io(this.url);
            this.socket.emit('getFirstXTemperatures', amount, whereUUID);

            this.socket.on('firstTemperature', function (data) {
                console.log('getFirstXTemperatures() in chair.service; only data: ' + data);
                observer.next(data);
            });
            return () => {
                this.socket.disconnect();
            };
        });
        return observable;
    }


}