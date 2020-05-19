import {Question} from "./question";
import {Users} from "./gameResults";
import {Message} from "./message";

export class WebsocketEvent {
  players: String[];
  question: Question;
  gameResults: Users;
  message: Message;
  type:EventType;
}

export enum EventType {
  RESULTS = "RESULTS",
  QUESTION = "QUESTION",
  PLAYERS = "PLAYERS",
  MESSAGE = "MESSAGE"
}
