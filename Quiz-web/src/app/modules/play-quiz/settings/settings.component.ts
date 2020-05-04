import {Component, OnInit} from '@angular/core';
import {WebsocketService} from "../../core/services/websocket.service";
import {SettingsModel} from "./settings.model";
import {ActivatedRoute} from "@angular/router";
import {SecurityService} from "../../core/services/security.service";
import {Question} from "../../core/models/question";
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  settingsModel: SettingsModel;

  message: string = "";
  isInvalid: boolean = false;
  private serverUrl = 'http://localhost:8080/socket';
  private stompClient;
  gameId:string;
  constructor(private route: ActivatedRoute,
              private securityService: SecurityService) {
    this.settingsModel = new SettingsModel();
    this.gameId = this.route.snapshot.paramMap.get('gameId');
    this.initializeWebSocketConnection();
  }

  question: Question;

  initializeWebSocketConnection() {
    let ws = new SockJS(this.serverUrl);

    this.stompClient = Stomp.Stomp.over(ws);
    let gameId = this.route.snapshot.paramMap.get('gameId');
    let user_id = this.securityService.getCurrentId();
    let that = this;
    console.log(gameId);
    this.stompClient.connect({}, function () {
      that.stompClient.send("/game/" + user_id + "/connect", {}, user_id);
      that.stompClient.subscribe("/game/" + gameId + "/user/" + user_id, (message) => {
        if (message.body) {
          let json = JSON.parse(message.body);
          if (json.command == 'question') {
            console.log("it is question");
            this.question = json.question;
            console.log(this.question);
          } else if (json.command == 'answer') {
            console.log("it is answer");
            console.log(message.body);
          }
        }
      });
    }, this);
  }


  sendMessage(message) {
    this.stompClient.send("/game/"+this.gameId+"/play", {}, message);
  }


  ngOnInit(): void {
  }

  isValid(): boolean {
    if (this.settingsModel.isTimeForAnswerChosen && this.settingsModel.timeForAnswer
      && this.settingsModel.timeForAnswer < 61 && this.settingsModel.timeForAnswer > 4) {
      return true;
    } else {
      this.isInvalid = true;
      this.message = "Час на відповідь маєбути заповненим та знаходитись в межах від 5 до 60"
    }
  }

  submit() {
    this.sendMessage("message");
    if (this.isValid()) {
      this.isInvalid = false;
    }
  }
}
