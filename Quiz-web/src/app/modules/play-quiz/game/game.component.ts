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
import {QuestionType} from "../../core/models/questionType";
import {socket} from "../../../../environments/environment.prod";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit, OnDestroy {

  question: Question;
  players: String[] = [];
  hostId: number;
  time: number;
  image: string;
  //private serverUrl = 'http://localhost:8080/socket';
  private stompClient;
  public gameResults: Users;

  public currentUser: UserDto;
  public gameId: string = this.route.snapshot.paramMap.get('gameId');
  isWaiting: boolean = true;

  constructor(private route: ActivatedRoute,
              private redirect: Router,
              private securityService: SecurityService,
              private playGameService: PlayGameService) {
    let userId = securityService.getCurrentId();
    if (!userId) userId = 0;
    this.initializeWebSocketConnection();

    this.playGameService.getGame(this.gameId).subscribe(
      game => {
        this.hostId = game.hostId;
        this.time = game.time;
        this.image = game.image;
        console.log(game);
      }, err => {
        console.log(err);
        this.redirect.navigate(['home']);
      });

    this.playGameService.sendJoinedUser(userId, this.gameId).subscribe(
      user => {
        console.log("userJoined");
        this.currentUser = user;
        /*this.playGameService.getJoinedPlayers(this.gameId).subscribe(
          players => {
            if (players)
              this.players = players;
            else players.push(this.currentUser.login);
          }, err => {
            console.log(err);
            this.redirect.navigate(['home']);
          }
        );*/
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
      that.stompClient.subscribe("/game/" + that.gameId + "/players", (message) => {
        if (message.body) {
          let json = JSON.parse(message.body);
          console.log(json);
          that.players = json;
        }
      });

      that.stompClient.subscribe("/game/" + that.gameId + "/play/question", (message) => {
        if (message.body) {
          let json = JSON.parse(message.body);
          that.isWaiting = false;
          that.gameResults = null;
          that.question = json;
        }
      });

      that.stompClient.subscribe("/game/" + that.gameId + "/play/results", (message) => {
        if (message.body) {
          //  let json = JSON.parse(message.body);
          that.isWaiting = false;
          that.question = null;
          console.log("results " + that.question);
          that.gameResults = {
            users: [
              {
                login: "some login",
                id: 2,
                score: 20
              }, {
                login: "another login",
                id: 5,
                score: 35
              }, {
                login: "my login",
                id: 10,
                score: 30
              }
            ]
          }
          //that.gameResults.singleResult = json;
        }
      });
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
    this.stompClient.disconnect();
  }
}
