import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { PassResetComponent } from './pass-reset/pass-reset.component';
import { MailConfirmComponent } from './registration/mail-confirm/mail-confirm.component';
import { PassResetConfirmComponent } from './pass-reset/pass-reset-confirm/pass-reset-confirm.component';

const routes: Routes = [
  { path: 'api/v1/login', component: LoginComponent },
  { path: 'api/v1/registration', component: RegistrationComponent },
  { path: 'api/v1/pass-reset', component: PassResetComponent },
  { path: 'api/v1/registration/:token', component: MailConfirmComponent },
  { path: 'api/v1/pass-reset/:token', component: PassResetConfirmComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
