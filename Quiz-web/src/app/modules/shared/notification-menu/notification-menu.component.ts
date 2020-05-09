import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../core/services/authentication.service";
import * as Stomp from "@stomp/stompjs";
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-notification-menu',
  templateUrl: './notification-menu.component.html',
  styleUrls: ['./notification-menu.component.css']
})
export class NotificationMenuComponent implements OnInit {

  private serverUrl = 'http://localhost:8080/socket';
  private stompClient;
 notifications:  String[] = [];
  notificationCount = 3;

  constructor(public authService: AuthenticationService) {

    }


  initializeWebSocketConnection() {
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.Stomp.over(ws);
    let that = this;

    this.stompClient.connect({}, function () {
      that.stompClient.subscribe("/game", (message) => {
        if (message.body) {
          let json = JSON.parse(message.body);
          console.log(json);

        }
      });
    } ,this);
  }


    ngOnInit(): void {
      this.initializeWebSocketConnection();
  }

   disconnect() {
    this.stompClient.disconnect();
    console.log("Disconnected");
  }

  sendMessage() {
    this.stompClient.send("/notification/notify" , {}, "message");
  }

}
