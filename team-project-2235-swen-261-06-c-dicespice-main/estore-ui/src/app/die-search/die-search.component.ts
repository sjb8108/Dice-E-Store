import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import {
   debounceTime, distinctUntilChanged, switchMap
 } from 'rxjs/operators';

import { Die } from '../die';
import { DieService } from '../die.service';

@Component({
  selector: 'app-die-search',
  templateUrl: './die-search.component.html',
  styleUrls: [ './die-search.component.css' ]
})
export class DieSearchComponent implements OnInit {
  dice$!: Observable<Die[]>;
  private searchTerms = new Subject<string>();

  constructor(private dieService: DieService) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term.toLowerCase());
  }

  ngOnInit(): void {
    this.dice$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.dieService.searchDice(term)),
    );
  }
}