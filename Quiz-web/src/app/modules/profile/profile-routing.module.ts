import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ViewProfile} from "./view-profile/view-profile.component";
import {EditorComponent} from "./editor/editor.component";

const routes: Routes = [
  { path: 'profile', component: ViewProfile },
  { path: 'edit', component: EditorComponent },
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
  RouterModule
]
})
export class ProfileRoutingModule { }
