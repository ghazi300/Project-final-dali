import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../../auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  userProfile: any;
  error: string = '';

  constructor(private http: HttpClient, private authService: AuthService) { }

  ngOnInit(): void {
    this.fetchUserProfile();
  }

  fetchUserProfile() {
    this.http.get<any>('http://localhost:8089/pi/api/auth/profile')
      .subscribe(
        userProfile => {
          this.userProfile = userProfile;
        },
        error => {
          if (error.status === 403) {
            this.error = 'You do not have permission to view the profile.';
          } else {
            this.error = 'An error occurred while fetching the profile.';
          }
        }
      );
  }
}
