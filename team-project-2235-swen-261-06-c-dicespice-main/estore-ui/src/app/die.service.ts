import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Die } from './die';
import { MessageService } from './message.service';


@Injectable({ providedIn: 'root' })
export class DieService {

  private diceURL = 'http://localhost:8080/inventory'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /** GET dice from the server */
  getDice(): Observable<Die[]> {
    return this.http.get<Die[]>(this.diceURL)
      .pipe(
        tap(_ => this.log('fetched dice')),
        catchError(this.handleError<Die[]>('getDice', []))
      );
  }

  /** GET die by id. Return `undefined` when id not found */
  getDieNo404<Data>(id: number): Observable<Die> {
    const url = `${this.diceURL}/?id=${id}`;
    return this.http.get<Die[]>(url)
      .pipe(
        map(dice => dice[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} die id=${id}`);
        }),
        catchError(this.handleError<Die>(`getDie id=${id}`))
      );
  }

  /** GET die by id. Will 404 if id not found */
  getDie(id: number): Observable<Die> {
    const url = `${this.diceURL}/${id}`;
    return this.http.get<Die>(url).pipe(
      tap(_ => this.log(`fetched die id=${id}`)),
      catchError(this.handleError<Die>(`getDie id=${id}`))
    );
  }

  /* GET dice whose name contains search term */
  searchDice(term: string): Observable<Die[]> {
    if (!term.trim()) {
      // if not search term, return empty die array.
      return of([]);
    }
    return this.http.get<Die[]>(`${this.diceURL}/?term=${term}`).pipe(
      tap(x => x.length ?
         this.log(`found dice matching "${term}"`) :
         this.log(`no dice matching "${term}"`)),
      catchError(this.handleError<Die[]>('searchDice', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new die to the server */
  addDie(die: Die): Observable<Die> {
    const url = `${this.diceURL}/add/${die.quantity}`;
    return this.http.post<Die>(url, die, this.httpOptions).pipe(
      tap((newDie: Die) => this.log(`added die w/ id=${newDie.id}`)),
      catchError(this.handleError<Die>('addDie'))
    );
  }

  /** DELETE: delete the die from the server */
  deleteDie(id: number): Observable<Die> {
    const url = `${this.diceURL}/${id}`;

    return this.http.delete<Die>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted die id=${id}`)),
      catchError(this.handleError<Die>('deleteDie'))
    );
  }

  /** PUT: update the die on the server */
  updateDie(die: Die): Observable<any> {
    return this.http.put(this.diceURL, die, this.httpOptions).pipe(
      tap(_ => this.log(`updated die id=${die.id}`)),
      catchError(this.handleError<any>('updateDie'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a DieService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`DieService: ${message}`);
  }
}