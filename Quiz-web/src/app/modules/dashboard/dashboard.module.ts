import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from "./dashboard.component";
import {NgxEchartsModule} from "ngx-echarts";
import {ChartModule} from 'primeng/chart';
import {PlayedProgressComponent} from './played-progress/played-progress.component';
import {PlayedQuizScoreComponent} from './played-quiz-score/played-quiz-score.component';
import {FriendsPreferenceComponent} from './friends-preference/friends-preference.component';
import {CorrectAnswersPercentComponent} from './correct-answers-percent/correct-answers-percent.component';
import {TopPlayersComponent} from './top-players/top-players.component';
import { PlayedQuizzesAmountComponent } from './played-quizzes-amount/played-quizzes-amount.component';
import { QuizzesStatusComponent } from './quizzes-status/quizzes-status.component';

@NgModule({
  declarations: [
    DashboardComponent,
    PlayedProgressComponent,
    PlayedQuizScoreComponent,
    FriendsPreferenceComponent,
    CorrectAnswersPercentComponent,
    TopPlayersComponent,
    PlayedQuizzesAmountComponent,
    QuizzesStatusComponent
  ],
  imports: [
    NgxEchartsModule,
    ChartModule,
    CommonModule
  ]
})
export class DashboardModule {
}
