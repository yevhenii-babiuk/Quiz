import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import {QuizzesComponent} from './quizzes/quizzes.component';
import {UpdateQuizComponent} from './update-quiz/update-quiz.component';
import {QuizRoutingModule} from './quiz-routing.module';
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    QuizzesComponent,
    UpdateQuizComponent
  ],
  imports: [
    CommonModule,
    QuizRoutingModule,
    RouterModule,
    FormsModule
  ]
})
export class QuizModule { }
