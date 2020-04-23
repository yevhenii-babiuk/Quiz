import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {ProfileComponent} from "./profile/profile.component";
import {EditorComponent} from "./editor/editor.component";

const pagesRoutes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'api/v1/profile', component: ProfileComponent },
  { path: 'api/v1/edit', component: EditorComponent },
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(pagesRoutes)
  ],
  exports: [
  RouterModule
]
})
export class PagesRoutingModule { }
