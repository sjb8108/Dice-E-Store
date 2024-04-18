import { Component, OnInit } from '@angular/core';
import { Die } from '../die';
import { DieService } from '../die.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  dice: Die[] = [];

  constructor(private dieService: DieService) { }

  ngOnInit(): void {
    this.getDice();
  }

  getDice(): void {
    this.dieService.getDice()
      .subscribe(dice => this.dice = dice);
  }
}