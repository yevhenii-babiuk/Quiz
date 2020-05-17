import {Component, OnInit} from '@angular/core';
import {Chat} from "../../core/models/chat";
import {Message} from "../../core/models/message";

@Component({
  selector: 'app-chat-area',
  templateUrl: './chat-area.component.html',
  styleUrls: ['./chat-area.component.css']
})
export class ChatAreaComponent implements OnInit {

  public messages: Array<Message> = [
    {
      id: 0, authorId: 11, creationDate: Date.now(), authorLogin: "first",
      messageText: "11-0We work directly with our designers and suppliers,\n" +
        "            and sell direct to you, which means quality, exclusive\n" +
        "            products, at a price anyone can afford. We work directly with our designers and suppliers,\n" +
        "            and sell direct to you, which means quality, exclusive\n" +
        "            products, at a price anyone can afford."
    } as Message,
    {
      id: 0, authorId: 11, creationDate: Date.now(), authorLogin: "first",
      messageText: "11-0We work di"
    } as Message,
    {
      id: 0, authorId: 11, creationDate: Date.now(), authorLogin: "first",
      messageText: "11-0We work di"
    } as Message,
    {
      id: 1, authorId: 12, creationDate: Date.now(), authorLogin: "second",
      messageText: "12-1We work directly with our designers and suppliers,\n" +
        "            and sell direct to you, which means quality, exclusive\n" +
        "            products, at a price anyone can afford."
    } as Message,
    {
      id: 2, authorId: 12, creationDate: Date.now(), authorLogin: "second",
      messageText: "12-2Test, which is a new approach to have"
    } as Message,
    {
      id: 3, authorId: 11, creationDate: Date.now(), authorLogin: "first",
      messageText: "11-3We work directly with our designers and suppliers,\n" +
        "            and sell direct to you, which means quality, exclusive\n" +
        "            products, at a price anyone can afford."
    } as Message,
    {
      id: 4, authorId: 12, creationDate: Date.now(), authorLogin: "second",
      messageText: "12-4We work directly with our designers and suppliers,\n" +
        "            and sell direct to you, which means quality, exclusive\n" +
        "            products, at a price anyone can afford."
    } as Message,
    {
      id: 5, authorId: 11, creationDate: Date.now(), authorLogin: "first",
      messageText: "11-5Apollo University, Delhi, India Test"
    } as Message,
    {
      id: 6, authorId: 12, creationDate: Date.now(), authorLogin: "second",
      messageText: "12-6We work directly with our designers and suppliers,\n" +
        "            and sell direct to you, which means quality, exclusive\n" +
        "            products, at a price anyone can afford."
    } as Message,
  ];

  constructor() {
  }

  ngOnInit(): void {
  }

}
