import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {QuizzesComponent} from './quizzesList/quizzes.component'

const authenticationRoutes: Routes = [
  {path: 'quizzes', component: QuizzesComponent}
];

@NgModule({
  imports: [
    RouterModule.forChild(authenticationRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class QuizRoutingModule {
}
