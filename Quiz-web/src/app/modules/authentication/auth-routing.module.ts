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
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuard]
  }
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'pass-recovery', component: PassRecoveryComponent },
  { path: 'registration/:token', component: MailConfirmComponent },
  { path: 'pass-recovery/:token', component: PassRecoveryConfirmComponent },
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
