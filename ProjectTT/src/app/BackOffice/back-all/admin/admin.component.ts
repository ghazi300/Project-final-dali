import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../auth.service'; // Import AuthService

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  constructor(public authService: AuthService) { } // Inject AuthService into the constructor

  ngOnInit(): void {
  }

}
