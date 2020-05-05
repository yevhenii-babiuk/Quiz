import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {SecurityService} from "../../../core/services/security.service";
import {PlayGameService} from "../../../core/services/play-game.service";
import {User} from "../../../core/models/user";
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-waiting-room',
  templateUrl: './waiting-room.component.html',
  styleUrls: ['./waiting-room.component.css']
})
export class WaitingRoomComponent implements OnInit {
  private serverUrl = 'http://localhost:8080/socket';
  private stompClient;
  players: String[] = [];

  @Output()
  public startGame: EventEmitter<any> = new EventEmitter();

  @Input()
  currentUser: User;

  hostId: number;
  gameId: string = this.route.snapshot.paramMap.get('gameId');

  constructor(private route: ActivatedRoute,
              private redirect: Router,
              private securityService: SecurityService,
              private playGameService: PlayGameService) {
    this.initializeWebSocketConnection();
    this.playGameService.getJoinedPlayers(this.gameId).subscribe(
      players => {
        if (players)
          this.players = players;
        else players.push(this.currentUser.login);
      }, err => {
        console.log(err);
        this.redirect.navigate(['home']);
      }
    );
  }

  initializeWebSocketConnection() {
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, function () {
      that.stompClient.send("/game/" + that.gameId + "/connect", {}, that.currentUser.id);
      that.stompClient.subscribe("/game/" + that.gameId + "/players", (message) => {
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

  copy(divElement) {
    divElement.select();
    document.execCommand('copy');
    divElement.setSelectionRange(0, 0);
  }
}
