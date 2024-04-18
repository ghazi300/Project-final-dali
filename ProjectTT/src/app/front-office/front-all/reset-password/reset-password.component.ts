import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent {
  password: string = '';
  confirmPassword: string = '';
  token: string = '';

  constructor(private http: HttpClient, private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
    });
  }

  resetPassword() {
    if (this.password !== this.confirmPassword) {
      console.error('Passwords do not match');
      // Handle error - passwords do not match
      return;
    }

    this.http.post('http://localhost:8089/pi/api/auth/reset-password', { password: this.password, token: this.token }).subscribe(
      () => {
        console.log('Password reset successfully');
        // Optionally, redirect to login page
      },
      error => {
        console.error('Error resetting password:', error);
        // Handle error
      }
    );
  }
}
