import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {FormsModule} from "@angular/forms";

import {ViewProfile} from './view-profile/view-profile.component'
import {EditorComponent} from './editor/editor.component'
import {ProfileRoutingModule} from './profile-routing.module';
import { AchivementsListComponent } from './achivements-list/achivements-list.component'
import {CardsModule, CarouselModule} from "angular-bootstrap-md";

@NgModule({
  declarations: [
    ViewProfile,
    EditorComponent,
    AchivementsListComponent
  ],
  imports: [
    CommonModule,
    ProfileRoutingModule,
    RouterModule,
    FormsModule,
    CarouselModule,
    CardsModule
  ]
})
export class ProfileModule { }
