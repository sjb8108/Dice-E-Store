import { Component, OnInit } from '@angular/core';
import { WishlistService } from '../wishlist.service';
import { ShoppingCartService } from '../shopping-cart.service';

import { Wishlist } from '../wishlist';
import { ListItem } from '../listitem';


@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrl: './wishlist.component.css'
})


export class WishlistComponent implements OnInit{
  list: Wishlist = {} as Wishlist;
  items: ListItem[] = [];

  constructor(
    private wishlistService: WishlistService,
    private shoppingCartService: ShoppingCartService

  ) {}


  ngOnInit(): void {
    this.getList();
  }


  getList(): void {
    this.wishlistService.getList().subscribe(list => {
      this.list = list;
      this.items = list.items;
    });
  }


  add(id: number): void {
    this.wishlistService.addItem(id).subscribe(list => {
      this.list = list;
      this.items = list.items;
    });
  }
  

  delete(id: number): void {
    this.wishlistService.deleteItem(id).subscribe(list => {
      console.log(list);

      this.list = list;
      this.items = list.items;
    });
  }


  addToCart(id: number): void {
    this.shoppingCartService.addItem(id).subscribe(list => {
      this.list = list;
      this.items = list.items;
      this.delete(id);
    });
  }


  clearWishlist(): void {
    this.wishlistService.clearList().subscribe(list => {
      this.list = list;
      this.items = [];
    });
  }

}
