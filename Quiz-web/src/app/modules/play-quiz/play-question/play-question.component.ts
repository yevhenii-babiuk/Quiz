import {Component, OnInit} from '@angular/core';
import {Question} from '../../core/models/question';
import {QuestionType} from "../../core/models/questionType";
import {QuestionOptions} from "../../core/models/questionOptions";
import {Image} from "../../core/models/image";

@Component({
  selector: 'app-play-question',
  templateUrl: './play-question.component.html',
  styleUrls: ['./play-question.component.css']
})
export class PlayQuestionComponent implements OnInit {

  question: Question;

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
