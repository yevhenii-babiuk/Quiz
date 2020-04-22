import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {QuizzesComponent} from './quizzes/quizzes.component'
import {UpdateQuizComponent} from './update-quiz/update-quiz.component'
import {ViewQuizComponent} from './view-quiz/view-quiz.component'

const authenticationRoutes: Routes = [
  {path: 'quizzes', component: QuizzesComponent},
  {path: 'quiz/:quizId/edit', component: UpdateQuizComponent},
  {path: 'quiz/:quizId', component: ViewQuizComponent},
  {path: 'createQuiz', component: UpdateQuizComponent}
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
