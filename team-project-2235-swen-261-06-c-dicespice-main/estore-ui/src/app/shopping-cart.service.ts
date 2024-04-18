import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of, BehaviorSubject } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { ShoppingCart } from './shopping-cart';
import { MessageService } from './message.service';

@Injectable({ providedIn: 'root' })
export class ShoppingCartService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) { }

  private cartURL = 'http://localhost:8080/shoppingcart'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  /** GET cart from the server */
  getCart(): Observable<ShoppingCart> {
    return this.http.get<ShoppingCart>(this.cartURL)
      .pipe(
        tap(_ => this.log('fetched cart')),
        catchError(this.handleError<ShoppingCart>('getCart'))
      );
  }

  /** POST: add a new die to the cart */
  addItem(id: number): Observable<ShoppingCart> {
    const url = `${this.cartURL}/${id}`;
    return this.http.post<ShoppingCart>(url, id, this.httpOptions).pipe(
      tap((newItem: ShoppingCart) => this.log(`added item w/ id=${id}`)),
      catchError(this.handleError<ShoppingCart>('addItem'))
    );
  }

  /** DELETE: delete the die from the cart */
  deleteItem(id: number): Observable<ShoppingCart> {
    const url = `${this.cartURL}/${id}`;

    return this.http.delete<ShoppingCart>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted item id=${id}`)),
      catchError(this.handleError<ShoppingCart>('deleteItem'))
    );
  }

  /** PUT: increment the quantity of the cart item */
  decrementCartItem(id: number): Observable<ShoppingCart> {
    const url = `${this.cartURL}/decrement/${id}`;

    return this.http.put<ShoppingCart>(url, null, this.httpOptions).pipe(
      tap(_ => this.log(`decremented item id=${id}`)),
      catchError(this.handleError<ShoppingCart>('decrementCartItem'))
    );
  }

  /** PUT: clear all dice from the cart */
  clearCart(): Observable<ShoppingCart> {
    const url = `${this.cartURL}/clear`;

    return this.http.put<ShoppingCart>(url, null, this.httpOptions).pipe(
      tap(_ => this.log(`cleared cart`)),
      catchError(this.handleError<ShoppingCart>('clearCart'))
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
  
    /** Log a ShoppingCartService message with the MessageService */
    private log(message: string) {
      this.messageService.add(`ShoppingCartService: ${message}`);
    }
}
