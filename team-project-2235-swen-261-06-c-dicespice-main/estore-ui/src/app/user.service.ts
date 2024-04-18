import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { User } from './user';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})

export class UserService {
  role: string = '';

  private loginURL = 'http://localhost:8080/user/login'
  private userURL = 'http://localhost:8080/user'
  private roleURL = 'http://localhost:8080/user/role'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  getUser(): Observable<User> {
    const url = `${this.userURL}/currentUser`
    return this.http.get<User>(url)
      .pipe(
        tap(_ => this.log('fetched user')),
        catchError(this.handleError<User>('getUser'))
    );
  }

  getRole(): Observable<string> {
    return this.http.get<string>(this.roleURL)
      .pipe(
        tap(_ => this.log('fetched role')),
        catchError(this.handleError<string>('getRole'))
    );
  }

  getAddress(): Observable<string> {
    const url = `${this.userURL}/getAddress`
    return this.http.get<string>(url)
      .pipe(
        tap(_ => this.log('fetched address')),
        catchError(this.handleError<string>('getAddress'))
    );
  }

  setAddress(address: string): Observable<any> {
    const url = `${this.userURL}/setAddress`
    return this.http.put(url, address, {responseType: 'text'}).pipe(
      tap(_ => this.log(`updated address=${address}`)),
      catchError(this.handleError<any>('setAddress'))
    );
  }

  deleteAddress(): Observable<any> {
    const url = `${this.userURL}/deleteAddress`
    return this.http.delete(url, this.httpOptions).pipe(
      tap(_ => this.log('deleted address')),
      catchError(this.handleError<any>('deleteAddress'))
    );
  }

  getCreditCardNumber(): Observable<string> {
    const url = `${this.userURL}/getCreditCardNumber`
    return this.http.get<string>(url)
      .pipe(
        tap(_ => this.log('fetched credit card number')),
        catchError(this.handleError<string>('getCreditCardNumber'))
    );
  }

  setCreditCardNumber(creditCardNumber: string): Observable<any> {
    const url = `${this.userURL}/setCreditCardNumber`
    return this.http.put(url, creditCardNumber, {responseType: 'text'}).pipe(
      tap(_ => this.log(`updated credit card number=${creditCardNumber}`)),
      catchError(this.handleError<any>('setCreditCardNumber'))
    );
  }

  deleteCreditCard(): Observable<any> {
    const url = `${this.userURL}/deleteCreditCard`
    return this.http.delete(url, {responseType: 'text'}).pipe(
      tap(_ => this.log('deleted credit card number')),
      catchError(this.handleError<any>('deleteCreditCard'))
    );
  }

  getCreditCardCCV(): Observable<string> {
    const url = `${this.userURL}/getCreditCardCCV`
    return this.http.get<string>(url)
      .pipe(
        tap(_ => this.log('fetched credit card CCV')),
        catchError(this.handleError<string>('getCreditCardCCV'))
    );
  }

  setCreditCardCCV(creditCardCCV: string): Observable<any> {
    const url = `${this.userURL}/setCreditCardCCV`
    return this.http.put(url, creditCardCCV, {responseType: 'text'}).pipe(
      tap(_ => this.log(`updated credit card CCV=${creditCardCCV}`)),
      catchError(this.handleError<any>('setCreditCardCCV'))
    );
  }

  login(username: string, password: string): Observable<User> {
    const body = { username: username, password: password };
    return this.http.post<User>(this.loginURL, body, this.httpOptions)
      .pipe(
        tap((user: User) => {
          if (user) {
            this.log('login successful');
          } else {
            this.log('login failed');
          }
        }),
        catchError(error => {
          let errorMsg: string;
          if (error.status === 400) {
            errorMsg = 'Incorrect username or password. ' + error.error;
          } else {
            errorMsg = 'Login failed with error code: ${error.status} message: ${error.error}';
          }
          this.log(errorMsg);
          return throwError(errorMsg);
        })
      );
  }

  isAdmin(): Observable<boolean> {
    return this.http.get<boolean>(this.roleURL, { responseType: 'text' as 'json' })
      .pipe(
        tap(_ => this.log('fetched role')),
        catchError(this.handleError<boolean>('isAdmin'))
    );
  }

  logout(): Observable<unknown> {
    const url = 'http://localhost:8080/user/logout'
    return this.http.post<unknown>(url, this.httpOptions)
      .pipe(
        tap(_ => this.log('logged out')),
        catchError(this.handleError<unknown>('logout'))
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
      this.log(`${operation} failed: ${error.message + " ERROR:" + error.error}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a UserService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`UserService: ${message}`);
  }
}
