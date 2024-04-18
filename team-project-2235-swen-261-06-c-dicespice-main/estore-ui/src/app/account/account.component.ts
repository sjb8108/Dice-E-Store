import { Component, OnInit } from '@angular/core';

import { Location } from '@angular/common';
import { UserService } from '../user.service';
import { User } from '../user';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})

export class AccountComponent implements OnInit{

  username: string = '';
  creditCard: string = '';
  address: string = '';
  CCV: string = '';
  user: User = {} as User;

  constructor(
    private location: Location,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.getUserInfo();
  }

  getUserInfo(): void {
    this.userService.getUser().subscribe(user => {
      this.username = user.username;
      this.address = user.address;
      this.creditCard = user.creditCardNumber;
      this.CCV = user.creditCardCCV;
    });
  }

  setCreditCardInfo(): void { 
    this.userService.setCreditCardNumber(this.creditCard).subscribe();
    console.log(this.creditCard);
    this.userService.setCreditCardCCV(this.CCV).subscribe();
    console.log(this.CCV);
  }

  clearCreditCardInfo(): void {
    this.userService.deleteCreditCard().subscribe(_ => {
      this.creditCard = '',
      this.CCV = ''
    });
  }

  setAddress(): void {
    this.userService.setAddress(this.address).subscribe();
    console.log(this.address);
  }

  clearAddress(): void {
    this.userService.deleteAddress().subscribe(_ => {
      this.address = ''
    });
  }

  goBack(): void {
    this.location.back();
  }
}
