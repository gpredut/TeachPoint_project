import { User } from './user.model';
import { Course } from './course.model';

export interface Instructor {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  user: User;
  courses: Course[];
}
