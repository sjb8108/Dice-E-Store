import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, switchMap } from 'rxjs';
import { Die } from './die';

@Injectable({
  providedIn: 'root'
})
export class InventoryService {

  private apiUrl = 'http://localhost:8080/inventory';

  constructor(private http: HttpClient) { }

  getInventory(): Observable<Die[]> {
    return this.http.get<Die[]>(this.apiUrl);
  }

    getDie(id: number): Observable<Die> {
        const url = `${this.apiUrl + '/'}/${id}`;
        return this.http.get<Die>(url).pipe(
            switchMap(die => {
                return this.getQuantity(id).pipe(
                    map(quantity => {
                        die.quantity = quantity;
                        return die;
                    })
                );
            })
        );
    }

    getQuantity(id: number): Observable<number> {
      const url = `${this.apiUrl + '/quantity/'}/${id}`;
      return this.http.get<number>(url);
    }

    updateQuantity(id: number, quantity: number): Observable<number> {
      const url = `${this.apiUrl}/quantity/${id}/${quantity}`;
      return this.http.put<number>(url, {});
    }

}

    