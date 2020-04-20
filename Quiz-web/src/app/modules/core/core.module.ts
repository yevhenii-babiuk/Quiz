import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AuthenticationService} from './services/authentication.service';
import {AlertService} from './services/alert.service';
import {HomeComponent} from './pages/home/home.component';

@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    CommonModule
  ],
  providers: [
    AlertService,
    AuthenticationService
  ]
})
export class CoreModule { }
