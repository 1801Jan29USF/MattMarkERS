import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Router } from '@angular/router';
import { SessionService } from '../../services/session.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  credential = {
    username: '',
    password: ''
  };

  constructor(private client: HttpClient, private router: Router) { }

  ngOnInit() {
  }

  login() {
    this.client.post(`${environment.context}login`, this.credential,
    {withCredentials: true})
      .subscribe(
        (succ: any) => {
          SessionService.loggedIn = true;
          if (succ.role === 1) {
            this.router.navigateByUrl('/employee');
          }else if (succ.role === 2) {
            this.router.navigateByUrl('/manager');
          }
        },
        (err) => {
          alert('failed to log in');
        }
      );
  }

}
