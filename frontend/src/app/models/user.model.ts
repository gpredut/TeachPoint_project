import { Instructor } from './instructor.model';
import { Role } from './role.model';

export interface User {
  id: number;
  name: string;
  surname: string;
  email: string;
  username: string;
  password: string;
  instructor?: Instructor;
  roles: Role[];
}
