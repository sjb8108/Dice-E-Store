import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatCardModule } from '@angular/material/card';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DieDetailComponent } from './die-detail/die-detail.component';
import { DiceComponent } from './dice/dice.component';
import { DieSearchComponent } from './die-search/die-search.component';
import { MessagesComponent } from './messages/messages.component';
import { LoginComponent } from './login/login.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { AccountComponent } from './account/account.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { CompletedOrderComponent } from './completed-order/completed-order.component';
import { WishlistComponent } from './wishlist/wishlist.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    MatCardModule
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    DiceComponent,
    DieDetailComponent,
    MessagesComponent,
    DieSearchComponent, 
    LoginComponent,
    ShoppingCartComponent,
    AccountComponent,
    CheckoutComponent,
    CompletedOrderComponent,
    WishlistComponent
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }