import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {FormsModule} from "@angular/forms";

import {ProfileComponent} from './profile/profile.component'
import {EditorComponent} from './editor/editor.component'
import {ProfileRoutingModule} from './profile-routing.module'

@NgModule({
  declarations: [
    ProfileComponent,
    EditorComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ProfileRoutingModule
  ]
})
export class ProfileModule { }
