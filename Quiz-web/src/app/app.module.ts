import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { FormsModule } from '@angular/forms';
import { HttpClientModule }    from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './registration/registration.component';
import { LoginComponent } from './login/login.component';
import { AlertComponent } from './alert/alert.component';
import { PassResetComponent } from './pass-reset/pass-reset.component';
import { MailConfirmComponent } from './registration/mail-confirm/mail-confirm.component';
import { PassResetConfirmComponent } from './pass-reset/pass-reset-confirm/pass-reset-confirm.component';

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    AlertComponent,
    PassResetComponent,
    MailConfirmComponent,
    PassResetConfirmComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
