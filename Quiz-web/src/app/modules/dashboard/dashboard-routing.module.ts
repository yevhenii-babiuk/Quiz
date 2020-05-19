import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardComponent} from "./dashboard.component";
import {AuthGuardService as AuthGuard} from "../core/services/auth-guard.service";

const pagesRoutes: Routes = [
  { path: 'profile/dashboard', component: DashboardComponent, canActivate: [AuthGuard]},
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(pagesRoutes)
  ],
  exports: [
  RouterModule
]
})
export class DashboardRoutingModule { }
