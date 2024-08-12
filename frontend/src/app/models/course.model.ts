import { Instructor } from './instructor.model';
import { Lecture } from './lecture.model';

export interface Course {
  id: number;
  title: string;
  description: string;
  instructor: Instructor;
  lectures: Lecture[];
}
