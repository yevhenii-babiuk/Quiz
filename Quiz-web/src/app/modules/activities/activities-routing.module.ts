import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ViewActivitiesComponent} from "./view-activities/view-activities.component";
import {AuthGuardService as AuthGuard} from "../core/services/auth-guard.service";

const routes: Routes = [
  {path: 'activities', component: ViewActivitiesComponent}, //canActivate: [AuthGuard]
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ActivitiesRoutingModule {
}
