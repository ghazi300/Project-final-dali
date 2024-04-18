import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit {
  users: any[] = [];
  error: string = '';

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchUsers();
  }

  fetchUsers() {
    this.http.get<any[]>('http://localhost:8089/pi/api/auth/admin/users')
      .subscribe(
        users => {
          this.users = users;
        },
        error => {
          if (error.status === 403) {
            this.error = 'You do not have permission to view users.';
          } else {
            this.error = 'An error occurred while fetching users.';
          }
        }
      );
  }

  deleteUser(userId: number) {
    this.http.delete(`http://localhost:8089/api/auth/admin/users/${userId}`)
      .subscribe(
        () => {
          // Refresh the user list after deletion
          this.fetchUsers();
        },
        error => {
          console.error('Error deleting user:', error);
          // Handle error if deletion fails
        }
      );
  }
  banUser(userId: number) {
    this.http.post(`http://localhost:8089/api/auth/admin/users/${userId}/ban`, null)
      .subscribe(
        () => {
          // Refresh the user list after banning the user
          this.fetchUsers();
        },
        error => {
          console.error('Error banning user:', error);
          // Handle error if banning fails
        }
      );
  }

}
