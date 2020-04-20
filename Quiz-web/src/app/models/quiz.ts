import {Tag} from "./tag";
import {Category} from "./category";

export class Quiz {
  id: number;
  name: string;
  authorId: number;
  category: Category;
  publishedDate: string;
  questionNumber: number;
  maxScore: number;
  tags: Tag[];
}
