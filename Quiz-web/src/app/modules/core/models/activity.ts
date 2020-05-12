import {Image} from "./image";
import {Imaged} from "./imaged";
import {FriendActivityType} from "./friendActivityType";

export class Activity{ //implements Imaged
  friendId:number
  friendLogin:string;
  activityId:number;
  activityContent:string;
  activityDate:Date;
  type:string;
  //imageId: number = -1;
  //image: Image;
}
