import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ViewProfileComponent} from "./view-profile/view-profile.component";
import {EditorComponent} from "./editor/editor.component";

const routes: Routes = [
  { path: 'api/v1/profile', component: ViewProfileComponent },
  { path: 'api/v1/edit', component: EditorComponent },
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
