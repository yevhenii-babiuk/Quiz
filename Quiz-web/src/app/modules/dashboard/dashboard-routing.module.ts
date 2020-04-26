import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardComponent} from "./dashboard.component";

const pagesRoutes: Routes = [
  { path: 'profile/dashboard', component: DashboardComponent},
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
