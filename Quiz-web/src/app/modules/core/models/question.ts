import {QuestionType} from './questionType';
import {ImageSnippet} from "./imageSnippet";
import {Imaged} from "./imaged";
import {QuestionOptions} from "./questionOptions";
import {Quiz} from "./quiz";
import {Image} from "./image";

export class Question implements Imaged {
  id: number;
  type: QuestionType = QuestionType.SELECT_OPTION;
  content: string;
  score: number;
  imageId: number;
  selectedFile: ImageSnippet;
  options: QuestionOptions[] = [];
  image: Image;

  constructor(id: number) {
    this.id = id;
  }
}
