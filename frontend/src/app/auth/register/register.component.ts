import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  username = '';
  password = '';
  error = '';
  success = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  register() {
    if (!this.username || !this.password) {
      this.error = 'Username and password are required';
      return;
    }

    this.authService.register(this.username, this.password).subscribe({
      next: () => {
        this.success = 'Registration successful! Please login.';
        this.error = '';

        
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 1000);
      },
      error: (err: any) => {
        console.error(err);
        this.error = 'User already exists or server error';
      }
    });
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }
}
