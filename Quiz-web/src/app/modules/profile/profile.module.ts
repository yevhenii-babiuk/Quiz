import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {FormsModule} from "@angular/forms";

import {ViewProfile} from './view-profile/view-profile.component'
import {EditorComponent} from './editor/editor.component'
import {ProfileRoutingModule} from './profile-routing.module'

@NgModule({
  declarations: [
    ViewProfile,
    EditorComponent
  ],
  imports: [
    CommonModule,
    ProfileRoutingModule,
    RouterModule,
    FormsModule
  ]
})
export class ProfileModule { }
