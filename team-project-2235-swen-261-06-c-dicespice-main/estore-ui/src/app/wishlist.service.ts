import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of, BehaviorSubject } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { Wishlist } from './wishlist';
import { MessageService } from './message.service';


@Injectable({ providedIn: 'root' })
export class WishlistService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) { }

  private listURL = 'http://localhost:8080/wishlist'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  /** GET: wishlist from the server */
  getList(): Observable<Wishlist> {
    return this.http.get<Wishlist>(this.listURL)
      .pipe(
        tap(_ => this.log('fetched list')),
        catchError(this.handleError<Wishlist>('getList'))
      );
  }

  /** POST: add a new die to the wishlist */
  addItem(id: number): Observable<Wishlist> {
    const url = `${this.listURL}/${id}`;
    return this.http.post<Wishlist>(url, id, this.httpOptions).pipe(
      tap((newItem: Wishlist) => this.log(`added item w/ id=${id}`)),
      catchError(this.handleError<Wishlist>('addItem'))
    );
  }

  /** DELETE: delete the die from the wishlist */
  deleteItem(id: number): Observable<Wishlist> {
    const url = `${this.listURL}/${id}`;

    return this.http.delete<Wishlist>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted item id=${id}`)),
      catchError(this.handleError<Wishlist>('deleteItem'))
    );
  }

  /** DELETE: clear the entire wishlist */
  clearList(): Observable<Wishlist> {
    return this.http.delete<Wishlist>(this.listURL, this.httpOptions).pipe(
      tap(_ => this.log('emptied list')),
      catchError(this.handleError<Wishlist>('emptyList'))
    );
  }

  /**POST: moves from wishlist to shopping cart */
  addToCart(id: number): Observable<Wishlist> {
    const url = `${this.listURL}/wishlist`;
    return this.http.post<Wishlist>(url, id, this.httpOptions).pipe(
      tap((newItem: Wishlist) => this.log(`added item w/ id=${id} to your cart`)),
      catchError(this.handleError<Wishlist>('addToCart'))
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
  
    /** Log a WishlistService message with the MessageService */
    private log(message: string) {
      this.messageService.add(`WishlistService: ${message}`);
    }
}
