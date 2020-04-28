import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AlertComponent} from './alert/alert.component';

import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {SidenavComponent} from './sidenav/sidenav.component';
import {RouterModule} from "@angular/router";


@NgModule({
  declarations: [
    AlertComponent,
    HeaderComponent,
    FooterComponent,
    SidenavComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    AlertComponent,
    HeaderComponent,
    FooterComponent,
    SidenavComponent
  ]
})
export class SharedModule { }
