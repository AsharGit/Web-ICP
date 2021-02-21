import {Component, NgModule, OnInit} from '@angular/core';
import { Subscription, interval } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  subscription: Subscription;
  targetDay = new Date('Jan 01 2022 00:00:00');
  timeDifference;
  seconds;
  minutes;
  hours;
  days;


  getTimeDifference () {
    this.timeDifference = this.targetDay.getTime() - new  Date().getTime();
    this.allocateTimeUnits(this.timeDifference);
  }

  allocateTimeUnits (timeDifference) {
    this.seconds = Math.floor((timeDifference) / 1000 % 60);
    this.minutes = Math.floor((timeDifference) / (1000 * 60) % 60);
    this.hours = Math.floor((timeDifference) / (1000 * 60 * 60) % 24);
    this.days = Math.floor((timeDifference) / (1000 * 60 * 60 * 24));
  }

  ngOnInit() {
    this.subscription = interval(1000)
      .subscribe(x => { this.getTimeDifference(); });
  }
}
