import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ViewProfile} from "./view-profile/view-profile.component";
import {EditorComponent} from "./editor/editor.component";
import {AuthGuardService as AuthGuard} from "../core/services/auth-guard.service";

const routes: Routes = [
  {path: 'profile', component: ViewProfile, canActivate: [AuthGuard]},
  {path: 'edit', component: EditorComponent, canActivate: [AuthGuard]}
]

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class ProfileRoutingModule {
}
