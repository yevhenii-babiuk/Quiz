import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AuthenticationService} from './services/authentication.service';
import {AlertService} from './services/alert.service';
import {HomeComponent} from './pages/home/home.component';
import {ProfileComponent} from "./pages/profile/profile.component";
import {SharedModule} from "../shared/shared.module";

import {EditorComponent} from "./pages/editor/editor.component";
import {ProfileService} from "./services/profile.service";
import {PagesRoutingModule} from "./pages/pages-routing.module";

@NgModule({
  declarations: [
    HomeComponent,
    ProfileComponent,
    EditorComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    PagesRoutingModule
  ],
  providers: [
    AlertService,
    AuthenticationService,
    ProfileService
  ]
})
export class CoreModule { }
