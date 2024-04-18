// email-confirmation.component.ts

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-email-confirmation',
  templateUrl: './email-confirmation.component.html',
  styleUrls: ['./email-confirmation.component.css']
})
export class EmailConfirmationComponent implements OnInit {

  constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient) { }

  ngOnInit(): void {
    // Get the token from the URL
    const token = this.route.snapshot.queryParams['token'];
    // Send a request to your backend to confirm the email
    this.http.get(`http://localhost:8089/pi/api/auth/confirm?token=${token}`).subscribe(
      () => {
        // Handle successful confirmation
        // Redirect to the login page
        this.router.navigate(['/login']);
      },
      (error) => {
        // Handle error, maybe display a message to the user
        console.error(error);
      }
    );
  }
}
