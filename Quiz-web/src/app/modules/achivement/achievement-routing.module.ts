import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthGuardService as AuthGuard} from "../core/services/auth-guard.service";
import {RoleGuardService as RoleGuard} from "../core/services/role-guard.service";
import {CreateAchievementComponent} from "./create-achievement/create-achievement.component";

const pagesRoutes: Routes = [
  { path: 'achievements/create', component: CreateAchievementComponent, canActivate: [RoleGuard], data: {expectedRole: 'ADMIN, MODERATOR'}},
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
export class AchievementRoutingModule { }
