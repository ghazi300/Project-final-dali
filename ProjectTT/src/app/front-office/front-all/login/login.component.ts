import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'; // Import Router
import { AuthService } from '../../../auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  submitted = false;
  error = '';

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router // Inject Router
  ) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      usernameOrEmail: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  // Convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.submitted = true;

    // Stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }

    this.authService.login(this.loginForm.value)
      .subscribe(
        response => {
          // Handle successful login
          console.log('Login successful', response);

          // Check user role and navigate accordingly
          if (this.authService.isAdmin()) {
            this.router.navigate(['/admin']); // Navigate to admin component
          } else if (this.authService.isUser()) {
            this.router.navigate(['/user']); // Navigate to user component
          } else {
            console.error('Unknown user role');
            this.error = 'Unknown user role';
          }
        },
        error => {
          // Handle login error
          console.error('Login error', error);
          this.error = 'Invalid username or password.';
        }
      );
  }
}
