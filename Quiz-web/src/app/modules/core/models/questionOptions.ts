import {Imaged} from "./imaged";
import {ImageSnippet} from "./imageSnippet";
import {Image} from "./image";


export class QuestionOptions implements Imaged{
  content: string = "";
  isCorrect: boolean = false;
  sequenceOrder: number = -1;

  imageId: number;
  selectedFile: ImageSnippet;
  image: Image;
  constructor(sequenceOrder: number) {
    this.sequenceOrder = sequenceOrder;
  };

}
