import { Component, OnInit } from '@angular/core';

import { Die } from '../die';
import { DieService } from '../die.service';

declare var parseInt: (input: string, radix?: number) => number;

@Component({
  selector: 'app-dice',
  templateUrl: './dice.component.html',
  styleUrls: ['./dice.component.css']
})
export class DiceComponent implements OnInit {
  dice: Die[] = [];

  constructor(private dieService: DieService) { }

  ngOnInit(): void {
    this.getDice();
  }

  getDice(): void {
    this.dieService.getDice()
    .subscribe(dice => this.dice = dice);
  }

  add(color: string, sidesStr: string, priceStr: string, quantityStr: string): void {
    color = color.trim().toLowerCase();
    const sides = Number(sidesStr)
    const price = Number(priceStr)
    const quantity = Number(quantityStr)
    if (!color || !sides) { return; }
    this.dieService.addDie({ color, sides, price, quantity } as Die)
      .subscribe(die => {
        this.dice.push(die);
      });
  }

  delete(die: Die): void {
    this.dice = this.dice.filter(d => d !== die);
    this.dieService.deleteDie(die.id).subscribe();
  }

}