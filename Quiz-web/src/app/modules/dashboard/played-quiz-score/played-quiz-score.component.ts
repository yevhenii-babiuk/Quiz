import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-played-quiz-score',
  templateUrl: './played-quiz-score.component.html',
  styleUrls: ['./played-quiz-score.component.css']
})
export class PlayedQuizScoreComponent implements OnInit {
  data = {
    labels: ['Qiuz_1', 'Qiuz_2', 'Qiuz_3', 'Qiuz_4', 'Qiuz_5', 'Qiuz_6', 'Qiuz_7'],
    datasets: [
      {
        label: 'Max score',
        backgroundColor: '#42A5F5',
        borderColor: '#1E88E5',
        data: [65, 59, 80, 81, 56, 55, 40]
      },
      {
        label: 'My score',
        backgroundColor: '#9CCC65',
        borderColor: '#7CB342',
        data: [28, 48, 40, 19, 86, 27, 90]
      }
    ]
  }

  constructor() { }

  ngOnInit(): void {
  }

}
