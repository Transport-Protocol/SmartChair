/**
 * Created by Kounex on 27.11.16.
 */

import {Injectable} from "@angular/core";
import {Observable} from 'rxjs/Observable';
import * as io from "socket.io-client";

@Injectable()
export class ChairService {

    private socket = io('http://localhost:8000');

    getChairs() {
        this.socket.disconnect()
        this.socket = io('http://localhost:8000');
        let observable = new Observable(observer => {
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
        this.socket.disconnect()
        this.socket = io('http://localhost:8000');
        let observable = new Observable(observer => {
            this.socket.emit('getPressure', whereUUID);

            this.socket.on('pressure', function (data) {
                //console.log('getPressure() in chair.service; only data: ' + data);
                observer.next(data);
            });
            return () => {
                this.socket.disconnect();
            };
        });
        return observable;
    }

    getTemperature(whereUUID) {
        this.socket.disconnect()
        this.socket = io('http://localhost:8000');
        let observable = new Observable(observer => {
            this.socket.emit('getTemperature', whereUUID);

            this.socket.on('temperature', function (data) {
                //console.log('getTemperature() in chair.service; only data: ' + data);
                observer.next(data);
            });
            return () => {
                this.socket.disconnect();
            };
        });
        return observable;
    }

    getFirstXTemperatures(amount, whereUUID) {
        this.socket.disconnect()
        this.socket = io('http://localhost:8000');
        let observable = new Observable(observer => {
            this.socket.emit('getFirstXTemperatures', amount, whereUUID);

            this.socket.on('firstTemperature', function (data) {
                //console.log('getFirstXTemperatures() in chair.service; only data: ' + data);
                observer.next(data);
            });
            return () => {
                this.socket.disconnect();
            };
        });
        return observable;
    }


}