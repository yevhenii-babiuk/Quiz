import {Question} from "./question";
import {Users} from "./gameResults";

export class WebsocketEvent {
  players: String[];
  question: Question;
  gameResults: Users;
  type:EventType;
}

export enum EventType {
  RESULTS = "RESULTS",
  QUESTION = "QUESTION",
  PLAYERS = "PLAYERS"
}
