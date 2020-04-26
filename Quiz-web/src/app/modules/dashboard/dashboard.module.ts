import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DashboardComponent} from "./dashboard.component";
import {NgxEchartsModule} from "ngx-echarts";
import {ChartModule} from 'primeng/chart';
import { PlayedProgressComponent } from './played-progress/played-progress.component';
import { PlayedQuizScoreComponent } from './played-quiz-score/played-quiz-score.component';

@NgModule({
  declarations: [
    DashboardComponent,
    PlayedProgressComponent,
    PlayedQuizScoreComponent
  ],
  imports: [
    NgxEchartsModule,
    ChartModule,
    CommonModule
  ]
})
export class DashboardModule { }
