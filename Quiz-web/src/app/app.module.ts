import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { FormsModule } from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './registration/registration.component';
import { LoginComponent } from './login/login.component';
import { AlertComponent } from './alert/alert.component';
import { PassRecoveryComponent } from './pass-recovery/pass-recovery.component';
import { MailConfirmComponent } from './registration/mail-confirm/mail-confirm.component';
import { PassRecoveryConfirmComponent } from './pass-recovery/pass-recovery-confirm/pass-recovery-confirm.component';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { SidenavComponent } from './sidenav/sidenav.component';
import {BasicAuthHtppInterceptorService} from "./service/basic-auth-htpp-interceptor.service";

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    AlertComponent,
    PassRecoveryComponent,
    MailConfirmComponent,
    PassRecoveryConfirmComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    SidenavComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS, useClass: BasicAuthHtppInterceptorService, multi: true
    }
],
  bootstrap: [AppComponent]
})
export class AppModule { }
