import {QuestionType} from './questionType';
import {ImageSnippet} from "./imageSnippet";
import {Imaged} from "./imaged";
import {QuestionOptions} from "./questionOptions";
import {Quiz} from "./quiz";

export class Question implements Imaged {
  id: number;
  type: QuestionType = QuestionType.SELECT_OPTION;
  content: string;
  score: number;
  imageId: number;
  selectedFile: ImageSnippet;
  options: QuestionOptions[] = [];

  constructor(id: number) {
    this.id = id;
  }
}
