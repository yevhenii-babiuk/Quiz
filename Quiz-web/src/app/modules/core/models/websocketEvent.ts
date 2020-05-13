import {Question} from "./question";
import {Users} from "./gameResults";
import {NotificationDto} from "./notificationDto";

export class WebsocketEvent {
  players: String[];
  question: Question;
  gameResults: Users;
  type:EventType;
  notification: NotificationDto[];
}

export enum EventType {
  RESULTS = "RESULTS",
  QUESTION = "QUESTION",
  PLAYERS = "PLAYERS",
  NOTIFICATION = "NOTIFICATION"
}
