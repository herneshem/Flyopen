import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {

  constructor(private authService: AuthService, private router: Router) {}
  email = "";
  password = "";

  login() {
    this.authService.login(this.email, this.password).subscribe(
      {
        next: () => {
          // Handle successful login, e.g., store the token, navigate to another page, etc.
          this.router.navigate(['/main-layout']); // Navigate to the main layout after successful login
        },
        error: (error) => {
          // Handle login error, e.g., show an error message to the user
          console.error('Login failed:', error);
        }
      }
    );
      }  
   
}
