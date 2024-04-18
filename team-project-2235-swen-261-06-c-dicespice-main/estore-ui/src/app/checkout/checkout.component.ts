import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { ShoppingCart } from '../shopping-cart';
import { CartItem } from '../cartitem';
import { ShoppingCartService } from '../shopping-cart.service';
import { UserService } from '../user.service';
import { User } from '../user';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent implements OnInit{

  cart: ShoppingCart | null = {} as ShoppingCart;
  items: CartItem[] = [];
  creditCard: string = '';
  address: string = '';
  CCV: string = '';
  user: User = {} as User;
  tax: number = 0;
  subtotal: number = 0;
  total: number = 0;

  constructor(
    private shoppingCartService: ShoppingCartService,
    private router: Router,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.getCart();
    this.getUserInfo();
  }

  getCart(): void {
    this.shoppingCartService.getCart().subscribe(cart => {
      this.cart = cart;
      this.items = cart.items;
    });
  }

  checkout(): void {
    this.shoppingCartService.clearCart().subscribe(cart => {
      this.cart = cart;
      this.items = cart.items;
      this.router.navigateByUrl("/completed-order");
    });
  }

  getUserInfo(): void {
    this.userService.getUser().subscribe(user => {
      this.address = user.address;
      this.creditCard = user.creditCardNumber;
      this.CCV = user.creditCardCCV;
    });
  }

  setCreditCardInfo(): void { 
    this.userService.setCreditCardNumber(this.creditCard).subscribe();
    this.userService.setCreditCardCCV(this.CCV).subscribe();
  }

  setAddress(): void {
    this.userService.setAddress(this.address).subscribe();
  }

  calculateTax(): number {
    this.tax = 0;
    this.items.forEach(item => {
      this.tax += (item.dice.price * item.quantity * .08);
    });
    this.tax = Math.round(this.tax * 100) / 100;
    return this.tax;
  }

  calculateSubtotal(): number {
    this.subtotal = 0;
    this.items.forEach(item => {
      this.subtotal += (item.dice.price * item.quantity);
    });
    this.subtotal = Math.round(this.subtotal * 100) / 100;
    return parseFloat(this.subtotal.toFixed(2));
  }

  calculateTotal(): number {
    this.total = this.calculateSubtotal() + this.calculateTax();
    this.total = Math.round(this.total * 100) / 100;
    return parseFloat(this.total.toFixed(2));
  }

}
