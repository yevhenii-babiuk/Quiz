import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from './home/home.component';

const pagesRoutes: Routes = [
  { path: 'home', component: HomeComponent},
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
export class PagesRoutingModule { }
