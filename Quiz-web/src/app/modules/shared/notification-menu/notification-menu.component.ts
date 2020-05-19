import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AuthenticationService} from "../../core/services/authentication.service";

import {socket} from "../../../../environments/environment.prod";
import * as Stomp from "@stomp/stompjs";
import * as SockJS from 'sockjs-client';
import {WebsocketEvent} from "../../core/models/websocketEvent";
import {SecurityService} from "../../core/services/security.service";
import {NotificationService} from "../../core/services/notification.service";
import {NotificationDto} from "../../core/models/notificationDto";
import {NotificationFilters} from "../../core/models/notificationFilters";
import {MessageMenuComponent} from "../message-menu/message-menu.component";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-notification-menu',
  templateUrl: './notification-menu.component.html',
  styleUrls: ['./notification-menu.component.css']
})

export class NotificationMenuComponent implements OnInit {

  public stompClient;
  notifications = [];
  unviewedNotificationCount = 0;
  receivedEvent: WebsocketEvent;
  userId: number;
  notificationFilters: NotificationFilters;

  @Input()
  public showNotification: boolean;
  showSettings: boolean;

  @Output() isNotification = new EventEmitter<boolean>();
  changeShowMenu(isShowMenu:boolean) {
    this.isNotification.emit(isShowMenu);
  }
  constructor(public securityService: SecurityService,
              public authService: AuthenticationService,
              private notificationService: NotificationService) {
  }

  initializeWebSocketConnection() {
    let ws = new SockJS(socket);
    this.stompClient = Stomp.Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, function () {
      that.stompClient.subscribe("/notification" + that.securityService.getCurrentId(), async (message) => {
        if (message.body) {
          that.receivedEvent = JSON.parse(message.body);
          if(!that.receivedEvent.notification.isMessage) {
            that.notifications.push(that.receivedEvent.notification);
            that.unviewedNotificationCount++;
          }
        }
      });
    }, this);
  }

  setViewed(notification: NotificationDto) {
    if (!notification.isViewed) {
      notification.isViewed = true;
      this.notificationService.updateNotificationView(notification).subscribe();
      if (this.unviewedNotificationCount > 0) {
        this.unviewedNotificationCount--;
      }
    }
  }

  deleteNotification(linkAction: string, id: number) {
    this.notifications.splice(this.notifications.indexOf(linkAction));
    this.notificationService.deleteNotificationById(id).subscribe();
  }

  deleteAllNotification() {
    this.notifications = [];
    this.unviewedNotificationCount = 0;
    this.notificationService.deleteAllByUserId(this.securityService.getCurrentId()).subscribe();
  }

  openSettings(state: boolean) {
    this.showSettings = state;
  }

  openNotification(state: boolean) {
      this.showNotification = state;
  }

  getNotificationFromDB() {
    this.notificationService.getNotificationsByUserId(this.securityService.getCurrentId()).subscribe(
      notifications => {
        console.log(notifications);
        this.notifications = this.notifications.concat(notifications);
        for (let i = 0; i < this.notifications.length; i++) {
          if (!this.notifications[i].isViewed) {
            this.unviewedNotificationCount++;
          }
        }
      },
      err => {
        console.log(err);
      });
  }

  updateSettings(notificationFilters: NotificationFilters) {
    this.notificationService.updateSettings(notificationFilters).subscribe();
    console.log(notificationFilters);
  }

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.initializeWebSocketConnection();
      this.getNotificationFromDB();
      this.notificationService.getSettingsByUserId(this.securityService.getCurrentId()).subscribe(
        notificationFilters => {
          this.notificationFilters = notificationFilters;
          console.log("notification settings: " + this.notificationFilters.newAnnouncement);
        },
        err => {
          console.log(err);
        });
    }
  }
}
