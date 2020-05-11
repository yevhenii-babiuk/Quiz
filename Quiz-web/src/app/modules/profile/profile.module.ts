import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {FormsModule} from "@angular/forms";

import {ViewProfile} from './view-profile/view-profile.component'
import {EditorComponent} from './editor/editor.component'
import {ProfileRoutingModule} from './profile-routing.module';
import { UserListComponent } from './user-list/user-list.component'
import {AchievementModule} from "../achivement/achievement.module";

@NgModule({
  declarations: [
    ViewProfile,
    EditorComponent,
    UserListComponent
  ],
  imports: [
    CommonModule,
    ProfileRoutingModule,
    RouterModule,
    FormsModule,
    AchievementModule
  ]
})
export class ProfileModule { }
