import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProfileComponent} from "./profile/profile.component";
import {EditorComponent} from "./editor/editor.component";

const routes: Routes = [
  { path: 'api/v1/profile', component: ProfileComponent },
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
