import {Image} from "./image";
import {Imaged} from "./imaged";

export class Activity implements Imaged{
  id: number;
  friendId:number
  friendLogin:string;
  achievementName:string;
  quizId:number;
  quizName:string;
  quizCategoryName:string;
  markedFavourite:boolean;
  friendOfFriendId:number;
  friendOfFriendLogin:string;
  activityDate: Date;
  imageId: number = -1;
  image: Image;
}
