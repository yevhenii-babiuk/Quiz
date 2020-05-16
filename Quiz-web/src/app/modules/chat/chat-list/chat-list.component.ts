import { Component, OnInit } from '@angular/core';
import {Chat} from "../../core/models/chat";

@Component({
  selector: 'app-chat-list',
  templateUrl: './chat-list.component.html',
  styleUrls: ['./chat-list.component.css']
})
export class ChatListComponent implements OnInit {

  public chats:Array<Chat> = [
    {id: 1, name: 'Test Chat1', creationDate: Date.now()} as Chat,
    {id: 2, name: 'Test Chat2', creationDate: Date.now()} as Chat,
    {id: 3, name: 'Test Chat3', creationDate: Date.now()} as Chat,
    {id: 4, name: 'Test Chat4', creationDate: Date.now()} as Chat,
    {id: 5, name: 'Test Chat5', creationDate: Date.now()} as Chat,
    {id: 6, name: 'Test Chat6', creationDate: Date.now()} as Chat,
    {id: 7, name: 'Test Chat7', creationDate: Date.now()} as Chat,
  ];

  constructor() {
    // this.chats = [];
  }

  ngOnInit(): void {
  }

}
