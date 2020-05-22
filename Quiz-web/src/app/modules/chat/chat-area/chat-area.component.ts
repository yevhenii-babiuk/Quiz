import {AfterViewChecked, Component, ElementRef, HostListener, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Chat} from "../../core/models/chat";
import {Message} from "../../core/models/message";
import {SecurityService} from "../../core/services/security.service";
import {ChatService} from "../../core/services/chat.service";
import {ActivatedRoute, Router} from "@angular/router";

import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import {socket} from "../../../../environments/environment.prod";
import {EventType, WebsocketEvent} from "../../core/models/websocketEvent";
import {ProfileService} from "../../core/services/profile.service";
import {User} from "../../core/models/user";

@Component({
  selector: 'app-chat-area',
  templateUrl: './chat-area.component.html',
  styleUrls: ['./chat-area.component.css']
})
export class ChatAreaComponent implements OnInit, OnDestroy {
  isWaiting: boolean = false;
  isOver: boolean = false;

  id: number;
  chat: Chat = new Chat();
  public messages: Array<Message> = new Array<Message>();
  message: Message = new Message();

  public invitation: boolean = false;
  public isLoaded: boolean = false;
  selectedFriend: User;
  friends: User[] = [];

  // @ViewChild('scrollMe') private myScrollContainer: ElementRef;

  private stompClient;
  eventType = EventType;
  receivedEvent: WebsocketEvent;
  s: any;

  constructor(private chatService: ChatService,
              private securityService: SecurityService,
              private route: ActivatedRoute,
              private router: Router,
              private profileService: ProfileService) {

    this.id = this.securityService.getCurrentId();
    this.chat.id = +this.route.snapshot.paramMap.get('chatId');

    this.chatService.getChat(this.chat.id)
      .subscribe(
        chat => {
          this.chat = chat;
        },
        err => {
          console.log(err);
        }
      );

    this.getNew();

    this.initializeWebSocketConnection();
  }

  ngOnInit(): void {
  }

  // scrollToBottom(): void {
  //   try {
  //     this.myScrollContainer.nativeElement.scrollTop = this.myScrollContainer.nativeElement.scrollHeight;
  //   } catch(err) {
  //     console.log(err);
  //   }
  // }

  initializeWebSocketConnection() {
    let ws = new SockJS(socket);
    this.stompClient = Stomp.Stomp.over(ws);
    let that = this;

    this.stompClient.connect({}, function () {
      that.s = that.stompClient.subscribe("/chat/" + that.chat.id, (message) => {
        if (message.body) {
          that.receivedEvent = JSON.parse(message.body);
          if (that.receivedEvent.type == that.eventType.MESSAGE) {
            that.messages.push(that.receivedEvent.message);
          }
        }
      });

    }, this);
  }

  sendMessage() {
    if (this.message.messageText.length == 0) {
      return;
    }

    this.message.chatId = this.chat.id;
    this.message.authorId = this.id;

    this.stompClient.send("/chat/" + this.chat.id, {}, JSON.stringify(this.message));
    this.message.messageText = '';
    // this.scrollToBottom();
  }

  ngOnDestroy(): void {
    this.s.unsubscribe();
    this.stompClient.disconnect();
  }

  updateChat() {
    this.chatService.updateChat(this.chat)
      .subscribe(
        err => {
          console.log(err);
        }
      );
  }

  leaveChat() {
    this.chatService.leaveChat(this.id, this.chat.id)
      .subscribe(
        data => {
          this.router.navigate([`chats`]).then();
        },
        error => {
          console.log(error)
        }
      )
  }

  loadFriends() {
    if (!this.isLoaded) {
      this.profileService.getFriends(this.id)
        .subscribe(
          friends => {
            this.friends = friends;
            this.isLoaded = true;
          },
          err => {
            console.log(err);
          })
    }
  }

  inviteFriend() {
    console.log(this.selectedFriend.id)
    if (this.selectedFriend) {
      this.chatService.inviteToChat(this.selectedFriend, this.chat.id)
        .subscribe(
          data => {
            this.invitation = false;
            this.chat.users.push(this.selectedFriend);
          },
          err => {
            console.log(err);
          }
        );
    }
  }

  getNew(): void {
    if (this.isWaiting) {
      return;
    }
    this.isWaiting = true;

    this.chatService.getMessages(this.chat.id, this.messages.length)
      .subscribe(
        messages => {
          if (messages.length < 10){
            this.isOver = true;
          }

          if (messages.length == 0) {
            this.isWaiting = false;
            return;
          }
          this.isWaiting = false;
          this.messages = messages.reverse().concat(this.messages);
        },
        err => {
          console.log(err);
        })
  }

  @HostListener('scroll', ['$event'])
  scrollHandler(event) {
    if (event.srcElement.scrollHeight - event.srcElement.scrollTop - event.srcElement.clientHeight > (event.srcElement.scrollTopMax - 20)) {
      if (!this.isOver){
        this.getNew();
      }
    }
  }

  onChange(value: string) {
    this.selectedFriend=this.friends.filter(value1 => value1.login==value)[0];
  }
}
