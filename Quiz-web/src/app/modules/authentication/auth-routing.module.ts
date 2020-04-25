import { NgModule } from '@angular/core';
import { Routes, RouterModule, CanActivate } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { PassRecoveryComponent } from './pass-recovery/pass-recovery.component';
import { MailConfirmComponent } from './registration/mail-confirm/mail-confirm.component';
import { PassRecoveryConfirmComponent } from './pass-recovery/pass-recovery-confirm/pass-recovery-confirm.component';
import {ProfileComponent} from "../core/pages/profile/profile.component";
import {AuthGuardService as AuthGuard} from '../core/services/auth-guard.service';

const authenticationRoutes: Routes = [
  { path: 'api/v1/login', component: LoginComponent },
  { path: 'api/v1/registration', component: RegistrationComponent },
  { path: 'api/v1/pass-recovery', component: PassRecoveryComponent },
  { path: 'api/v1/registration/:token', component: MailConfirmComponent },
  { path: 'api/v1/pass-recovery/:token', component: PassRecoveryConfirmComponent },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(authenticationRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class AuthRoutingModule { }
