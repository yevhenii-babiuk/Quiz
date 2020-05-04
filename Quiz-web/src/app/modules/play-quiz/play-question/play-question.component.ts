import {Component, OnInit} from '@angular/core';
import {Question} from '../../core/models/question';
import {QuestionType} from "../../core/models/questionType";
import {QuestionOptions} from "../../core/models/questionOptions";
import {GameResults, SingleResult} from "../../core/models/gameResults";
import {Sort} from "@angular/material/sort";

import {timer} from 'rxjs';
import {Answer} from "../../core/models/answer";

@Component({
  selector: 'app-play-question',
  templateUrl: './play-question.component.html',
  styleUrls: ['./play-question.component.css']
})
export class PlayQuestionComponent implements OnInit {
  public isSend: boolean = false;

  public subscribeTimer: number;
  public timeLeft: number = 10;

  // question: Question;
  //gameResults: GameResults;
  gameResults: GameResults = {
    singleResult: [
      {
        login: "some login",
        id: 2,
        score: 20
      }, {
        login: "another login",
        id: 5,
        score: 35
      }, {
        login: "my login",
        id: 10,
        score: 30
      }
    ]
  }

  question: Question = {
    id: 1,
    type: QuestionType.ENTER_ANSWER,
    content: "Ambitioni dedisse scripsisse iudicaretur. Cras mattis iudicium purus sit amet fermentum.",
    score: 10,
    imageId: -1,
    options: [
      {
        id: 12,
        content: "falewtetse fhsf",
        isCorrect: false,
        imageId: -1
      } as QuestionOptions,
      {
        id: 14,
        content: "trudsfdsge fsjet rye",
        isCorrect: true,
        imageId: -1
      } as QuestionOptions,
      {
        id: 16,
        content: "falewftetse",
        isCorrect: false,
        imageId: -1
      } as QuestionOptions,
      {
        id: 11,
        content: "trudswgefdsge fsg",
        isCorrect: false,
        imageId: -1
      } as QuestionOptions
    ],
    image: null
  };

  answer: Answer = {
    user_id: 2,
    question_id: this.question.id,
    result: '',
    time: this.timeLeft
  }

  map = new Map();
  answerText = '';

  constructor() {
    timer(1000, 2000).subscribe(val => {
      if (this.subscribeTimer != 0) {
        this.subscribeTimer = this.timeLeft - val;
      } else {
        if (!this.isSend) {
          this.nextQuestion();
          this.isSend = true;
        }
      }
    });
  }

  ngOnInit(): void {
  }

  nextQuestion() {
    if (this.question.type == 'SELECT_SEQUENCE') {
      for (let key of this.map.keys()) {
        this.answer.result += key.toString() + '-' + this.map.get(key).toString() + ' ';
      }

      this.answer.result.trim();
    }

    if (this.question.type == 'ENTER_ANSWER') {
      this.answer.result = this.answerText;
    }


    this.answer.result = this.question.type.toString() + ':' + this.answer.result;
    this.answer.time = this.subscribeTimer;
    this.isSend = true;

    console.log(this.answer);
  }

  setOption(value: string) {
    this.answer.result = value;
  }

  setSequence(opId: number, seqId: number) {
    if (this.map.has(opId)) {
      this.map.delete(opId);
    }
    this.map.set(opId, seqId);
  }

  sortedData: SingleResult[] = this.gameResults.singleResult;

  sortData(sort: Sort) {
    const data = this.gameResults.singleResult.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }

    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'login':
          return compare(a.login, b.login, isAsc);
        case 'score':
          return compare(a.score, b.score, isAsc);
        /*case 'fat': return compare(a.fat, b.fat, isAsc);
        case 'carbs': return compare(a.carbs, b.carbs, isAsc);
        case 'protein': return compare(a.protein, b.protein, isAsc);*/
        default:
          return 0;
      }
    });
  }
}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
