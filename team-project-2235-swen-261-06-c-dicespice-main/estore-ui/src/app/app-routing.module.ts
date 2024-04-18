import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { DiceComponent } from './dice/dice.component';
import { DieDetailComponent } from './die-detail/die-detail.component';
import { LoginComponent } from './login/login.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { CompletedOrderComponent } from './completed-order/completed-order.component';
import { AccountComponent } from './account/account.component';
import { WishlistComponent } from './wishlist/wishlist.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' }, 
  { path: 'dashboard', component: DashboardComponent },
  { path: 'detail/:id', component: DieDetailComponent },
  { path: 'dice', component: DiceComponent },
  { path: 'login', component: LoginComponent }, 
  { path: 'logout', component: LoginComponent }, 
  { path: 'shoppingcart', component: ShoppingCartComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'completed-order', component: CompletedOrderComponent },
  { path: 'account', component: AccountComponent},
  { path: 'wishlist', component: WishlistComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}