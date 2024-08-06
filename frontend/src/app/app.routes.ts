import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { StudentDashboardComponent } from './components/student-dashboard/student-dashboard.component';
import { InstructorDashboardComponent } from './components/instructor-dashboard/instructor-dashboard.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { RoleGuard } from './guards/role.guard';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'student-dashboard',
    component: StudentDashboardComponent,
    canActivate: [RoleGuard],
    data: { roles: ['STUDENT'] }, // Allow only users with STUDENT role
  },
  {
    path: 'instructor-dashboard',
    component: InstructorDashboardComponent,
    canActivate: [RoleGuard],
    data: { roles: ['INSTRUCTOR'] }, // Allow only users with INSTRUCTOR role
  },
  {
    path: 'admin-dashboard',
    component: AdminDashboardComponent,
    canActivate: [RoleGuard],
    data: { roles: ['ADMIN'] }, // Allow only users with ADMIN role
  },
  { path: '', redirectTo: '/home', pathMatch: 'full' }, // Default route
  { path: '**', redirectTo: '/home' }, // Catch-all route
];
