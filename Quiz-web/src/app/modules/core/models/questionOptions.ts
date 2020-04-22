import {Imaged} from "./imaged";
import {ImageSnippet} from "./imageSnippet";


export class QuestionOptions implements Imaged{
  content: string = "";
  isCorrect: boolean = false;
  sequenceOrder: number = -1;

  imageId: number;
  selectedFile: ImageSnippet;

  constructor(sequenceOrder: number) {
    this.sequenceOrder = sequenceOrder;
  };

}
