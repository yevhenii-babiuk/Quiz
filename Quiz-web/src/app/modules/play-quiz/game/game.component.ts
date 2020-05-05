import {Component, Input, OnInit} from '@angular/core';
import {GameResults} from "../../core/models/gameResults";
import {Question} from "../../core/models/question";
import {Answer} from "../../core/models/answer";
import {ActivatedRoute, Router} from "@angular/router";
import {SecurityService} from "../../core/services/security.service";
import {PlayGameService} from "../../core/services/play-game.service";
import {Game} from "../../core/models/game";
import {User} from "../../core/models/user";
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  players: String[] = [];
  private serverUrl = 'http://localhost:8080/socket';
  private stompClient;
  public question: Question;
  public gameResults: GameResults;

  public currentUser:User;
  isWaiting: boolean = true;

  constructor(private route: ActivatedRoute,
              private redirect: Router,
              private securityService: SecurityService,
              private playGameService: PlayGameService) {
    console.log("parCon");
    let userId = securityService.getCurrentId();
    this.initializeWebSocketConnection();

    this.playGameService.sendJoinedUser(userId, this.route.snapshot.paramMap.get('gameId')).subscribe(
      user => {
        console.log("userJoined");
        this.currentUser = user;
        this.playGameService.getJoinedPlayers(this.route.snapshot.paramMap.get('gameId')).subscribe(
          players => {
            if (players)
              this.players = players;
            else players.push(this.currentUser.login);
          }, err => {
            console.log(err);
            this.redirect.navigate(['home']);
          }
        );
     //   this.players.push(this.currentUser.login);
      }, err => {
        console.log(err);
        this.redirect.navigate(['home']);
      });


  }

  initializeWebSocketConnection() {
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.Stomp.over(ws);
    let that = this;
    console.log("socket"+this.currentUser);
    this.stompClient.connect({}, function () {
      that.stompClient.subscribe("/game/" + that.route.snapshot.paramMap.get('gameId') + "/players", (message) => {
        console.log("received");
        if (message.body) {
          let json = JSON.parse(message.body);
          console.log(json);
          //if (json.command == 'question') {
          //  console.log("it is question");
          that.players = json;
          console.log(that.players);
          //  } else if (json.command == 'answer') {
          //    console.log("it is answer");
          //    console.log(message.body);
          //  }
        }
      });
    }, this);
  }

  ngOnInit(): void {
  }

  processAnswer(answer: Answer) {
    console.log(answer);
  }

  startGame() {

  }
}
