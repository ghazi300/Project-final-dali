import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  email: string = '';

  constructor(private http: HttpClient) { }

  forgotPassword() {
    this.http.post('http://localhost:8080/pi/api/auth/forgot-password', { email: this.email }).subscribe(
      () => {
        console.log('Password reset email sent successfully');
        // Optionally, redirect to a confirmation page
      },
      error => {
        console.error('Error sending password reset email:', error);
        // Handle error
      }
    );
  }
}
