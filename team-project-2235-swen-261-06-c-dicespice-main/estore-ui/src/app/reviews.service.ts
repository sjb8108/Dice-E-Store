import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of} from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class ReviewsService{

  private reviewsURL = 'http://localhost:8080/reviews'
  private starReviewsURL = 'http://localhost:8080//reviewStars'
  
  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) { }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  getReviews(id: number): Observable<string[]> {
    const url = `${this.reviewsURL}/${id}`;
    return this.http.get<string[]>(url)
      .pipe(
        tap(_ => this.log(`fetched reviews id=${id}`)),
        catchError(this.handleError<string[]>(`getReviews id=${id}`))
      );
  }


  addReview(id: number, review: string): Observable<any> {
    const url = `${this.reviewsURL}/add/${review}`;
    return this.http.post<ReviewsService>(url, id, this.httpOptions).pipe(
      tap((newReview: ReviewsService) => this.log(`added review to id=${id}`)),
      catchError(this.handleError<ReviewsService>('addReview'))
    );
  }

  getStarReviews(id: number): Observable<number> {
    const url = `${this.starReviewsURL}/${id}`;
    return this.http.get<number>(url)
    .pipe(
      tap(_ => this.log(`fetched star reviews id=${id}`)),
      catchError(this.handleError<number>(`getStarReviews id=${id}`))
    );
  }

  addStarReview(id: number, review: number): Observable<ReviewsService> {
    const url = `${this.starReviewsURL}/add/${review}`;
    return this.http.post<ReviewsService>(url, id, this.httpOptions).pipe(
      tap((newReview: ReviewsService) => this.log(`added star review to id=${id}`)),
      catchError(this.handleError<ReviewsService>('addStarReview'))
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

  private log(message: string) {
    this.messageService.add(`DieService: ${message}`);
  }
}
