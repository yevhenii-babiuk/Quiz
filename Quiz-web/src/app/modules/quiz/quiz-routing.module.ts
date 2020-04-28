import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {QuizzesComponent} from './quizzes/quizzes.component'
import {UpdateQuizComponent} from './update-quiz/update-quiz.component'
import {ViewQuizComponent} from './view-quiz/view-quiz.component'
import {ViewProfile} from "../profile/view-profile/view-profile.component";
import {AuthGuardService as AuthGuard} from "../core/services/auth-guard.service";

const authenticationRoutes: Routes = [
  {path: 'quizzes', component: QuizzesComponent},
  {path: 'quiz/:quizId/edit', component: UpdateQuizComponent, canActivate: [AuthGuard]},
  {path: 'quiz/:quizId', component: ViewQuizComponent},
  {path: 'createQuiz', component: UpdateQuizComponent, canActivate: [AuthGuard]}
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
