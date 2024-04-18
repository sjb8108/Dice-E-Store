import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private isAdminSource = new BehaviorSubject(false);
  currentIsAdmin = this.isAdminSource.asObservable();

  constructor() { }

  changeIsAdmin(value: boolean) {
    this.isAdminSource.next(value);
  }
}
