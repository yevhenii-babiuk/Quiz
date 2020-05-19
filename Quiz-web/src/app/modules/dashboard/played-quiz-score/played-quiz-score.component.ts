import {Component, OnInit} from '@angular/core';
import {ComparedScores} from "../../core/models/comparedScores";
import {DashboardService} from "../../core/services/dashboard.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-played-quiz-score',
  templateUrl: './played-quiz-score.component.html',
  styleUrls: ['./played-quiz-score.component.css']
})
export class PlayedQuizScoreComponent implements OnInit {

  score: ComparedScores[] = [];
  data: any;

  getScore() {
    this.dashboardService.getCompareScore().subscribe(statistics => {
        this.score = statistics;
        this.plotGraph();
      },
      err => {
        console.log(err);
      });
  }

  plotGraph() {

    let label:Array<string> = [];
    let score:Array<number> = [];
    let record:Array<number> = [];

    this.score.forEach(element=>label.push(element.name))
    this.score.forEach(element=>score.push(element.score))
    this.score.forEach(element=>record.push(element.record))

    this.data = {
      labels: label,
      datasets: [
        {
          label: 'Max score',
          backgroundColor: '#42A5F5',
          borderColor: '#1E88E5',
          data: record
        },
        {
          label: 'My score',
          backgroundColor: '#9CCC65',
          borderColor: '#7CB342',
          data: score
        }
      ]
    }
  }

  constructor(private dashboardService: DashboardService,
              public translate: TranslateService) {
  }

  ngOnInit(): void {
    this.getScore();
  }

}
