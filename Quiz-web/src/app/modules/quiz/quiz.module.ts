import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import {QuizzesComponent} from './quizzesList/quizzes.component';
import {QuizRoutingModule} from './quiz-routing.module';

@NgModule({
  declarations: [
    QuizzesComponent
  ],
  imports: [
    CommonModule,
    QuizRoutingModule,
    RouterModule
  ]
})
export class QuizModule { }
