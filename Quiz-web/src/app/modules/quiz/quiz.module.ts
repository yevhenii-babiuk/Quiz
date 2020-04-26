import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {FormsModule} from "@angular/forms";

import {QuizzesComponent} from './quizzes/quizzes.component';
import {UpdateQuizComponent} from './update-quiz/update-quiz.component';
import {ViewQuizComponent} from './view-quiz/view-quiz.component';
import {QuizRoutingModule} from './quiz-routing.module';

@NgModule({
  declarations: [
    QuizzesComponent,
    UpdateQuizComponent,
    ViewQuizComponent
  ],
  imports: [
    CommonModule,
    QuizRoutingModule,
    RouterModule,
    FormsModule
  ]
})
export class QuizModule { }
