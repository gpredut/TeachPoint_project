import { Course } from './course.model';

export interface Lecture {
  id: number;
  title: string;
  content: string;
  course?: Course;
}
