import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        if (response.token) {
          console.log('Login successful', response.token);
          this.authService.saveToken(response.token);
          this.router.navigate(['/home']); // Redirect to home page
        } else if (response.error) {
          console.error('Login error', response.error);
          this.errorMessage = response.error;
        }
      },
      error: (err) => {
        console.error('Login error', err);
        this.errorMessage = 'Login failed. Please try again.';
      },
    });
  }
}
