import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {QuizzesComponent} from "./quizzes/quizzes.component";

const routes: Routes = [
  { path:  '', pathMatch:  'full', redirectTo:  'home'},
  {path: 'api/v1/quizzes', component: QuizzesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
