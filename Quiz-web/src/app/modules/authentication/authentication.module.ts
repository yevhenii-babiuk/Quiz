import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import {LoginComponent} from './login/login.component';
import {PassRecoveryComponent} from './pass-recovery/pass-recovery.component';
import {MailConfirmComponent} from './registration/mail-confirm/mail-confirm.component';
import {PassRecoveryConfirmComponent} from './pass-recovery/pass-recovery-confirm/pass-recovery-confirm.component';
import {RegistrationComponent} from './registration/registration.component';
import {AuthRoutingModule} from './auth-routing.module';
import {SharedModule} from "../shared/shared.module";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {BasicAuthHtppInterceptorService} from "../core/services/auth-http-interceptor.service";

@NgModule({
  declarations: [
    LoginComponent,
    PassRecoveryComponent,
    MailConfirmComponent,
    PassRecoveryConfirmComponent,
    RegistrationComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    RouterModule,
    SharedModule
  ]
})
export class AuthenticationModule { }
