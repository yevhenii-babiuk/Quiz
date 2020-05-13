export class Answer {
  userId: number;
  gameId: string;
  questionId: number;

  options: number[] = [];
  fullAnswer: string = '';
  trueFalse: boolean;
  sequence: Map<number, number> = new Map();

  time: number;
}
