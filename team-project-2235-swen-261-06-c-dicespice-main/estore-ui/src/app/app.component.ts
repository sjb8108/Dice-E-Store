import { Component, OnInit } from '@angular/core';
import { AdminService } from './admin.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent implements OnInit{

  title = 'DiceSpice';
  isAdmin: boolean = false;

  constructor(
    private adminService: AdminService
  ) { }

  ngOnInit(): void {
    this.adminService.currentIsAdmin.subscribe(isAdmin => this.isAdmin = isAdmin);
  }
}