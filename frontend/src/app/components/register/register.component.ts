import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
})
export class RegisterComponent {
  name: string = '';
  surname: string = '';
  username: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  role: string = 'STUDENT'; // Ensure this is a valid role
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'Passwords do not match!';
      return;
    }

    this.authService
      .register(
        this.name,
        this.surname,
        this.username,
        this.email,
        this.password,
        this.role
      )
      .subscribe({
        next: (response) => {
          if (response.message) {
            console.log('Registration successful', response.message);
            this.successMessage = response.message;
            this.router.navigate(['/login']);
          } else if (response.error) {
            console.error('Registration error', response.error);
            this.errorMessage = response.error;
          }
        },
        error: (err) => {
          console.error('Registration error', err);
          this.errorMessage = 'Registration failed. Please try again.';
        },
      });
  }
}
