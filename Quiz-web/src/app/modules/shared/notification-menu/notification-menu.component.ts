import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../core/services/authentication.service";

import {socket} from "../../../../environments/environment.prod";
import * as Stomp from "@stomp/stompjs";
import * as SockJS from 'sockjs-client';
import {WebsocketEvent} from "../../core/models/websocketEvent";

@Component({
  selector: 'app-notification-menu',
  templateUrl: './notification-menu.component.html',
  styleUrls: ['./notification-menu.component.css']
})

export class NotificationMenuComponent implements OnInit {

  public stompClient;
  public notifications:  String[] = [];
  notificationCount = 2;
  receivedEvent: WebsocketEvent;

  constructor(public authService: AuthenticationService) {
    }

  initializeWebSocketConnection() {
    let ws = new SockJS(socket);
    this.stompClient = Stomp.Stomp.over(ws);
    let that = this;

    this.stompClient.connect({}, function () {
      that.stompClient.subscribe("/notification", async (message) => {
        if (message.body) {
          console.log("message.body: " + message.body);
          that.receivedEvent = JSON.parse(message.body);
          console.log("received notification: " + that.receivedEvent);
          console.log(that.receivedEvent.notification);
          that.notifications.push(message.body);
          that.notificationCount++;
        }
      });
    } ,this);
  }

  showNotification: boolean;
  showSettings: boolean;

  openSettings(state: boolean) {
    this.showSettings = state;
  }
  openNotification(state: boolean) {
    this.showNotification = state;
  }
  ngOnInit(): void {
    this.initializeWebSocketConnection();
  }

  disconnect() {
    this.stompClient.disconnect();
    console.log("Disconnected");
  }

  sendMessage(message : string) {
    console.log(message);
    this.stompClient.send("/notification/notify" , {}, message);
  }
}
