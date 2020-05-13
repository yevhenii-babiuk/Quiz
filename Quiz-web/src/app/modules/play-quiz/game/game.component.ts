import {Component, OnDestroy, OnInit} from '@angular/core';
import {Users} from "../../core/models/gameResults";
import {Question} from "../../core/models/question";
import {Answer} from "../../core/models/answer";
import {ActivatedRoute, Router} from "@angular/router";
import {SecurityService} from "../../core/services/security.service";
import {PlayGameService} from "../../core/services/play-game.service";
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import {UserDto} from "../../core/models/userDto";
import {socket} from "../../../../environments/environment.prod";
import {EventType, WebsocketEvent} from "../../core/models/websocketEvent";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit, OnDestroy {

  question: Question;
  receivedQuestion: Question;
  players: String[] = [];
  hostId: number;
  time: number;
  image: string;
  private stompClient;
  public gameResults: Users;
  eventType = EventType;
  receivedEvent: WebsocketEvent;
  s:any;

  public currentUser: UserDto;
  public gameId: string = this.route.snapshot.paramMap.get('gameId');
  isWaiting: boolean;

  constructor(private route: ActivatedRoute,
              private redirect: Router,
              private securityService: SecurityService,
              private playGameService: PlayGameService) {
    let userId = securityService.getCurrentId();
    if (!userId) {
      userId = localStorage.getItem("playerId");
      if (!userId) userId = 0;
    }
    this.initializeWebSocketConnection();

    this.playGameService.getGame(this.gameId).subscribe(
      game => {
        this.hostId = game.hostId;
        this.time = game.time;
        this.image = game.image;
        this.isWaiting = true;
        console.log(game);
      }, err => {
        console.log(err);
        this.redirect.navigate(['home']);
      });

    this.playGameService.sendJoinedUser(userId, this.gameId).subscribe(
      user => {
        this.currentUser = user;
        if (!securityService.getCurrentId() && !localStorage.getItem("playerId"))
          localStorage.setItem("playerId", String(this.currentUser.id));
        this.playGameService.getJoinedPlayers(this.gameId).subscribe(
          players => {
            if (players)
              this.players = players;
          }, err => {
            console.log(err);
            this.redirect.navigate(['home']);
          }
        );
        this.playGameService.getCurrentQuestion(this.gameId, this.currentUser.id).subscribe(
          question => {
            if (question) {
              this.isWaiting = false;
              this.gameResults = null;
              this.question = this.receivedEvent.question;
            }
          }, err => {
            console.log(err);
            this.redirect.navigate(['home']);
          }
        );

      }, err => {
        console.log(err);
        this.redirect.navigate(['home']);
      });


  }

  initializeWebSocketConnection() {
    let ws = new SockJS(socket);
    this.stompClient = Stomp.Stomp.over(ws);
    let that = this;

    this.stompClient.connect({}, function () {
      this.s = that.stompClient.subscribe("/game/" + that.gameId + "/play", async (message) => {
        if (message.body) {
          console.log(message.body)
          that.receivedEvent = JSON.parse(message.body);
          console.log(that.receivedEvent)
          console.log(that.receivedEvent.type)
          if (that.receivedEvent.type == that.eventType.PLAYERS) {
            that.players = that.receivedEvent.players;
          }
          if (that.receivedEvent.type == that.eventType.QUESTION) {
            console.log("it is question")
            that.isWaiting = false;
            that.gameResults = null;
            that.receivedQuestion = that.receivedEvent.question;
            if (that.question && that.receivedQuestion.id == that.question.id) that.question = that.receivedQuestion;
            else {
              if (that.question != null) localStorage.removeItem("endTime" + that.question.id);
              that.question = null;
              await sleep(500);
              that.question = that.receivedEvent.question;
              console.log(that.question)
            }
          }
          if (that.receivedEvent.type == that.eventType.RESULTS) {
            if (that.question != null) localStorage.removeItem("endTime" + that.question.id);
            that.isWaiting = false;
            that.question = null;
            that.gameResults = that.receivedEvent.gameResults;
          }
        }
      });

      /* that.stompClient.subscribe("/game/" + that.gameId + "/play/question", async (message) => {
         if (message.body) {
           let json = JSON.parse(message.body);
           that.isWaiting = false;
           that.gameResults = null;
           that.receivedQuestion = json;
           if (that.question && that.receivedQuestion.id == that.question.id) that.question = that.receivedQuestion;
           else {
             that.question = null;
             await sleep(1000);
             that.question = json;
           }
         }
       });

       that.stompClient.subscribe("/game/" + that.gameId + "/play/results", (message) => {
         if (message.body) {
           let json = JSON.parse(message.body);
           that.isWaiting = false;
           that.question = null;
           console.log("results " + json);
           that.gameResults = json;
           console.log(that.gameResults);
         }
       });*/
    }, this);

  }

  ngOnInit(): void {
  }

  sendAnswer(answer: Answer) {
    answer.gameId = this.gameId;
    answer.userId = this.currentUser.id;
    this.stompClient.send("/game/" + this.gameId + "/play", {}, JSON.stringify(answer));
  }

  startGame() {
    this.isWaiting = false;
    this.stompClient.send("/game/" + this.gameId + "/start", {}, "start game");
  }

  ngOnDestroy(): void {
    this.s.unsubscribe();
    this.stompClient.disconnect();
  }

}

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}
