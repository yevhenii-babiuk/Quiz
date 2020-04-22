import {Tag} from "./tag";
import {Category} from "./category";
import {Question} from "./question";
import {ImageSnippet} from "./imageSnippet";
import {Imaged} from "./imaged";

export class Quiz implements Imaged {
  id: number;
  name: string;
  authorId: number;
  category: Category=new Category();
  publishedDate: string;
  questionNumber: number;
  maxScore: number;
  tags: Tag[]=[];
  questions: Question[]=[new Question(0)];
  imageId: number;
  selectedFile: ImageSnippet;
}
