import {NgModule} from "@angular/core";

import { Routes, RouterModule, CanActivate } from '@angular/router';

import {CreateAdminComponent} from "./pages/create-admin.component";
import {TranslateModule} from "@ngx-translate/core";

const createAdminRoutes: Routes = [
  { path: 'createAdmin', component: CreateAdminComponent },

];

@NgModule({
  imports: [
    RouterModule.forChild(createAdminRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class CreateAdminRoutingModule { }
