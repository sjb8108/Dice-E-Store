import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { InventoryService } from '../inventory-service';

import { Die } from '../die';
import { DieService } from '../die.service';
import { ShoppingCartService } from '../shopping-cart.service';
import { ShoppingCart } from '../shopping-cart';
import { WishlistService } from '../wishlist.service';
import { Wishlist } from '../wishlist';
import { AdminService } from '../admin.service';
import { ReviewsService } from '../reviews.service';

@Component({
  selector: 'app-die-detail',
  templateUrl: './die-detail.component.html',
  styleUrls: [ './die-detail.component.css' ]
})
export class DieDetailComponent implements OnInit {
  die: Die | undefined;
  cart: ShoppingCart = {} as ShoppingCart;
  list: Wishlist = {} as Wishlist;
  isAdmin: boolean = false;
  reviews: string[] = [];
  review: string = '';
  starReview: number = 0;
  averageRating: number = 0;
  // starReviews:
  // reviews:  
  // will be all reviews in list for both written and star -- unsure what type to use


  constructor(
    private route: ActivatedRoute,
    private dieService: DieService,
    private location: Location,
    private InventoryService: InventoryService, 
    private ShoppingCartService: ShoppingCartService,
    private adminService: AdminService,
    private reviewsService: ReviewsService,
    private WishlistService: WishlistService
  ) {}

  ngOnInit(): void {
    this.getDie();
    this.adminService.currentIsAdmin.subscribe((isAdmin: boolean) => this.isAdmin = isAdmin);
    this.getReviews();
  }

  getDie(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.dieService.getDie(id)
      .subscribe(die => {
        this.die = die;
        this.getReviews();
        this.getStarReviews();
        this.InventoryService.getQuantity(id)
          .subscribe(quantity => this.die!.quantity = quantity);
      });
  }

  goBack(): void {
    this.location.back();
  }
  
  addItem(): void {
    if (this.die) {
      this.ShoppingCartService.addItem(this.die.id)
        .subscribe(cart => {
          this.cart = cart, 
          this.goBack()
        });
    }
  }

  addWishlist(): void {
    if (this.die) {
      this.WishlistService.addItem(this.die.id)
        .subscribe(list => {
          this.list = list,
          this.goBack()
        });
    }
  }


  save(): void {
    if (this.die) {
      console.log(this.die);
      this.dieService.updateDie(this.die)
        .subscribe(die => {
          this.InventoryService.updateQuantity(this.die!.id, this.die!.quantity)
            .subscribe(() => {
              console.log(die.id)
              this.goBack();
            });
        });
    }
  }

  addReview(review: string): void {
    if (this.die) {
      this.reviewsService.addReview(this.die.id, review)
        .subscribe(() => {
          this.getReviews();
        });
    }
  }

  getReviews(): void {
    if (this.die) {
      this.reviewsService.getReviews(this.die.id)
        .subscribe(reviews => {
          this.reviews = reviews;
        });
    }
  }

  addStarReview(starReview: number): void {
    if (this.die) {
      console.log(starReview);
      this.reviewsService.addStarReview(this.die.id, starReview)
        .subscribe();
    }
  }

  getStarReviews(): void {
    if (this.die) {
      this.reviewsService.getStarReviews(this.die.id)
        .subscribe(averageRating => {
          this.averageRating = averageRating;
        });
    }
  }
}