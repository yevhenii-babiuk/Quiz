import {NgModule} from "@angular/core";

import {Routes, RouterModule, CanActivate} from '@angular/router';

import {CreateAdminComponent} from "./pages/create-admin.component";
import {RoleGuardService as RoleGuard} from "../core/services/role-guard.service";

const createAdminRoutes: Routes = [
  {
    path: 'createAdmin', component: CreateAdminComponent,
    canActivate: [RoleGuard],
    data: {
      expectedRole: 'SUPER_ADMIN, ADMIN'
    }
  }

];

@NgModule({
  imports: [
    RouterModule.forChild(createAdminRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class CreateAdminRoutingModule {
}
