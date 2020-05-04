import {Component, OnInit} from '@angular/core';
import {Question} from '../../core/models/question';
import {QuestionType} from "../../core/models/questionType";
import {QuestionOptions} from "../../core/models/questionOptions";
import {Image} from "../../core/models/image";
import {GameResults, SingleResult} from "../../core/models/gameResults";
import {Sort} from "@angular/material/sort";

@Component({
  selector: 'app-play-question',
  templateUrl: './play-question.component.html',
  styleUrls: ['./play-question.component.css']
})
export class PlayQuestionComponent implements OnInit {

  question: Question;
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

  sortedData: SingleResult[]=this.gameResults.singleResult;


  sortData(sort: Sort) {
    const data = this.gameResults.singleResult.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }

    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'login': return compare(a.login, b.login, isAsc);
        case 'score': return compare(a.score, b.score, isAsc);
        /*case 'fat': return compare(a.fat, b.fat, isAsc);
        case 'carbs': return compare(a.carbs, b.carbs, isAsc);
        case 'protein': return compare(a.protein, b.protein, isAsc);*/
        default: return 0;
      }
    });
  }


  // question: Question = {
  //   id: 1,
  //   type: QuestionType.SELECT_SEQUENCE,
  //   content: "Ambitioni dedisse scripsisse iudicaretur. Cras mattis iudicium purus sit amet fermentum.",
  //   score: 10,
  //   imageId: -1,
  //   options: [
  //     {
  //       content: "falewtetse fhsf",
  //       isCorrect: false,
  //       imageId: -1
  //     } as QuestionOptions,
  //     {
  //       content: "trudsfdsge fsjet rye",
  //       isCorrect: true,
  //       imageId: -1
  //     } as QuestionOptions,
  //     {
  //       content: "falewftetse",
  //       isCorrect: false,
  //       imageId: -1
  //     } as QuestionOptions,
  //     {
  //       content: "trudswgefdsge fsg",
  //       isCorrect: false,
  //       imageId: -1
  //     } as QuestionOptions
  //   ],
  //   image: null
  // };

  constructor() {
  }

  ngOnInit(): void {
  }

  nextQuestion() {
  }
}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
