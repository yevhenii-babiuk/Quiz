import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from "./dashboard.component";
import {NgxEchartsModule} from "ngx-echarts";
import {ChartModule} from 'primeng/chart';
import {PlayedProgressComponent} from './played-progress/played-progress.component';
import {PlayedQuizScoreComponent} from './played-quiz-score/played-quiz-score.component';
import {FriendsPreferenceComponent} from './friends-preference/friends-preference.component';
import {CorrectAnswersPercentComponent} from './correct-answers-percent/correct-answers-percent.component';
import {BestQuizzesResultComponent} from './best-quizzes-result/best-quizzes-result.component';
import {PlayedQuizzesByPerriodComponent} from './played-quizzes-by-perriod/played-quizzes-by-perriod.component';
import {TopPlayersComponent} from './top-players/top-players.component';

@NgModule({
  declarations: [
    DashboardComponent,
    PlayedProgressComponent,
    PlayedQuizScoreComponent,
    FriendsPreferenceComponent,
    CorrectAnswersPercentComponent,
    BestQuizzesResultComponent,
    PlayedQuizzesByPerriodComponent,
    TopPlayersComponent
  ],
  imports: [
    NgxEchartsModule,
    ChartModule,
    CommonModule
  ]
})
export class DashboardModule {
}
