import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../shopping-cart.service';
import { ShoppingCart } from '../shopping-cart';
import { CartItem } from '../cartitem';
import { Router } from '@angular/router';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrl: './shopping-cart.component.css'
})

export class ShoppingCartComponent implements OnInit{

  cart: ShoppingCart = {} as ShoppingCart;
  items: CartItem[] = [];

  constructor(
    private shoppingCartService: ShoppingCartService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getCart();
  }

  getCart(): void {
    
    this.shoppingCartService.getCart().subscribe(cart => {
      this.cart = cart;
      this.items = cart.items;
    });
  }

  add(id: number): void {
    this.shoppingCartService.addItem(id).subscribe(cart => {
      this.cart = cart;
      this.items = cart.items;
    });
  }
  
  delete(id: number): void {
    this.shoppingCartService.deleteItem(id).subscribe(cart => {
      this.cart = cart;
      this.items = cart.items;
    });
  }
  
  decrementItem(id: number): void {
    this.shoppingCartService.decrementCartItem(id).subscribe(cart => {
      this.cart = cart;
      this.items = cart.items;
    });
  }

  goToCheckout(): void {
    this.shoppingCartService.getCart().subscribe(_ => {
      this.router.navigateByUrl("/checkout");
    });
  }
}
