import { Component, Input, OnInit } from '@angular/core';
import {GameResults} from "../../core/models/gameResults";
import {Question} from "../../core/models/question";
import {QuestionType} from "../../core/models/questionType";
import {QuestionOptions} from "../../core/models/questionOptions";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  isWainting:boolean = false;

  public question: Question = {
    id: 1,
    type: QuestionType.SELECT_OPTION,
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

  public gameResults: GameResults = {
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

  constructor() { }

  ngOnInit(): void {
  }

}
