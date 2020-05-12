import {Image} from "./image";
import {Imaged} from "./imaged";
import {Role} from "./role";

export class User implements Imaged {
  id: number;
  firstName: string;
  secondName: string;
  login: string;
  mail: string;
  password: string;
  profile: string;
  score: number;
  role: Role;
  imageId: number = -1;
  image: Image = new Image();
}
