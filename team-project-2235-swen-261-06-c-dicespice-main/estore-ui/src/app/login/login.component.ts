import { Component} from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../user';
import { UserService } from '../user.service';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent{
  
  user: User = {} as User;
  role: string = '';
  state: unknown;

  constructor(
    private userService: UserService,
    private router: Router,
    private adminService: AdminService
  ) { }

  getUser(): void {
    this.userService.getUser()
      .subscribe(user => this.user = user);
  }

  login(username: string, password: string): void {
    this.userService.login(username, password).subscribe(user => {
      this.user = user;
      this.adminService.changeIsAdmin(this.isAdmin());
      this.router.navigateByUrl("/dashboard");
    });
  }

  isAdmin(): boolean {
    return this.user.role === 'admin';
  }

  logout(): void {
    this.userService.logout().subscribe(() => {
      this.router.navigateByUrl("/login");
    });
  }
}
